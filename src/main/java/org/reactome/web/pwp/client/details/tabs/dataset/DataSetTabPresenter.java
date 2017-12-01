package org.reactome.web.pwp.client.details.tabs.dataset;

import java.io.IOException;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.reactome.web.pwp.client.common.events.DataSetSelectedEvent;
import org.reactome.web.pwp.client.common.events.StateChangedEvent;
import org.reactome.web.pwp.client.common.handlers.DataSetSelectedHandler;
import org.reactome.nursa.model.DataSet;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetTabPresenter implements DataSetTab.Presenter, DataSetSelectedHandler {

    private DataSetTab.Display display;

    // Initialize the resty context root. This ensures that requests
    // go to /NursaContent/... rather than /Browser/NursaContent/...
    //
    // Note: if additional widgets use a resty helper, then it would
    // be cleaner to move this initializer to a global init class.
    static {
        Defaults.setServiceRoot("/");
    }
    
    public DataSetTabPresenter(EventBus eventBus, DataSetTab.Display display) {
        this.display = display;
        this.display.setPresenter(this);
        eventBus.addHandler(DataSetSelectedEvent.TYPE, this);
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

    private void getDataset(String doi) {
        NursaClient client = GWT.create(NursaClient.class);
        client.getDataset(doi, new MethodCallback<DataSet>() {
            
            @Override
            public void onSuccess(Method method, DataSet dataset) {
                display.showDetails(dataset);
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
