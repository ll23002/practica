package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Named
@SessionScoped
public class FrmProgramacion extends FrmAbstractPersistence<Programacion> implements Serializable {
    @Inject
    ProgramacionBean dataBean;
    @Inject
    FacesContext facesContext;
    Programacion registro;
    LazyDataModel<Programacion> modelo;
    private ScheduleModel eventModel;
    Integer idSala;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        eventModel = new DefaultScheduleModel();
        cargarEventos();
        System.out.println("Estado: " + estado);
    }

    @Override
    protected AbstractDataPersistence<Programacion> getDataBean() {
        return dataBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Programacion createNewInstance() {
        try {
            registro = new Programacion();
            return registro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Programacion buscarRegistroPorId(String id) {
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
    public String buscarIdPorRegistro(Programacion dato) {
        if (dato != null && dato.getIdProgramacion() != null) {
            return dato.getIdProgramacion().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Gestión de Programación";
    }

    @Override
    protected Object getId(Programacion object) {
        return object.getIdProgramacion();
    }

    public LazyDataModel<Programacion> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Programacion> modelo) {
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

    public Programacion getRegistro() {
        return registro;
    }

    public void setRegistro(Programacion registro) {
        this.registro = registro;
    }

    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event, this.registro);
        System.out.println("Registro nuevo en FrmProgramacion: " + estado);
        Integer id = dataBean.findLastId();
        try {
            if (id != null) {
                registro.setIdProgramacion(id + 1);
            } else {
                registro.setIdProgramacion(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnGuardar(ActionEvent event) {
        super.btnGuardar(event, this.registro);
        System.out.println("Registro guardado en FrmProgramacion: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        super.btnCancelar(event, this.registro);
        System.out.println("Registro cancelado en FrmProgramacion: " + estado);
    }

    public void btnEditar(ActionEvent event) {
        super.btnEditar(event, this.registro);
        System.out.println("Registro editado en FrmProgramacion: " + estado);
    }

    public void btnEliminar(ActionEvent event) {
        super.btnEliminar(event, this.registro);
        System.out.println("Registro eliminado en FrmProgramacion: " + estado);
    }

    @Override
    public void onRowSelect() {
        super.onRowSelect();
        System.out.println("Registro seleccionado en FrmProgramacion: " + estado);
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    private void cargarEventos() {
        List<Programacion> programaciones = dataBean.findAll();
        for (Programacion programacion : programaciones) {
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title(programacion.getIdPelicula().getNombre())
                    .startDate(LocalDateTime.ofInstant(programacion.getDesde().toInstant(), ZoneOffset.UTC))
                    .endDate(LocalDateTime.ofInstant(programacion.getHasta().toInstant(), ZoneOffset.UTC))
                    .description(programacion.getComentarios())
                    .build();
            eventModel.addEvent(event);
        }
    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        ScheduleEvent<?> event = selectEvent.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento seleccionado", event.getTitle());
        FacesContext.getCurrentInstance().addMessage(null, message);

        this.registro = findProgramacionByEvent(event);
    }

    private Programacion findProgramacionByEvent(ScheduleEvent<?> event) {
        // Implement the logic to find the Programacion entity based on the event
        return dataBean.findById(Integer.parseInt(event.getId()));
    }
}