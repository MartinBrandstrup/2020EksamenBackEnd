package entities;

import java.io.Serializable;
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
    @Column(name = "id")
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

}
