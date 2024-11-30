package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ReservaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Reserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reserva")
public class ReservaResource implements Serializable {
    @Inject
    ReservaBean rBean;
//DA ERROR: No se puede serializar la propiedad 'reservaDetalles' de sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Reserva
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
                List<Reserva> encontrados = rBean.findRange(firstResult, maxResults);
                Long total = (long) rBean.count();
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
                Reserva encontrado = rBean.findById(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found", "Reserva con id: " + id).build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
        return Response.status(422).header("Wrong-Parameter", "id: " + id).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(Reserva reserva, @Context UriInfo uriInfo) {
        if (reserva != null && reserva.getIdReserva() == null) {
            try {
                rBean.create(reserva);
                if (reserva.getIdReserva() != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(reserva.getIdReserva()));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("Process-Error", "Record couldn't be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "reserva: " + reserva).build();
    }
}
