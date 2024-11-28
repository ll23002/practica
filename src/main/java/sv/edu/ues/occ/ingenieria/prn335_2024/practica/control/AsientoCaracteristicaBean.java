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

    public List<AsientoCaracteristica> findByIdAsiento(final Long idAsiento, int first, int max) {
        try {
            TypedQuery<AsientoCaracteristica> q = em.createNamedQuery("AsientoCaracteristica.findByIdAsiento", AsientoCaracteristica.class);
            q.setParameter("idAsiento", idAsiento);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing findByIdAsiento query", e);
            throw new RuntimeException("Error executing findByIdAsiento query", e);
        }
    }

    public int countByIdAsiento(final Long idAsiento) {
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

    public Long findLastId() {
        try {
            TypedQuery<Long> q = em.createNamedQuery("AsientoCaracteristica.findLastId", Long.class);
            return q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing findLastId query", e);
            throw new RuntimeException("Error executing findLastId query", e);
        }
    }

}
