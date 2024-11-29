package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoPelicula;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@ViewScoped
public class FrmTipoPelicula extends FrmAbstractPersistence<TipoPelicula> implements Serializable {
    @Inject
    TipoPeliculaBean TPB;
    @Inject
    FacesContext facesContext;
    TipoPelicula registro;
    LazyDataModel<TipoPelicula> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

    @Override
    protected AbstractDataPersistence<TipoPelicula> getDataBean() {
        return TPB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoPelicula createNewInstance() {
        try {
            registro = new TipoPelicula();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public TipoPelicula buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoPelicula dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Pelicula";
    }

    @Override
    protected Object getId(TipoPelicula object) {
        return object.getIdTipoPelicula();
    }

    public LazyDataModel<TipoPelicula> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoPelicula> modelo) {
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

    public TipoPelicula getRegistro() {
        return registro;
    }

    public void setRegistro(TipoPelicula registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = TPB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoPelicula(id + 1);
            } else {
                registro.setIdTipoPelicula(1);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnGuardar(ActionEvent event) {super.btnGuardar(event, this.registro);}

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