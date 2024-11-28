package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.Dependent;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Long idAsiento;
    List<TipoAsiento> tipoAsientoList;
    TipoAsiento selectedTipoAsiento;

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
    public String getRowKey(AsientoCaracteristica object) {//el object esta como entity en el otro
        if (object != null && object.getIdTipoAsiento() != null) {
            return object.getIdAsientoCaracteristica().toString();
        }
        return null;

    }

    @Override
    public AsientoCaracteristica getRowData(String rowKey) {
        if (rowKey != null) {
            try {
                return ACB.findById(Long.parseLong(rowKey));
                //return this.modelo.getWrappedData().stream().filter(r -> r.getIdAsientoCaracteristica().toString().equals(rowKey)).findFirst().orElse(null);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<AsientoCaracteristica> load(int firstResult, int maxResults, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        try {
            if (this.idAsiento != null && ACB != null) {
                return ACB.findByIdAsiento(this.idAsiento, firstResult, maxResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public int count(Map<String, FilterMeta> filterMeta) {
        try {
            if (idAsiento != null && ACB != null) {
                return ACB.countByIdAsiento(this.idAsiento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected AbstractDataPersistence<AsientoCaracteristica> getDataBean() {
        return ACB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected AsientoCaracteristica createNewInstance() {//devuelve el registro
        try {
            registro = new AsientoCaracteristica();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*@Override
    protected AsientoCaracteristica createNewEntity() {//este metodo ayudara a poner el id
        AsientoCaracteristica ac =new AsientoCaracteristica();
        if(idAsiento != null){
            Asiento asiento = new Asiento();
            asiento.setIdAsiento(idAsiento);
            ac.setIdAsiento(asiento);
        }
        if(tipoAsientoList != null && !tipoAsientoList.isEmpty()){
            ac.setIdTipoAsiento(tipoAsientoList.get(0));
        }
        return ac;
    }*/


    @Override
    public AsientoCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
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
        return "Asiento Caracteristica";
    }

    public Integer getIdTipoAsientoSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoAsiento() != null) {
            return this.registro.getIdTipoAsiento().getIdTipoAsiento();
        }
        return null;
    }

    public void setIdTipoAsientoSeleccionado(final Integer idTipoAsiento) {
        if (this.registro != null && this.tipoAsientoList != null && !this.tipoAsientoList.isEmpty()) {
            this.registro.setIdTipoAsiento(this.tipoAsientoList.stream().filter(r -> r.getIdTipoAsiento().equals(idTipoAsiento)).findFirst().orElse(null));
        }
    }

    public void validarValor(FacesContext facesContext, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;

        // Verificar si el registro y el tipo de película son válidos
        if (registro != null && this.registro.getIdTipoAsiento() != null) {
            String nuevoValor = valor.toString();
            String expresionRegular = this.registro.getIdTipoAsiento().getExpresionRegular();
            Pattern patron = Pattern.compile(expresionRegular);
            Matcher validador = patron.matcher(nuevoValor);

            // Validar según la expresión regular definida
            if (validador.matches()) {
                input.setValid(true);
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Validación exitosa", "El valor ingresado es correcto"));
                return;
            } else {
                // Si no cumple con la expresión regular
                input.setValid(false);
                if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("CLIMA")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Aire Acondicionado,Calefaccion,\nVentilacion,Climatizado");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("ERGONOMIA")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Ergonomico,Comodo,Ajustable,\nRespaldo Lumbar,Reposabrazos");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                } else if (registro.getIdTipoAsiento() != null && registro.getIdTipoAsiento().getNombre().equals("ACCESIBILIDAD")) {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Formato esperado", "Rampas,Ascensor,Espacio,\nAcesible,Silla de ruedas");
                    facesContext.addMessage(componente.getClientId(facesContext), message);
                }


            }
        }


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

    public AsientoCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(AsientoCaracteristica registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        System.out.println("REGISTRO NUEVO DE FrmAsientoCaracteristica: " + estado);
        Long id = ACB.findLastId();
        try {
            if (id != null) {
                registro.setIdAsientoCaracteristica((id + 1));
            } else {
                registro.setIdAsientoCaracteristica(Long.valueOf(1));
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

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmSalaCaracteristica: " + estado);
    }

    public void setIdAsiento(long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public AsientoCaracteristicaBean getACB() {
        return ACB;
    }

    public void setACB(AsientoCaracteristicaBean ACB) {
        this.ACB = ACB;
    }

    public TipoAsientoBean getTAB() {
        return TAB;
    }

    public void setTAB(TipoAsientoBean TAB) {
        this.TAB = TAB;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    public void setTipoAsientoList(List<TipoAsiento> tipoAsientoList) {
        this.tipoAsientoList = tipoAsientoList;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public List<AsientoCaracteristica> getCaracteristicas() {
        if (idAsiento != null) {
            return ACB.findByIdAsiento(idAsiento, 0, Integer.MAX_VALUE);
        }
        return List.of();
    }

    public long getIdAsiento() {
        return idAsiento;
    }


    public TipoAsiento getSelectedTipoAsiento() {
        return selectedTipoAsiento;
    }

    public void setSelectedTipoAsiento(TipoAsiento selectedTipoAsiento) {
        this.selectedTipoAsiento = selectedTipoAsiento;
    }

    public void cargarCaracteristicas() {
        try {
            if (idAsiento != null) {
                // Recarga los datos del modelo con las características del asiento
                modelo.setWrappedData(ACB.findByIdAsiento(idAsiento, 0, Integer.MAX_VALUE));
            } else {
                modelo.setWrappedData(List.of());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
