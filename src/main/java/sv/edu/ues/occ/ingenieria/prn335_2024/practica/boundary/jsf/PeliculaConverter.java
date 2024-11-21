package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.PeliculaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;

@FacesConverter("peliculaConverter")
public class PeliculaConverter implements Converter {

    @Override
public Object getAsObject(FacesContext context, UIComponent component, String value) {
    try {
        if (value == null || value.isEmpty()) {
            System.out.println("Value is null");
            return null;
        }
        // Cambiar a Integer.valueOf
        PeliculaBean peliculaService = context.getApplication().evaluateExpressionGet(context, "#{peliculaService}", PeliculaBean.class);
        System.out.println(peliculaService.findById(Integer.valueOf(value)));
        return peliculaService.findById(Integer.valueOf(value)); // Cambiado a Integer
    } catch (Exception e) {
        System.out.println("Error en getAsObject: " + e.getMessage());
        e.printStackTrace();
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
            return pelicula.getIdPelicula().toString(); // O el identificador único de la película
        } else {
            throw new IllegalArgumentException("Objeto no es de tipo Pelicula");
        }
    }
}

