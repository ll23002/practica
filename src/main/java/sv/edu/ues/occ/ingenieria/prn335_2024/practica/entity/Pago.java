package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pago", schema = "public")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pago_id_gen")
    @SequenceGenerator(name = "pago_id_gen", sequenceName = "pago_id_pago_seq", allocationSize = 1)
    @Column(name = "id_pago", nullable = false)
    private Long idPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    private Factura idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_pago")
    private TipoPago idTipoPago;

    @Column(name = "fecha")
    private OffsetDateTime fecha;

    @OneToMany(mappedBy = "idPago")
    private Set<PagoDetalle> pagoDetalles = new LinkedHashSet<>();

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long id) {
        this.idPago = id;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public TipoPago getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(TipoPago idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public Set<PagoDetalle> getPagoDetalles() {
        return pagoDetalles;
    }

    public void setPagoDetalles(Set<PagoDetalle> pagoDetalles) {
        this.pagoDetalles = pagoDetalles;
    }

}