package pl.joboffers.domain.loginandregister.loginandregisterdto;

import javax.validation.constraints.NotBlank;

public record RegisterUserDto(
        @NotBlank(message = "{username.not.blank")
        String username,
        @NotBlank(message = "{password.not.blank}")
        String password) {
}
