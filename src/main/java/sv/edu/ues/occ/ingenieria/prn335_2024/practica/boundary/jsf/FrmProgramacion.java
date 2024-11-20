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
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sala;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
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
    boolean mostrarDialogo = false; // Para manejar la visibilidad del diálogo
    LocalDateTime localDesde;
    LocalDateTime localHasta;
    Pelicula nuevaPelicula;

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
        // Asignar idSala desde FrmSala
        if (frmSala != null && frmSala.getRegistro() != null && frmSala.getRegistro().getIdSala() != null) {
            Sala sala = new Sala(); // Crea una nueva instancia
            sala.setIdSala(frmSala.getRegistro().getIdSala()); // Asigna el idSala
            this.registro.setIdSala(sala); // Asigna el objeto Sala
            System.out.println("Sala asignada al nuevo registro de Programacion: " + sala.getIdSala());
        }
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
        if (registro == null) {
            registro = new Programacion(); // Inicializar si está nulo
            System.out.println("Registro inicializado en btnGuardar");
        }

        // Obtener las fechas seleccionadas en los campos de fecha
        if (localDesde != null) {
            registro.setDesde(localDesde.atOffset(ZoneOffset.UTC)); // Asignar el valor de txtDesde a 'desde'
        }
        if (localHasta != null) {
            registro.setHasta(localHasta.atOffset(ZoneOffset.UTC)); // Asignar el valor de txtHasta a 'hasta'
        }

        // Validar que ambos campos tengan valores
        if (registro.getDesde() == null || registro.getHasta() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Las fechas 'Desde' y 'Hasta' son obligatorias."));
            return;
        }

        try {
            mostrarSchedule = true;

            convertirYGuardarFecha();
            System.out.println("Registro a guardar: " + registro);
            super.btnGuardar(event, this.registro);
            mostrarDialogo = false;
            System.out.println("Registro guardado en FrmProgramacion: " + estado);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo guardar el registro."));
            e.printStackTrace();
        }
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
        btnNuevo(null); // Llama a btnNuevo de FrmProgramacion
        localHasta = null; // Dejar hasta vacío inicialmente
        nuevaPelicula = null; // Vaciar el nombre de la película
        mostrarDialogo = true; // Mostrar el cuadro de diálogo
        System.out.println("Fecha seleccionada: " + localDesde);
    }

    private Programacion findProgramacionByEvent(ScheduleEvent<?> event) {
        return dataBean.findById(Integer.parseInt(event.getId()));
    }

    public void convertirYGuardarFecha() {
        if (localDesde != null) {
            registro.setDesde(localDesde.atOffset(ZoneOffset.of("-06:00")));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una fecha de inicio"));
            System.out.println("Debe seleccionar una fecha de inicio");
        }
        if (localHasta != null) {
            registro.setHasta(localHasta.atOffset(ZoneOffset.of("-06:00")));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una fecha de fin"));
            System.out.println("Debe seleccionar una fecha de fin");
        }
    }

   public List<Pelicula> completarPelicula(String query) {
    try {
        List<Pelicula> todasPeliculas = peliculaBean.findAll(); // Recuperar todas las películas
        System.out.println("Películas disponibles: " + todasPeliculas.size());
        return todasPeliculas.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(query.toLowerCase())) // Filtrar por nombre
                .collect(Collectors.toList());
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>(); // Retornar lista vacía en caso de error
    }
}

public void seleccionarPelicula(SelectEvent<Pelicula> event) {
    System.out.println("Evento de selección de película");
    if (event.getObject() != null) {
        this.nuevaPelicula = event.getObject(); // Asigna la película seleccionada
        if (this.registro != null) {
            this.registro.setIdPelicula(nuevaPelicula); // Asigna la película completa al registro
        }
        System.out.println("Pelicula seleccionada: " + nuevaPelicula.getNombre() + " (ID: " + nuevaPelicula.getIdPelicula() + ")");
    }else{
        System.out.println("No se seleccionó ninguna película");
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
        System.out.println("Fecha de Hasta: " + localHasta);
        return localHasta;
    }

    public void setLocalHasta(LocalDateTime localHasta) {
        System.out.println("Fecha de Hasta: " + localHasta);
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
        System.out.println("Pelicula seleccionada: " + nuevaPelicula);
        return nuevaPelicula;
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
