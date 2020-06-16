/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.FoodItem;
import entities.Storage;

/**
 *
 * @author Brandstrup
 */
public class FoodItemDTOOut
{
    private String ingredientName;
    private long ingredientPrice, ingredientAmount;

    public FoodItemDTOOut(Storage storage, FoodItem foodItem)
    {
        this.ingredientName = foodItem.getItemName();
        this.ingredientPrice = foodItem.getPricePerKG();
        this.ingredientAmount = storage.getFoodItemAmount();
    }
    
}
