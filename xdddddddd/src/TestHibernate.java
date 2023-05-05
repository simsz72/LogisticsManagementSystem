import hibernate.UserHib;
import model.Driver;
import model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TestHibernate {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TruckersMP");
        UserHib userHib = new UserHib(entityManagerFactory);
    }
}
