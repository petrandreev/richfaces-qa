package org.richfaces.tests.metamer.bean.issues;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "rf13216")
@SessionScoped
public class RF13216 {

    private boolean showPopup = false;

    public RF13216() {
        System.out.println("New bean created!");
    }

    public String action() {
        System.out.println("action called!");
        return "";
    }

    public String switchPopup() {
        showPopup = !showPopup;
        System.out.println("Switched popup called! Showpopup property set to " + showPopup);
        return "";
    }

    public boolean isShowPopup() {
        System.out.println("isShowPopup called!");
        return showPopup;
    }

    public void setShowPopup(boolean showPopup) {
        this.showPopup = showPopup;
    }
}
