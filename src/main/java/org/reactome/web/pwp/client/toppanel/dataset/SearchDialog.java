package org.reactome.web.pwp.client.toppanel.dataset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import java.util.function.Consumer;
import org.reactome.web.diagram.common.PwpButton;
import org.reactome.web.pwp.client.common.events.DataSetSelectedEvent;
import org.reactome.web.widgets.search.Searcher;
import org.reactome.web.widgets.search.SuggestionComboBox;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class SearchDialog extends DialogBox implements ClickHandler {

    private static final int TOP_PANEL_HEIGHT = 40;

    public SearchDialog(final EventBus eventBus) {
        setAutoHideEnabled(false);
        setModal(false);
        setAnimationEnabled(true);
        setGlassEnabled(false);
        setAutoHideOnHistoryEventsEnabled(false);
        setStyleName(RESOURCES.getCSS().main());
        setTitlePanel("DataSet Search");

        // Unfortunately, the dialog position must be set programatically.
        // GWT sets the element position to top left in the DOM, so a CSS
        // position is ignored. We want the dialog in the top left just
        // below the top panel, so set the offset width to 2/3 of the
        // display and the offset height to 40 pixels, which is roughly
        // the height of the top panel. Kind of a kludge, but the alternative
        // requires overriding the dialog box position using the top panel
        // offsets. This probably would need to be done by the app controller,
        // which is even more of a kludge.
        setPopupPositionAndShow(new PositionCallback(){

            @Override
            public void setPosition(int offsetWidth, int offsetHeight) {
                // The old values are ignored. Place the pop-up in the
                // right third of the display area just below the top panel.
                offsetWidth = 2 * Window.getClientWidth() / 3;
                setPopupPosition(offsetWidth, TOP_PANEL_HEIGHT);
            }

        });      
        
        Searcher searcher = new DataSetSearcher();
        Consumer<String> consumer = new Consumer<String>() {

            @Override
            public void accept(String doi) {
                if (doi != null) {
                    eventBus.fireEventFromSource(new DataSetSelectedEvent(doi), this);
                }
            }
  
        };
        SuggestionComboBox<String> comboBox = new SuggestionComboBox<String>(searcher, consumer);
        Widget combo = comboBox.asWidget();
        combo.addStyleName(RESOURCES.getCSS().combo());

        FlowPanel container = new FlowPanel();
        PwpButton close = new PwpButton("Close", RESOURCES.getCSS().close(), this);
        container.add(close);
        container.add(combo);
        setWidget(container);
    }

    @Override
    public void onClick(ClickEvent clickEvent) {
        hide();
    }

    private void setTitlePanel(String title) {
        Label label = new Label(title);
        label.setStyleName(RESOURCES.getCSS().headerText());
        SafeHtml safeHtml = SafeHtmlUtils.fromSafeConstant(label.toString());
        getCaption().setHTML(safeHtml);
        getCaption().asWidget().setStyleName(RESOURCES.getCSS().header());
    }

    public static Resources RESOURCES;
    static {
        RESOURCES = GWT.create(Resources.class);
        RESOURCES.getCSS().ensureInjected();
    }

    public interface Resources extends ClientBundle {
        @Source(Css.CSS)
        Css getCSS();

        @Source("images/close_normal.png")
        ImageResource closeNormal();

        @Source("images/close_hovered.png")
        ImageResource closeHovered();

        @Source("images/close_clicked.png")
        ImageResource closeClicked();
    }

    public interface Css extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String CSS = "SearchDialog.gss";

        String main();

        String header();

        String headerText();

        String combo();

        String close();
    }
}
