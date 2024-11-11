package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped
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
    // Extraer solo el nombre del idioma antes del signo '='
    String idiomaNombre = idioma.split("=")[0].trim();
    System.out.println("Nuevo idioma seleccionado: " + idiomaNombre);
    for (Map.Entry<String, Locale> entry : idiomas.entrySet()) {
        System.out.println("Comparando con clave: " + entry.getKey());
        if (entry.getKey().equals(idiomaNombre)) {
            facesContext.getViewRoot().setLocale(entry.getValue());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Idioma cambiado", "Nuevo idioma: " + idiomaNombre);
            facesContext.addMessage(null, message);
            System.out.println("Idioma cambiado a: " + idiomaNombre);
        } else {
            System.out.println("No se pudo cambiar el idioma");
        }
    }
}
}
