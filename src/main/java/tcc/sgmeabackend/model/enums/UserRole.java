package tcc.sgmeabackend.model.enums;


public enum UserRole {
    ADMIN("admin"),
    GESTOR("gestor"),
    FUNCIONARIO("funcionario"),
    TECNICO("tecnico");



    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
