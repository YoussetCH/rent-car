package com.yousset.rentcar.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author david
 */
@Entity
@Table(name = "CARS")
@NamedQueries({})
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Cars implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "LICENSE_PLATE")
    private String licensePlate;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "BRAND")
    private String brand;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "MODEL")
    private String model;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "TYPE")
    private String type;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRICE", columnDefinition = "NUMERIC")
    private Double price;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "COLOR")
    private String color;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "KM")
    private Integer km;
    @JoinColumn(name = "ID_GAMA", referencedColumnName = "ID_GAMA")
    @ManyToOne(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    private Gama gama;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "STATUS")
    private Integer status;

    public Cars() {
    }

    public Cars(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Cars(String licensePlate, Integer status, Double price) {
        this.licensePlate = licensePlate;
        this.status = status;
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licensePlate != null ? licensePlate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cars)) {
            return false;
        }
        Cars other = (Cars) object;
        return !((this.licensePlate == null && other.licensePlate != null) || (this.licensePlate != null && !this.licensePlate.equals(other.licensePlate)));
    }

    @Override
    public String toString() {
        return "com.yousset.rentcar.models.Cars[ licensePlate=" + licensePlate + " ]";
    }

}
