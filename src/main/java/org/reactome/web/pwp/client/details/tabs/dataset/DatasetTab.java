package org.reactome.web.pwp.client.details.tabs.dataset;

import org.reactome.web.pwp.client.details.tabs.DetailsTab;
import org.reactome.web.pwp.nursa.model.client.classes.Dataset;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DatasetTab {

    interface Presenter extends DetailsTab.Presenter {
    }

    interface Display extends DetailsTab.Display<Presenter> {
        void showLoading(String datasetId);
        void showDetails(Dataset dataset);
    }
}
