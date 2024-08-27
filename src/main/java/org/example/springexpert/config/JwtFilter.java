package org.example.springexpert.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springexpert.domain.user.enums.UserRole;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();

        // 회원가입과 로그인은 인증 처리에서 제외
        if (url.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String bearerJwt = httpRequest.getHeader("Authorization");

        if (bearerJwt == null || !bearerJwt.startsWith("Bearer ")) {
            // 토큰이 없는 경우 400을 반환
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "JWT 토큰이 필요합니다.");
            return;
        }

        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            // JWT 유효성 검사와 claims 추출
            Claims claims = jwtUtil.extractClaims(jwt);

            // 관리자 권한이 필요한 경로 설정
            List<String> allowedMethods = Arrays.asList("PUT", "DELETE");
            String pathPrefix = "/todos/";

            // 경로와 메서드를 체크하고, 관리자 권한이 필요한 경로인지 확인
            if (checkMethodPath(method, url, allowedMethods, pathPrefix)) {
                String userRole = claims.get("userRole", String.class);
                if (!UserRole.ADMIN.name().equals(userRole)) {
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 필요합니다."); // 관리자 권한이 없는 경우 403을 반환
                    return;
                }
            }

            chain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않는 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 JWT 토큰입니다.");
        } catch (Exception e) {
            log.error("JWT 토큰 검증 중 오류가 발생했습니다.", e);
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰 검증 중 오류가 발생했습니다.");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean checkMethodPath(String method, String url, List<String> allowedMethods, String pathPrefix) {
        if (!checkHttpMethod(method, allowedMethods)) {
            return false;
        }

        if (!checkPathUrl(url, pathPrefix)) {
            return false;
        }

        String idPart = extractIdFromPath(url, pathPrefix);
        return isNumeric(idPart);
    }

    private boolean checkHttpMethod(String method, List<String> allowedMethods) {
        return allowedMethods.stream().anyMatch(allowedMethod -> allowedMethod.equalsIgnoreCase(method));
    }

    private boolean checkPathUrl(String url, String pathPrefix) {
        return url.startsWith(pathPrefix);
    }

    private String extractIdFromPath(String url, String pathPrefix) {
        return url.substring(pathPrefix.length());
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
