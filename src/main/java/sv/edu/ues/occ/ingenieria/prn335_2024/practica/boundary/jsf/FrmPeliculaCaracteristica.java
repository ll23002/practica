package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoPeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmPeliculaCaracteristica extends FrmAbstractPersistence<PeliculaCaracteristica> implements Serializable {
    @Inject
    PeliculaCaracteristicaBean PCB;
    @Inject
    FacesContext facesContext;
    @Inject
    TipoPeliculaBean TPB;
    PeliculaCaracteristica registro;
    LazyDataModel<PeliculaCaracteristica> modelo;

    Integer idPelicula;
    List<TipoPelicula> tipoPeliculaList;

    @PostConstruct
    public void inicializar() {
        try {
            modelo = this;
            estado = ESTADO_CRUD.NONE;
            this.tipoPeliculaList = TPB.findRange(0, Integer.MAX_VALUE);
            if (this.tipoPeliculaList != null && !this.tipoPeliculaList.isEmpty()) {
                this.setIdTipoPeliculaSeleccionada(this.tipoPeliculaList.get(0).getIdTipoPelicula());
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
        }
    }

    @Override
    public List<PeliculaCaracteristica> load(int firstResult, int maxResults, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
         try {
            if (this.idPelicula != null && PCB != null) {
                return PCB.findByIdPelicula(idPelicula, firstResult, maxResults);
            }
        } catch (Exception e) {
             Logger.getLogger(getClass().getName()).error(e);
            return null;
        }
        return List.of();
    }

    @Override
    public int count(Map<String, FilterMeta> filterMeta) {
         try {
            if (this.idPelicula != null && PCB != null) {
                return PCB.countByIdPelicula(idPelicula);
            }
        } catch (Exception e) {
             Logger.getLogger(getClass().getName()).error(e);
        }
        return 0;
    }

    @Override
    protected AbstractDataPersistence<PeliculaCaracteristica> getDataBean() {
        return PCB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected PeliculaCaracteristica createNewInstance() {
        try {
            registro = new PeliculaCaracteristica();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
            return null;
        }
    }

    @Override
    public PeliculaCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream().filter(p -> p.getIdPeliculaCaracteristica().toString().equals(id)).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(PeliculaCaracteristica dato) {
        if (dato != null && dato.getIdPeliculaCaracteristica() != null) {
            return dato.getIdPeliculaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return " Pelicula Caracteristica";
    }

    @Override
    protected Object getId(PeliculaCaracteristica object) {
        return object.getIdPeliculaCaracteristica();
    }

    public LazyDataModel<PeliculaCaracteristica> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<PeliculaCaracteristica> modelo) {
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

    public PeliculaCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(PeliculaCaracteristica registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = PCB.findLastId();
        try {
            if (id != null) {
                registro.setIdPeliculaCaracteristica((id + 1));
            } else {
                registro.setIdPeliculaCaracteristica(1);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
        }

    }

    public void btnGuardar(ActionEvent event) {
        if (registro != null && idPelicula != null) {
            registro.setIdPelicula(new Pelicula(idPelicula));
        }
        if (registro == null || registro.getValor() == null ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, Llene el formulario", " "));
        }else {
            super.btnGuardar(event, this.registro);
        }
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
    }

    public void btnEditar(ActionEvent event) {
        if (registro == null || registro.getValor() == null || registro.getIdTipoPelicula()==null ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, Llene el formulario", " "));
        }else {
            super.btnEditar(event, this.registro);
        }
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
    }

    public PeliculaCaracteristica crearNuevo() {
        PeliculaCaracteristica pc = new PeliculaCaracteristica();
        if (idPelicula != null) {
            pc.setIdPelicula(new Pelicula(idPelicula));
        }
        if (tipoPeliculaList != null && !tipoPeliculaList.isEmpty()) {
            pc.setIdTipoPelicula(tipoPeliculaList.get(0));
        }
        return pc;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public List<TipoPelicula> getTipoPeliculaList() {
        return tipoPeliculaList;
    }

    public Integer getIdTipoPeliculaSeleccionada(){
        if (registro != null && this.registro.getIdPelicula() != null) {
            return this.registro.getIdTipoPelicula().getIdTipoPelicula();
        }
        return null;
    }
    public void setIdTipoPeliculaSeleccionada(Integer idTipoPelicula){
        if (this.registro != null && this.tipoPeliculaList != null && !this.tipoPeliculaList.isEmpty()) {
            this.registro.setIdTipoPelicula(this.tipoPeliculaList.stream().filter(tp -> tp.getIdTipoPelicula().equals(idTipoPelicula)).findFirst().orElse(null));
        }
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
    }
    public void validarValor(FacesContext fc, UIComponent component, Object valor) {
        UIInput input = (UIInput) component;
        if (registro != null && this.registro.getIdTipoPelicula() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoPelicula().getExpresionRegular());
            Matcher validador = patron.matcher(nuevo);
            if (validador.find()){
                input.setValid(true);
                return;
            }
        }
        input.setValid(false);
    }
}