package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.WeekMenuDTO;
import utils.EMF_Creator;
import facades.MasterFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 *
 * @author Brandstrup
 */
@Path("menu")
public class MenuPlannerResource
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
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public String getRecipeCount()
    {
        long count = FACADE.getRecipeCount();
        return "{\"count\":" + count + "}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getWeekMenu()
    {
        WeekMenuDTO wmDTO = FACADE.getWeekMenuPlanForCurrentWeek();

        return GSON.toJson(wmDTO);
    }
    
    @GET
    @Path("/recipe/all")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllRecipes()
    {
        RecipeListDTO rlDTO = FACADE.getAllRecipes();
        
        return GSON.toJson(rlDTO);
    }
    
    @GET
    @Path("/populate")
    @Produces(MediaType.APPLICATION_JSON)
    public void populateDatabaseWithDummyData()
    {
        FACADE.populateDatabase();;
    }

}
