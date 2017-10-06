package org.reactome.web.pwp.client.common.handlers;

import org.reactome.web.pwp.client.common.events.DataSetLoadedEvent;
import com.google.gwt.event.shared.EventHandler;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface DataSetLoadedHandler extends EventHandler {

    void onDataSetLoaded(DataSetLoadedEvent datasetLoadedEvent);

}
