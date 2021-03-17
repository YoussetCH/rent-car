package com.yousset.rentcar.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yousset.rentcar.RentCarStartApplication;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author david
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CreateReserve implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtFrom;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtTo;
    @NotBlank
    private String licensePlate;

}
