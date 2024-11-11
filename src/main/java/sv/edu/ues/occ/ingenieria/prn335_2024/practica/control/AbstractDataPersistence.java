package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public abstract class AbstractDataPersistence<A> {

    public abstract EntityManager getEntityManager();//Es la interfaz que se encarga de la gestión de las entidades en la base de datos.

    EntityManager em = null;//em es una variable de tipo EntityManager que se encarga de almacenar la instancia de la interfaz EntityManager.

    Class<A> entityClass;//entityClass es una variable de tipo Class que se encarga de almacenar la clase de la entidad.

    public AbstractDataPersistence(Class<A> entityClass) {//Constructor de la clase AbstractDataPersistence que recibe como parámetro una clase de tipo A.
        this.entityClass = entityClass;
    }

    public void create(A entity) {//Método que se encarga de almacenar un registro en la base de datos.
        if (entity == null) {//Si la entidad es nula, se lanza una excepción de tipo IllegalArgumentException.
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        try {
            em = getEntityManager();//Se obtiene la instancia de la interfaz EntityManager.
            if (em == null) {//Si la instancia de la interfaz EntityManager es nula, se lanza una excepción de tipo IllegalStateException.
                throw new IllegalStateException("Error al acceder al repositorio");
            }
            em.persist(entity);//Se almacena la entidad en la base de datos.
        } catch (
                Exception ex) {//Si ocurre un error al acceder al repositorio, se lanza una excepción de tipo IllegalStateException.
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public A findById(final Object id) {//Método que se encarga de buscar un registro en la base de datos por un identificador único.
        if (id == null) {//Si el ID es nulo, se lanza una excepción de tipo IllegalArgumentException.
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
        return em.find(entityClass, id);//Se busca el registro en el repositorio por un identificador único.

    }

    public List<A> findRange(int first, int max) {//Método que se encarga de buscar un rango de registros en la base de datos.
        if (first < 0 || max <= 0) {
            throw new IllegalArgumentException("Los valores de first y max deben ser mayores a 0");
        }
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            /*Obtiene una instancia de CriteriaBuilder de la interfaz EntityManager.
             * CriteriaBuilder es una API que proporciona métodos para construir consultas
             * de manera programática en JPA*/
            CriteriaQuery<A> cq = cb.createQuery(entityClass);
            /*Crea una instancia de CriteriaQuery de la clase de la entidad.
             * Define el tipo de los resultados que se esperan al ejecutar la consulta*/
            Root<A> root = cq.from(entityClass);
            /*Crea una instancia de Root de la clase de la entidad,
             * que representa la raíz de la consulta.
             * En JPA, la raíz se refiere a la entidad desde la cual se parte para construir
             * la consulta*/
            cq.select(root);//Configura la consulta para seleccionar la raíz, es decir, todos los atributos de la entidad representada por la raíz.
            TypedQuery<A> tq = em.createQuery(cq);
            /*Crea una instancia de TypedQuery a partir de la consulta de criterios (CriteriaQuery).
             * Permite ejecutar la consulta y obtener resultados de tipo específico, en este caso de la clase A*/
            tq.setFirstResult(first);//Establece el primer resultado a obtener.
            tq.setMaxResults(max);//Establece el máximo de resultados a obtener.
            return tq.getResultList();//Obtiene los resultados de la consulta y los devuelve como una lista tipada.
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public int count() {//Método que se encarga de contar el número de registros de una tabla en la base de datos.
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();//CriteriaBuilder es una API que proporciona métodos para construir consultas de manera programática en JPA
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<A> root = cq.from(entityClass);
            cq.select(cb.count(root));//Configura la consulta para que devuelva un conteo de todas las filas de la entidad representada por la raíz.
            Long count = em.createQuery(cq).getSingleResult();//Obtiene el resultado de la consulta y lo devuelve como un valor único.
            return count.intValue();//Devuelve el valor del conteo como un entero.
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

    public A update(A entity) throws IllegalArgumentException, IllegalStateException {//Método que se encarga de actualizar un registro en la base de datos.
        if (entity == null) {
            throw new IllegalArgumentException("La entidad no puede ser nula");
        }
        em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("Error al acceder al repositorio");
        }
        try {
            A mergedEntity = em.merge(entity);//Fusiona el estado de la entidad con el contexto de persistencia y devuelve la entidad fusionada.
            em.flush();//Es para que los cambios se reflejen en la base de datos.
            return mergedEntity;//Fusiona el estado de la entidad con el contexto de persistencia y devuelve la entidad fusionada, pocas palabras actualiza la entidad.
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
            if (!em.contains(entity)) {//Si la entidad no esta asociada al contexto de persistencia, se asocia.
                entity = em.merge(entity);//Fusiona el estado de la entidad con el contexto de persistencia y devuelve la entidad fusionada.
            }
            em.remove(entity);//Elimina la entidad del contexto de persistencia.
        } catch (Exception ex) {
            throw new IllegalStateException("Error al acceder al repositorio", ex);
        }
    }

}
