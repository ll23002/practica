import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;

import java.util.List;

public class TipoSalaResourceClientTest {

    Client cliente;
    WebTarget webTarget;

    public TipoSalaResourceClientTest() {
        cliente = ClientBuilder.newClient();
        webTarget = cliente.target("http://localhost:9080/cine-1.0-SNAPSHOT/v1/");
    }

    @Test
    public void testFindRange() {
        System.out.println("findRange");
        int statusEsperado = Response.Status.OK.getStatusCode();
        Response respuesta = webTarget.path("tiposala").
                queryParam("first", 0).
                queryParam("max", 3).
                request(MediaType.APPLICATION_JSON).get();
        Assertions.assertEquals(statusEsperado, respuesta.getStatus());
        List<TipoSala> listaRespuesta = respuesta.readEntity(new GenericType<List<TipoSala>>() {
        });
        Assertions.assertNotNull(listaRespuesta);
        Assertions.assertFalse(listaRespuesta.isEmpty());
        Assertions.assertEquals(3, listaRespuesta.size());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Total-Records"));
        Assertions.assertTrue(Integer.parseInt(respuesta.getHeaderString("Total-Records")) > 0);

    }

    @Test
    public void testFindById() {
        System.out.println("findById");
        int statusEsperado = Response.Status.OK.getStatusCode();
        Integer idBuscado = 1;
        Response respuesta = webTarget.path("tiposala/{id}")
                .resolveTemplate("id", idBuscado)
                .request(MediaType.APPLICATION_JSON).get();
        Assertions.assertEquals(statusEsperado, respuesta.getStatus());
        TipoSala tipoSala = respuesta.readEntity(TipoSala.class);
        Assertions.assertNotNull(tipoSala);
        Assertions.assertEquals(idBuscado, tipoSala.getIdTipoSala());
    }


    @Test
    public void testCreate() {
        System.out.println("create");
        TipoSala tipoSala = new TipoSala();
        tipoSala.setActivo(true);
        tipoSala.setExpresionRegular(".");
        tipoSala.setNombre("Creado en clase rest " + System.currentTimeMillis());
        Response respuesta = webTarget.path("tiposala").request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(tipoSala, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(respuesta.getStatus(), Response.Status.CREATED.getStatusCode());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] partes = respuesta.getHeaderString("Location").split("/");
        Integer idCreado = Integer.parseInt(partes[partes.length - 1]);
        Assertions.assertNotNull(idCreado);
        System.out.println("Se creo: " + idCreado);

    }
}
