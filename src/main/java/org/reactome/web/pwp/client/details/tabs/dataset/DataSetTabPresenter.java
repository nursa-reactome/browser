package org.reactome.web.pwp.client.details.tabs.dataset;

import java.io.IOException;
//import org.apache.commons.collections4.IteratorUtils;
//import org.apache.commons.collections4.Transformer;
//import org.apache.commons.collections4.iterators.TransformIterator;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.reactome.web.pwp.client.common.events.DataSetLoadedEvent;
import org.reactome.web.pwp.client.common.events.DataSetSelectedEvent;
import org.reactome.web.pwp.client.common.events.StateChangedEvent;
import org.reactome.web.pwp.client.common.handlers.DataSetLoadedHandler;
import org.reactome.web.pwp.client.common.handlers.DataSetSelectedHandler;
import org.reactome.nursa.model.DataSet;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetTabPresenter
implements DataSetTab.Presenter, DataSetSelectedHandler, DataSetLoadedHandler {

    private DataSetTab.Display display;
    private EventBus eventBus;

    // Initialize the resty context root. This ensures that requests
    // go to /NursaContent/... rather than /Browser/NursaContent/...
    static {
        Defaults.setServiceRoot("/");
    }
    
    public DataSetTabPresenter(EventBus eventBus, DataSetTab.Display display) {
        this.eventBus = eventBus;
        this.display = display;
        this.display.setPresenter(this);
        eventBus.addHandler(DataSetSelectedEvent.TYPE, this);
        eventBus.addHandler(DataSetLoadedEvent.TYPE, this);
    }

    @Override
    public void onStateChanged(StateChangedEvent event) {
        // The DataSet tab is insensitive to the object selected in the viewport.
    }

    @Override
    public void onDataSetSelected(DataSetSelectedEvent event) {
        String doi = event.getDoi();
        this.display.showLoading(doi);
        getDataset(doi);
    }

    @Override
    public void onDataSetLoaded(DataSetLoadedEvent event) {
        DataSet dataset = event.getDataSet();
        this.display.showDetails(dataset);
    }

    private void getDataset(String doi) {
        NursaClient client = GWT.create(NursaClient.class);
        final EventBus eventBus = this.eventBus;
        client.getDataset(doi, new MethodCallback<DataSet>() {
            
            @Override
            public void onSuccess(Method method, DataSet dataset) {
                DataSetLoadedEvent event = new DataSetLoadedEvent(dataset);
                eventBus.fireEventFromSource(event, this);
            }
            
            @Override
            public void onFailure(Method method, Throwable exception) {
                try {
                    throw new IOException("Dataset " + doi + " was not retrieved", exception);
                } catch (IOException e) {
                    // TODO - how are I/O errors handled in Reactome?
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
