package com.yousset.rentcar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.RentCarStartApplication;
import com.yousset.rentcar.models.json_views.UsersEntityView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author david
 */
@Entity
@Table(name = "RESERVE")
@NamedQueries({})
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Reserve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "reserve_id_reserve_seq", sequenceName = "reserve_id_reserve_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserve_id_reserve_seq")
    @Column(name = "ID_RESERVE")
    private Long idReserve;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "PRICE", columnDefinition = "NUMERIC")
    private Double price;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DT_FROM")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtFrom;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DT_TO")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtTo;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @JsonView({UsersEntityView.Complete.class})
    private Users user;
    @JoinColumn(name = "LICENSE_PLATE", referencedColumnName = "LICENSE_PLATE")
    @ManyToOne(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    private Cars car;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "STATUS")
    private Integer status;

    public Reserve() {
    }

    public Reserve(Long idReserve) {
        this.idReserve = idReserve;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idReserve != null ? idReserve.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Reserve)) {
            return false;
        }
        Reserve other = (Reserve) object;
        return !((this.idReserve == null && other.idReserve != null) || (this.idReserve != null && !this.idReserve.equals(other.idReserve)));
    }

    @Override
    public String toString() {
        return "com.yousset.rentcar.models.Reserve[ idReserve=" + idReserve + " ]";
    }

}
