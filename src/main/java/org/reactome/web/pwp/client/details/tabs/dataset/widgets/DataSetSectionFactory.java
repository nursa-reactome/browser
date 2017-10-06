package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.reactome.web.pwp.client.details.common.widgets.panels.TextPanel;
import org.reactome.web.pwp.nursa.model.DataSet;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public abstract class DataSetSectionFactory {

    private static String OVERVIEW_TITLE = "Overview";
    private static String GENE_LIST_TITLE = "Gene List";
    private static String EXPRESSION_TITLE = "Expression";

    public static Widget getOverviewSection(DataSet dataset) {
        Widget panel = new TextPanel(dataset.getDescription());
        return getDataSetSection(OVERVIEW_TITLE, panel);
    }

    public static Widget getDataPointsSection(DataSet dataset) {
        Widget table = DataPointTableFactory.getTable(dataset);
        return getDataSetSection(GENE_LIST_TITLE, table);
    }

    public static Widget getExpressionSection(DataSet dataset) {
        Widget panel = new TextPanel("TODO - Expression panel");
        return getDataSetSection(EXPRESSION_TITLE, panel);
    }

    public static Widget getDataSetSection(String title, Widget child) {
        List<Widget> list = new LinkedList<>();
        list.add(child);
        return getDataSetSection(title, list);
    }

    public static Widget getDataSetSection(String title, List<Widget> children) {
        VerticalPanel content = new VerticalPanel();
        for (Widget child : children) {
            content.add(child);
        }
        content.setWidth("100%");
        DataSetSection row = new DataSetSection();
        row.setWidth("100%");
        row.add(title, content);

        return row;
    }
}
