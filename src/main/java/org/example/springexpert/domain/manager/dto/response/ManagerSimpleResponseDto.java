package org.example.springexpert.domain.manager.dto.response;

import lombok.Getter;

@Getter
public class ManagerSimpleResponseDto {

        private final Long id;
        private final String username;
        private final String email;

        public ManagerSimpleResponseDto(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }
}
