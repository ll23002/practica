package sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sala", schema = "public")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sala_id_gen")
    @SequenceGenerator(name = "sala_id_gen", sequenceName = "sala_id_sala_seq", allocationSize = 1)
    @Column(name = "id_sala", nullable = false)
    private Integer idSala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal")
    private Sucursal idSucursal;

    @Size(max = 155)
    @Column(name = "nombre", length = 155)
    private String nombre;

    @Column(name = "activo")
    private Boolean activo;

    @Lob
    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "idSala")
    private Set<Asiento> asientos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSala")
    private Set<Programacion> programacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idSala")
    private Set<SalaCaracteristica> salaCaracteristicas = new LinkedHashSet<>();


    public Sala(Integer idSala) {
        this.idSala = idSala;
    }

    public Sala() {//agregar un constructor vacio, necesario en FrmSala

    }
    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer id) {
        this.idSala = id;
    }

    public Sucursal getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Sucursal idSucursal) {
        this.idSucursal = idSucursal;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Set<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(Set<Asiento> asientos) {
        this.asientos = asientos;
    }

    public Set<Programacion> getProgramacions() {
        return programacions;
    }

    public void setProgramacions(Set<Programacion> programacions) {
        this.programacions = programacions;
    }

    public Set<SalaCaracteristica> getSalaCaracteristicas() {
        return salaCaracteristicas;
    }

    public void setSalaCaracteristicas(Set<SalaCaracteristica> salaCaracteristicas) {
        this.salaCaracteristicas = salaCaracteristicas;
    }

}