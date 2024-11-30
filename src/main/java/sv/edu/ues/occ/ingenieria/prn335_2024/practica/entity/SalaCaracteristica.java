package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

@Entity
@Table(name = "sala_caracteristica", schema = "public")
@NamedQueries({
        @NamedQuery(name = "SalaCaracteristica.findByIdSala", query = "SELECT sc FROM SalaCaracteristica sc WHERE sc.idSala.idSala = :idSala ORDER BY sc.idTipoSala.nombre ASC"),
        @NamedQuery(name = "SalaCaracteristica.countByIdSala", query = "SELECT COUNT(sc.idSalaCaracteristica) FROM SalaCaracteristica sc WHERE sc.idSala.idSala = :idSala")
})
public class SalaCaracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sala_caracteristica_id_gen")
    @SequenceGenerator(name = "sala_caracteristica_id_gen", sequenceName = "sala_caracteristica_id_sala_caracteristica_seq", allocationSize = 1)
    @Column(name = "id_sala_caracteristica", nullable = false)
    private Integer idSalaCaracteristica;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_sala")
    private TipoSala idTipoSala;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Integer getIdSalaCaracteristica() {
        return idSalaCaracteristica;
    }

    public void setIdSalaCaracteristica(Integer id) {
        this.idSalaCaracteristica = id;
    }

    public TipoSala getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(TipoSala idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}