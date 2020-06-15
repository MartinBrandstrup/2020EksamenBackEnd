package rest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dtos.ChuckDTO;
import dtos.CombinedJokeDTO;
import dtos.DadDTO;
import facades.JokeFacade;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;
import utils.WebScraper;

/**
 * REST Web Service
 *
 * @author Brandstrup
 */
@Path("jokes")
public class JokeResource
{

    @Context
    private UriInfo context;

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
            "pu",
            "jdbc:mysql://localhost:3307/2020CA3_test",
            "dev",
            "ax2",
            EMF_Creator.Strategy.CREATE);

    private static final JokeFacade FACADE = JokeFacade.getJokeFacade(EMF);
    private static final WebScraper WEBSCRAPER = WebScraper.getWebScraper();

    // URLs
    private static final String dadURL = "https://icanhazdadjoke.com";
    private static final String chuckURL = "https://api.chucknorris.io/jokes/random";

    private ChuckDTO scrapeChuckJoke(String url)
            throws SocketTimeoutException, ProtocolException, IOException
    {
        Gson gson = new Gson();
        HashMap headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("User-Agent", "server");
        String result = WEBSCRAPER.sendRequest(url, "GET", headers, 5000);

        // Jeg mener at TypeToken er den, der holder styr på hvilke JSON attributter
        // der bliver smidt med og får dem placeret korrekt i den resulterende DTO
        // så længe at navngivningen holder
        ChuckDTO chuckDTO = gson.fromJson(result, new TypeToken<ChuckDTO>()
        {
        }.getType());

        return chuckDTO;
    }

    private DadDTO scrapeDadJoke(String url)
            throws SocketTimeoutException, ProtocolException, IOException
    {
        Gson gson = new Gson();
        HashMap headers = new HashMap();
        headers.put("Accept", "application/json");
        headers.put("User-Agent", "server");
        String result = WEBSCRAPER.sendRequest(url, "GET", headers, 5000);

        DadDTO dadDTO = gson.fromJson(result, new TypeToken<DadDTO>()
        {
        }.getType());

        return dadDTO;
    }

    @GET
    @Path("/{chuck}/{dad}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJokes(@PathParam("chuck") int chuck, @PathParam("dad") int dad)
    {
        Gson gson = new Gson();
        List<ChuckDTO> chuckList = new ArrayList<>();
        List<DadDTO> dadList = new ArrayList<>();

        try
        {
            for (int i = 0; i < chuck; i++)
            {
                ChuckDTO dto = scrapeChuckJoke(chuckURL);
                if (dto == null || dto.getUrl().isEmpty())
                {
                    throw new IllegalArgumentException();
                }
                chuckList.add(dto);
                FACADE.persistChuckJoke(dto);
            }

            for (int i = 0; i < dad; i++)
            {
                DadDTO dto = scrapeDadJoke(dadURL);
                if (dto == null || dto.getId().isEmpty())
                {
                    throw new IllegalArgumentException();
                }
                dadList.add(dto);
                FACADE.persistDadJoke(dto);
            }
        }
        catch (ProtocolException ex)
        {
            Logger.getLogger(JokeResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(JokeResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        CombinedJokeDTO combDTO = new CombinedJokeDTO(chuckList, dadList);

        String combJSON = gson.toJson(combDTO);
        return combJSON;
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJokes() throws IOException
//    {
//        Gson gson = new Gson();
//        String chuck1 = HttpUtils_deprecated.fetchData("https://api.chucknorris.io/jokes/random");
//        ChuckDTO chuckDTO1 = gson.fromJson(chuck1, ChuckDTO.class);
//
//        String chuck2 = HttpUtils_deprecated.fetchData("https://api.chucknorris.io/jokes/random");
//        ChuckDTO chuckDTO2 = gson.fromJson(chuck2, ChuckDTO.class);
//
//        String chuck3 = HttpUtils_deprecated.fetchData("https://api.chucknorris.io/jokes/random");
//        ChuckDTO chuckDTO3 = gson.fromJson(chuck3, ChuckDTO.class);
//
//        String chuck4 = HttpUtils_deprecated.fetchData("https://api.chucknorris.io/jokes/random");
//        ChuckDTO chuckDTO4 = gson.fromJson(chuck4, ChuckDTO.class);
//
//        String dad = HttpUtils_deprecated.fetchData("https://icanhazdadjoke.com");
//        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
//
//        OurDTO_deprecated combinedDTO = new OurDTO_deprecated(chuckDTO1, chuckDTO2, chuckDTO3, chuckDTO4, dadDTO);
//
//        //This is what your endpoint should return
//        String combinedJSON = gson.toJson(combinedDTO);
//
//        return combinedJSON;
//    }
}
