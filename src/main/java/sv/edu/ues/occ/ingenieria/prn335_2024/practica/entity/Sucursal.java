package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sucursal", schema = "public")
@NamedQueries({
       @NamedQuery(name = "Sucursal.findByIdSucursal", query = "SELECT s FROM Sucursal s WHERE s.idSucursal = :idSucursal ORDER BY s.nombre ASC"),
       @NamedQuery(name = "Sucursal.countByIdSucursal", query = "SELECT COUNT(s.idSucursal) FROM Sucursal s WHERE s.idSucursal = :idSucursal")
})

public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_id_gen")
    @SequenceGenerator(name = "sucursal_id_gen", sequenceName = "sucursal_id_sucursal_seq", allocationSize = 1)
    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "latitud")
    private Double latitud;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Column(name = "activo")
    private Boolean activo;

    @JsonbTransient
    @OneToMany(mappedBy = "idSucursal")
    private Set<Sala> salas = new LinkedHashSet<>();

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer id) {
        this.idSucursal = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Set<Sala> getSalas() {
        return salas;
    }

    public void setSalas(Set<Sala> salas) {
        this.salas = salas;
    }

}