package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.SalaCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class SalaCaracteristicaBean extends AbstractDataPersistence<SalaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(SalaCaracteristicaBean.class.getName());

    public SalaCaracteristicaBean() {
        super(SalaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<SalaCaracteristica> findByIdSala(final Integer idSala, int first, int max) {
        try {
            TypedQuery<SalaCaracteristica> q = em.createNamedQuery("SalaCaracteristica.findByIdSala", SalaCaracteristica.class);
            q.setParameter("idSala", idSala);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing findByIdSala query", e);
            throw new RuntimeException("Error executing findByIdSala query", e);
        }
    }

    public int countByIdSala(final Integer idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("SalaCaracteristica.countByIdSala", Long.class);
            q.setParameter("idSala", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing countByIdSala query", e);
            throw new RuntimeException("Error executing countByIdSala query", e);
        }
    }

    @Override
    public void delete(SalaCaracteristica registro) {
        super.delete(registro);
    }

    @Override
    public SalaCaracteristica update(SalaCaracteristica registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLasId() {
        try {
            return em.createQuery("SELECT MAX(sc.idSalaCaracteristica) FROM SalaCaracteristica sc", Integer.class).getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error executing findLasId query", e);
            return null;
        }
    }
}