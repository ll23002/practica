package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

@Named
@Dependent
public class FrmAsientoCaracteristica extends FrmAbstractPersistence<AsientoCaracteristica> implements Serializable {
@Inject
AsientoCaracteristicaBean ACB;
@Inject
TipoAsientoBean TAB;
@Inject
FacesContext facesContext;
AsientoCaracteristica registro;
LazyDataModel<AsientoCaracteristica> modelo;
Integer idAsiento;
List<TipoAsiento> tipoAsientoList;

@PostConstruct
public void inicializar() {
    try {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        System.out.println("Estado: " + estado);
        this.tipoAsientoList = TAB.findRange(0, Integer.MAX_VALUE);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @Override
    public List<AsientoCaracteristica> load(int firstResult, int maxResults, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        try {
            if (this.idAsiento != null && ACB != null) {
                return ACB.findByIdAsiento(idAsiento, firstResult, maxResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public int count(Map<String, FilterMeta> filterMeta) {
        try {
            if (this.idAsiento != null && ACB != null) {
                return ACB.countByIdAsiento(idAsiento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected AbstractDataPersistence<AsientoCaracteristica> getDataBean() {return ACB;}

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected AsientoCaracteristica createNewInstance() {
        try {
            registro = new AsientoCaracteristica();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AsientoCaracteristica buscarRegistroPorId(String id) {
    if(id!=null && this.modelo != null){
        try {
            return this.modelo.getWrappedData().stream().filter(r -> r.getIdAsientoCaracteristica().toString().equals(id)).findFirst().orElse(null);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
    return null;
    }

    @Override
    public String buscarIdPorRegistro(AsientoCaracteristica dato) {
    if (dato != null && dato.getIdAsientoCaracteristica() != null) {
        return dato.getIdAsientoCaracteristica().toString();
    }
    return null;
    }

    @Override
    public String getTituloPagina() {
        return "";
    }

    @Override
    protected Object getId(AsientoCaracteristica object) {
        return object.getIdAsientoCaracteristica();
    }

    public LazyDataModel<AsientoCaracteristica> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<AsientoCaracteristica> modelo) {
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

    public AsientoCaracteristica getRegistro() {return registro;}

    public void setRegistro(AsientoCaracteristica registro) {this.registro = registro;}

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        System.out.println("REGISTRO NUEVO DE FrmAsientoCaracteristica: " + estado);
        Integer id = ACB.findLastId();
        try {
            if (id != null) {
                registro.setIdAsientoCaracteristica((id + 1));
            } else {
                registro.setIdAsientoCaracteristica(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
        System.out.println("REGISTRO GUARDADO DE FrmAsientoCaracteristica: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
        System.out.println("REGISTRO CANCELADO DE FrmAsientoCaracteristica: " + estado);
    }

    public void btnEditar(ActionEvent event) {

        super.btnEditar(event, this.registro);
        System.out.println("REGISTRO EDITADO DE FrmAsientoCaracteristica: " + estado);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
        System.out.println("REGISTRO ELIMINADO DE FrmAsientoCaracteristica: " + estado);
    }

    public Integer getIdAsiento() {
        return idAsiento;
    }

    public void setIdAsiento(Integer idAsiento) {
        this.idAsiento = idAsiento;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmSalaCaracteristica: " + estado);
    }


    public List<AsientoCaracteristica> getCaracteristicas() {
        if (idAsiento != null) {
            return ACB.findByIdAsiento(idAsiento, 0, Integer.MAX_VALUE);
        }
        return List.of();
    }


}
