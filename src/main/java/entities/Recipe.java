package entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 *
 * @author Brandstrup
 */
@Entity
@Table(name = "recipe")
@NamedQuery(name = "Recipe.deleteAllRows", query = "DELETE from Recipe")
public class Recipe implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "recipe_name")
    private String recipeName;
    
    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "directions")
    private String directions;

    @OneToMany(mappedBy = "recipe", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Recipe_FoodItem> ingredientSet;

    @ManyToMany(mappedBy = "recipes")
    private Set<WeekMenu> menu;
    
    
    
    public Recipe()
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
