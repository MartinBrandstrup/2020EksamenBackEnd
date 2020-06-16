/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Recipe;
import entities.WeekMenu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Brandstrup
 */
public class WeekMenuDTO
{
    private String weekNumber, year;
    private List<RecipeDTO> recipes = new ArrayList();

    public WeekMenuDTO(WeekMenu menu)
    {
        this.weekNumber = "" + menu.getWeekNumber();
        this.year = "" + menu.getYear();
        
        for (Recipe recipe : menu.getRecipes())
        {
            recipes.add(new RecipeDTO(recipe));
        }
    }
    
    
}
