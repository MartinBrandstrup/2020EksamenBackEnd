package facades;

import entities.FoodItem;
import entities.Recipe;
import utils.EMF_Creator;
import entities.Storage;
import entities.WeekMenu;
import java.util.HashSet;
import java.util.Set;
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

    FoodItem testFI1 = new FoodItem("Kartofler", 1000); //Pris pr 1000gram
    FoodItem testFI2 = new FoodItem("Løg", 900);
    Storage testS1 = new Storage(60, testFI1); //Vægt i gram
    
    Recipe testR1 = new Recipe("Grøntsagssuppe", 30, "Lav suppen"); //Tid i minutter
    Recipe testR2 = new Recipe("Bagte Bagekartofler", 30, "Bag dem");
    Recipe testR3 = new Recipe("Råstegte Kartofler", 30, "Steg dem");
    Recipe testR4 = new Recipe("Gratinerede Kartofler", 30, "Gratiner dem");
    Recipe testR5 = new Recipe("Hasselback Kartofler", 30, "Do the thing");
    Recipe testR6 = new Recipe("Pomfritter", 30, "Frituresteg dem");
    Recipe testR7 = new Recipe("Rå Kartofler", 30, "Don't do the thing");
    
    Set<Recipe> potatoes = new HashSet();
    
    WeekMenu menu = new WeekMenu(potatoes);
   
    // Setup the DataBase in a known state BEFORE EACH TEST
    @BeforeEach
    public void setUp()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();
            em.createNamedQuery("Recipe_FoodItem.deleteAllRows").executeUpdate();
            em.createNamedQuery("FoodItem.deleteAllRows").executeUpdate();
            em.createNamedQuery("Storage.deleteAllRows").executeUpdate();
            em.createNamedQuery("Recipe.deleteAllRows").executeUpdate();

            testR1.addFoodItem(testFI1, 12000); //Vægt i gram
            testR1.addFoodItem(testFI2, 20000);
            
            testR2.addFoodItem(testFI1, 16000);
            testR3.addFoodItem(testFI1, 12000);
            testR4.addFoodItem(testFI1, 12000);
            testR5.addFoodItem(testFI1, 12000);
            testR6.addFoodItem(testFI1, 8000);
            testR7.addFoodItem(testFI1, 12000);
            potatoes.add(testR1);
            potatoes.add(testR2);
            potatoes.add(testR3);
            potatoes.add(testR4);
            potatoes.add(testR5);
            potatoes.add(testR6);
            potatoes.add(testR7);
            
            
            em.persist(testFI1);
            em.persist(testFI2);
            em.persist(testS1);
            em.persist(testR1);
            em.persist(testR2);
            em.persist(testR3);
            em.persist(testR4);
            em.persist(testR5);
            em.persist(testR6);
            em.persist(testR7);

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
        
        long expResult = 1;
        long result = facade.getStorageCount();
        assertEquals(expResult, result);
    }

    /**
     * Test of persistFoodItemToStorage method, of class MasterFacade.
     * When providing existing FoodItem.
     */
    @Test
    public void testExistingPersistFoodItemToStorage()
    {
        System.out.println("persistFoodItemToStorage");
        
        String itemName = "Kartofler";
        long itemAmount = 60;
        long itemPrice = 200;
        
        long expResult = testS1.getFoodItemAmount() + itemAmount;
        long result = facade.persistUpdateFoodItemToStorage(itemName, itemAmount, itemPrice)
                .getFoodItemAmount();
        
        assertEquals(expResult, result);
    }
    
//    /**
//     * Test of persistFoodItemToStorage method, of class MasterFacade.
//     * When providing non-existing FoodItem.
//     */
//    @Test
//    public void testNewPersistFoodItemToStorage()
//    {
//        System.out.println("persistFoodItemToStorage");
//        
//        String itemName = "Hvidløg";
//        long itemAmount = 3;
//        long itemPrice = 4444;
//        
//        assertEquals(2, facade.getFoodItemCount());
//        assertEquals(1, facade.getStorageCount());
//        
//        facade.persistFoodItemToStorage(itemName, itemAmount, itemPrice);
//        
//        assertEquals(3, facade.getFoodItemCount());
//        assertEquals(2, facade.getStorageCount());
//    }

}
