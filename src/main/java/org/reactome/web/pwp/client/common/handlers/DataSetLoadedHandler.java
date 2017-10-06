package org.reactome.web.pwp.client.common.handlers;

import org.reactome.web.pwp.client.common.events.DatasetLoadedEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DatasetLoadedHandler extends EventHandler {

    void onDatasetLoaded(DatasetLoadedEvent datasetLoadedEvent);

}
