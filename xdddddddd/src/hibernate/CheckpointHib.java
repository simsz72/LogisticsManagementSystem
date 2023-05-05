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

public class CheckpointHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CheckpointHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    //CREATE
    public void createCheckpoint(Checkpoint checkpoint){
        entityManager = emf.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(checkpoint);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //UPDATE
    public void updateCheckpoint(Checkpoint checkpoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(checkpoint);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //REMOVE
    public void removeCheckpoint(Checkpoint checkpoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Checkpoint.class, checkpoint.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //READ
    public Checkpoint getCheckpointById(int id) {
        entityManager = emf.createEntityManager();
        Checkpoint checkpoint = null;
        try{
            entityManager.getTransaction().begin();
            checkpoint = entityManager.find(Checkpoint.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Could not find checkpoint by given Id");
        }
        return checkpoint;
    }

    public List<Checkpoint> getAllCheckpoints(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Checkpoint.class));
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
