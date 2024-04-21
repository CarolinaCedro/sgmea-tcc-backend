package tcc.sgmeabackend.model.dtos;

import tcc.sgmeabackend.model.enums.UserRole;

import javax.management.relation.Role;

public record RegisterDto(String nome, String senha, UserRole role) {
}
