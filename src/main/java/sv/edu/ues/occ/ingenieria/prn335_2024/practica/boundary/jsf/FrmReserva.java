package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

@Named
@ViewScoped
public class FrmReserva extends FrmAbstractPersistence<Reserva> implements Serializable {
    private DualListModel<String> asientos;
    @Inject
    ReservaBean rBean;

    @Inject
    TipoReservaBean trBean;

    @Inject
    ProgramacionBean pgBean;

    @Inject
    FrmTipoReserva frmTipoReserva;

    @Inject
    FacesContext facesContext;

    Reserva registro;
    List<TipoReserva> trList;
    List<Programacion> funciones;

    LazyDataModel<Reserva> modelo;

    LocalDate fechaSeleccionada;
    Programacion funcionSeleccionada;

    private List<Asiento> asientosDisponibles;
    private List<Asiento> asientosSeleccionados;


    private int activeTabIndex = 0;

    @PostConstruct
    public void inicializar() {
        try {
            List<String> asientosSource = new ArrayList<>();
            List<String> asientosTarget = new ArrayList<>();

            asientosSource.add("Asiento 1");
            asientosSource.add("Asiento 2");
            asientosSource.add("Asiento 3");
            asientosSource.add("Asiento 4");
            asientosSource.add("Asiento 5");

            asientos = new DualListModel<>(asientosSource, asientosTarget);
            modelo = this;
            estado = ESTADO_CRUD.NONE;
            registro = new Reserva();

            this.trList = trBean.findRange(0, Integer.MAX_VALUE);

            if (this.trList != null && !this.trList.isEmpty()) {
                this.setIdTipoReservaSeleccionado(this.trList.get(0).getIdTipoReserva());
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error(e);
        }
    }

    public DualListModel<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(DualListModel<String> asientos) {
        this.asientos = asientos;
    }

    public void reservar() {
        // Lógica para reservar los asientos seleccionados
        System.out.println("Asientos reservados: " + asientos.getTarget());
    }

    @Override
    protected AbstractDataPersistence<Reserva> getDataBean() {
        return rBean;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected Reserva createNewInstance() {
        try {
            registro = new Reserva();
            return registro;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
            return null;
        }
    }

    @Override
    public Reserva buscarRegistroPorId(String id) {
        if (id != null && !id.isEmpty()) {
            try {
                return rBean.findById(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                Logger.getLogger(this.getClass().getName()).error(e);
            }
        }
        return null;
    }

    @Override
    public String buscarIdPorRegistro(Reserva dato) {
        if (dato != null && dato.getIdReserva() != null) {
            return dato.getIdReserva().toString();
        }
        return null;
    }

    @Override
    public String getTituloPagina() {
        return "Reservas";
    }

    @Override
    protected Object getId(Reserva object) {
        return object.getIdReserva();
    }

    @Override
    public ESTADO_CRUD getEstado() {
        return estado;
    }

    @Override
    public void setEstado(ESTADO_CRUD estado) {
        this.estado = estado;
    }

    public LazyDataModel<Reserva> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<Reserva> modelo) {
        this.modelo = modelo;
    }

    public Reserva getRegistro() {
        return registro;
    }

    public void setRegistro(Reserva registro) {
        this.registro = registro;
    }

    public List<TipoReserva> getTrList() {
        return trList;
    }

    public void setTrList(List<TipoReserva> trList) {
        this.trList = trList;
    }

    public Integer getIdTipoReservaSeleccionado() {
        if (this.registro != null && this.registro.getIdTipoReserva() != null) {
            return this.registro.getIdTipoReserva().getIdTipoReserva();
        }

        return null;
    }

    public void setIdTipoReservaSeleccionado(Integer idTipoReserva) {
        if (this.registro != null && this.trList != null && !this.trList.isEmpty()) {
            this.registro.setIdTipoReserva(this.trList.stream().filter(tr -> tr.getIdTipoReserva().equals(idTipoReserva)).findFirst().orElse(null));
        }
    }

    public List<Programacion> getFunciones() {
        return pgBean.findRange(0, Integer.MAX_VALUE);
    }

    public void setFunciones(List<Programacion> funciones) {
        this.funciones = funciones;
    }

    public LocalDate getFechaSeleccionada() {
        return fechaSeleccionada;
    }

    public void setFechaSeleccionada(LocalDate fechaSeleccionada) {
        this.fechaSeleccionada = fechaSeleccionada;
    }

    public Programacion getFuncionSeleccionada() {
        return funcionSeleccionada;
    }

    public void setFuncionSeleccionada(Programacion funcionSeleccionada) {
        this.funcionSeleccionada = funcionSeleccionada;
    }

    public int getActiveTabIndex() {
        return activeTabIndex;
    }

    public void setActiveTabIndex(int activeTabIndex) {
        this.activeTabIndex = activeTabIndex;
    }

    public void nextTab() {
        System.out.println("Entra a nextTab");
        if (activeTabIndex < 3) {
            System.out.println(activeTabIndex);
            activeTabIndex++;
            System.out.println(activeTabIndex);
        }
    }

    public List<Programacion> completarFunciones(String query) {
        try {
            if (fechaSeleccionada != null) {

                // Convertir la fecha seleccionada a OffsetDateTime (inicio del día seleccionado)
                OffsetDateTime inicioDia = fechaSeleccionada.atStartOfDay().atOffset(OffsetDateTime.now().getOffset());
                OffsetDateTime finDia = inicioDia.plusDays(1); // Fin del día seleccionado

                // Llamar al método en ProgramacionBean para obtener las funciones por rango de fecha y nombre
                System.out.println("retornando en completarFunciones: " + pgBean.findFuncionesPorFechaYNombre(inicioDia, finDia, query));
                return pgBean.findFuncionesPorFechaYNombre(inicioDia, finDia, query);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }
        return new ArrayList<>();
    }


    public void seleccionarFuncion(SelectEvent<Programacion> event) {
        try {
            if (event.getObject() != null) {
                System.out.println("Selecciona función");
                this.funcionSeleccionada = event.getObject();
                System.out.println("Función seleccionada: " + this.funcionSeleccionada.getIdProgramacion());
                cargarInformacionDePelicula(); // Cargar información de la película seleccionada
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }

    }


    public void btnNuevo(ActionEvent event) {
        super.btnNuevo(event);

        Integer newId = rBean.findLastId();

        try {
            if (newId != null) {
                registro.setIdReserva(newId + 1);
            } else {
                registro.setIdReserva(1);
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }
    }

    public void cambiarTab(TabChangeEvent event) {
        String value = event.getTab().getTitle();

        switch (value) {
            case "Fecha":
                if (this.registro != null && this.registro.getIdTipoReserva() != null && this.registro.getFechaReserva() != null) {
                    this.frmTipoReserva.setIdReserva(this.registro.getIdReserva());
                }
                break;

            default:
                break;
        }
    }

    public void btnGuardar(ActionEvent event, Reserva registro) {
        if (registro == null || registro.getIdTipoReserva() == null
                || registro.getFechaReserva() == null
                || registro.getIdProgramacion() == null
                || registro.getObservaciones() == null) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Llene el formulario.", "Hay campos vacíos."));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Registro guardado con éxito.", "Reserva creada exitosamente."));

            super.btnGuardar(event, registro);
        }
    }

    public void btnCancelar(ActionEvent event, Reserva registro) {
        super.btnCancelar(event, registro);
    }

    public String continuar() {
        if (funcionSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una función."));
            return null;
        }
        // Lógica para continuar con la reserva.
        return "siguientePaso.xhtml";
    }


    // Nueva lista para almacenar características
    private List<PeliculaCaracteristica> peliculaCaracteristicas;

    // Método para obtener las características de la película seleccionada
    public void cargarInformacionDePelicula() {
        System.out.println("entra a cargar informacion de pelicula");
        try {
            if (funcionSeleccionada != null && funcionSeleccionada.getIdPelicula() != null) {
                System.out.println("Cargando información de la película");
                Pelicula pelicula = funcionSeleccionada.getIdPelicula();

                // Cargar características de la película
                this.peliculaCaracteristicas = rBean.findCaracteristicasByPelicula(pelicula.getIdPelicula());

                // Esto es opcional, pero asegura que los datos se imprimen en los logs
                System.out.println("Sinopsis: " + pelicula.getSinopsis());
                System.out.println("Características: " + this.peliculaCaracteristicas.size());
            } else {
                this.peliculaCaracteristicas = new ArrayList<>(); // Limpia la lista si no hay película seleccionada
                System.out.println("No hay película seleccionada");
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).error(e);
        }

    }


    // Getter para la lista de características
    public List<PeliculaCaracteristica> getPeliculaCaracteristicas() {
        return peliculaCaracteristicas;
    }

    // Método para obtener la sinopsis
    public String getSinopsisPeliculaSeleccionada() {
        if (funcionSeleccionada != null && funcionSeleccionada.getIdPelicula() != null) {
            return funcionSeleccionada.getIdPelicula().getSinopsis();
        }
        return null;
    }

    public void finalizarReserva() {
        // Lógica para finalizar la reserva
    }

}