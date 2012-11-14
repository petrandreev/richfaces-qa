package org.richfaces.tests.metamer.bean.issues;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "rf12421")
@RequestScoped
public class RF12421 {

    public int getFacesMessagesCount() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage("This is a message");

//        facesContext.addMessage(null, facesMessage);

        return facesContext.getMessageList().size();
    }
}
