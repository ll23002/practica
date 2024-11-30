package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.rest.server;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Max;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoProductoBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("tipoproducto")
public class TipoProductoResource implements Serializable {

    @Inject
    TipoProductoBean tpBean;

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
                List<TipoProducto> encontrados = tpBean.findRange(firstResult, maxResults);
                Long total = (long) tpBean.count();
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
                TipoProducto encontrado = tpBean.findById(id);
                if (encontrado != null) {
                    Response.ResponseBuilder builder = Response.ok(encontrado).type(MediaType.APPLICATION_JSON);
                    return builder.build();
                }
                return Response.status(404).header("Not-Found", "TipoProducto con id: " + id).build();
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
    public Response create(TipoProducto tipoProducto, @Context UriInfo uriInfo) {
        if (tipoProducto != null && tipoProducto.getIdTipoProducto() == null) {
            try {
                tpBean.create(tipoProducto);
                if (tipoProducto.getIdTipoProducto() != null) {
                    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
                    uriBuilder.path(String.valueOf(tipoProducto.getIdTipoProducto()));
                    return Response.created(uriBuilder.build()).build();
                }
                return Response.status(500).header("Process-Error", "Record couldn't be created").build();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                return Response.status(500).entity(e.getMessage()).build();
            }
        }
        return Response.status(422).header("Wrong-Parameter", "tipoproducto: " + tipoProducto).build();
    }
}