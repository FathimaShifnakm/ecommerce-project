package com.revshop.userService.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
	private String username;
    private String email;
    private String password;
}
