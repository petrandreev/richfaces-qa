package org.richfaces.tests.metamer.bean.issues;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "rf13216")
@RequestScoped
public class RF13216 {

    public String action() {
        System.out.println("action called!");
        return "";
    }
}
