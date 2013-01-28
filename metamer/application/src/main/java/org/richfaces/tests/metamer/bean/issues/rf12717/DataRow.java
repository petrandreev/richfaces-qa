package org.richfaces.tests.metamer.bean.issues.rf12717;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class DataRow implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String description;
    private Date theDate;
    private List<String> aliases;

    public DataRow() {}

    public DataRow(Long id, String description, Date theDate, List<String> aliases) {
        super();
        this.id = id;
        this.description = description;
        this.theDate = theDate;
        this.aliases = aliases;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTheDate() {
        return theDate;
    }

    public void setTheDate(Date theDate) {
        this.theDate = theDate;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }


}
