package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.annotation.Resource;
import jakarta.ejb.Asynchronous;
import jakarta.ejb.LocalBean;
import jakarta.ejb.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.logging.Logger;


@Stateless
@LocalBean
public class ContadorBean implements Serializable {
    public enum  TIPO_MENSAJE {
        CUENTA_RESPONSE, CUENTA_ERROR;
    }

    public  enum TIPO_RESPUESTA {
        ERROR, EXITO;
    }

    @Resource
    SessionContext sessionContext;

    @Asynchronous
    public void contarDespacio(int actual, UUID identificador, Consumer<JsonObject> callback) {
        JsonObjectBuilder builder = jakarta.json.Json.createObjectBuilder();
        try {
            Thread.sleep(3000);
            System.out.println("Ejecutándose");
            builder.add("TipoRespuesta", TIPO_RESPUESTA.EXITO.toString());
            builder.add("TipoMensaje", TIPO_MENSAJE.CUENTA_RESPONSE.toString());
            builder.add("UUID", identificador.toString());
            builder.add("cuenta", ++actual);
            callback.accept(builder.build());
            System.out.println("Dice algo");
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).severe(ex.getMessage());
            builder.add("TipoRespuesta", TIPO_RESPUESTA.ERROR.toString());
            builder.add("TipoMensaje", TIPO_MENSAJE.CUENTA_ERROR.toString());
            builder.add("UUID", identificador.toString());
              callback.accept(builder.build());
            sessionContext.setRollbackOnly();
        }

    }
}
