package com.bambu.backend.dtos;

import com.bambu.backend.models.UserRole;

public record RegisterDTO(String login, String password, UserRole role){
}