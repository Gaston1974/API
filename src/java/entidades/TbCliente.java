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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "tb_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "clientes", query = "SELECT t FROM TbCliente t WHERE t.nrodni = ?")
    , @NamedQuery(name = "TbCliente.findByIdcliente", query = "SELECT t FROM TbCliente t WHERE t.idcliente = :idcliente")
    , @NamedQuery(name = "cli.nombre", query = "SELECT t FROM TbCliente t WHERE t.nombre = ?")
    , @NamedQuery(name = "cli.apellido", query = "SELECT t FROM TbCliente t WHERE t.apellido = ?")
    , @NamedQuery(name = "TbCliente.findByIdlocalidad", query = "SELECT t FROM TbCliente t WHERE t.idlocalidad = :idlocalidad")
    , @NamedQuery(name = "TbCliente.findByTelefono", query = "SELECT t FROM TbCliente t WHERE t.telefono = :telefono")
    , @NamedQuery(name = "TbCliente.findByTipodni", query = "SELECT t FROM TbCliente t WHERE t.tipodni = :tipodni")
    , @NamedQuery(name = "getdocument", query = "SELECT t FROM TbCliente t WHERE t.nrodni = ?")})
public class TbCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id 
    @Basic(optional = false)
    @Column(name = "idcliente")
    //@SequenceGenerator(name="seq-gen",sequenceName="MY_SEQ_GEN",initialValue=2, allocationSize=12)
    //@GeneratedValue(strategy= GenerationType.TABLE)
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long idcliente;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Column(name = "idlocalidad")
    private String idlocalidad;
    @Basic(optional = false)
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "tipodni")
    private String tipodni;
    @Basic(optional = false)
    @Column(name = "nrodni")
    private String nrodni;

    public TbCliente() {
    }

    public TbCliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public TbCliente(Long idcliente, String nombre, String apellido, String idlocalidad, String telefono, String tipodni, String nrodni) {
        this.idcliente = idcliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idlocalidad = idlocalidad;
        this.telefono = telefono;
        this.tipodni = tipodni;
        this.nrodni = nrodni;
    }

    public Long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long idcliente) {
        this.idcliente = idcliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdlocalidad() {
        return idlocalidad;
    }

    public void setIdlocalidad(String idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipodni() {
        return tipodni;
    }

    public void setTipodni(String tipodni) {
        this.tipodni = tipodni;
    }

    public String getNrodni() {
        return nrodni;
    }

    public void setNrodni(String nrodni) {
        this.nrodni = nrodni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbCliente)) {
            return false;
        }
        TbCliente other = (TbCliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
return 
"	<tr>\n" +
"       <td><font size=2 color=\"green\" face=\"helvetica\">" + idcliente + "</font></td>\n" +
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + nombre + "</font></td>\n" +
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + apellido + "</font></td>\n" +                
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + idlocalidad + "</font></td>\n" +
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + telefono + "</font></td>\n" +                
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + tipodni + "</font></td>\n" +
"   	<td><font size=2 color=\"green\" face=\"helvetica\">" + nrodni + "</font></td>\n" +        
"	</tr>" ;     
    }
    
}
