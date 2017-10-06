package org.reactome.web.pwp.client.toppanel.dataset;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DatasetSelector {

    interface Presenter extends EventHandler {
        void search();
    }

    interface Display extends IsWidget {
        void setPresenter(Presenter presenter);
    }
}
