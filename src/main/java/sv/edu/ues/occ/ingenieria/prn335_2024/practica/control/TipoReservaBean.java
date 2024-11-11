package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoReserva;

import java.io.Serializable;

@Stateless
@LocalBean
public class TipoReservaBean extends AbstractDataPersistence<TipoReserva> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public TipoReservaBean() {
        super(TipoReserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void delete(TipoReserva registro) {
        super.delete(registro);
    }

    @Override
    public TipoReserva update(TipoReserva registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(t.idTipoReserva) FROM TipoReserva t", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}