package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 *
 * @author Brandstrup
 */
@Entity
@Table(name = "week_menu")
@NamedQuery(name = "WeekMenu.deleteAllRows", query = "DELETE from WeekMenu")
public class WeekMenu implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(nullable = false, name = "week_number")
    private int weekNumber;

    @Column(nullable = false, name = "year")
    private int year;

    @ManyToMany
    @JoinTable(name = "weekMenu_recipes")
    private Set<Recipe> recipes;

    /**
     * Requires a Set of 7 legal Recipe objects.
     * By creating this object all Recipe objects used will have relations set 
     * accordingly.
     * 
     * @param recipes the HashSet of Recipe objects to be added to this object.
     * @throws IllegalArgumentException if more or fewer than 7 Recipe objects
     * are passed as parameter.
     */
    public WeekMenu(Set<Recipe> recipes) throws IllegalArgumentException
    {
        this.weekNumber = LocalDate.now(ZoneId.of("UTC")).get(WeekFields.ISO.weekOfYear());
        this.year = LocalDate.now(ZoneId.of("UTC")).get(ChronoField.YEAR);
        this.recipes = new HashSet();
        addRecipes(recipes);
    }

    public WeekMenu()
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

    public int getWeekNumber()
    {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber)
    {
        this.weekNumber = weekNumber;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public Set<Recipe> getRecipes()
    {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) throws IllegalArgumentException
    {
        addRecipes(recipes);
    }

    private void addRecipes(Set<Recipe> recipes)
    {
        if (recipes == null || recipes.isEmpty() || recipes.size() != 7)
        {
            throw new IllegalArgumentException();
        }

        recipes.forEach((recipe) ->
        {
            recipe.addMenu(this);
        });
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
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
        final WeekMenu other = (WeekMenu) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "WeekMenu{" 
                + "id=" + id 
                + ", weekNumber=" + weekNumber 
                + ", year=" + year 
                + ", recipes=" + recipes 
                + '}';
    }
    
}
