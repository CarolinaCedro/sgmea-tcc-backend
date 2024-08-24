package tcc.sgmeabackend.model.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ResetPasswordRequest {
    private String token;
    private String newPassword;


}
