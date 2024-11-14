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
            System.out.println("Error al buscar el último ID de la tabla Programacion.");
            return null;
        }
    }
}