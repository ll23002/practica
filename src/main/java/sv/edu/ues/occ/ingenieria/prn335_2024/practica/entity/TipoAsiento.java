package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_asiento", schema = "public")
public class TipoAsiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_asiento_id_gen")
    @SequenceGenerator(name = "tipo_asiento_id_gen", sequenceName = "tipo_asiento_id_tipo_asiento_seq", allocationSize = 1)
    @Column(name = "id_tipo_asiento", nullable = false)
    private Integer idTipoAsiento;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @Lob
    @Column(name = "expresion_regular")
    private String expresionRegular;

    @JsonbTransient
    @OneToMany(mappedBy = "idTipoAsiento")
    private Set<AsientoCaracteristica> asientoCaracteristicas = new LinkedHashSet<>();

    public Integer getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(Integer id) {
        this.idTipoAsiento = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public Set<AsientoCaracteristica> getAsientoCaracteristicas() {
        return asientoCaracteristicas;
    }

    public void setAsientoCaracteristicas(Set<AsientoCaracteristica> asientoCaracteristicas) {
        this.asientoCaracteristicas = asientoCaracteristicas;
    }

}