package org.reactome.web.pwp.client.toppanel.dataset;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import org.reactome.web.diagram.common.PwpButton;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class SearchDialog extends DialogBox implements ClickHandler {

    public SearchDialog() {
        setAutoHideEnabled(true);
        setModal(true);
        setAnimationEnabled(true);
        setGlassEnabled(true);
        setAutoHideOnHistoryEventsEnabled(false);
        this.setStyleName(RESOURCES.getCSS().popupPanel());
        setTitlePanel("Search DataSet");

        int width = Window.getClientWidth() * 2 / 3;
        int height = Window.getClientHeight() * 2 / 3;
        String w, h;
        if (width > height) {
            w = width + "px";
            h = width * 0.5625 + "px";
        } else {
            w = height * 1.7778 + "px";
            h = height + "px";
        }
        String bogus = "<span>TODO - Insert combo box here</span>";
        HTMLPanel combo = new HTMLPanel(SafeHtmlUtils.fromTrustedString(bogus));
        combo.setStyleName(RESOURCES.getCSS().dialog());

        FlowPanel container = new FlowPanel();
        container.add(new PwpButton("Close", RESOURCES.getCSS().close(), this));
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
        @Source(ResourceCSS.CSS)
        ResourceCSS getCSS();

        @Source("images/close_normal.png")
        ImageResource closeNormal();

        @Source("images/close_hovered.png")
        ImageResource closeHovered();

        @Source("images/close_clicked.png")
        ImageResource closeClicked();
    }

    @CssResource.ImportedWithPrefix("diagram-DataSetContainer")
    public interface ResourceCSS extends CssResource {
        /**
         * The path to the default CSS styles used by this resource.
         */
        String CSS = "org/reactome/web/pwp/client/toppanel/dataset/SearchDialog.gss";

        String popupPanel();

        String header();

        String headerText();

        String dialog();

        String close();
    }
}
