package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;




import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sucursal;


import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Stateless
@LocalBean
public class TipoSucursalBean extends AbstractDataPersistence<Sucursal> implements Serializable {
   @PersistenceContext(unitName = "practicaPU")
   EntityManager em;


   private static final Logger LOGGER = Logger.getLogger(TipoSucursalBean.class.getName());
   public TipoSucursalBean() {super(Sucursal.class);}


   @Override
   public EntityManager getEntityManager() {return em;}


   public List<Sucursal> findByIdSucursal(final Integer idSucursal, int first, int max) {
       try {
           TypedQuery<Sucursal> q = em.createNamedQuery("Sucursal.findByIdSucursal", Sucursal.class);
                   q.setParameter("idSucursal", idSucursal);
                   q.setFirstResult(first);
                   q.setMaxResults(max);
                   return q.getResultList();
       } catch (Exception e) {
           LOGGER.log(Level.SEVERE, "Error al ejecutar la consulta: findByIdSucursal ", e);
           throw new RuntimeException("Error al ejecutar la consulta: findByIdSucursal", e);
       }
   }


   public int countByIdSucursal(final Integer idSucursal) {
       try {
           TypedQuery<Long> q = em.createNamedQuery("Sucursal.countByIdSucursal", Long.class);
                   q.setParameter("idSucursal", idSucursal);
                   return q.getSingleResult().intValue();
       } catch (Exception e) {
           LOGGER.log(Level.SEVERE, "Error al ejecutar la consulta: countByIdSucursal", e);
           throw new RuntimeException("Error al ejecutar la consulta: countByIdSucursal", e);
       }
   }


   @Override
   public void delete(Sucursal registro) {super.delete(registro);}


   @Override
   public Sucursal update(Sucursal registro) throws IllegalArgumentException, IllegalStateException {return super.update(registro);}


   public Integer findLastId() {
       try {
       return em.createQuery("SELECT MAX(t.idSucursal) FROM Sucursal t", Integer.class).getSingleResult();//Se busca el último ID de la tabla TipoSucursal.
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }
}
