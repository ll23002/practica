package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ProgramacionBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;

import java.util.logging.Logger;

@FacesConverter("programacionConverter")
public class ProgramacionConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null || value.isEmpty()) {
                return null;
            }
            // Obtener el ProgramacionBean usando evaluateExpressionGet
            ProgramacionBean programacionService = context.getApplication()
                    .evaluateExpressionGet(context, "#{pgBean}", ProgramacionBean.class);
            // Buscar la entidad por ID
            return programacionService.findById(Integer.valueOf(value));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe("Error en getAsObject: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        if (object == null) {
            return "";
        }
        if (object instanceof Programacion) {
            Programacion programacion = (Programacion) object;
            return programacion.getIdProgramacion().toString();
        } else {
            throw new IllegalArgumentException("Objeto no es de tipo Programacion");
        }
    }
}
