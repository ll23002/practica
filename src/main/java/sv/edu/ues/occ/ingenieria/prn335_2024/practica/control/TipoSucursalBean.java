package sv.edu.ues.occ.ingenieria.prn335_2024.practica.control;


import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.practica.entity.Sucursal;

import java.io.Serializable;

@Stateless
@LocalBean
public class TipoSucursalBean extends AbstractDataPersistence<Sucursal> implements Serializable {
    @PersistenceContext(unitName = "practicaPU")
    EntityManager em;
    public TipoSucursalBean() {super(Sucursal.class);}

    @Override
    public EntityManager getEntityManager() {return em;}

    @Override
    public void delete(Sucursal registro) {super.delete(registro);}

    @Override
    public Sucursal update(Sucursal registro) throws IllegalArgumentException, IllegalStateException {return super.update(registro);}

    public Integer findLastId() {
        try {
        return em.createQuery("SELECT MAX(t.idSucursal) FROM Sucursal t", Integer.class).getSingleResult();//Se busca el último ID de la tabla TipoSucursal.
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
