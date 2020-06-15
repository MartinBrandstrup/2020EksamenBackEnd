package facades;

import dtos.ChuckDTO;
import dtos.DadDTO;
import entities.Joke;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class JokeFacade
{

    private static JokeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private JokeFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static JokeFacade getJokeFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new JokeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    /**
     * Counts the amount of entries existing in the database.
     *
     * @return The amount of existing entries in the database.
     */
    public long getJokeCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long jokeCount = (long) em.createQuery("SELECT COUNT(o) FROM Joke o").getSingleResult();
            return jokeCount;
        }
        finally
        {
            em.close();
        }
    }

    public Joke persistChuckJoke(ChuckDTO DTO)
    {
        EntityManager em = emf.createEntityManager();
        Joke joke = null;
        
        try
        {
            em.getTransaction().begin();
            joke = new Joke(DTO.getUrl(), DTO.getValue());
            System.out.println(joke.toString());
            em.persist(joke);
            em.getTransaction().commit();
            return joke;
        }
        finally
        {
            em.close();
        }
    }
    
    public Joke persistDadJoke(DadDTO DTO)
    {
        EntityManager em = emf.createEntityManager();
        Joke joke;
        
        try
        {
            em.getTransaction().begin();
            joke = new Joke(DTO.getId(), DTO.getJoke());
            em.persist(joke);
            em.getTransaction().commit();
            return joke;
        }
        finally
        {
            em.close();
        }
    }
}
