package com.skysoft.friends.web.common.request;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotNull
    private Integer confirmationCode;
}
