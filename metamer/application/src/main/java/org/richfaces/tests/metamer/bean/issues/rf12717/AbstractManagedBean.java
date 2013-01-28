package org.richfaces.tests.metamer.bean.issues.rf12717;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractManagedBean implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(AbstractManagedBean.class);
    private static final long serialVersionUID = 1L;

    public static final String LONG_DATE_PATTERN = "MM/dd/yyyy h:mm aa zz";
    public static final String SHORT_DATE_PATTERN = "MM/dd/yyyy";

    protected Boolean editing = false;
    protected Integer scrollPage;

    public String getDateTimeStamp() {
        return DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    }

    public HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public String redirect(String path) {
        if (StringUtils.isNotBlank(path)) {
            return path + "?faces-redirect=true";
        } else {
            return path;
        }
    }

    /**
     * Convenience method to avoid concatenating a bunch of strings on all the returns.
     */
    public String makeResourcePath(String page) {
        if (getBaseResourcePath() == null || page == null) {
            return null;
        } else {
            StringBuilder str = new StringBuilder();
            str.append(getBaseResourcePath());
            str.append(page);
            return str.toString();
        }
    }

    /**
     * DRY
     *
     * Makes sure we create messages the same everywhere.
     *
     * @param severity the severity (see {@link Severity})
     * @param summary Localized summary message text
     * @param detail Localized detail message text
     */
    protected void createFacesMessage(Severity severity, String clientId, String summary, String detail) {
        if (severity == null) {
            severity = FacesMessage.SEVERITY_INFO;
        }

        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(severity, summary, detail));
    }

    /**
     * The abstract bean has no concept of where the pages it needs to forward to are located. This method gives the path to the
     * resources. Note this should also be used by any forward returns you make in your implementing class as well to reduce the
     * number of places we have to store absolute paths.
     * <p>
     *
     * @return The path the resource in the webapp folder.
     */
    public abstract String getBaseResourcePath();

    public String getLongDatePattern() {
        return LONG_DATE_PATTERN;
    }

    public String getShortDatePattern() {
        return SHORT_DATE_PATTERN;
    }

    public Integer getScrollPage() {
        return scrollPage;
    }

    public void setScrollPage(Integer scrollPage) {
        this.scrollPage = scrollPage;
    }

    public abstract String begin();
}
