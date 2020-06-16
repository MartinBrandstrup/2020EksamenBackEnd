/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author Brandstrup
 */
public class RecipeListDTO
{
    private List<RecipeDTO> recipeList;

    public RecipeListDTO(List<RecipeDTO> recipeList)
    {
        this.recipeList = recipeList;
    }
    
}
