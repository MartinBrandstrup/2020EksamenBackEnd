package facades;

import entities.FoodItem;
import entities.Storage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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

    ///////////////////////// FoodItems/Storage \\\\\\\\\\\\\\\\\\\\\\\\\
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
