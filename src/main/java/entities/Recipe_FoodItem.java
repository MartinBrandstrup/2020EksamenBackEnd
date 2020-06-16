package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 *
 * @author Brandstrup
 */
@Entity
@Table(name = "recipe_foodItem")
@NamedQuery(name = "Recipe_FoodItem.deleteAllRows", query = "DELETE from Recipe_FoodItem")
public class Recipe_FoodItem implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "food_item_id")
    private FoodItem foodItem;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // in grams
    @Column(name = "food_item_amount")
    private long foodItemAmount;

    public Recipe_FoodItem(FoodItem foodItem, Recipe recipe, long foodItemAmount)
    {
        this.foodItem = foodItem;
        this.recipe = recipe;
        this.foodItemAmount = foodItemAmount;
    }

    public Recipe_FoodItem()
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

    public FoodItem getFoodItem()
    {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem)
    {
        this.foodItem = foodItem;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public long getFoodItemAmount()
    {
        return foodItemAmount;
    }

    public void setFoodItemAmount(long foodItemAmount)
    {
        this.foodItemAmount = foodItemAmount;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Recipe_FoodItem other = (Recipe_FoodItem) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Recipe_FoodItem{" 
                + "id=" + id 
                + ", foodItem=" + foodItem 
                + ", recipe=" + recipe 
                + ", foodItemAmount=" + foodItemAmount 
                + '}';
    }

}
