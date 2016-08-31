package cesar.model;

import java.util.Date;

public abstract class AbstractMessage {

    protected Integer id;

    protected String  subject;
    protected String  content;

    protected User    author;
    protected Date    date;

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject( String subject )
    {
        this.subject = subject;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent( String content )
    {
        this.content = content;
    }

    public User getAuthor()
    {
        return author;
    }

    public void setAuthor( User author )
    {
        this.author = author;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

}
