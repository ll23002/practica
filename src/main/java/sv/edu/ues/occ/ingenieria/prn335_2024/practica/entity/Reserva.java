package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "reserva", schema = "public")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_id_gen")
    @SequenceGenerator(name = "reserva_id_gen", sequenceName = "reserva_id_reserva_seq", allocationSize = 1)
    @Column(name = "id_reserva", nullable = false)
    private Integer idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programacion")
    private Programacion idProgramacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_reserva")
    private TipoReserva idTipoReserva;

    @Column(name = "fecha_reserva")
    private OffsetDateTime fechaReserva;

    @Size(max = 155)
    @Column(name = "estado", length = 155)
    private String estado;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "idReserva")
    private Set<ReservaDetalle> reservaDetalles = new LinkedHashSet<>();

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer id) {
        this.idReserva = id;
    }

    public Programacion getIdProgramacion() {
        return idProgramacion;
    }

    public void setIdProgramacion(Programacion idProgramacion) {
        this.idProgramacion = idProgramacion;
    }

    public TipoReserva getIdTipoReserva() {
        return idTipoReserva;
    }

    public void setIdTipoReserva(TipoReserva idTipoReserva) {
        this.idTipoReserva = idTipoReserva;
    }

    public OffsetDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(OffsetDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<ReservaDetalle> getReservaDetalles() {
        return reservaDetalles;
    }

    public void setReservaDetalles(Set<ReservaDetalle> reservaDetalles) {
        this.reservaDetalles = reservaDetalles;
    }

}