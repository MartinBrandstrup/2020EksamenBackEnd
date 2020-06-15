package facades;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import errorhandling.AuthenticationException;
import errorhandling.AlreadyExistsException;
import errorhandling.NotFoundException;
import javax.persistence.TypedQuery;

/**
 * @author lam@cphbusiness + Christian
 */
public class UserFacade
{

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade()
    {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException
    {
        EntityManager em = emf.createEntityManager();
        User user;
        try
        {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password))
            {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally
        {
            em.close();
        }
        return user;
    }

    public User createNormalUser(String username, String password, String role ) throws AlreadyExistsException
    {
        EntityManager em = emf.createEntityManager();
        User userRegister = new User(username, password);

        try
        {
          Role userRole = em.find(Role.class, role);
//            Role userRole = new Role("user");
            userRegister.addRole(userRole);
            User user = em.find(User.class, username);
            if (user != null)
            {
                throw new AlreadyExistsException("User name already exists");
            }
            em.getTransaction().begin();
            em.persist(userRegister);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }
        return userRegister;
    }

   
    public long getUserCount()
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            long renameMeCount = (long) em.createQuery("SELECT COUNT(r) FROM User r").getSingleResult();
            return renameMeCount;
        } finally
        {
            em.close();
        }

    }

    public User getUserByName(String userName) throws NotFoundException
    {
        EntityManager em = emf.createEntityManager();
        try
        {
            User u = em.find(User.class, userName);
            if (u == null)
            {
                throw new NotFoundException("No object matching provided id exists in database.");
            }
            return u;
        } catch (IllegalArgumentException ex)
        {
            throw new NotFoundException("No object matching provided id exists in database. IllegalArgumentException.");
        } finally
        {
            em.close();
        }
    }

}
