package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;

import java.io.Serializable;

@Named
@SessionScoped
public class FrmTipoSala extends FrmAbstractPersistence<TipoSala> implements Serializable {
    @Inject
    TipoSalaBean TSB;
    @Inject
    FacesContext facesContext;
    TipoSala registro;
    LazyDataModel<TipoSala> modelo;//modelo es una variable de tipo LazyDataModel que se encarga de almacenar los datos de la tabla TipoSala.
    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        System.out.println("Estado: " + estado);
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
            e.printStackTrace();
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
        return null;
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
        super.btnNuevo(event, this.registro);
        Integer id = TSB.findLastId();
        registro.setActivo(true);
        try {
            if (id != null) {
                registro.setIdTipoSala(id + 1);//Se incrementa el id del registro en 1 porque está en base cero.
            } else {
                registro.setIdTipoSala(1);//Si no hay registros, se asigna el id 1.
            }
        } catch (Exception e) {
            e.printStackTrace();
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
