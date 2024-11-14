package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;

import java.io.Serializable;

@Named
@SessionScoped
public class FrmProgramacion extends FrmAbstractPersistence<Programacion> implements Serializable {
    @Inject
    ProgramacionBean dataBean;
    @Inject
    FacesContext facesContext;
    Programacion registro;
    LazyDataModel<Programacion> modelo;

    Integer idSala;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        System.out.println("Estado: " + estado);
    }

    @Override
    protected AbstractDataPersistence<Programacion> getDataBean() {
        return dataBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Programacion createNewInstance() {
        try {
            registro = new Programacion();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Programacion buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Programacion dato) {
        if (dato != null && dato.getIdProgramacion() != null) {
            return dato.getIdProgramacion().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Gestión de Programación";
    }

    @Override
    protected Object getId(Programacion object) {
        return object.getIdProgramacion();
    }

    public LazyDataModel<Programacion> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Programacion> modelo) {
        this.modelo = modelo;
    }

    @Override
    public ESTADO_CRUD getEstado() {
        return estado;
    }

    @Override
    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public Programacion getRegistro() {
        return registro;
    }

    public void setRegistro(Programacion registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        System.out.println("Registro nuevo en FrmProgramacion: " + estado);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdProgramacion(id + 1);
            } else {
                registro.setIdProgramacion(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
        System.out.println("Registro guardado en FrmProgramacion: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
        System.out.println("Registro cancelado en FrmProgramacion: " + estado);
    }

    public void btnEditar(ActionEvent event) {
        super.btnEditar(event, this.registro);
        System.out.println("Registro editado en FrmProgramacion: " + estado);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
        System.out.println("Registro eliminado en FrmProgramacion: " + estado);
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmProgramacion: " + estado);
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}