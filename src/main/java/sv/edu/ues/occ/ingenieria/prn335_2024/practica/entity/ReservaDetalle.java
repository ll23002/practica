package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "reserva_detalle", schema = "public")
public class ReservaDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_detalle_id_gen")
    @SequenceGenerator(name = "reserva_detalle_id_gen", sequenceName = "reserva_detalle_id_reserva_detalle_seq", allocationSize = 1)
    @Column(name = "id_reserva_detalle", nullable = false)
    private Long idReservaDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva")
    private Reserva idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asiento")
    private Asiento idAsiento;

    @Size(max = 155)
    @Column(name = "estado", length = 155)
    private String estado;

    @OneToMany(mappedBy = "idReservaDetalle")
    private Set<FacturaDetalleSala> facturaDetalleSalas = new LinkedHashSet<>();

    public Long getIdReservaDetalle() {
        return idReservaDetalle;
    }

    public void setIdReservaDetalle(Long id) {
        this.idReservaDetalle = id;
    }

    public Reserva getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Reserva idReserva) {
        this.idReserva = idReserva;
    }

    public Asiento getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Asiento idAsiento) {
        this.idAsiento = idAsiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<FacturaDetalleSala> getFacturaDetalleSalas() {
        return facturaDetalleSalas;
    }

    public void setFacturaDetalleSalas(Set<FacturaDetalleSala> facturaDetalleSalas) {
        this.facturaDetalleSalas = facturaDetalleSalas;
    }

}