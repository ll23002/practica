package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;

import java.io.Serializable;

@Named
@ViewScoped
public class FrmPelicula extends FrmAbstractPersistence<Pelicula> implements Serializable {
    @Inject
    PeliculaBean dataBean;
    @Inject
    FacesContext facesContext;
    @Inject
    FrmPeliculaCaracteristica frmPeliculaCaracteristica;
    Pelicula registro;
    LazyDataModel<Pelicula> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
    }

     public void cambiarTab(TabChangeEvent tce){
        if (tce.getTab().getTitle().equals("Tipos")){
            if (this.registro != null && this.frmPeliculaCaracteristica != null){
                this.frmPeliculaCaracteristica.setIdPelicula(this.registro.getIdPelicula());
            }
        }
    }

    @Override
    protected AbstractDataPersistence<Pelicula> getDataBean() {
        return dataBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Pelicula createNewInstance() {
        try {
            registro = new Pelicula();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
            return null;
        }
    }

    @Override
    public Pelicula buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(this.getClass().getName()).error(e);
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Pelicula dato) {
        if (dato != null && dato.getIdPelicula() != null) {
            return dato.getIdPelicula().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Pelicula";
    }

    @Override
    protected Object getId(Pelicula object) {
        return object.getIdPelicula();
    }

    public LazyDataModel<Pelicula> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Pelicula> modelo) {
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

    public Pelicula getRegistro() {
        return registro;
    }

    public void setRegistro(Pelicula registro) {
        this.registro = registro;
    }

    public FrmPeliculaCaracteristica getFrmPeliculaCaracteristica() {
        return frmPeliculaCaracteristica;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdPelicula(id + 1);
            } else {
                registro.setIdPelicula(1);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }
    }

    public void btnGuardar(ActionEvent event) {
        if (registro == null || registro.getNombre() == null || registro.getNombre().isEmpty()|| registro.getSinopsis()==null || registro.getSinopsis().isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error,Llene el formulario", "El nombre es requerido"));
        }else{
            super.btnGuardar(event, this.registro);
        }
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
    }

    public void btnEditar(ActionEvent event) {
        if (registro == null || registro.getNombre() == null || registro.getNombre().isEmpty() || registro.getSinopsis()==null || registro.getSinopsis().isEmpty()  ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, Llene el formulario", "El nombre es requerido"));
        }else {
            super.btnEditar(event, this.registro);
        }
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
    }
}