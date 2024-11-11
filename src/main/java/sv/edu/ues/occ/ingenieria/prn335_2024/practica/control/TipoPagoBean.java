package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.TipoPago;

import java.io.Serializable;

@Stateless
@LocalBean
public class TipoPagoBean extends AbstractDataPersistence<TipoPago> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public TipoPagoBean() {
        super(TipoPago.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void delete(TipoPago registro) {
        super.delete(registro);
    }

    @Override
    public TipoPago update(TipoPago registro) throws IllegalArgumentException, IllegalStateException {
        return super.update(registro);
    }

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(t.idTipoPago) FROM TipoPago t", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}