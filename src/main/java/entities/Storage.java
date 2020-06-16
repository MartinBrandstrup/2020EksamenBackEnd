package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "storage")
@NamedQuery(name = "Storage.deleteAllRows", query = "DELETE from Storage")
public class Storage implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    // in grams
    @Column(name = "food_item_amount")
    private long foodItemAmount;

    // Jeg antager at OneToMany er opsat for at der kan være flere varianter af 
    // en type ingrediens. Jeg mener at det forvirrer mere end det gavner, og 
    // har istedet valgt at lave det til en OneToOne relation.
//    @OneToMany(mappedBy = "storage", cascade =
//    {
//        CascadeType.MERGE, CascadeType.PERSIST
//    })
//    @JoinColumn(name = "food_item_id")
//    private Set<FoodItem> foodItems;
    
    @OneToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    public Storage(long foodItemAmount, FoodItem foodItem)
    {
        this.foodItemAmount = foodItemAmount;
//        this.foodItems = new HashSet();
        this.foodItem = foodItem;
        foodItem.setStorage(this);
    }

    public Storage()
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

    public long getFoodItemAmount()
    {
        return foodItemAmount;
    }

    public void setFoodItemAmount(long foodItemAmount)
    {
        this.foodItemAmount = foodItemAmount;
    }

    public FoodItem getFoodItem()
    {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem)
    {
        this.foodItem = foodItem;
        foodItem.setStorage(this);
    }

    public void removeFoodItem(FoodItem foodItem)
    {
        this.foodItem = null;
        foodItem.setStorage(null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Storage other = (Storage) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Storage{"
                + "id=" + id
                + ", foodItemAmount=" + foodItemAmount
                + ", foodItem=" + foodItem
                + '}';
    }

}
