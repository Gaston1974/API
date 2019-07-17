/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author gaston
 */
@Entity
@Table(name = "f_equipo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Select_equipoId", query = "SELECT t FROM Fequipo t WHERE t.equipoId = ?")
    ,   @NamedQuery(name = "Delete", query = "DELETE FROM Fequipo t WHERE t.equipo = ?")})

public class Fequipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @Basic(optional = false)
    @Column(name = "equipo_id")
    //@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN",initialValue=2, allocationSize=12)
    //@GeneratedValue(strategy= GenerationType.TABLE)
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private int equipoId;
    @Basic(optional = true)
    @Column(name = "pais_id")         
    private int pais_id;
    @Basic(optional = false)
    @Column(name = "equipo")
    private String equipo;
    @Basic(optional = true)
    @Column(name = "logo_url")
    private String logo_url;
    @Basic(optional = true)
    @Column(name = "api_id")
    private String api_id;
    @Basic(optional = true)
    @Column(name = "status")
    private String status;
     @Basic(optional = true)
    @Column(name = "id_api_cristian")
    private String id_cristian;
    
    
public Fequipo () {
    
}

    public Fequipo(int pais_id, int equipoId, String equipo, String api_id) {
        this.pais_id = pais_id;
        this.equipoId = equipoId;
        this.equipo = equipo;
        this.api_id = api_id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPais_id() {
        return pais_id;
    }

    public String getEquipo() {
        return equipo;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getApi_id() {
        return api_id;
    }

    public String getStatus() {
        return status;
    }

    public int getEquipoId() {
        return equipoId;
    }

    public void setPais_id(int pais_id) {
        this.pais_id = pais_id;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public void setApi_id(String api_id) {
        this.api_id = api_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_cristian() {
        return id_cristian;
    }



}
