package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sala;

import java.io.Serializable;

@Stateless
@LocalBean
public class SalaBean extends AbstractDataPersistence<Sala> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;
    public SalaBean() {super(Sala.class);}
    @Override
    public EntityManager getEntityManager() {return em;}
    @Override
    public void delete(Sala registro) {super.delete(registro);}
    @Override
    public Sala update(Sala registro) throws IllegalArgumentException, IllegalStateException {return super.update(registro);}

    public Integer findLastId() {
        try {
            return em.createQuery("SELECT MAX(s.idSala) FROM Sala s", Integer.class).getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Sala findById(Object id) {return super.findById(id);}
}
