package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractDataPersistence<A> {

    public abstract EntityManager getEntityManager();

    EntityManager em = null;

    Class<A> entityClass;

    public AbstractDataPersistence(Class<A> entityClass) {//Constructor de la clase AbstractDataPersistence que recibe como parámetro una clase de tipo A.
        this.entityClass = entityClass;
    }

    public void create(A entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(entity);
        } catch (
                Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public A findById(final Object id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        try {
            em = getEntityManager();
            if (em == null) {
                throw new IllegalStateException("Error al acceder al repositorio");
            }
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
        return em.find(entityClass, id);

    }

    public List<A> findRange(int first, int max) {
        if (first < 0 || max <= 0) {
            throw new IllegalArgumentException("Los valores de first y max deben ser mayores a 0");
        }
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<A> cq = cb.createQuery(entityClass);
            Root<A> root = cq.from(entityClass);
            cq.select(root);
            TypedQuery<A> tq = em.createQuery(cq);
            tq.setFirstResult(first);
            tq.setMaxResults(max);
            return tq.getResultList();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public int count() {
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<A> root = cq.from(entityClass);
            cq.select(cb.count(root));
            Long count = em.createQuery(cq).getSingleResult();
            return count.intValue();
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public A update(A entity) throws IllegalArgumentException, IllegalStateException {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            A mergedEntity = em.merge(entity);
            em.flush();
            return mergedEntity;
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public void delete(A entity) {
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

}
