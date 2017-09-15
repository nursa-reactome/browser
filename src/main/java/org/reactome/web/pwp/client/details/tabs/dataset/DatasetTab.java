package org.reactome.web.pwp.client.details.tabs.dataset;

import org.reactome.web.pwp.client.details.tabs.DetailsTab;
import org.reactome.web.pwp.model.client.classes.DatabaseObject;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DatasetTab {

    interface Presenter extends DetailsTab.Presenter {
    }

    interface Display extends DetailsTab.Display<Presenter> {
        void showDetails(DatabaseObject databaseObject);
        void updateTitle(DatabaseObject databaseObject);
    }
}
