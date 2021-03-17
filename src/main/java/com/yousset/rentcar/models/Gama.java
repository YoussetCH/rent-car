package com.yousset.rentcar.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author david
 */
@Entity
@Table(name = "GAMA")
@NamedQueries({})
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Gama implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "gama_id_gama_seq", sequenceName = "gama_id_gama_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gama_id_gama_seq")
    @Column(name = "ID_GAMA")
    private Long idGama;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "PRICE", columnDefinition = "NUMERIC")
    private Double price;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "STATUS")
    private Integer status;

    public Gama() {
    }

    public Gama(Long idGama) {
        this.idGama = idGama;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGama != null ? idGama.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gama)) {
            return false;
        }
        Gama other = (Gama) object;
        return !((this.idGama == null && other.idGama != null) || (this.idGama != null && !this.idGama.equals(other.idGama)));
    }

    @Override
    public String toString() {
        return "com.yousset.rentcar.models.Gama[ idGama=" + idGama + " ]";
    }

}
