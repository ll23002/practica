package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "asiento", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Asiento.findByNombre", query = "SELECT a FROM Asiento a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Asiento.countByNombre", query = "SELECT COUNT(a) FROM Asiento a WHERE a.nombre = :nombre")
})
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "asiento_id_gen")
    @SequenceGenerator(name = "asiento_id_gen", sequenceName = "asiento_id_asiento_seq", allocationSize = 1)
    @Column(name = "id_asiento", nullable = false)
    private Integer idAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sala")
    private Sala idSala;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @JsonbTransient
    @OneToMany(mappedBy = "idAsiento")
    private Set<AsientoCaracteristica> asientoCaracteristicas = new LinkedHashSet<>();

    @JsonbTransient
    @OneToMany(mappedBy = "idAsiento")
    private Set<ReservaDetalle> reservaDetalles = new LinkedHashSet<>();

    public Integer getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Integer id) {
        this.idAsiento = id;
    }

    public Sala getIdSala() {
        return idSala;
    }

    public void setIdSala(Sala idSala) {
        this.idSala = idSala;
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

    public Set<AsientoCaracteristica> getAsientoCaracteristicas() {
        return asientoCaracteristicas;
    }

    public void setAsientoCaracteristicas(Set<AsientoCaracteristica> asientoCaracteristicas) {this.asientoCaracteristicas = asientoCaracteristicas;}

    public Set<ReservaDetalle> getReservaDetalles() {
        return reservaDetalles;
    }

    public void setReservaDetalles(Set<ReservaDetalle> reservaDetalles) {
        this.reservaDetalles = reservaDetalles;
    }
}