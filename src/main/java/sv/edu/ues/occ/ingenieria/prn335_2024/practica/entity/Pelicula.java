package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pelicula", schema = "public")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pelicula_id_gen")
    @SequenceGenerator(name = "pelicula_id_gen", sequenceName = "pelicula_id_pelicula_seq", allocationSize = 1)
    @Column(name = "id_pelicula", nullable = false)
    private Long idPelicula;

    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "sinopsis")
    private String sinopsis;

    @OneToMany(mappedBy = "idPelicula")
    private Set<PeliculaCaracteristica> peliculaCaracteristicas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPelicula")
    private Set<Programacion> programacions = new LinkedHashSet<>();

    public Long getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Long id) {
        this.idPelicula = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Set<PeliculaCaracteristica> getPeliculaCaracteristicas() {
        return peliculaCaracteristicas;
    }

    public void setPeliculaCaracteristicas(Set<PeliculaCaracteristica> peliculaCaracteristicas) {
        this.peliculaCaracteristicas = peliculaCaracteristicas;
    }

    public Set<Programacion> getProgramacions() {
        return programacions;
    }

    public void setProgramacions(Set<Programacion> programacions) {
        this.programacions = programacions;
    }

}