package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.AsientoCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class AsientoCaracteristicaBean extends AbstractDataPersistence<AsientoCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(SalaCaracteristicaBean.class.getName());

    public AsientoCaracteristicaBean() {super(AsientoCaracteristica.class);}

    @Override
    public EntityManager getEntityManager() {return em;}

  public List<AsientoCaracteristica> findByIdAsiento(final Integer idAsiento) {
    try {
        TypedQuery<AsientoCaracteristica> q = em.createNamedQuery("AsientoCaracteristica.findByIdAsiento", AsientoCaracteristica.class);
        q.setParameter("idAsiento", idAsiento);
        return q.getResultList();
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error al ejecutar la consulta findByIdAsiento", e);
        throw new RuntimeException("Error al ejecutar la consulta findByIdAsiento", e);
    }
}

    public int countByIdAsiento(final Integer idAsiento) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("AsientoCaracteristica.countByIdAsiento", Long.class);
            q.setParameter("idAsiento", idAsiento);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing countByIdAsiento query", e);
            throw new RuntimeException("Error executing countByIdAsiento query", e);
        }
    }

    @Override
    public void delete(AsientoCaracteristica registro) {
        super.delete(registro);
    }

    @Override
    public AsientoCaracteristica update(AsientoCaracteristica registro)throws IllegalArgumentException, IllegalStateException  {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            TypedQuery<Integer> q = em.createNamedQuery("AsientoCaracteristica.findLastId", Integer.class);
            return q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing findLastId query", e);
            throw new RuntimeException("Error executing findLastId query", e);
        }
    }
    public List<AsientoCaracteristica> findAll() {
       return em.createNamedQuery("AsientoCaracteristica.findAll", AsientoCaracteristica.class).getResultList();
    }

}
