/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Brandstrup
 */
public class FoodItemDTOIn
{
    private String itemName;
    private long pricePerKG, itemAmount; //price in øre, amount in grams

    public FoodItemDTOIn(String itemName, long pricePerKG, long itemAmount)
    {
        this.itemName = itemName;
        this.pricePerKG = pricePerKG;
        this.itemAmount = itemAmount;
    }

    public String getItemName()
    {
        return itemName;
    }

    public long getPricePerKG()
    {
        return pricePerKG;
    }

    public long getItemAmount()
    {
        return itemAmount;
    }
    
}
