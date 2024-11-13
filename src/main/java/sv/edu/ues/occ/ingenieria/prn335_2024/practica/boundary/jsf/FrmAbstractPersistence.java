package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.event.ActionEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import java.util.List;
import java.util.Map;

public abstract class FrmAbstractPersistence<F> extends LazyDataModel<F> {
    protected ESTADO_CRUD estado;

    public ESTADO_CRUD getEstado() {
        return estado;
    }

    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    protected abstract AbstractDataPersistence<F> getDataBean();

    protected abstract FacesContext getFacesContext();

    protected abstract F createNewInstance();

    public abstract F buscarRegistroPorId(String id);

    public abstract String buscarIdPorRegistro(F dato);

    public abstract String getTituloPagina();

    protected abstract Object getId(F object);

    @Override
    public String getRowKey(F object) {//Se obtiene el identificador de la fila.
        try {
            return String.valueOf(getId(object));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public F getRowData(String rowKey) {//Se obtiene la fila de una tabla de la base de datos.
        try {
            return getDataBean().findById(Integer.parseInt(rowKey));//Se busca la fila en la base de datos por un identificador único.
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int count(Map<String, FilterMeta> filterMeta) {//Se cuenta el número de registros en la base de datos.
        try {
            return getDataBean().count();//Se retorna el número de registros en la base de datos.
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     *
     * @param first indice del primer registro a cargar
     * @param pageSize El numero maximo de registros a cargar
     * @param sorMeta información de ordenamiento(no se usa en nuestro caso)
     * @param filterMeta información de filtrado(no se usa en nuestro caso)
     * @return Lista de un rango de registros de la base de datos
     */
    @Override
    public List<F> load(int first, int pageSize, Map<String, SortMeta> sorMeta, Map<String, FilterMeta> filterMeta) {//Permite cargar un rango de datos de la base de datos.
        try {
            if (first>=0 && pageSize>0) {
                return getDataBean().findRange(first, pageSize);//Se obtiene un rango de registros de la base de datos.
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return List.of();//Por defecto retorna una lista vacia.
    }

    public void onRowSelect() {//Método que se encarga de cambiar el estado cuando se selecciona un registro.
        estado = ESTADO_CRUD.UPDATE;//Se cambia el estado a UPDATE.
    }

    public void btnNuevo(ActionEvent event, F registro) {//Método que se encarga de crear un registro en la base de datos.
        try {
            estado = ESTADO_CRUD.CREATE;//Se cambia el estado a CREATE.
            registro = createNewInstance();//Se crea una nueva instancia de la entidad.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnGuardar(ActionEvent event, F registro) {
        try {
            estado = ESTADO_CRUD.NONE;
            getDataBean().create(registro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro guardado", "El registro ha sido guardado exitosamente."));
            registro = null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el registro."));
            e.printStackTrace();
        }
    }

    public void btnCancelar(ActionEvent event, F registro) {
        try {
            estado = ESTADO_CRUD.NONE;
            registro = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operación cancelada", "La operación ha sido cancelada."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo cancelar la operación."));
            e.printStackTrace();
        }
    }

    public void btnEditar(ActionEvent event, F registro) {
        try {
            estado = ESTADO_CRUD.NONE;
            getDataBean().update(registro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro editado", "El registro ha sido editado exitosamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo editar el registro."));
            e.printStackTrace();
        }
    }

    public void btnEliminar(ActionEvent event, F registro) {
        try {
            estado = ESTADO_CRUD.NONE;
            getDataBean().delete(registro);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro eliminado", "El registro ha sido eliminado exitosamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el registro."));
            e.printStackTrace();
        }
    }


}
