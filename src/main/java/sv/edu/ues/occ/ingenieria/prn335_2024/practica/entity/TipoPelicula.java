package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_pelicula", schema = "public")
public class TipoPelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_pelicula_id_gen")
    @SequenceGenerator(name = "tipo_pelicula_id_gen", sequenceName = "tipo_pelicula_id_tipo_pelicula_seq", allocationSize = 1)
    @Column(name = "id_tipo_pelicula", nullable = false)
    private Integer idTipoPelicula;

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

    @OneToMany(mappedBy = "idTipoPelicula")
    private Set<PeliculaCaracteristica> peliculaCaracteristicas = new LinkedHashSet<>();

    public Integer getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(Integer id) {
        this.idTipoPelicula = id;
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

    public Set<PeliculaCaracteristica> getPeliculaCaracteristicas() {
        return peliculaCaracteristicas;
    }

    public void setPeliculaCaracteristicas(Set<PeliculaCaracteristica> peliculaCaracteristicas) {
        this.peliculaCaracteristicas = peliculaCaracteristicas;
    }

}