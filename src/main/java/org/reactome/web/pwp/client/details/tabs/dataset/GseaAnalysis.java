package org.reactome.web.pwp.client.details.tabs.dataset;

import java.util.List;

import org.reactome.gsea.model.AnalysisResult;

public interface GseaAnalysis {

    public interface Presenter extends DataSetAnalysis.Presenter, GseaCompletedHandler {
    }

    interface Display extends DataSetAnalysis.Display {
        void showResult(List<AnalysisResult> result);
    }

}
