package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.json.JsonObject;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.ContadorBean;

import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class FrmWebSocket implements Serializable {

    @Inject
    @Push(channel = "websocket")
    PushContext pushContext;


    @Inject
    ContadorBean contadorBean;
    int cuenta = 0;
    UUID identificador;
    public void enviarMensaje() {
        try {
             identificador = UUID.randomUUID();
        contadorBean.contarDespacio(cuenta, identificador, this::recibirMensaje);//Antes era mensaje -> recibirMensaje(mensaje)
        pushContext.send("Mensaje enviado: " + System.currentTimeMillis());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void recibirMensaje(JsonObject respuesta) {

        try {
             if (respuesta != null) {
           if (respuesta.getString("TipoRespuesta").equals(ContadorBean.TIPO_RESPUESTA.EXITO.toString()) &&
                   respuesta.getString("UUID").equals(this.identificador.toString())) {
               cuenta = respuesta.getInt("cuenta");
               pushContext.send("cuenta: " + cuenta);
           }
       }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public int getCuenta() {
        return cuenta;
    }
}
