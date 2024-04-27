package com.a18delsol.grattorepo.request;

import com.a18delsol.grattorepo.model.ModelUser;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RequestUserSignIn extends ModelUser {
    @NotBlank(message = "User mail cannot be empty.")
    private String userMail;

    @NotBlank(message = "User pass cannot be empty.")
    private String userPass;
}
