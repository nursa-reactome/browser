package org.reactome.web.pwp.client.details.tabs.analysis.widgets.filtering.handlers;

import com.google.gwt.event.shared.EventHandler;
import org.reactome.web.pwp.client.details.tabs.analysis.widgets.filtering.events.RangeValueChangedEvent;

/**
 * @author Kostas Sidiropoulos <ksidiro@ebi.ac.uk>
 */
public interface RangeValueChangedHandler extends EventHandler {

    void onRangeValueChanged(RangeValueChangedEvent event);

}
