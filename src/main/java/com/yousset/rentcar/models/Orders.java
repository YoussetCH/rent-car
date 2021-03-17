package com.yousset.rentcar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yousset.rentcar.RentCarStartApplication;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author david
 */
@Entity
@Table(name = "ORDERS")
@NamedQueries({})
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "orders_id_order_seq", sequenceName = "orders_id_order_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_order_seq")
    @Column(name = "ID_ORDER")
    private Long idOrder;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "PRICE", columnDefinition = "NUMERIC")
    private Double price;
    @Column(name = "RECHARGE", columnDefinition = "NUMERIC")
    private Double recharge;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DT_PICK_UP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtPickUp;
    @Column(name = "DT_GIVE_UP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtGiveUp;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "PLACE_PICK_UP")
    private String placePickUp;
    @Column(name = "PLACE_GIVE_UP")
    private String placeGiveUp;
    @JoinColumn(name = "ID_RESERVE", referencedColumnName = "ID_RESERVE")
    @ManyToOne(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    private Reserve reserve;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "STATUS")
    private Integer status;

    public Orders() {
    }

    public Orders(Long idOrder) {
        this.idOrder = idOrder;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOrder != null ? idOrder.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        return !((this.idOrder == null && other.idOrder != null) || (this.idOrder != null && !this.idOrder.equals(other.idOrder)));
    }

    @Override
    public String toString() {
        return "com.yousset.rentcar.models.Reserve[ idOrder=" + idOrder + " ]";
    }

}
