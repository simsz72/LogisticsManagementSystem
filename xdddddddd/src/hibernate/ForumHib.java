package hibernate;

import model.Forum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class ForumHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public ForumHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    //CREATE
    public void createForum(Forum forum){
        entityManager = emf.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(forum);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //UPDATE
    public void updateForum(Forum forum) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(forum);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //REMOVE
    public void removeForum(Forum forum) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Forum.class, forum.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //READ
    public Forum getForumById(int id) {
        entityManager = emf.createEntityManager();
        Forum forum = null;
        try{
            entityManager.getTransaction().begin();
            forum = entityManager.find(Forum.class, id);
            entityManager.getTransaction().commit();;
        } catch (Exception e) {
            System.out.println("Could not find forum by given Id");
        }
        return forum;
    }

    public List<Forum> getAllForums(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Forum.class));
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
