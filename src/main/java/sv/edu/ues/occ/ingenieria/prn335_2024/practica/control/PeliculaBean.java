package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Pelicula;

import java.io.Serializable;

@Stateless
@LocalBean
public class PeliculaBean extends AbstractDataPersistence<Pelicula> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public PeliculaBean() {
        super(Pelicula.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void delete(Pelicula registro) {
        super.delete(registro);
    }

    @Override
    public Pelicula update(Pelicula registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(p.idPelicula) FROM Pelicula p", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pelicula findById(Object id) {
        return super.findById(id);
    }
}