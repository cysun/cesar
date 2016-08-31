package cesar.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Email extends AbstractMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<User>        recipients;

    public Email()
    {
        recipients = new ArrayList<User>();
    }

    public List<User> getRecipients()
    {
        return recipients;
    }

    public void setRecipients( List<User> recipients )
    {
        this.recipients = recipients;
    }

}
