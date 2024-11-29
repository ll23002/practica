package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.*;

import java.io.Serializable;
import java.util.logging.Logger;
//no olvidar agregar el @Named y @ViewScoped
public class FrmReserva extends FrmAbstractPersistence<Reserva> implements Serializable {
    @Inject
    ReservaBean rBean;

    @Inject
    TipoReservaBean trBean;

    @Inject
    ReservaDetalleBean rdBean;

    @Inject
    PeliculaBean pBean;

    @Inject
    AsientoBean aBean;

    @Inject
    AsientoCaracteristicaBean acBean;

    @Inject
    SalaBean sBean;

    @Inject
    ProgramacionBean pgBean;

    @Inject
    FacesContext facesContext;

    Reserva registro;
    LazyDataModel<Reserva> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        registro = new Reserva();
    }

    @Override
    protected AbstractDataPersistence<Reserva> getDataBean() {
        return rBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Reserva createNewInstance() {
        try {
            registro = new Reserva();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public Reserva buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return rBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Reserva dato) {
        if (dato != null && dato.getIdReserva() != null) {
            return dato.getIdReserva().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Reservas";
    }

    @Override
    protected Object getId(Reserva object) {
        return object.getIdReserva();
    }

    @Override
    public ESTADO_CRUD getEstado() {
        return estado;
    }

    @Override
    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public LazyDataModel<Reserva> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Reserva> modelo) {
        this.modelo = modelo;
    }

    public Reserva getRegistro() {
        return registro;
    }

    public void setRegistro(Reserva registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);

        Integer newId = rBean.findLastId();

        try {
            if (newId != null) {
                registro.setIdReserva(newId + 1);
            } else {
                registro.setIdReserva(1);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnGuardar(ActionEvent event, Reserva registro) {
        super.btnGuardar(event, registro);
    }

    public void btnEditar(ActionEvent event, Reserva registro) {
        super.btnEditar(event, registro);
    }

    public void btnCancelar(ActionEvent event, Reserva registro) {
        super.btnCancelar(event, registro);
    }

    public void btnEliminar(ActionEvent event, Reserva registro) {
        super.btnEliminar(event, registro);
    }

}
