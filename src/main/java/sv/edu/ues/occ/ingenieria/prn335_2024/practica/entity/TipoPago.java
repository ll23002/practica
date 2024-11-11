package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_pago", schema = "public")
public class TipoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_pago_id_gen")
    @SequenceGenerator(name = "tipo_pago_id_gen", sequenceName = "tipo_pago_id_tipo_pago_seq", allocationSize = 1)
    @Column(name = "id_tipo_pago", nullable = false)
    private Integer idTipoPago;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @OneToMany(mappedBy = "idTipoPago")
    private Set<Pago> pagos = new LinkedHashSet<>();

    public Integer getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(Integer id) {
        this.idTipoPago = id;
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

    public Set<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(Set<Pago> pagos) {
        this.pagos = pagos;
    }

}