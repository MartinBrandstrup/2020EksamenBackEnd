package facades;

import entities.FoodItem;
import utils.EMF_Creator;
import entities.Storage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class MasterFacadeTest
{

    private static EntityManagerFactory emf;
    private static MasterFacade facade;

    public MasterFacadeTest()
    {
    }

    //@BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/2020Eksamen_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
        facade = MasterFacade.getMasterFacade(emf);
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
        emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST, Strategy.DROP_AND_CREATE);
        facade = MasterFacade.getMasterFacade(emf);
    }

    @AfterAll
    public static void tearDownClass()
    {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    FoodItem testFI1 = new FoodItem("Kartofler", 1000);
    FoodItem testFI2 = new FoodItem("Løg", 900);
    Storage testS1 = new Storage(4, testFI1);

    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Storage.deleteAllRows").executeUpdate();
            em.createNamedQuery("FoodItem.deleteAllRows").executeUpdate();

//            FoodItem testFI1 = new FoodItem("Kartofler", 1000);
//            FoodItem testFI2 = new FoodItem("Løg", 900);
//            Storage testS1 = new Storage(4, testFI1);

            em.persist(testFI1);
            em.persist(testFI2);
            em.persist(testS1);

            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    @AfterEach
    public void tearDown()
    {
//        Remove any data after each test was run
    }

    
    ///////////////////////// FoodItems/Storage \\\\\\\\\\\\\\\\\\\\\\\\\
    
    
    /**
     * Test of getFoodItemCount method, of class MasterFacade.
     */
    @Test
    public void testGetFoodItemCount()
    {
        System.out.println("getFoodItemCount");
        
        long expResult = 2;
        long result = facade.getFoodItemCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStorageCount method, of class MasterFacade.
     */
    @Test
    public void testGetStorageCount()
    {
        System.out.println("getStorageCount");
        
        long expResult = 0;
        long result = facade.getStorageCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of persistFoodItemToStorage method, of class MasterFacade.
     */
    @Test
    public void testPersistFoodItemToStorage()
    {
        System.out.println("persistFoodItemToStorage");
        
        String itemName = "Kartofler";
        long itemAmount = 5;
        long itemPrice = 4444;
        
        long expResult = testS1.getFoodItemAmount() + itemAmount;
        long result = facade.persistFoodItemToStorage(itemName, itemAmount, itemPrice).getFoodItemAmount();
        assertEquals(expResult, result);
        assertEquals(2, facade.getFoodItemCount());
        assertEquals(1, facade.getStorageCount());
        
        facade.persistFoodItemToStorage("Hvidløg", itemAmount, itemPrice);
        assertEquals(3, facade.getFoodItemCount());
        assertEquals(2, facade.getStorageCount());
    }

}
