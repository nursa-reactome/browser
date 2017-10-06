package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import org.reactome.web.pwp.nursa.model.DataSet;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetPanel extends DockLayoutPanel {

    public DataSetPanel(DataSet dataset) {
        super(Style.Unit.EM);
        HorizontalPanel topBar = getTopBar(dataset);
        addNorth(topBar, 4);
        ScrollPanel scrollPanel = new ScrollPanel();
        Widget table = getMainContent(dataset);
        scrollPanel.add(table);
        add(scrollPanel);
    }

    private HorizontalPanel getTopBar(DataSet dataset) {
        HorizontalPanel topBar = new HorizontalPanel();
        topBar.setWidth("100%");
        Widget name = new HTMLPanel(dataset.getName());
        name.setStyleName("elv-Details-Title-Wrap");
        topBar.add(name);
        Widget doi = new HTMLPanel("DOI: " + dataset.getDOI());
        doi.setStyleName("elv-Details-Title-Wrap");
        topBar.add(doi);
        Button compare = new Button("Compare...");
        topBar.add(compare);
        topBar.setCellVerticalAlignment(name, HasVerticalAlignment.ALIGN_MIDDLE);
        return topBar;
    }

    private Widget getMainContent(DataSet dataset) {
        VerticalPanel vp = new VerticalPanel();
        vp.add(DataSetSectionFactory.getOverviewSection(dataset));
        vp.add(DataSetSectionFactory.getDataPointsSection(dataset));
        vp.add(DataSetSectionFactory.getExpressionSection(dataset));
        return vp;
    }
}
