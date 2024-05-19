package tcc.sgmeabackend.model.dtos;

import tcc.sgmeabackend.model.Perfil;
import tcc.sgmeabackend.model.enums.UserRole;

public record RegisterDto(String nome, String senha, String cpf, String email, UserRole role, Perfil perfil) {
}
