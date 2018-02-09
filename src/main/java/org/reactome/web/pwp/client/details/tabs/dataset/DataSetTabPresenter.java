package org.reactome.web.pwp.client.details.tabs.dataset;

import java.io.IOException;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.reactome.web.pwp.client.common.events.StateChangedEvent;
import org.reactome.web.pwp.client.common.module.AbstractPresenter;
import org.reactome.web.pwp.client.manager.state.State;
import org.reactome.web.pwp.client.tools.dataset.NursaClient;
import org.reactome.nursa.model.DataSet;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetTabPresenter extends AbstractPresenter implements DataSetTab.Presenter {

    private DataSetTab.Display display;
    private DataSet dataset;
    
    public DataSetTabPresenter(EventBus eventBus, DataSetTab.Display display) {
        super(eventBus);
        this.display = display;
        this.display.setPresenter(this);
    }

    @Override
    public void onStateChanged(StateChangedEvent event) {
        State state = event.getState();
        if (!state.getDetailsTab().equals(display.getDetailTabType())) {
            return;
        }
        if (state.getDataSet() == null) {
            // The dataset tab was clicked.
            return;
        }
        // A dataset was selected by the search dialog.
        // Note: we do not check whether the selected dataset
        // DOI is the same as the current dataset DOI. A loaded
        // dataset could have been reselected. In that case,
        // reload the dataset anyway since that is presumably
        // the user's intent.
        this.dataset = state.getDataSet();
        loadDataset(this.dataset.getDoi());
    }

    public void loadDataset(String doi) {
        this.display.showLoading(doi);
        getDataset(doi);
    }

    private void getDataset(String doi) {
        NursaClient client = GWT.create(NursaClient.class);
        client.getDataset(doi, new MethodCallback<DataSet>() {
            
            @Override
            public void onSuccess(Method method, DataSet dataset) {
                // Guard against a loaded dataset which differs from
                // the loading dataset. This could occur in the rare
                // case of a second dataset being selected in the
                // search dialog while the first dataset is being
                // loaded. In that case, ignore the loaded dataset,
                // throwing away the result and continuing to wait
                // for the second selected dataset load to complete.
                if (DataSetTabPresenter.this.dataset.getDoi() == dataset.getDoi()) {
                    DataSetTabPresenter.this.dataset = dataset;
                    display.showDetails(dataset);
                }
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
