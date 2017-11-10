package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.event.shared.EventHandler;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public interface GseaCompletedHandler extends EventHandler {

    void onCompleted(GseaCompletedEvent gseaCompletedEvent);

}
