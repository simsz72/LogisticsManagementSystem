package hibernate;

import model.Driver;
import model.DriverManager;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class UserHib {

    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    //CREATE
    public void createUser(User user){
        entityManager = emf.createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //UPDATE
    public void updateUser(User user) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //REMOVE
    public void removeUser(User user) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.find(Driver.class, user.getId()));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    //READ
    public User getDriverById(int id) {
        entityManager = emf.createEntityManager();
        Driver driver = null;
        try{
            entityManager.getTransaction().begin();
            driver = entityManager.find(Driver.class, id);
            entityManager.getTransaction().commit();;
        } catch (Exception e) {
            System.out.println("Could not find user by given Id");
        }
        return driver;
    }

    public User getUserByLoginData(String login, String password, boolean isManager) {
        entityManager = emf.createEntityManager();
        Query q = null;
        CriteriaQuery<Driver> queryDriver = null;
        CriteriaQuery<DriverManager> queryDriverManager = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        if(!isManager) {
            queryDriver = cb.createQuery(Driver.class);
            Root<Driver> root = queryDriver.from(Driver.class);
            queryDriver.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
        }
        else{
            queryDriverManager = cb.createQuery(DriverManager.class);
            Root<DriverManager> root = queryDriverManager.from(DriverManager.class);
            queryDriverManager.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
        }
        try {
            if(queryDriver != null) q= entityManager.createQuery(queryDriver);
            if(queryDriverManager != null) q= entityManager.createQuery(queryDriverManager);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Driver> getAllDrivers(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Driver.class));
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

    public List<DriverManager> getAllDriverManagers(){
        entityManager = emf.createEntityManager();
        try{
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(DriverManager.class));
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
