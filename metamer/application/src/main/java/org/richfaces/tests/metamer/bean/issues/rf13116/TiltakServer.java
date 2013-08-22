package org.richfaces.tests.metamer.bean.issues.rf13116;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class TiltakServer {

    private List<Tiltak> tiltak;

    @PostConstruct
    public void initiateBean() {
        tiltak = new ArrayList<Tiltak>();
        for(int i = 0; i < 30; i++) {
            tiltak.add(new Tiltak("Robot " + i));
            tiltak.add(new Tiltak("Human " + i));
        }
    }

    public List<Tiltak> getTiltak() {
        return tiltak;
    }

    public void setTiltak(List<Tiltak> tiltak) {
        this.tiltak = tiltak;
    }
}
