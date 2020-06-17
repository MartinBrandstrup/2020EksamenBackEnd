package facades;

import dtos.RecipeDTO;
import dtos.RecipeListDTO;
import dtos.WeekMenuDTO;
import entities.FoodItem;
import entities.Recipe;
import entities.Storage;
import entities.WeekMenu;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotSupportedException;

/**
 *
 *
 * @author Brandstrup
 */
public class MasterFacade
{

    private static MasterFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MasterFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MasterFacade getMasterFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new MasterFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void populateDatabase()
    {
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
        WeekMenu testM1 = null;
        
        EntityManager em = emf.createEntityManager();
        try
        {
            em.getTransaction().begin();

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
            
            testM1 = new WeekMenu(potatoes);
            
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
            em.persist(testM1);

            em.getTransaction().commit();
        }
        finally
        {
            em.close();
        }
    }

    
    ///////////////////////// WeekMenu/Recipe \\\\\\\\\\\\\\\\\\\\\\\\\
    
    
    /**
     * Counts the amount of entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    public long getRecipeCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long recipeCount = (long) em.createQuery("SELECT COUNT(e) FROM Recipe e").getSingleResult();
            return recipeCount;
        }
        finally
        {
            em.close();
        }
    }

    public RecipeListDTO getAllRecipes()
    {
        EntityManager em = emf.createEntityManager();
        List<RecipeDTO> recipeDTOList = new ArrayList();
        
        
        try
        {
            TypedQuery<Recipe> query 
                    = em.createQuery("SELECT e FROM Recipe e", Recipe.class);

            query.getResultList().forEach((recipe) ->
            {
                recipeDTOList.add(new RecipeDTO(recipe));
            });
            
            RecipeListDTO result = new RecipeListDTO(recipeDTOList);
            return result;
        }
        finally
        {
            em.close();
        }
    }
    
    public WeekMenuDTO getWeekMenuPlanForCurrentWeek()
    {
        EntityManager em = emf.createEntityManager();
        int currentWeek = LocalDate.now(ZoneId.of("UTC")).get(WeekFields.ISO.weekOfYear());
        WeekMenu menu = null;
        
        try
        {
            TypedQuery<WeekMenu> query = em.createQuery("SELECT e FROM WeekMenu e "
                    + "WHERE e.weekNumber = :number", WeekMenu.class)
                    .setParameter("number", currentWeek);
            menu = query.getSingleResult();
            
            WeekMenuDTO result = new WeekMenuDTO(menu);
            return result;
        }
        finally
        {
            em.close();
        }
    }
    
    public WeekMenuDTO getWeekMenuPlanForSpecifiedWeek(int weekNumber)
    {
        throw new NotSupportedException();
    }

    
    ///////////////////////// FoodItems/Storage \\\\\\\\\\\\\\\\\\\\\\\\\
    
    
    /**
     * Counts the amount of entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    public long getFoodItemCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long foodItemCount = (long) em.createQuery("SELECT COUNT(e) FROM FoodItem e").getSingleResult();
            return foodItemCount;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * Counts the amount of entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    public long getStorageCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long storageCount = (long) em.createQuery("SELECT COUNT(e) FROM Storage e").getSingleResult();
            return storageCount;
        }
        finally
        {
            em.close();
        }
    }

    /**
     * This method attempts to update an existing Storage entity to the database
     * with the newly provided supply (itemAmount). It checks if the provided
     * itemName already exists in the database as an FoodItem entity, and if not
     * will automatically persist this FoodItem as well as the corresponding
     * Storage entity. This ensures that the OneToOne relationship between these
     * two entities will always be in effect; thus if one exists so must the
     * other.
     *
     * @param itemName
     * @param itemAmount
     * @param itemPrice
     * @return
     */
    public Storage updateFoodItemToStorage(String itemName, long itemAmount, long itemPrice)
    {
        EntityManager em = getEntityManager();
        Storage storage = null;
        FoodItem foodItem = null;

        try
        {
            TypedQuery<FoodItem> query = em.createQuery("SELECT e FROM FoodItem e "
                    + "WHERE e.itemName = :name", FoodItem.class)
                    .setParameter("name", itemName);
            foodItem = query.getSingleResult();

            storage = foodItem.getStorage();
            long oldAmount = storage.getFoodItemAmount();
            storage.setFoodItemAmount(oldAmount + itemAmount);

            em.getTransaction().begin();
            em.merge(storage);
            em.getTransaction().commit();
            return storage;
        }
        catch (NoResultException ex)
        {
            return persistFoodItemToStorage(itemName, itemAmount, itemPrice);
        }
        finally
        {
            em.close();
        }
    }

    public Storage persistFoodItemToStorage(String itemName, long itemAmount, long itemPrice)
    {
        EntityManager em = getEntityManager();
        Storage storage = null;
        FoodItem foodItem = null;

        try
        {
            foodItem = new FoodItem(itemName, itemPrice);
            storage = new Storage(itemAmount, foodItem);

            em.getTransaction().begin();
            em.persist(foodItem);
            em.persist(storage);
            em.getTransaction().commit();
            return storage;
        }
        finally
        {
            em.close();
        }
    }

}
