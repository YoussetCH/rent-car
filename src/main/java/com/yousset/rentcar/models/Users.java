package com.yousset.rentcar.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.yousset.rentcar.RentCarStartApplication;
import com.yousset.rentcar.models.json_views.EntityView;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author david
 */
@Entity
@Table(name = "USERS")
@NamedQueries({})
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "users_id_user_seq", sequenceName = "users_id_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_user_seq")
    @Column(name = "ID_USER")
    private Long idUser;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "NAME")
    private String name;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;
    @Column(name = "ROLE")
    private String role;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "DT_REGISTER")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = RentCarStartApplication.DEFAULT_FORMAT_DATE)
    private Date dtRegister;
    @Basic(optional = false)
    @com.fasterxml.jackson.annotation.JsonProperty(required = true)
    @Column(name = "STATUS")
    private Integer status;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView({EntityView.Complete.class})
    private List<Reserve> reserves;

    public Users() {
    }

    public Users(Long idUser) {
        this.idUser = idUser;
    }

    public Users(Long idUser, Integer status) {
        this.idUser = idUser;
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        return !((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser)));
    }

    @Override
    public String toString() {
        return "com.yousset.rentcar.models.Users[ idUser=" + idUser + " ]";
    }

}
