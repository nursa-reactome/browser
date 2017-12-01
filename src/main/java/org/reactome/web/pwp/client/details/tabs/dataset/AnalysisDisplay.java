package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

import org.reactome.web.pwp.client.common.CommonImages;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public abstract class AnalysisDisplay extends ResizeComposite implements DataSetAnalysis.Display {

    @SuppressWarnings("unused")
    private DataSetAnalysis.Presenter presenter;
    private Panel content;

    public AnalysisDisplay(Panel content) {
        this.content = content;
        setInitialState();
    }

    @Override
    public void setPresenter(DataSetAnalysis.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setInitialState() {
        this.content.clear();
    }

    @Override
    public void showInProgressMessage() {
        HorizontalPanel message = new HorizontalPanel();
        Image loader = new Image(CommonImages.INSTANCE.loader());
        message.add(loader);

        Label label = new Label("Performing the analysis. Please wait...");
        label.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
        message.add(label);

        this.content.clear();
        this.content.add(message);
    }

    @Override
    public void showErrorMessage(String message) {
        HorizontalPanel panel = new HorizontalPanel();
        Image loader = new Image(CommonImages.INSTANCE.exclamation());
        panel.add(loader);

        Label label = new Label(message);
        label.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
        panel.add(label);

        this.content.clear();
        this.content.add(panel);
    }
}
