package entities;

import java.io.Serializable;
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
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "item_name")
    private String itemName;

    @Column(name = "price_per_kg")
    private double pricePerKG;

    @OneToMany(mappedBy = "foodItem", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Recipe_FoodItem> ingredientSet;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    
    
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

}
