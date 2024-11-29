package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoProducto;

import java.io.Serializable;

@Named
@SessionScoped
public class FrmTipoProducto extends FrmAbstractPersistence<TipoProducto> implements Serializable {
    @Inject
    TipoProductoBean TPB;
    @Inject
    FacesContext facesContext;
    TipoProducto registro;
    LazyDataModel<TipoProducto> modelo;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        System.out.println("Estado: " + estado);
    }

    @Override
    protected AbstractDataPersistence<TipoProducto> getDataBean() {
        return TPB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected TipoProducto createNewInstance() {
        try {
            registro = new TipoProducto();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TipoProducto buscarRegistroPorId(String id) {
        return null;
    }

    @Override
    public String buscarIdPorRegistro(TipoProducto dato) {
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Tipo de Producto";
    }

    @Override
    protected Object getId(TipoProducto object) {
        return object.getIdTipoProducto();
    }

    public LazyDataModel<TipoProducto> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<TipoProducto> modelo) {
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

    public TipoProducto getRegistro() {
        return registro;
    }

    public void setRegistro(TipoProducto registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);
        Integer id = TPB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoProducto(id + 1);
            } else {
                registro.setIdTipoProducto(1);
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