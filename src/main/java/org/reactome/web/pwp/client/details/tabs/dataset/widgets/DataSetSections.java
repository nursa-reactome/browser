package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.reactome.web.pwp.client.details.common.widgets.panels.TextPanel;
import org.reactome.nursa.model.DataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public abstract class DataSetSectionFactory {

    public static String OVERVIEW_TITLE = "Overview";
    public static String GENE_LIST_TITLE = "Gene List";
    public static String PATHWAY_TITLE = "Pathway";

    public static Map<String, Widget> build(DataSet dataset) {
        HashMap<String, Widget> sections = new HashMap<String, Widget>();
        sections.put(OVERVIEW_TITLE, getOverviewSection(dataset));
        sections.put(GENE_LIST_TITLE, getDataPointsSection(dataset));
        sections.put(PATHWAY_TITLE, getPathwaySection(dataset));
        return sections;
    }

    private static Widget getOverviewSection(DataSet dataset) {
        Widget panel = new TextPanel(dataset.getDescription());
        panel.setStyleName(DataSetPanel.RESOURCES.getCSS().overview());
        return getDataSetSection(OVERVIEW_TITLE, panel);
    }

    private static Widget getDataPointsSection(DataSet dataset) {
        Widget table = DataPointTableFactory.getTable(dataset);
        return getDataSetSection(GENE_LIST_TITLE, table);
    }

    private static Widget getPathwaySection(DataSet dataset) {
        Widget panel = new PathwayPanel(dataset);
        return getDataSetSection(PATHWAY_TITLE, panel);
    }

    private static Widget getDataSetSection(String title, Widget child) {
        List<Widget> children = new ArrayList<Widget>(1);
        children.add(child);
        return getDataSetSection(title, children);
    }

    private static Widget getDataSetSection(String title, List<Widget> children) {
        VerticalPanel content = new VerticalPanel();
        for (Widget child : children) {
            content.add(child);
        }
        content.setWidth("100%");
        DataSetSection section = new DataSetSection();
        section.setWidth("100%");
        section.add(title, content);

        return section;
    }
}
