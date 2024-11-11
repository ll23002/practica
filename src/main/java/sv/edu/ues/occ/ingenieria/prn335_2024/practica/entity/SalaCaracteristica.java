package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sala_caracteristica", schema = "public")
public class SalaCaracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sala_caracteristica_id_gen")
    @SequenceGenerator(name = "sala_caracteristica_id_gen", sequenceName = "sala_caracteristica_id_sala_caracteristica_seq", allocationSize = 1)
    @Column(name = "id_sala_caracteristica", nullable = false)
    private Long idSalaCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_sala")
    private TipoSala idTipoSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Long getIdSalaCaracteristica() {
        return idSalaCaracteristica;
    }

    public void setIdSalaCaracteristica(Long id) {
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