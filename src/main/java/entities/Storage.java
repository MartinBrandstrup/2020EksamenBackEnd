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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "storage", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    @JoinColumn(name = "food_item_id")
    private Set<FoodItem> foodItem;
    
    // in grams
    @Column(name = "food_item_amount")
    private long foodItemAmount;

    
    
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

}
