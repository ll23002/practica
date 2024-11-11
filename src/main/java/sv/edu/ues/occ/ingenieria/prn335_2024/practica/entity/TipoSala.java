package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_sala", schema = "public")
public class TipoSala {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_sala_id_gen")
    @SequenceGenerator(name = "tipo_sala_id_gen", sequenceName = "tipo_sala_id_tipo_sala_seq", allocationSize = 1)
    @Column(name = "id_tipo_sala", nullable = false)
    private Integer idTipoSala;

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

    @OneToMany(mappedBy = "idTipoSala")
    private Set<SalaCaracteristica> salaCaracteristicas = new LinkedHashSet<>();

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer id) {
        this.idTipoSala = id;
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

    public Set<SalaCaracteristica> getSalaCaracteristicas() {
        return salaCaracteristicas;
    }

    public void setSalaCaracteristicas(Set<SalaCaracteristica> salaCaracteristicas) {
        this.salaCaracteristicas = salaCaracteristicas;
    }

}