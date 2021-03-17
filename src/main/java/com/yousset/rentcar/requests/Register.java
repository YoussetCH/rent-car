package com.yousset.rentcar.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author david
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @NotBlank
    @Size(max = 200)
    private String email;
    @NotBlank
    @Size(max = 50)
    private String password;
    private String role = "USER";
    private Integer status = 1;

}
