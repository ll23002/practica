package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Asiento;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class AsientoBean extends AbstractDataPersistence<Asiento> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public AsientoBean() {
        super(Asiento.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Asiento> findByNombre(final String nombre, int first, int max) {
        try {
            TypedQuery<Asiento> q = em.createNamedQuery("Asiento.findByNombre", Asiento.class);
            q.setParameter("nombre", nombre);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public int countByNombre(final String nombre) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("Asiento.countByNombre", Long.class);
            q.setParameter("nombre", nombre);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(Asiento registro) {
        super.delete(registro);
    }

    @Override
    public Asiento update(Asiento registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(a.idAsiento) FROM Asiento a", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}