package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;

import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class FrmMenu implements Serializable {

    @Inject
    FacesContext facesContext;

    DefaultMenuModel model;//propiedad de primefaces

    @PostConstruct
    public void init(){
        model = new DefaultMenuModel();
        //primera parte del menu
        DefaultSubMenu tipos = DefaultSubMenu.builder()
                .label("Tipos")
                .expanded(true)
                .build();
        //item del menu value like SALA
        DefaultMenuItem item = DefaultMenuItem.builder()
                .value("Sala")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoSala.jsf')}")
                .build();
        tipos.getElements().add(item);
        //items del menu value like Asiento
        DefaultMenuItem itemAsiento = DefaultMenuItem.builder()
                .value("Asiento")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoAsiento.jsf')}")
                .build();
        tipos.getElements().add(itemAsiento);
        //items del menu value like Pelicula
        DefaultMenuItem itemPelicula = DefaultMenuItem.builder()
                .value("Pelicula")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPelicula.jsf')}")
                .build();
        tipos.getElements().add(itemPelicula);
        //items del menu value like Producto
        DefaultMenuItem itemProducto = DefaultMenuItem.builder()
                .value("Producto")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoProducto.jsf')}")
                .build();
        tipos.getElements().add(itemProducto);
        //items del menu value like Reserva
        DefaultMenuItem itemReserva = DefaultMenuItem.builder()
                .value("Reserva")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoReserva.jsf')}")
                .build();
        tipos.getElements().add(itemReserva);
        //items del menu value like Pago
        DefaultMenuItem itemPago = DefaultMenuItem.builder()
                .value("Pago")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoPago.jsf')}")
                .build();
        tipos.getElements().add(itemPago);
        model.getElements().add(tipos);

        //segunda parte del menu
        DefaultSubMenu cine = DefaultSubMenu.builder()
                .label("Cine")
                .expanded(true)
                .build();

        DefaultMenuItem itemP = DefaultMenuItem.builder()
                .value("Pelicula")
                .ajax(true)
                .command("#{frmMenu.navegar('Pelicula.jsf')}")
                .build();
        cine.getElements().add(itemP);

        DefaultMenuItem itemS = DefaultMenuItem.builder()
                .value("Sala")
                .ajax(true)
                .command("#{frmMenu.navegar('Sala.jsf')}")
                .build();
        cine.getElements().add(itemS);

        DefaultMenuItem itemSucursal = DefaultMenuItem.builder()
                .value("Sucursal")
                .ajax(true)
                .command("#{frmMenu.navegar('TipoSucursal.jsf')}")
                .build();
        cine.getElements().add(itemSucursal);
        DefaultMenuItem itemReservA = DefaultMenuItem.builder()
                .value("Reserva")
                .ajax(true)
                .command("#{frmMenu.navegar('Reserva.jsf')}")
                .build();
        cine.getElements().add(itemReservA);
        model.getElements().add(cine);
    }

    public void navegar(String formulario) throws IOException {
        facesContext.getExternalContext().redirect(formulario);
    }

    public DefaultMenuModel getModel() {
        return model;
    }
}
