package org.reactome.web.pwp.client.details.tabs.dataset;

import org.reactome.web.pwp.client.details.tabs.DetailsTab;

import com.google.gwt.event.shared.EventBus;

import org.reactome.nursa.model.DataSet;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DataSetTab {

    interface Presenter extends DetailsTab.Presenter {
    }

    interface Display extends DetailsTab.Display<Presenter> {
        void showLoading(String doi);
        void showDetails(DataSet dataset);
    }
}
