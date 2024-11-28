package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;

import java.io.Serializable;

@Named
@SessionScoped
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
        System.out.println("Estado: " + estado);
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
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Asiento buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
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
        super.btnNuevo(event, this.registro);
        System.out.println("Registro nuevo en FrmAsiento: " + estado);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdAsiento(Long.valueOf(id+1) );
            } else {
                registro.setIdAsiento(Long.valueOf(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
        System.out.println("Registro guardado en FrmAsiento: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
        System.out.println("Registro cancelado en FrmAsiento: " + estado);
    }

    public void btnEditar(ActionEvent event) {
        super.btnEditar(event, this.registro);
        System.out.println("Registro editado en FrmAsiento: " + estado);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
        System.out.println("Registro eliminado en FrmAsiento: " + estado);
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmAsiento: " + estado);
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


    public void setFrmAsientoCaracteristica(FrmAsientoCaracteristica frmAsientoCaracteristica) {
        this.frmAsientoCaracteristica = frmAsientoCaracteristica;
    }
}