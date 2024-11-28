package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.ReservaDetalle;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ReservaDetalleBean extends AbstractDataPersistence<ReservaDetalle> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public ReservaDetalleBean() {
        super(ReservaDetalle.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public ReservaDetalle findById(Object id) {
        return super.findById(id);
    }

    @Override
    public List<ReservaDetalle> findRange(int first, int max) {
        return super.findRange(first, max);
    }
}
