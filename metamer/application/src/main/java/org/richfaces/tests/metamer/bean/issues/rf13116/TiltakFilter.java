package org.richfaces.tests.metamer.bean.issues.rf13116;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import org.richfaces.model.Filter;

@ManagedBean
@SessionScoped
public class TiltakFilter implements Serializable {


    private static final long serialVersionUID = 3891868950010001044L;
    /**
     *
     */
    private String fristFilter = "";

    private String tittelFilter = "robot";
    private String ansvarligFilter = "";
    private String lokasjonFilter = "";
    private String kategoriFilter = "";
    private String statusFilter = "";
    private String avdelingFilter = "";


    public Filter<?> getTittelImpl() {

        return new Filter<Object>() {
            @Override
            public boolean accept(Object i) {

                String s = getTittelFilter();

                Tiltak item = (Tiltak) i;

                if (s.length() == 0)
                    return true;

                if (item != null && item.getTittel() != null) {

                    if (item.getTittel().toLowerCase().indexOf(s.toLowerCase()) >= 0)
                        return true;
                    else
                        return false;
                }

                return false;
            }
        };
    }

    public String getFristFilter() {
        return fristFilter;
    }

    public void setFristFilter(String fristFilter) {
        this.fristFilter = fristFilter;
    }

    /**
     * @return the tittelFilter
     */
    public String getTittelFilter() {
        return this.tittelFilter;
    }

    /**
     * @param tittelFilter the tittelFilter to set
     */
    public void setTittelFilter(String tittelFilter) {
        this.tittelFilter = tittelFilter;
    }

    /**
     * @return the avdelingFilter
     */
    public String getAvdelingFilter() {
        return this.avdelingFilter;
    }

    /**
     * @param avdelingFilter the avdelingFilter to set
     */
    public void setAvdelingFilter(String avdelingFilter) {
        this.avdelingFilter = avdelingFilter;
    }

    /**
     * @return the lokasjonFilter
     */
    public String getLokasjonFilter() {
        return this.lokasjonFilter;
    }

    /**
     * @param lokasjonFilter the lokasjonFilter to set
     */
    public void setLokasjonFilter(String lokasjonFilter) {
        this.lokasjonFilter = lokasjonFilter;
    }

    /**
     * @return the ansvarligFilter
     */
    public String getAnsvarligFilter() {
        return this.ansvarligFilter;
    }

    /**
     * @param ansvarligFilter the ansvarligFilter to set
     */
    public void setAnsvarligFilter(String ansvarligFilter) {
        this.ansvarligFilter = ansvarligFilter;
    }

    /**
     * @return the kategoriFilter
     */
    public String getKategoriFilter() {
        return this.kategoriFilter;
    }

    /**
     * @param kategoriFilter the kategoriFilter to set
     */
    public void setKategoriFilter(String kategoriFilter) {
        this.kategoriFilter = kategoriFilter;
    }

    /**
     * @return the statusFilter
     */
    public String getStatusFilter() {
        return this.statusFilter;
    }

    /**
     * @param statusFilter the statusFilter to set
     */
    public void setStatusFilter(String statusFilter) {
        this.statusFilter = statusFilter;
    }


}