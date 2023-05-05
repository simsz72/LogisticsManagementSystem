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

public class DestinationHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public DestinationHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    //CREATE
    public void createDestination(Destination destination){
        entityManager = emf.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(destination);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //UPDATE
    public void updateDestination(Destination destination) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(destination);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //REMOVE
    public void removeDestination(Destination destination) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Destination.class, destination.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //READ
    public Destination getDestinationById(int id) {
        entityManager = emf.createEntityManager();
        Destination destination = null;
        try{
            entityManager.getTransaction().begin();
            destination = entityManager.find(Destination.class, id);
            entityManager.getTransaction().commit();;
        } catch (Exception e) {
            System.out.println("Could not find destination by given Id");
        }
        return destination;
    }

    public List<Destination> getAllDestinations(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Destination.class));
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
