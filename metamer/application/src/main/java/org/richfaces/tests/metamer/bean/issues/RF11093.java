package org.richfaces.tests.metamer.bean.issues;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

import static javax.faces.component.visit.VisitContext.createVisitContext;
import static javax.faces.component.visit.VisitResult.ACCEPT;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 */
@ManagedBean(name = "rf11093")
@RequestScoped
public class RF11093 {

    private List<String> values;
    private static final Logger LOGGER = Logger.getLogger(RF11093.class.getName());

    @PostConstruct
    public void initialize() {
        values = new ArrayList<String>();
        values.add("One");
        values.add("Two");
        values.add("Three");
        values.add("Four");
        values.add("Five");
    }

    public String someAction() {
        LOGGER.severe("Some action!");

        VisitContext vc = createVisitContext(getCurrentInstance());
        FacesContext.getCurrentInstance().getViewRoot().visitTree(vc, new VisitCallback() {

            @Override
            public VisitResult visit(VisitContext vc, UIComponent uic) {
                LOGGER.info("Visit callback!");
                return ACCEPT;
            }
        });
        LOGGER.severe("After visit");
        FacesContext context = FacesContext.getCurrentInstance();
        String item = context.getApplication().evaluateExpressionGet(context, "#{item}", String.class);
        LOGGER.severe("Var: " + item);
        return "";
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
