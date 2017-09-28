package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public enum PropertyType {

    OVERVIEW("Overview"),
    COMPARE("Compare to Other Dataset"),
    GENE_LIST("Gene List"),
    PATHWAY("Pathway");
	
    private String title;

    PropertyType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
