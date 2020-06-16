package entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 *
 * @author Brandstrup
 */
@Entity
@Table(name = "food_item")
@NamedQuery(name = "FoodItem.deleteAllRows", query = "DELETE from FoodItem")
public class FoodItem implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "item_name")
    private String itemName;

    // In øre
    @Column(name = "price_per_kg")
    private long pricePerKG;

    // in which recipes is this ingredient used in
    @OneToMany(mappedBy = "foodItem", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Recipe_FoodItem> recipes;

//    @ManyToOne
    @OneToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    public FoodItem(String itemName, long pricePerKG)
    {
        this.itemName = itemName;
        this.pricePerKG = pricePerKG;
        this.recipes = new HashSet();
        this.storage = null;
    }

    public FoodItem()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public long getPricePerKG()
    {
        return pricePerKG;
    }

    public void setPricePerKG(long pricePerKG)
    {
        this.pricePerKG = pricePerKG;
    }

    public Set<Recipe_FoodItem> getRecipes()
    {
        return recipes;
    }

    public Storage getStorage()
    {
        return storage;
    }

    /**
     * !!Warning!! This method should not be called! Use the setFoodItem method
     * from the appropriate Storage object instead.
     */
    public void setStorage(Storage storage)
    {
        this.storage = storage;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final FoodItem other = (FoodItem) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "FoodItem{"
                + "id=" + id
                + ", itemName=" + itemName
                + ", pricePerKG=" + pricePerKG
                + ", recipes=" + recipes
                + ", storage=" + storage
                + '}';
    }

}
