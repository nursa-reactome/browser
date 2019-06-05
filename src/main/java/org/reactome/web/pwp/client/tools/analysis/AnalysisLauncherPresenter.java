package org.reactome.web.pwp.client.tools.analysis;

import com.google.gwt.event.shared.EventBus;
import org.reactome.web.analysis.client.AnalysisClient;
import org.reactome.web.analysis.client.AnalysisHandler;
import org.reactome.web.analysis.client.model.AnalysisError;
import org.reactome.web.pwp.client.common.PathwayPortalTool;
import org.reactome.web.pwp.client.common.events.*;
import org.reactome.web.pwp.client.common.handlers.BrowserReadyHandler;
import org.reactome.web.pwp.client.common.module.AbstractPresenter;
import org.reactome.web.pwp.client.tools.analysis.tissues.client.ExperimentSummariesClient;
import org.reactome.web.pwp.client.tools.analysis.tissues.client.model.ExperimentError;
import org.reactome.web.pwp.client.tools.analysis.tissues.client.model.ExperimentSummary;
import org.reactome.web.pwp.model.client.classes.DBInfo;
import org.reactome.web.pwp.model.client.classes.Species;
import org.reactome.web.pwp.model.client.common.ContentClientHandler;
import org.reactome.web.pwp.model.client.content.ContentClient;
import org.reactome.web.pwp.model.client.content.ContentClientError;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.reactome.web.pwp.client.tools.analysis.AnalysisLauncher.Status.*;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public class AnalysisLauncherPresenter extends AbstractPresenter implements AnalysisLauncher.Presenter, BrowserReadyHandler {

    private AnalysisLauncher.Display display;

    private DBInfo dbInfo;
    private org.reactome.web.analysis.client.model.DBInfo analysisInfo;

    private boolean summariesRetrieved;

    public AnalysisLauncherPresenter(EventBus eventBus, AnalysisLauncher.Display display) {
        super(eventBus);
        this.display = display;
        this.display.setPresenter(this);
        this.display.setVersionInfo("");

        this.eventBus.addHandler(BrowserReadyEvent.TYPE, this);
    }

    @Override
    public void displayClosed() {
        this.eventBus.fireEventFromSource(new ToolSelectedEvent(PathwayPortalTool.NONE), this);
    }

    @Override
    public void analysisCompleted(AnalysisCompletedEvent event) {
        this.display.hide();
        this.eventBus.fireEventFromSource(event, this);
    }

    @Override
    public void onBrowserReady(BrowserReadyEvent event) {
        this.dbInfo = event.getDbInfo();
        retrieveSpeciesList();
    }

    @Override
    public void onStateChanged(StateChangedEvent event) {
        PathwayPortalTool tool = event.getState().getTool();
        if (tool.equals(PathwayPortalTool.ANALYSIS)) {
            if (!summariesRetrieved)        retrieveExperimentSummaries();
            if (analysisInfo == null)       retrieveAnalysisInfo();
            display.show();
            display.center();
        } else {
            display.hide();
        }
    }

    private void retrieveSpeciesList() {
        ContentClient.getSpeciesList(new ContentClientHandler.ObjectListLoaded<Species>() {
            @Override
            public void onObjectListLoaded(List<Species> list) {
                display.setSpeciesList(list);
            }

            @Override
            public void onContentClientException(Type type, String message) {
                display.setSpeciesList(new LinkedList<>());
                eventBus.fireEventFromSource(new ErrorMessageEvent(message), this);
            }

            @Override
            public void onContentClientError(ContentClientError error) {
                display.setSpeciesList(new LinkedList<>());
                //TODO
                eventBus.fireEventFromSource(new ErrorMessageEvent(error.getMessage().get(0)), this);
            }
        });
    }

    private void retrieveExperimentSummaries() {
        ExperimentSummariesClient.getSummaries(new ExperimentSummariesClient.Handler() {
            @Override
            public void onSummariesSuccess(List<ExperimentSummary> summaries) {
                display.setExperimentSummaries(summaries);
                summariesRetrieved = true;
            }

            @Override
            public void onSummariesError(ExperimentError error) {
                display.setExperimentSummaries(new LinkedList<>());
            }

            @Override
            public void onSummariesException(String msg) {
                display.setExperimentSummaries(new LinkedList<>());
            }
        });
    }

    private void retrieveAnalysisInfo() {
        AnalysisClient.getDatabaseInformation(new AnalysisHandler.DatabaseInformation() {
            @Override
            public void onDBInfoLoaded(org.reactome.web.analysis.client.model.DBInfo dbInfo) {
                analysisInfo = dbInfo;
                display.setVersionInfo("Reactome v" + dbInfo.getVersion());
                if (!Objects.equals(AnalysisLauncherPresenter.this.dbInfo.getChecksum(), dbInfo.getChecksum())){
                    display.setStatus(WARNING);
                } else {
                    display.setStatus(ACTIVE);
                }
            }

            @Override
            public void onDBInfoError(AnalysisError error) {
                display.setStatus(ERROR);
            }

            @Override
            public void onAnalysisServerException(String message) {
                display.setStatus(ERROR);
            }
        });
    }
}
