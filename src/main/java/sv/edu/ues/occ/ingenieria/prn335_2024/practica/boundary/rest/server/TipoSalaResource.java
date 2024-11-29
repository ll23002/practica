package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.Path;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("tiposala")
public class TipoSalaResource implements Serializable {

    @Inject
    TipoSalaBean tsBean;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findRange(
            @QueryParam("first")
            @DefaultValue("0")
            int firstResult,
            @QueryParam("max")
            @DefaultValue("50")
            @Max(50)
            int maxResults) {
        try {

            if (firstResult >= 0 && maxResults > 0 && maxResults <= 50) {
                List<TipoSala> encontrados = tsBean.findRange(firstResult, maxResults);
                for (TipoSala ts : encontrados) {
                    ts.getSalaCaracteristicas().size(); // Forzar inicialización
                }
                Long total = (long) tsBean.count();
                Response.ResponseBuilder builder = Response.ok(encontrados)
                        .header("Total_Records", total)
                        .type(MediaType.APPLICATION_JSON);
                return builder.build();
            } else {
                return Response.status(422).header("Wrong-Parameter", "first: " + firstResult + " max: " + maxResults).build();
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error en findRange", e);
            return Response.status(500).entity(e.getMessage()).build();
        }
    }

/*
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        if (id != null) {
            try {
                TipoSala encontrado = tsBean.findById(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found", "TipoSala con id: " + id).build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "id: " + id).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(TipoSala tipoSala, @Context UriInfo uriInfo) {
        if (tipoSala != null && tipoSala.getIdTipoSala() == null) {
            try {
                tsBean.create(tipoSala);
                if (tipoSala.getIdTipoSala() != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(tipoSala.getIdTipoSala()));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("Process-Error","Record couldnt be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }

        }
        return Response.status(422).header("Wrong-Parameter", "tiposala: " + tipoSala).build();
    }


 */

}
