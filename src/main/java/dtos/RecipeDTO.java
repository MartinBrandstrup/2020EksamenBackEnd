/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Recipe;

/**
 *
 * @author Brandstrup
 */
public class RecipeDTO
{
    private String recipeName, instructions, preparationTime;

    public RecipeDTO(Recipe recipe)
    {
        this.recipeName = recipe.getRecipeName();
        this.instructions = recipe.getInstructions();
        
        int totalPrepTime = recipe.getPreparationTime();
        int hours = totalPrepTime/60;
        int minutes = totalPrepTime%60;
        this.preparationTime = hours + " hours, " + minutes + " minutes.";
    }
    
}
