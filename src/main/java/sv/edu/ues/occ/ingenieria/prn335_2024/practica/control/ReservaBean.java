package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Reserva;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class ReservaBean extends AbstractDataPersistence<Reserva> implements Serializable {

    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;

    public ReservaBean() {
        super(Reserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Reserva findById(Object id) {
        return super.findById(id);
    }

    //No
    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(r.idReserva) FROM Reserva r", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<PeliculaCaracteristica> findCaracteristicasByPelicula(Integer idPelicula) {
        try {
            return em.createNamedQuery("PeliculaCaracteristica.findByIdPelicula", PeliculaCaracteristica.class)
                    .setParameter("idPelicula", idPelicula)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).severe(e.getMessage());
        }
        return null;
    }
}
