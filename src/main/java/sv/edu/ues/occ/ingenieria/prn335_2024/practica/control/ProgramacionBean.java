package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ProgramacionBean extends AbstractDataPersistence<Programacion> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public ProgramacionBean() {
        super(Programacion.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Programacion> findByIdSala(final Integer idSala, int first, int max) {
        try {
            TypedQuery<Programacion> q = em.createNamedQuery("Programacion.findByIdSala", Programacion.class);
            q.setParameter("idSala", idSala);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public int countByIdSala(final Integer idSala) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("Programacion.countByIdSala", Long.class);
            q.setParameter("idSala", idSala);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(Programacion registro) {
        super.delete(registro);
    }

    @Override
    public Programacion update(Programacion registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(p.idProgramacion) FROM Programacion p", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}