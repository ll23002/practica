package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.PeliculaCaracteristica;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class PeliculaCaracteristicaBean extends AbstractDataPersistence<PeliculaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public PeliculaCaracteristicaBean() {
        super(PeliculaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }


    public List<PeliculaCaracteristica> findByIdPelicula(final Integer idPelicula, int first, int max) {
        try {
            TypedQuery<PeliculaCaracteristica> q = em.createNamedQuery("PeliculaCaracteristica.findByIdPelicula", PeliculaCaracteristica.class);
            q.setParameter("idPelicula", idPelicula);
            q.setFirstResult(first);
            q.setMaxResults(max);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }
    public int countByIdPelicula(final Integer idPelicula) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("PeliculaCaracteristica.countByIdPelicula", Long.class);//Por que se usa Long.class si es Integer?
            q.setParameter("idPelicula", idPelicula);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void delete(PeliculaCaracteristica registro) {
        super.delete(registro);
    }

    @Override
    public PeliculaCaracteristica update(PeliculaCaracteristica registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(pc.idPeliculaCaracteristica) FROM PeliculaCaracteristica pc", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}