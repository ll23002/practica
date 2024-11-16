package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "asiento_caracteristica", schema = "public")
@NamedQueries({
        @NamedQuery(name = "AsientoCaracteristica.findByIdAsiento", query = "SELECT ac FROM AsientoCaracteristica ac WHERE ac.idAsiento.idAsiento = :idAsiento ORDER BY ac.idTipoAsiento.nombre ASC"),
        @NamedQuery(name = "AsientoCaracteristica.countByIdAsiento", query = "SELECT COUNT(ac.idAsientoCaracteristica) FROM AsientoCaracteristica ac WHERE ac.idAsiento.idAsiento = :idAsiento")
})
public class AsientoCaracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asiento_caracteristica_id_gen")
    @SequenceGenerator(name = "asiento_caracteristica_id_gen", sequenceName = "asiento_caracteristica_id_asiento_caracteristica_seq", allocationSize = 1)
    @Column(name = "id_asiento_caracteristica", nullable = false)
    private Integer idAsientoCaracteristica;//estaba como long

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asiento")
    private Asiento idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_asiento")
    private TipoAsiento idTipoAsiento;

    @Lob
    @Column(name = "valor")
    private String valor;

    public Integer getIdAsientoCaracteristica() {
        return idAsientoCaracteristica;
    }

    public void setIdAsientoCaracteristica(Integer id) {
        this.idAsientoCaracteristica = id;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public TipoAsiento getIdTipoAsiento() {
        return idTipoAsiento;
    }

    public void setIdTipoAsiento(TipoAsiento idTipoAsiento) {
        this.idTipoAsiento = idTipoAsiento;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

}