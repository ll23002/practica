package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoReserva;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoReserva extends FrmAbstractPersistence<TipoReserva> implements Serializable {
    @Inject
    TipoReservaBean TRB;
    @Inject
    FacesContext facesContext;
    TipoReserva registro;
    LazyDataModel<TipoReserva> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

    @Override
    protected AbstractDataPersistence<TipoReserva> getDataBean() {
        return TRB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoReserva createNewInstance() {
        try {
            registro = new TipoReserva();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public TipoReserva buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoReserva dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Reserva";
    }

    @Override
    protected Object getId(TipoReserva object) {
        return object.getIdTipoReserva();
    }

    public LazyDataModel<TipoReserva> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoReserva> modelo) {
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

    public TipoReserva getRegistro() {
        return registro;
    }

    public void setRegistro(TipoReserva registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = TRB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoReserva(id + 1);
            } else {
                registro.setIdTipoReserva(1);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
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