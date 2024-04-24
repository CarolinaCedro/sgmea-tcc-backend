package tcc.sgmeabackend.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ADMIN"),
    GESTOR("GESTOR"),
    FUNCIONARIO("FUNCIONARIO"),
    TECNICO("TECNICO");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }


}
