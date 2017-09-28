package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.reactome.web.pwp.client.details.common.widgets.panels.DetailsPanel;
import org.reactome.web.pwp.client.details.common.widgets.panels.TextPanel;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public abstract class DatasetRowFactory {

    private static String OVERVIEW_TITLE = "Overview";
    private static String COMPARE_TITLE = "Compare to Other Dataset";
    private static String GENE_LIST_TITLE = "Gene List";
    private static String PATHWAY_TITLE = "Pathway";

    public static Widget getOverviewRow(Dataset dataset) {
        TextPanel panel = new TextPanel("TODO - Overview panel");
        return getDatasetRow(OVERVIEW_TITLE, panel);
    }

    public static Widget getComparisonRow(Dataset dataset) {
        TextPanel panel = new TextPanel("TODO - Comparison panel");
        return getDatasetRow(COMPARE_TITLE, panel);
    }

    public static Widget getGenesRow(Dataset dataset) {
        TextPanel panel = new TextPanel("TODO - Gene List panel");
        return getDatasetRow(GENE_LIST_TITLE, panel);
    }

    public static Widget getPathwayRow(Dataset dataset) {
        TextPanel panel = new TextPanel("TODO - Pathway panel");
        return getDatasetRow(PATHWAY_TITLE, panel);
    }

    public static Widget getDatasetRow(String title, DetailsPanel panel) {
        List<DetailsPanel> list = new LinkedList<>();
        list.add(panel);
        return getDatasetRow(title, list);
    }

    public static DatasetRow getDatasetRow(String title, List<DetailsPanel> panelList) {
        VerticalPanel content = new VerticalPanel();
        for (DetailsPanel detailsPanel : panelList) {
            content.add(detailsPanel);
        }
        content.setWidth("100%");

        DatasetRow row = new DatasetRow();
        row.setWidth("100%");
        row.add(title, content);

        return row;
    }
}
