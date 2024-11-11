package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_producto", schema = "public")
public class TipoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_producto_id_gen")
    @SequenceGenerator(name = "tipo_producto_id_gen", sequenceName = "tipo_producto_id_tipo_producto_seq", allocationSize = 1)
    @Column(name = "id_tipo_producto", nullable = false)
    private Integer idTipoProducto;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "comentarios")
    private String comentarios;

    @OneToMany(mappedBy = "idTipoProducto")
    private Set<Producto> productos = new LinkedHashSet<>();

    public Integer getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(Integer id) {
        this.idTipoProducto = id;
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

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

}