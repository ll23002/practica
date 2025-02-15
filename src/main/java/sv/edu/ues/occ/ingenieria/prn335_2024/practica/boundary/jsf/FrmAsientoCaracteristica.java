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
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.AsientoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
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

    @PostConstruct
    public void inicializar() {
        try {
            modelo = this;
            estado = ESTADO_CRUD.NONE;
            this.tipoAsientoList = TAB.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }
    }

    @Override
    public String getRowKey(AsientoCaracteristica object) {
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
            } catch (NumberFormatException e) {
                Logger.getLogger(getClass().getName()).severe(e.getMessage());
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
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
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
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
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
    protected AsientoCaracteristica createNewInstance() {
        try {
            registro = new AsientoCaracteristica();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public AsientoCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            try {
                return this.modelo.getWrappedData().stream().filter(r -> r.getIdAsientoCaracteristica().toString().equals(id)).findFirst().orElse(null);
            } catch (NumberFormatException e) {
                Logger.getLogger(getClass().getName()).severe(e.getMessage());
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

    public void validarValor(FacesContext facesContext, UIComponent componente, Object valor) {
        UIInput input = (UIInput) componente;

        if (registro != null && this.registro.getIdTipoAsiento() != null) {
            String nuevoValor = valor.toString();
            String expresionRegular = this.registro.getIdTipoAsiento().getExpresionRegular();
            Pattern patron = Pattern.compile(expresionRegular);
            Matcher validador = patron.matcher(nuevoValor);

            if (validador.matches()) {
                input.setValid(true);
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Validación exitosa", "El valor ingresado es correcto"));
                return;
            } else {
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
        super.btnNuevo(event);
        Long id = ACB.findLastId();
        try {
            if (id != null) {
                registro.setIdAsientoCaracteristica((id + 1));
            } else {
                registro.setIdAsientoCaracteristica(Long.valueOf(1));
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
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

    @Override
    public void onRowSelect() {
        super.onRowSelect();
    }
    public void setIdAsiento(long idAsiento) {
        this.idAsiento = idAsiento;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public void cargarCaracteristicas() {
        try {
            if (idAsiento != null) {
                modelo.setWrappedData(ACB.findByIdAsiento(idAsiento, 0, Integer.MAX_VALUE));
            } else {
                modelo.setWrappedData(List.of());
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe(e.getMessage());
        }
    }
}
