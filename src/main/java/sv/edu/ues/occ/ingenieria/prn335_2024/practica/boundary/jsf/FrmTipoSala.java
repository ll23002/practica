package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoSala extends FrmAbstractPersistence<TipoSala> implements Serializable {
    @Inject
    TipoSalaBean TSB;
    @Inject
    FacesContext facesContext;
    TipoSala registro;
    LazyDataModel<TipoSala> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

    @Override
    protected AbstractDataPersistence<TipoSala> getDataBean() {
        return TSB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoSala createNewInstance() {
        try {
            registro = new TipoSala();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public TipoSala buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoSala dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Sala";
    }

    @Override
    protected Object getId(TipoSala object) {
        return object.getIdTipoSala();
    }

    public LazyDataModel<TipoSala> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoSala> modelo) {
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

    public TipoSala getRegistro() {
        return registro;
    }

    public void setRegistro(TipoSala registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = TSB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoSala(id + 1);
            } else {
                registro.setIdTipoSala(1);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnGuardar(ActionEvent event) {//No ocupa @Override porque no lo estamos sobreescribiendo, crea una implementacion diferente.
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
