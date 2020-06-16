package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
@Path("recipe")
public class RecipeResource
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
    public String getRecipeCount()
    {
        long count = FACADE.getRecipeCount();
        return "{\"count\":" + count + "}";
    }

}
