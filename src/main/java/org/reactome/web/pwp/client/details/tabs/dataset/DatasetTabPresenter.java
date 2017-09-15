package org.reactome.web.pwp.client.details.tabs.dataset;

import com.google.gwt.event.shared.EventBus;
import org.reactome.web.pwp.client.common.events.ErrorMessageEvent;
import org.reactome.web.pwp.client.common.events.StateChangedEvent;
import org.reactome.web.pwp.client.common.module.AbstractPresenter;
import org.reactome.web.pwp.client.manager.state.State;
import org.reactome.web.pwp.model.client.classes.DatabaseObject;
import org.reactome.web.pwp.model.client.classes.ReferenceEntity;
import org.reactome.web.pwp.model.client.classes.ReferenceSequence;
import org.reactome.web.pwp.model.client.common.ContentClientHandler;
import org.reactome.web.pwp.model.client.content.ContentClient;
import org.reactome.web.pwp.model.client.content.ContentClientError;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Fred Loney <loneyf@ohsu.edu>
 */
public class DatasetTabPresenter extends AbstractPresenter implements DatasetTab.Presenter {

    private DatasetTab.Display display;
    private DatabaseObject currentlyShown;

    public DatasetTabPresenter(EventBus eventBus, DatasetTab.Display display) {
        super(eventBus);
        this.display = display;
        this.display.setPresenter(this);
    }

    @Override
    public void onStateChanged(StateChangedEvent event) {
        State state = event.getState();
        DatabaseObject target = state.getTarget();

        //Is it me the one to show data?
        if (!state.getDetailsTab().equals(display.getDetailTabType())){
            display.updateTitle(target);
            return;
        }

        //Show the data
        if(target==null){
            this.currentlyShown = null;
            display.setInitialState();
        }else if(!target.equals(this.currentlyShown)){
            this.currentlyShown = target;
            display.showDetails(target);
        }
    }

}
