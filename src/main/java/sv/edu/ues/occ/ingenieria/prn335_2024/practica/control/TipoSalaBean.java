package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoSala;

import java.io.Serializable;
@Stateless
@LocalBean
public class TipoSalaBean extends AbstractDataPersistence<TipoSala> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;
    public TipoSalaBean() {
        super(TipoSala.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void delete(TipoSala registro) {
        super.delete(registro);
    }

    @Override
    public TipoSala update(TipoSala registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(t.idTipoSala) FROM TipoSala t", Integer.class).getSingleResult();//Se busca el último ID de la tabla TipoSala.
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
