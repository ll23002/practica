package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AsientoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("asiento")
public class AsientoResource implements Serializable {

    @Inject
    AsientoBean aBean;

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
                List<Asiento> encontrados = aBean.findRange(firstResult, maxResults);
                Long total = (long) aBean.count();
                Response.ResponseBuilder builder = Response.ok(encontrados)
                        .header("Total-Records", total)
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

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id) {
        if (id != null) {
            try {
                Asiento encontrado = aBean.findById(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found", "Asiento con id: " + id).build();
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
    public Response create(Asiento asiento, @Context UriInfo uriInfo) {
        if (asiento != null && asiento.getIdAsiento() == null) {
            try {
                aBean.create(asiento);
                if (asiento.getIdAsiento() != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(asiento.getIdAsiento()));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("Process-Error", "Record couldn't be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "asiento: " + asiento).build();
    }
}
