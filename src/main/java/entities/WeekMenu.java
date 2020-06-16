package entities;

import java.io.Serializable;
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
    @Column(name = "id")
    private Long id;

    @ManyToMany
    @JoinTable(name = "weekMenu_recipes")
    private Set<Recipe> recipes;

    @Column(nullable = false, name = "week_number")
    private int weekNumber;

    @Column(nullable = false, name = "year")
    private int year;
    
    
    
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

}
