package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import com.google.gwt.user.client.ui.ScrollPanel;

import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetPanel extends ScrollPanel {

    public DatasetPanel(Dataset dataset) {
        DatasetTable table = new DatasetTable(dataset);
        add(table);
    }
}
