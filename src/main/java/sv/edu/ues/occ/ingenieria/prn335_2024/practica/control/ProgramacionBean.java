package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Programacion;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Named("pgBean")
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

    public List<Programacion> findAll() {
        try {
            TypedQuery<Programacion> query = em.createQuery("SELECT p FROM Programacion p", Programacion.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Programacion> findFuncionesPorFechaYNombre(OffsetDateTime inicioDia, OffsetDateTime finDia, String nombre) {
        TypedQuery<Programacion> query = em.createQuery(
                "SELECT p FROM Programacion p WHERE p.desde >= :inicioDia AND p.desde < :finDia AND LOWER(p.idPelicula.nombre) LIKE :nombre",
                Programacion.class
        );
        query.setParameter("inicioDia", inicioDia);
        query.setParameter("finDia", finDia);
        query.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
        return query.getResultList();
    }

    public Programacion findById(Object id) {
        return em.createQuery(
                        "SELECT p FROM Programacion p JOIN FETCH p.idPelicula WHERE p.idProgramacion = :id",
                        Programacion.class)
                .setParameter("id", id)
                .getSingleResult();
    }


}