package facades;

import entities.FoodItem;
import entities.RenameMe;
import entities.Storage;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
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

    public Storage persistFoodItemToStorage(String itemName, long itemAmount, long itemPrice)
    {
        EntityManager em = getEntityManager();
        try
        {
            Storage storage = null;
            FoodItem foodItem = null;

            TypedQuery<FoodItem> query = em.createQuery("SELECT e FROM FoodItem e "
                    + "WHERE e.itemName = :name", FoodItem.class)
                    .setParameter("name", itemName);
            foodItem = query.getSingleResult();
            
            if (foodItem == null)
            {
                foodItem = new FoodItem(itemName, itemPrice);
                storage = new Storage(itemAmount, foodItem);
                
                em.persist(foodItem);
                em.persist(storage);
                return storage;
            }
            else
            {
                storage = foodItem.getStorage();
                long oldAmount = storage.getFoodItemAmount();
                storage.setFoodItemAmount(oldAmount + itemAmount);
                
                em.merge(storage);
                return storage;
            }
        }
        finally
        {
            em.close();
        }
    }

}
