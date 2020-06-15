package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 *
 * @author Brandstrup
 */
@Entity
@Table(name = "jokes")
@NamedQuery(name = "Joke.deleteAllRows", query = "DELETE from Joke")
public class Joke implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "joke_body")
    private String jokeBody;

    @Column(name = "persist_date")
    private LocalDate persistDate;

    public Joke()
    {
    }

    public Joke(String url, String jokeBody)
    {
        this.url = url;
        this.jokeBody = jokeBody;
        this.persistDate = LocalDate.now(ZoneId.of("UTC"));
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getJokeBody()
    {
        return jokeBody;
    }

    public void setJokeBody(String jokeBody)
    {
        this.jokeBody = jokeBody;
    }

    public LocalDate getPersistDate()
    {
        return persistDate;
    }

    public void setPersistDate(LocalDate persistDate)
    {
        this.persistDate = persistDate;
    }

    @Override
    public String toString()
    {
        return "Joke{" + "id=" + id + ", url=" + url + ", jokeBody=" + jokeBody + '}';
    }

}
