package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmAsiento extends FrmAbstractPersistence<Asiento> implements Serializable {
    @Inject
    AsientoBean dataBean;
    @Inject
    FacesContext facesContext;
    Asiento registro;
    LazyDataModel<Asiento> modelo;
    Integer idSala;
    @Inject
    FrmAsientoCaracteristica frmAsientoCaracteristica;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

    @Override
    protected AbstractDataPersistence<Asiento> getDataBean() {
        return dataBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Asiento createNewInstance() {
        try {
            registro = new Asiento();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public Asiento buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Asiento dato) {
        if (dato != null && dato.getIdAsiento() != null) {
            return dato.getIdAsiento().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Asiento";
    }

    @Override
    protected Object getId(Asiento object) {
        return object.getIdAsiento();
    }

    public LazyDataModel<Asiento> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Asiento> modelo) {
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

    public Asiento getRegistro() {
        return registro;
    }

    public void setRegistro(Asiento registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdAsiento(id+1) ;
            } else {
                registro.setIdAsiento(1);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnGuardar(ActionEvent event) {super.btnGuardar(event, this.registro);}

    public void btnCancelar(ActionEvent event) {super.btnCancelar(event, this.registro);}

    public void btnEditar(ActionEvent event) {super.btnEditar(event, this.registro);}

    public void btnEliminar(ActionEvent event) {super.btnEliminar(event, this.registro);}

    @Override
    public void onRowSelect() {
        super.onRowSelect();
    if (this.registro != null && this.registro.getIdAsiento() != null) {
        frmAsientoCaracteristica.setIdAsiento(this.registro.getIdAsiento());
        frmAsientoCaracteristica.cargarCaracteristicas();
    }
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public FrmAsientoCaracteristica getFrmAsientoCaracteristica() {
        return frmAsientoCaracteristica;
    }

    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica frmAsientoCaracteristica) {this.frmAsientoCaracteristica = frmAsientoCaracteristica;}
}