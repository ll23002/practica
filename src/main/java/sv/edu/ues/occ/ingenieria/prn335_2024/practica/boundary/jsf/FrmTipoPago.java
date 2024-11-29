package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoPagoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoPago;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoPago extends FrmAbstractPersistence<TipoPago> implements Serializable {
    @Inject
    TipoPagoBean TPB;
    @Inject
    FacesContext facesContext;
    TipoPago registro;
    LazyDataModel<TipoPago> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

    @Override
    protected AbstractDataPersistence<TipoPago> getDataBean() {
        return TPB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoPago createNewInstance() {
        try {
            registro = new TipoPago();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public TipoPago buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoPago dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Pago";
    }

    @Override
    protected Object getId(TipoPago object) {
        return object.getIdTipoPago();
    }

    public LazyDataModel<TipoPago> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoPago> modelo) {
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

    public TipoPago getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPago registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = TPB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoPago(id + 1);
            } else {
                registro.setIdTipoPago(1);
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