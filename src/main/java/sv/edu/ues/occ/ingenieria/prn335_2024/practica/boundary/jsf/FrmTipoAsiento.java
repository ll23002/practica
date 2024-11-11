package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoAsiento;

import java.io.Serializable;

@Named
@SessionScoped
public class FrmTipoAsiento extends FrmAbstractPersistence<TipoAsiento> implements Serializable {
    @Inject
    TipoAsientoBean TAB;
    @Inject
    FacesContext facesContext;
    TipoAsiento registro;
    LazyDataModel<TipoAsiento> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        System.out.println("Estado: " + estado);
    }

    @Override
    protected AbstractDataPersistence<TipoAsiento> getDataBean() {
        return TAB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoAsiento createNewInstance() {
        try {
            registro = new TipoAsiento();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TipoAsiento buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoAsiento dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return null;
    }

    @Override
    protected Object getId(TipoAsiento object) {
        return object.getIdTipoAsiento();
    }

    public LazyDataModel<TipoAsiento> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoAsiento> modelo) {
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

    public TipoAsiento getRegistro() {
        return registro;
    }

    public void setRegistro(TipoAsiento registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        Integer id = TAB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoAsiento(id + 1);
            } else {
                registro.setIdTipoAsiento(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
    }

    public void btnEditar(ActionEvent event) {
        super.btnEditar(event, this.registro);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
    }
}