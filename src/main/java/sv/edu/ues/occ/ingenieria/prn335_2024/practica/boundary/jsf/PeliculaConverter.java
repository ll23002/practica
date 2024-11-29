package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;

import java.util.logging.Logger;

@FacesConverter("peliculaConverter")
public class PeliculaConverter implements Converter {

    @Override
public Object getAsObject(FacesContext context, UIComponent component, String value) {
    try {
        if (value == null || value.isEmpty()) {
            return null;
        }
        PeliculaBean peliculaService = context.getApplication().evaluateExpressionGet(context, "#{peliculaService}", PeliculaBean.class);
        return peliculaService.findById(Integer.valueOf(value));
    } catch (Exception e) {
        Logger.getLogger(getClass().getName()).severe(e.getMessage());
        return null;
    }
}

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof Pelicula) {
            Pelicula pelicula = (Pelicula) object;
            return pelicula.getIdPelicula().toString();
        } else {
            throw new IllegalArgumentException("Objeto no es de tipo Pelicula");
        }
    }
}

