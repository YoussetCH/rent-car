package com.yousset.rentcar.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Validated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogin {

    @NotBlank
    @Email
    private String user;
    @NotBlank
    private String password;
}
