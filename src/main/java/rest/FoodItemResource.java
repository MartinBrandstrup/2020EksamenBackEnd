package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.EMF_Creator;
import dtos.FoodItemDTOIn;
import dtos.FoodItemDTOOut;
import entities.FoodItem;
import entities.Storage;
import facades.MasterFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 
 * 
 * @author Brandstrup
 */
@Path("ingredient")
public class FoodItemResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020Eksamen_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);
    private static final MasterFacade FACADE = MasterFacade.getMasterFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String demo()
    {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFoodItemCount()
    {
        long count = FACADE.getFoodItemCount();
        return "{\"count\":" + count + "}";
    }

    @POST
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String persistUpdateFoodItemToStorage(String requestBody)
    {
        FoodItemDTOIn fiDTO = GSON.fromJson(requestBody, FoodItemDTOIn.class);
        
        Storage sManaged = FACADE.updateFoodItemToStorage(
                fiDTO.getItemName(), fiDTO.getItemAmount(), fiDTO.getPricePerKG());

        FoodItem fiManaged = sManaged.getFoodItem();
        FoodItemDTOOut result = new FoodItemDTOOut(sManaged, fiManaged);
        
        return GSON.toJson(result);
    }
    
}
