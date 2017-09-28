package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

import org.reactome.web.pwp.client.common.CommonImages;
import org.reactome.web.pwp.client.details.tabs.DetailsTabTitle;
import org.reactome.web.pwp.client.details.tabs.DetailsTabType;
import org.reactome.web.pwp.client.details.tabs.dataset.widgets.DatasetPanel;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetTabDisplay extends ResizeComposite implements DatasetTab.Display {

    @SuppressWarnings("unused")
    private DatasetTab.Presenter presenter;

    private DockLayoutPanel container;
    private DetailsTabTitle title;
    private DatasetPanel content;

    public DatasetTabDisplay() {
        this.title = getDetailTabType().getTitle();
        this.container = new DockLayoutPanel(Style.Unit.EM);
        initWidget(this.container);
        setInitialState();
    }

    @Override
    public DetailsTabType getDetailTabType() {
        return DetailsTabType.DATASET;
    }

    @Override
    public Widget getTitleContainer() {
        return this.title;
    }

    @Override
    public void setPresenter(DatasetTab.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showDetails(Dataset dataset) {
        this.content = new DatasetPanel(dataset);
        this.container.clear();
        this.container.add(this.content);
    }

    @Override
    public void showLoading(String datasetId) {
        setTitle(datasetId);
        showLoadingMessage();
    }

    @Override
    public void setInitialState() {
        this.container.clear();
        this.container.add(getDetailTabType().getInitialStatePanel());
    }

    @Override
    public void showLoadingMessage() {
        HorizontalPanel message = new HorizontalPanel();
        Image loader = new Image(CommonImages.INSTANCE.loader());
        message.add(loader);

        Label label = new Label("Loading the dataset. Please wait...");
        label.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
        message.add(label);

        this.container.clear();
        this.container.add(message);
    }

    @Override
    public void showErrorMessage(String message) {
        HorizontalPanel panel = new HorizontalPanel();
        Image loader = new Image(CommonImages.INSTANCE.exclamation());
        panel.add(loader);

        Label label = new Label(message);
        label.getElement().getStyle().setMarginLeft(5, Style.Unit.PX);
        panel.add(label);

        this.container.clear();
        this.container.add(panel);
    }

    @Override
    public void setTitle(String datasetId){
        this.title.setTitle(datasetId);
    }
}
