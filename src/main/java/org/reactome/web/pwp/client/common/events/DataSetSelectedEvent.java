package org.reactome.web.pwp.client.common.events;

import org.reactome.web.pwp.client.common.handlers.DataSetSelectedHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DataSetSelectedEvent extends GwtEvent<DataSetSelectedHandler> {
    public static Type<DataSetSelectedHandler> TYPE = new Type<>();
    
    private String doi;

    public DataSetSelectedEvent(String doi) {
        this.doi = doi;
    }

    public String getDOI() {
        return doi;
    }

    @Override
    public Type<DataSetSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    public void dispatch(DataSetSelectedHandler handler) {
        handler.onDataSetSelected(this);
    }
}
