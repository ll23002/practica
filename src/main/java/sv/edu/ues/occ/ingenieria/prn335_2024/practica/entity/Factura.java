package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "factura", schema = "public")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_id_gen")
    @SequenceGenerator(name = "factura_id_gen", sequenceName = "factura_id_factura_seq", allocationSize = 1)
    @Column(name = "id_factura", nullable = false)
    private Long idFactura;

    @Size(max = 255)
    @Column(name = "cliente")
    private String cliente;

    @Size(max = 155)
    @Column(name = "dui", length = 155)
    private String dui;

    @Column(name = "fecha")
    private OffsetDateTime fecha;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @OneToMany(mappedBy = "idFactura")
    private Set<FacturaDetalleProducto> facturaDetalleProductos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idFactura")
    private Set<FacturaDetalleSala> facturaDetalleSalas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idFactura")
    private Set<Pago> pagos = new LinkedHashSet<>();

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long id) {
        this.idFactura = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<FacturaDetalleProducto> getFacturaDetalleProductos() {
        return facturaDetalleProductos;
    }

    public void setFacturaDetalleProductos(Set<FacturaDetalleProducto> facturaDetalleProductos) {
        this.facturaDetalleProductos = facturaDetalleProductos;
    }

    public Set<FacturaDetalleSala> getFacturaDetalleSalas() {
        return facturaDetalleSalas;
    }

    public void setFacturaDetalleSalas(Set<FacturaDetalleSala> facturaDetalleSalas) {
        this.facturaDetalleSalas = facturaDetalleSalas;
    }

    public Set<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        this.pagos = pagos;
    }

}