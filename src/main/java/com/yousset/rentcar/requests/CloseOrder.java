package com.yousset.rentcar.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yousset.rentcar.RentCarStartApplication;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author david
 */
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CloseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @DecimalMin("0.0")
    private Double recharge;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtGiveUp = new Date();
    @NotBlank
    @Size(max = 200)
    private String placeGiveUp;
}
