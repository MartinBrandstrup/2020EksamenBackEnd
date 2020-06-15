/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.RenameMe;
import entities.Role;
import entities.User;
import errorhandling.AlreadyExistsException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Christian
 */
//Uncomment the line below, to temporarily disable this test
//@Disabled
public class UserFacadeTest
{

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    User user = new User("user", "test42");
    User admin = new User("admin", "test42");
    User both = new User("user_admin", "test42");
//    private static Role userRole = null;
//    private static Role adminRole = null;
    private static Role userRole = new Role("user");
    private static Role adminRole = new Role("admin");
    User user1 = new User("TestPerson1", "person1Password");
    User user2 = new User("TestPerson2", "person2Password");

    public UserFacadeTest()
    {
    }

    //@BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/2020CA3_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = UserFacade.getUserFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    @BeforeAll
    public static void setUpClassV2()
    {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.DROP_AND_CREATE);
//        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, EMF_Creator.Strategy.NONE);
        facade = UserFacade.getUserFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();

        try
        {
//            userRole = em.find(Role.class, "user");
//            adminRole = em.find(Role.class, "admin");
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
//            em.createNamedQuery("Role.deleteAllRows",)
//            user.addRole(userRole);
//            admin.addRole(adminRole);
//            both.addRole(userRole);
//            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
//            em.persist(user);
//            em.persist(admin);
//            em.persist(both);
            user1.addRole(userRole);
            user2.addRole(userRole);
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
    }

    @AfterEach
    public void tearDown()
    {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void getUserCount()
    {
        long result = facade.getUserCount();
        assertEquals(2, result, "Expects two rows in the database");
    }

    @Test
    public void createNormalUserAndGetUserByName() throws AlreadyExistsException, NotFoundException
    {
        String name = "Testperson3";
        User expt = facade.createNormalUser(name, "person3Password", "user");
        User result = facade.getUserByName(name);
//       User result = new User(retivedUser.getUserName(), retivedUser.getUserPass());

        System.out.println("Expetet: " + expt + " Resulte: " + result);
        assertEquals(expt.getUserName(), result.getUserName());
    }

}
