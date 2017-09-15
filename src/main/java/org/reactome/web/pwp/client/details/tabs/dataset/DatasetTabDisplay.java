package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import org.reactome.web.pwp.client.common.CommonImages;
import org.reactome.web.pwp.client.details.tabs.DetailsTabTitle;
import org.reactome.web.pwp.client.details.tabs.DetailsTabType;
import org.reactome.web.pwp.client.details.tabs.dataset.events.DatasetLoadedEvent;
import org.reactome.web.pwp.client.details.tabs.dataset.handlers.DatasetLoadedHandler;
import org.reactome.web.pwp.client.details.tabs.dataset.widgets.*;
import org.reactome.web.pwp.model.client.classes.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetTabDisplay extends ResizeComposite implements DatasetTab.Display, DatasetLoadedHandler {

    @SuppressWarnings("unused")
	private DatasetTab.Presenter presenter;

    private DockLayoutPanel container;
    private DetailsTabTitle title;

    private Map<DatabaseObject, DatasetPanel> cache = new HashMap<>();
    private DatasetPanel currentPanel;

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
    public void onDatasetLoaded(DatasetLoadedEvent datasetLoadedEvent) {
        DatasetPanel panel = (DatasetPanel) datasetLoadedEvent.getSource();
        if(this.currentPanel==panel){
            this.setTitle(panel);
        }
    }

    @Override
    public void showDetails(DatabaseObject databaseObject) {
        if(cache.containsKey(databaseObject)){
            this.currentPanel = cache.get(databaseObject);
            this.container.clear();
            showDatasetPanel(this.currentPanel);
        }else{
            this.currentPanel = new EmptyDatasetPanel();
            this.showDatasetPanel(this.currentPanel);
            this.cache.put(databaseObject, this.currentPanel);
        }
    }

    @Override
    public void updateTitle(DatabaseObject databaseObject) {
        if(this.cache.containsKey(databaseObject)){
            this.setTitle(this.cache.get(databaseObject));
        }else{
            this.title.resetCounter();
        }
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

        Label label = new Label("Loading the data required to show the structure data. Please wait...");
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

    private void showDatasetPanel(DatasetPanel datasetPanel){
        this.container.clear();
        this.container.add(datasetPanel);
        this.setTitle(datasetPanel);
    }

    private void setTitle(DatasetPanel panel){
        // TODO - implement
    }
}
