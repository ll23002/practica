package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "pelicula_caracteristica", schema = "public")
@NamedQueries({
        @NamedQuery(name = "PeliculaCaracteristica.findByIdPelicula", query = "SELECT pc FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula = :idPelicula ORDER BY pc.idTipoPelicula.nombre ASC "),
        @NamedQuery(name = "PeliculaCaracteristica.countByIdPelicula", query = "SELECT COUNT(pc.idPeliculaCaracteristica) FROM PeliculaCaracteristica pc WHERE pc.idPelicula.idPelicula = :idPelicula")
})
public class PeliculaCaracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pelicula_caracteristica_id_gen")
    @SequenceGenerator(name = "pelicula_caracteristica_id_gen", sequenceName = "pelicula_caracteristica_id_pelicula_caracteristica_seq", allocationSize = 1)
    @Column(name = "id_pelicula_caracteristica", nullable = false)
    private Integer idPeliculaCaracteristica;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pelicula")
    private TipoPelicula idTipoPelicula;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pelicula")
    private Pelicula idPelicula;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Integer getIdPeliculaCaracteristica() {
        return idPeliculaCaracteristica;
    }

    public void setIdPeliculaCaracteristica(Integer id) {
        this.idPeliculaCaracteristica = id;
    }

    public TipoPelicula getIdTipoPelicula() {
        return idTipoPelicula;
    }

    public void setIdTipoPelicula(TipoPelicula idTipoPelicula) {
        this.idTipoPelicula = idTipoPelicula;
    }

    public Pelicula getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Pelicula idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}