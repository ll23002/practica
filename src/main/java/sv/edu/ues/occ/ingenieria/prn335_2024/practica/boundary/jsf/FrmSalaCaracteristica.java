package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
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
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.SalaCaracteristicaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoAsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.SalaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Dependent
public class FrmSalaCaracteristica extends FrmAbstractPersistence<SalaCaracteristica> implements Serializable {
    @Inject
    SalaCaracteristicaBean SCB;
    @Inject
    TipoSalaBean TSB;
    @Inject
    TipoAsientoBean TAB;
    @Inject
    FacesContext facesContext;
    SalaCaracteristica registro;
    LazyDataModel<SalaCaracteristica> modelo;

    Integer idSala;
    List<TipoSala> tipoSalaList;
    List<TipoAsiento> tipoAsientoList;

    @PostConstruct
    public void inicializar() {
        try {
            modelo = this;
            estado = ESTADO_CRUD.NONE;
            System.out.println("Estado: " + estado);
            this.tipoSalaList = TSB.findRange(0, Integer.MAX_VALUE);
            this.tipoAsientoList = TAB.findRange(0, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SalaCaracteristica> load(int firstResult, int maxResults, Map<String, SortMeta> sortMeta, Map<String, FilterMeta> filterMeta) {
        try {
            if (this.idSala != null && SCB != null) {
                return SCB.findByIdSala(idSala, firstResult, maxResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public int count(Map<String, FilterMeta> filterMeta) {
        try {
            if (this.idSala != null && SCB != null) {
                return SCB.countByIdSala(idSala);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected AbstractDataPersistence<SalaCaracteristica> getDataBean() {
        return SCB;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected SalaCaracteristica createNewInstance() {
        try {
            registro = new SalaCaracteristica();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SalaCaracteristica buscarRegistroPorId(String id) {
        if (id != null && this.modelo != null) {
            return this.modelo.getWrappedData().stream().filter(s -> s.getIdSalaCaracteristica().toString().equals(id)).findFirst().orElse(null);
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(SalaCaracteristica dato) {
        if (dato != null && dato.getIdSalaCaracteristica() != null) {
            return dato.getIdSalaCaracteristica().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return " ";
    }

    @Override
    protected Object getId(SalaCaracteristica object) {
        return object.getIdSalaCaracteristica();
    }

    public LazyDataModel<SalaCaracteristica> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<SalaCaracteristica> modelo) {
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

    public SalaCaracteristica getRegistro() {
        return registro;
    }

    public void setRegistro(SalaCaracteristica registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        System.out.println("REGISTRO NUEVO DE FrmSalaCaracteristica: " + estado);
        Integer id = SCB.findLasId();
        try {
            if (id != null) {
                registro.setIdSalaCaracteristica( (id + 1));
            } else {
                registro.setIdSalaCaracteristica(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
        System.out.println("REGISTRO GUARDADO DE FrmSalaCaracteristica: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
        System.out.println("REGISTRO CANCELADO DE FrmSalaCaracteristica: " + estado);
    }

    public void btnEditar(ActionEvent event) {

        super.btnEditar(event, this.registro);
        System.out.println("REGISTRO EDITADO DE FrmSalaCaracteristica: " + estado);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
        System.out.println("REGISTRO ELIMINADO DE FrmSalaCaracteristica: " + estado);
    }

    public SalaCaracteristica crearNuevo() {
        SalaCaracteristica sc = new SalaCaracteristica();
        if (idSala != null) {
            sc.setIdSala(new Sala(idSala));
        }
        if (tipoSalaList != null && !tipoSalaList.isEmpty()) {
            sc.setIdTipoSala(tipoSalaList.get(0));
        }
        return sc;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public List<TipoSala> getTipoSalaList() {
        return tipoSalaList;
    }

    public List<TipoAsiento> getTipoAsientoList() {
        return tipoAsientoList;
    }

    public Integer getIdTipoSalaSeleccionada() {
        if (registro != null && registro.getIdTipoSala() != null) {
            return this.registro.getIdTipoSala().getIdTipoSala();
        }
        return null;
    }

    public void setIdTipoSalaSeleccionada(Integer idTipoSala) {
        if (this.registro != null && tipoSalaList != null && !tipoSalaList.isEmpty()) {
            this.registro.setIdTipoSala(tipoSalaList.stream().filter(ts -> ts.getIdTipoSala().equals(idTipoSala)).findFirst().orElse(null));
        }
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmSalaCaracteristica: " + estado);
    }

    public void validarValor(FacesContext fc, UIComponent component, Object valor) {
        UIInput input = (UIInput) component;
        if (registro != null && this.registro.getIdTipoSala() != null) {
            String nuevo = valor.toString();
            Pattern patron = Pattern.compile(this.registro.getIdTipoSala().getExpresionRegular());
            Matcher validador = patron.matcher(nuevo);
            if (validador.find()) {
                input.setValid(true);
                return;
            }
        }
        input.setValid(false);
    }
}