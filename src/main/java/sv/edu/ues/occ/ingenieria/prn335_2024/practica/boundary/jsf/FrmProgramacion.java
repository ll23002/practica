package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sala;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class FrmProgramacion extends FrmAbstractPersistence<Programacion> implements Serializable {
    @Inject
    ProgramacionBean dataBean;
    @Inject
    PeliculaBean peliculaBean;
    @Inject
    FrmSala frmSala;
    List<Pelicula> peliculasFiltradas;
    @Inject
    FacesContext facesContext;

    Programacion registro;
    Sala Sala;
    LazyDataModel<Programacion> modelo;
    ScheduleModel eventModel;
    Integer idSala;
    boolean mostrarSchedule = true;
    boolean mostrarDialogo = false;
    LocalDateTime localDesde;
    LocalDateTime localHasta;
    Pelicula nuevaPelicula;

    @PostConstruct
    public void inicializar() {
        modelo = this;
        estado = ESTADO_CRUD.NONE;
        eventModel = new DefaultScheduleModel();
        cargarEventos();
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
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
            return null;
        }
    }

    @Override
    public Programacion buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return dataBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
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
        super.btnNuevo(event);
        mostrarSchedule = false;
        if (frmSala != null && frmSala.getRegistro() != null && frmSala.getRegistro().getIdSala() != null) {
            Sala sala = new Sala();
            sala.setIdSala(frmSala.getRegistro().getIdSala());
            this.registro.setIdSala(sala);
        }
        Integer id = dataBean.findLastId();

        try {
            if (id != null) {
                registro.setIdProgramacion(id + 1);
            } else {
                registro.setIdProgramacion(1);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnGuardar(ActionEvent event) {
        if (registro == null) {
            registro = new Programacion();
        }

        if (localDesde != null) {
            registro.setDesde(localDesde.atOffset(ZoneOffset.UTC));
        }
        if (localHasta != null) {
            registro.setHasta(localHasta.atOffset(ZoneOffset.UTC));
        }
        if (registro.getDesde() == null || registro.getHasta() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas 'Desde' y 'Hasta' son obligatorias."));
            return;
        }

        try {
            mostrarSchedule = true;

            convertirYGuardarFecha();
            super.btnGuardar(event, this.registro);
            mostrarDialogo = false;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el registro."));
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
    }

    public void btnCancelar(ActionEvent event) {
        mostrarSchedule = true;
        mostrarDialogo = false;
        super.btnCancelar(event, this.registro);
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
        btnNuevo(null);
        localHasta = null;
        nuevaPelicula = null;
        mostrarDialogo = true;
    }

    private Programacion findProgramacionByEvent(ScheduleEvent<?> event) {
        return dataBean.findById(Integer.parseInt(event.getId()));
    }

    public void convertirYGuardarFecha() {
        if (localDesde != null) {
            registro.setDesde(localDesde.atOffset(ZoneOffset.of("-06:00")));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una fecha de inicio"));
        }
        if (localHasta != null) {
            registro.setHasta(localHasta.atOffset(ZoneOffset.of("-06:00")));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una fecha de fin"));
        }
    }

   public List<Pelicula> completarPelicula(String query) {
    try {
        List<Pelicula> todasPeliculas = peliculaBean.findAll();
        return todasPeliculas.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    } catch (Exception e) {
        Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        return new ArrayList<>();
    }
}

public void seleccionarPelicula(SelectEvent<Pelicula> event) {
    if (event.getObject() != null) {
        this.nuevaPelicula = event.getObject();
        if (this.registro != null) {
            this.registro.setIdPelicula(nuevaPelicula);
        }
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

    public Pelicula getNuevaPelicula() {
        if (registro == null){
            return null;
        }
        if (this.registro.getIdPelicula() != null) {
            nuevaPelicula = this.registro.getIdPelicula();
            return nuevaPelicula;
        }else {
            return null;
        }
    }

    public void setNuevaPelicula(Pelicula nuevaPelicula) {
        this.nuevaPelicula = nuevaPelicula;
    }

    public Integer getIdSala() {
        return idSala;
    }

    public void setIdSala(Integer idSala) {
        this.idSala = idSala;
    }

    public List<Pelicula> getPeliculasFiltradas() {
        return peliculasFiltradas;
    }

    public void setPeliculasFiltradas(List<Pelicula> peliculasFiltradas) {
        this.peliculasFiltradas = peliculasFiltradas;
    }

    public FrmSala getFrmSala() {
        return frmSala;
    }

    public void setFrmSala(FrmSala frmSala) {
        this.frmSala = frmSala;
    }
}
