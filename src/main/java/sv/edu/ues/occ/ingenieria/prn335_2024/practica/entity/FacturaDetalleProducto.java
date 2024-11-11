package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "factura_detalle_producto", schema = "public")
public class FacturaDetalleProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "factura_detalle_producto_id_gen")
    @SequenceGenerator(name = "factura_detalle_producto_id_gen", sequenceName = "factura_detalle_producto_id_factura_detalle_producto_seq", allocationSize = 1)
    @Column(name = "id_factura_detalle_producto", nullable = false)
    private Long idFacturaDetalleProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura")
    private Factura idFactura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto idProducto;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    public Long getIdFacturaDetalleProducto() {
        return idFacturaDetalleProducto;
    }

    public void setIdFacturaDetalleProducto(Long id) {
        this.idFacturaDetalleProducto = id;
    }

    public Factura getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Factura idFactura) {
        this.idFactura = idFactura;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

}