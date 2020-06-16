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
//    @Column(name = "id")
    private Long id;

    @Column(nullable = false, unique = true, name = "recipe_name")
    private String recipeName;

    // in minutes
    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "instructions")
    private String instructions;

    // which ingredients are used in this recipe
    @OneToMany(mappedBy = "recipe", cascade =
    {
        CascadeType.MERGE, CascadeType.PERSIST
    })
    private Set<Recipe_FoodItem> ingredients;

    @ManyToMany(mappedBy = "recipes")
    private Set<WeekMenu> menu;

    public Recipe(String recipeName, int preparationTime, String instructions)
    {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.instructions = instructions;
        this.ingredients = new HashSet();
        this.menu = new HashSet();
    }

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

    public String getRecipeName()
    {
        return recipeName;
    }

    public void setRecipeName(String recipeName)
    {
        this.recipeName = recipeName;
    }

    public int getPreparationTime()
    {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime)
    {
        this.preparationTime = preparationTime;
    }

    public String getInstructions()
    {
        return instructions;
    }

    public void setInstructions(String instructions)
    {
        this.instructions = instructions;
    }

    public Set<Recipe_FoodItem> getIngredients()
    {
        return ingredients;
    }

    /**
     * This method creates a join object in the form of a Recipe_FoodItem object
     * and proceeds to set this join object with the provided FoodItem object as
     * well as this Recipe object. The Recipe_FoodItem join object will also
     * have an amount parameter which is provided in this method. Amount is
     * measured in grams.
     *
     * @param foodItem the FoodItem object to add to this Recipe using a join
     * object to conserve proper persistence relations.
     * @param amount the amount of provided FoodItem in grams.
     */
    public void addFoodItem(FoodItem foodItem, long amount)
    {
        Recipe_FoodItem joinObject = new Recipe_FoodItem(foodItem, this, amount);
        this.ingredients.add(joinObject);
        foodItem.getRecipes().add(joinObject);
    }

    /**
     * This method aims to clean up any relations between a Recipe and a
     * FoodItem object (aka ingredient). Simultaneously it will also attempt to
     * remove the joinObject's relations in order to manage successful
     * persistence to the database. When using this method, remember to use an
     * entity manager to remove remnants from the database.
     *
     * @param joinObject the Recipe_FoodItem object that holds the relationship.
     * @param foodItem the FoodItem object used in this Recipe.
     * @throws IllegalArgumentException if the FoodItem object is not a part of
     * this recipe.
     */
    public void removeFoodItem(Recipe_FoodItem joinObject, FoodItem foodItem)
            throws IllegalArgumentException
    {
        if (!(joinObject.getFoodItem().equals(foodItem)))
        {
            throw new IllegalArgumentException();
        }

        this.ingredients.remove(joinObject);
        joinObject.setRecipe(null);
        foodItem.getRecipes().remove(joinObject);
        joinObject.setFoodItem(null);

    }

    public Set<WeekMenu> getMenu()
    {
        return menu;
    }

    public void addMenu(WeekMenu menu)
    {
        this.menu.add(menu);
        menu.getRecipes().add(this);
    }

    public void removeMenu(WeekMenu menu)
    {
        this.menu.remove(menu);
        menu.getRecipes().remove(this);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Recipe other = (Recipe) obj;
        if (!Objects.equals(this.recipeName, other.recipeName))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "Recipe{"
                + "id=" + id
                + ", recipeName=" + recipeName
                + ", preparationTime=" + preparationTime
                + ", instructions=" + instructions
                + ", ingredients=" + ingredients
                + ", menu=" + menu
                + '}';
    }

}
