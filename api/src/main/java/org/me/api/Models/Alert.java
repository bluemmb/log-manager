package org.me.api.Models;

import java.util.Date;

public class Alert {
    public int id;
    public String ruleName;
    public String component;
    public String description;
    public Date created_at;

    public Alert(int id, String ruleName, String component, String description, Date created_at)
    {
        this.id = id;
        this.ruleName = ruleName;
        this.component = component;
        this.description = description;
        this.created_at = created_at;
    }
}
