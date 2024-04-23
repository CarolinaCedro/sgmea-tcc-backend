package tcc.sgmeabackend.model.enums;

import lombok.Getter;

@Getter
public enum UserRole {

	ADMIN(0, "ROLE_ADMIN"),
	GESTOR(1, "ROLE_GESTOR"),
	FUNCIONARIO(2, "ROLE_FUNCIONARIO"),
	TECNICO(3, "ROLE_TECNICO");

	private Integer codigo;
	private String descricao;

	private UserRole(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

    public static UserRole toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(UserRole x : UserRole.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Perfil inválido");
	}
}
