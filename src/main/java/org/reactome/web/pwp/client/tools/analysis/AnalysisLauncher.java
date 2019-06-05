package org.reactome.web.pwp.client.tools.analysis;

import org.reactome.web.pwp.client.common.events.AnalysisCompletedEvent;
import org.reactome.web.pwp.client.common.module.BrowserModule;
import org.reactome.web.pwp.client.tools.analysis.tissues.client.model.ExperimentSummary;
import org.reactome.web.pwp.model.client.classes.Species;

import java.util.List;

/**
 * @author Antonio Fabregat <fabregat@ebi.ac.uk>
 */
public interface AnalysisLauncher {

    enum Status {
        ACTIVE,
        WARNING,
        ERROR
    }

    interface Presenter extends BrowserModule.Presenter {
        void displayClosed();
        void analysisCompleted(AnalysisCompletedEvent event);
    }

    interface Display extends BrowserModule.Display {
        void hide();
        void center();
        void show();
        void setPresenter(Presenter presenter);
        void setSpeciesList(List<Species> speciesList);
        void setExperimentSummaries(List<ExperimentSummary> summaries);
        void setVersionInfo(String version);
        void setStatus(Status status);
    }
}
