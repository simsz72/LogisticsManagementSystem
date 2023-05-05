package hibernate;

import model.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CargoHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    //CREATE
    public void createCargo(Cargo cargo){
        entityManager = emf.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //UPDATE
    public void updateCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //REMOVE
    public void removeCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Cargo.class, cargo.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //READ
    public Cargo getCargoById(int id) {
        entityManager = emf.createEntityManager();
        Cargo cargo = null;
        try{
            entityManager.getTransaction().begin();
            cargo = entityManager.find(Cargo.class, id);
            entityManager.getTransaction().commit();;
        } catch (Exception e) {
            System.out.println("Could not find cargo by given Id");
        }
        return cargo;
    }

    public List<Cargo> getAllCargos(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Cargo.class));
            Query q = entityManager.createQuery(query);
            return  q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null){
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }
}
