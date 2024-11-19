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
import java.time.OffsetDateTime;
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
    ScheduleModel eventModel;
    Integer idSala;
    boolean mostrarSchedule = true;
    boolean mostrarDialogo = false; // Para manejar la visibilidad del diálogo
    LocalDateTime localDesde;
    LocalDateTime localHasta;
    String nuevaPelicula;

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
        return "Programación";
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
        mostrarSchedule = false;
        System.out.println("Registro nuevo en FrmProgramacion: " + estado);
    }

    public void btnGuardar(ActionEvent event) {
        mostrarSchedule = true;
        convertirYGuardarFecha();
        super.btnGuardar(event, this.registro);
        mostrarDialogo = false;
        System.out.println("Registro guardado en FrmProgramacion: " + estado);
    }

    public void btnCancelar(ActionEvent event) {
        mostrarSchedule = true;
        mostrarDialogo = false;
        super.btnCancelar(event, this.registro);
        System.out.println("Registro cancelado en FrmProgramacion: " + estado);
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

    public void onDateSelect(SelectEvent<LocalDateTime> event) {
        localDesde = event.getObject();
        localHasta = null; // Dejar hasta vacío inicialmente
        nuevaPelicula = ""; // Vaciar el nombre de la película
        mostrarDialogo = true; // Mostrar el cuadro de diálogo
        System.out.println("Fecha seleccionada: " + localDesde);
    }

    private Programacion findProgramacionByEvent(ScheduleEvent<?> event) {
        return dataBean.findById(Integer.parseInt(event.getId()));
    }

    public void convertirYGuardarFecha() {
        if (localDesde != null) {
            registro.setDesde(localDesde.atOffset(ZoneOffset.of("-06:00")));
        }
        if (localHasta != null) {
            registro.setHasta(localHasta.atOffset(ZoneOffset.of("-06:00")));
        }
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public LocalDateTime getLocalDesde() {
        return localDesde;
    }

    public void setLocalDesde(LocalDateTime localDesde) {
        this.localDesde = localDesde;
    }

    public LocalDateTime getLocalHasta() {
        return localHasta;
    }

    public void setLocalHasta(LocalDateTime localHasta) {
        this.localHasta = localHasta;
    }

    public boolean isMostrarSchedule() {
        return mostrarSchedule;
    }

    public void setMostrarSchedule(boolean mostrarSchedule) {
        this.mostrarSchedule = mostrarSchedule;
    }

    public boolean isMostrarDialogo() {
        return mostrarDialogo;
    }

    public void setMostrarDialogo(boolean mostrarDialogo) {
        this.mostrarDialogo = mostrarDialogo;
    }

    public String getNuevaPelicula() {
        return nuevaPelicula;
    }

    public void setNuevaPelicula(String nuevaPelicula) {
        this.nuevaPelicula = nuevaPelicula;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }
}
