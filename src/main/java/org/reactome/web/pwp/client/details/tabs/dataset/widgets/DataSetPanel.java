package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import java.util.Map;

import org.reactome.nursa.model.DataSet;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetPanel extends DockLayoutPanel {
    /**
     * The UiBinder interface.
     */
//    interface Binder extends UiBinder<Widget, DataSetPanel> {
//    }
//    static final Binder uiBinder = GWT.create(Binder.class);

    private Map<String, Widget> sections;

    public DataSetPanel(DataSet dataset) {
        super(Style.Unit.EM);
        addStyleName(RESOURCES.getCSS().main());
        //initWidget(uiBinder.createAndBindUi(this));
        HorizontalPanel topBar = getTopBar(dataset);
        addNorth(topBar, 4);
        ScrollPanel scrollPanel = new ScrollPanel();
        Widget table = getMainContent(dataset);
        scrollPanel.add(table);
        add(scrollPanel);
    }

    private HorizontalPanel getTopBar(DataSet dataset) {
        HorizontalPanel topBar = new HorizontalPanel();
        topBar.setWidth("95%");
        Widget name = new HTMLPanel(dataset.getName());
        // Borrow the Details tab style.
        name.setStyleName("elv-Details-Title-Wrap");
        topBar.add(name);
        Widget doi = new HTMLPanel("DOI: " + dataset.getDoi());
        doi.setStyleName(RESOURCES.getCSS().doi());
        topBar.add(doi);
        // The alignment directive below only applies to widgets added
        // after the property is set.
        topBar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        Button compare = new Button("Compare...");
        topBar.add(compare);
        topBar.setCellVerticalAlignment(name, HasVerticalAlignment.ALIGN_MIDDLE);
        return topBar;
    }

    private Widget getMainContent(DataSet dataset) {
        this.sections = DataSetSectionFactory.build(dataset);
        VerticalPanel vp = new VerticalPanel();
        for (Widget section: this.sections.values()) {
            vp.add(section);
        }
        return vp;
    }

    public Widget getSection(String title) {
        return this.sections.get(title);
    }

    public static Resources RESOURCES;

    static {
        RESOURCES = GWT.create(Resources.class);
        RESOURCES.getCSS().ensureInjected();
    }
 
    /**
     * A ClientBundle of resources used by this widget.
     */
    interface Resources extends ClientBundle {
 
        /**
         * The styles used in this widget.
         */
        @Source(Css.CSS)
        Css getCSS();

    }

    /**
     * Styles used by this widget.
     */
    interface Css extends CssResource {
 
        /**
         * The path to the default CSS styles used by this resource.
         */
        String CSS = "DataSetPanel.css";

        String main();

        String doi();

        String overview();

    }}
