package sv.edu.ues.occ.ingenieria.prn335_2024.practica.boundary.jsf;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.LazyDataModel;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.AbstractDataPersistence;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.control.TipoSucursalBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sucursal;


import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;


@Named
@SessionScoped
public class FrmTipoSucursal extends FrmAbstractPersistence<Sucursal> implements Serializable {
 @Inject
   TipoSucursalBean TSB;
 @Inject
   FacesContext facesContext;
   Sucursal registro;
   LazyDataModel<Sucursal> modelo;
   Integer idSucursal;
   List<Sucursal> sucursalList;

   @PostConstruct
   public void inicializar() {
       modelo = this;
       estado = ESTADO_CRUD.NONE;
   }

   @Override
   protected AbstractDataPersistence<Sucursal> getDataBean() {return TSB;}

   @Override
   protected FacesContext getFacesContext() {return facesContext;}

   @Override
   protected Sucursal createNewInstance() {
      try{
          registro=new Sucursal();
             return registro;
      }catch (Exception e){
          Logger.getLogger(getClass().getName()).severe(e.getMessage());
          return null;
      }
   }

   @Override
   public Sucursal buscarRegistroPorId(String id) {return null;}

   @Override
   public String buscarIdPorRegistro(Sucursal dato) {return null;}

   @Override
   public String getTituloPagina() {return "";}

   @Override
   protected Object getId(Sucursal object) {return object.getIdSucursal();}

   public LazyDataModel<Sucursal> getModelo() {return modelo;}

   public void setModelo(LazyDataModel<Sucursal> modelo) {this.modelo = modelo;}

   @Override
   public ESTADO_CRUD getEstado(){return estado;}

   @Override
   public void setEstado(ESTADO_CRUD estado){this.estado = estado;}

   public Sucursal getRegistro(){return registro;}

   public void setRegistro(Sucursal registro){this.registro = registro;}

   public void btnNuevo(ActionEvent event) {
       super.btnNuevo(event);
       Integer id = TSB.findLastId();
       registro.setActivo(true);
       try {
           if (id != null) {
               registro.setIdSucursal(id + 1);
           } else {
               registro.setIdSucursal(1);
           }
       } catch (Exception e) {
           Logger.getLogger(getClass().getName()).severe(e.getMessage());
       }
   }

   public void btnGuardar(jakarta.faces.event.ActionEvent event) {super.btnGuardar(event, this.registro);}


   public void btnCancelar(jakarta.faces.event.ActionEvent event) {super.btnCancelar(event, this.registro);}


   public void btnEditar(jakarta.faces.event.ActionEvent event) {super.btnEditar(event, this.registro);}


   public void btnEliminar(jakarta.faces.event.ActionEvent event) {super.btnEliminar(event, this.registro);}
}

