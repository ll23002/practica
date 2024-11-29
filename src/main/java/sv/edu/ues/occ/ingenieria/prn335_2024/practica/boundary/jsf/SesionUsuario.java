package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named
@ViewScoped
public class SesionUsuario implements Serializable {
    @Inject
    FacesContext facesContext;
    Map<String, Locale> idiomas=new HashMap<>();
    String idiomaSeleccionado;

    @PostConstruct
    public void init() {
        idiomas.put("English", new Locale.Builder().setLanguage("en").build());
        idiomas.put("Español", new Locale.Builder().setLanguage("es").build());
    }

    public Map<String, Locale> getIdiomas() {
        return idiomas;
    }

    public String getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(String idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

public void cambiarIdioma(ValueChangeEvent event) {
    String idioma = event.getNewValue().toString();
    String idiomaNombre = idioma.split("=")[0].trim();
    for (Map.Entry<String, Locale> entry : idiomas.entrySet()) {
        if (entry.getKey().equals(idiomaNombre)) {
            facesContext.getViewRoot().setLocale(entry.getValue());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Idioma cambiado", "Nuevo idioma: " + idiomaNombre);
            facesContext.addMessage(null, message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Idioma no cambiado", "No se pudo cambiar el idioma");
            facesContext.addMessage(null, message);
        }
    }
}
}
