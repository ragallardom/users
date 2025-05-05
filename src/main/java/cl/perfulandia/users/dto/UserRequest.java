package cl.perfulandia.users.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @NotBlank private String role;
    @NotBlank private String email;

}
