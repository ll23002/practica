package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jboss.logging.Logger;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.SalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sucursal;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class FrmSala extends FrmAbstractPersistence<Sala> implements Serializable {
    @Inject
    SalaBean dataBean;
    @Inject
    FacesContext facesContext;
    @Inject
    FrmSalaCaracteristica frmSalaCaracteristica;
    @Inject
    FrmAsiento frmAsiento;
    @Inject
    FrmProgramacion frmProgramacion;
    Sala registro;
    LazyDataModel<Sala> modelo;
    @Inject
    TipoSucursalBean TSB;
    List<Sucursal> sucursalList;
    Integer idSucursal;
    Integer idAsiento;

    @PostConstruct
    public void inicializar() {
        try {
            modelo = this;
            estado = ESTADO_CRUD.NONE;
            this.sucursalList = TSB.findRange(0, Integer.MAX_VALUE);
            if (this.sucursalList != null && !this.sucursalList.isEmpty()) {
                this.setIdSucursalSeleccionada(this.sucursalList.get(0).getIdSucursal());
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
        }
    }

    @Override
    protected AbstractDataPersistence<Sala> getDataBean() {
        return dataBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Sala createNewInstance() {
        try {
            registro = new Sala();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
            return null;
        }
    }

    @Override
    public Sala buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(getClass().getName()).error(e);
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Sala dato) {
        if (dato != null && dato.getIdSala() != null) {
            return dato.getIdSala().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return " Sala";
    }

    @Override
    protected Object getId(Sala object) {
        return object.getIdSala();
    }

    public LazyDataModel<Sala> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Sala> modelo) {this.modelo = modelo;}

    @Override
    public ESTADO_CRUD getEstado() {
        return estado;
    }

    @Override
    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public Sala getRegistro() {
        return registro;
    }

    public void setRegistro(Sala registro) {
        this.registro = registro;
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {this.sucursalList = sucursalList;}

    public Integer getIdSucursalSeleccionada(){
        if (registro != null && this.registro.getIdSucursal() != null) {
            return this.registro.getIdSucursal().getIdSucursal();
        }
        return null;
    }
    public void setIdSucursalSeleccionada(Integer idSucursal){
        if (this.registro != null && this.sucursalList != null && !this.sucursalList.isEmpty()) {
            this.registro.setIdSucursal(this.sucursalList.stream().filter(tp -> tp.getIdSucursal().equals(idSucursal)).findFirst().orElse(null));
        }
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdSala(id + 1);
            } else {
                registro.setIdSala(1);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
        }
    }

    public void btnGuardar(ActionEvent event) {
        if (registro == null || registro.getIdSucursal() == null || registro.getNombre() == null || registro.getNombre().isEmpty() || registro.getActivo()== null ||registro.getObservaciones().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, Llene el formulario", "El nombre es requerido"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado con éxito", "Registro guardado"));
            super.btnGuardar(event, this.registro);
        }
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
    }

    public void btnEditar(ActionEvent event) {
        if (registro == null || registro.getNombre() == null || registro.getNombre().isEmpty() || registro.getActivo()== null ||registro.getObservaciones()== null || registro.getObservaciones().isEmpty() ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, Llene el formulario", "El nombre es requerido"));
        }else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Editado con éxito:", "Registro editado"));
            super.btnEditar(event, this.registro);
        }
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        idAsiento = registro.getIdSala();
    }

    public void cambiarTab(TabChangeEvent tce) {
        if (tce.getTab().getTitle().equals("Caracteristicas")) {
            if (this.registro != null && this.frmSalaCaracteristica != null) {
                this.frmSalaCaracteristica.setIdSala(this.registro.getIdSala());
            }
        } else if (tce.getTab().getTitle().equals("Asientos")) {
            if (this.registro != null && this.frmAsiento != null) {
                this.frmAsiento.setIdSala(this.registro.getIdSala());
            }
        } else if (tce.getTab().getTitle().equals("Programacion")) {
            if (this.registro != null && this.frmProgramacion != null) {
                this.frmProgramacion.setIdSala(this.registro.getIdSala());
            }
        }
    }

    public FrmSalaCaracteristica getFrmSalaCaracteristica() {
        return frmSalaCaracteristica;
    }

    public void setFrmSalaCaracteristica(FrmSalaCaracteristica frmSalaCaracteristica) {
        this.frmSalaCaracteristica = frmSalaCaracteristica;
    }

    public FrmAsiento getFrmAsiento() {
        return frmAsiento;
    }

    public void setFrmAsiento(FrmAsiento frmAsiento) {
        this.frmAsiento = frmAsiento;
    }

    public FrmProgramacion getFrmProgramacion() {
        return frmProgramacion;
    }

    public void setFrmProgramacion(FrmProgramacion frmProgramacion) {
        this.frmProgramacion = frmProgramacion;
    }

    public String getNombreSucursal(Sala sala) {
        if (sala != null && sala.getIdSucursal() != null) {
            return sala.getIdSucursal().getNombre();
        }
        return "MISTAKE";
    }
}