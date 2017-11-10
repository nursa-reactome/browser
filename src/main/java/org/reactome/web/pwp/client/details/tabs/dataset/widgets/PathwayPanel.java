package org.reactome.web.pwp.client.details.tabs.dataset.widgets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.reactome.gsea.model.AnalysisResult;
import org.reactome.nursa.model.DataPoint;
import org.reactome.nursa.model.DataSet;
import org.reactome.web.analysis.client.AnalysisClient;
import org.reactome.web.analysis.client.AnalysisHandler;
import org.reactome.web.analysis.client.model.AnalysisError;
import org.reactome.web.pwp.client.details.tabs.analysis.widgets.results.AnalysisResultTable;
import org.reactome.web.pwp.client.details.tabs.dataset.GseaClient;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PathwayPanel extends VerticalPanel {

    final String GENE_NAMES_HEADER = "#Gene names";

    private DataSet dataset;
    private SimplePanel analysisPanel;

    public PathwayPanel(DataSet dataset) {
        this.dataset = dataset;
        HorizontalPanel btnPanel = new HorizontalPanel();
        Button gseaButton = new Button("GSEA Enrichment");
        gseaButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                gseaAnalyse();
            }
        });
        btnPanel.add(gseaButton);
        Button reactomeButton = new Button("Reactome Enrichment");
        reactomeButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reactomeAnalyse();
            }
        });
        btnPanel.add(reactomeButton);
        add(btnPanel);
        // The analysis display panel.
        analysisPanel = new SimplePanel();
        add(analysisPanel);
    }

    protected void gseaAnalyse() {
        GseaClient client = GWT.create(GseaClient.class);
        // Transform the data points into the GSEA REST PUT
        // payload using the Java8 list comprehension idiom.
        List<List<String>> rankedList =
                this.dataset.getDataPoints()
                    .stream()
                    .map(PathwayPanel::pullRank)
                    .collect(Collectors.toList());
        // Call the GSEA REST service.
        client.analyse(rankedList, new MethodCallback<List<AnalysisResult>>() {
        
            @Override
            public void onSuccess(Method method, List<AnalysisResult> result) {
                 showGseaResult(result);
             }
             
             @Override
             public void onFailure(Method method, Throwable exception) {
                 try {
                     throw new IOException("GSEA execution unsuccessful", exception);
                 } catch (IOException e) {
                     // TODO - how are I/O errors handled in Reactome?
                     throw new RuntimeException(e);
                 }
             }
         });
    }

    protected void showGseaResult(List<AnalysisResult> result) {
        Widget table = GseaResultTableFactory.getTable(result);
        analysisPanel.setWidget(table);
    }

    protected void reactomeAnalyse() {
        // The input is a table of gene symbol lines.
        List<String> rankedList =
                this.dataset.getDataPoints()
                    .stream()
                    .map(dp -> dp.getSymbol())
                    .collect(Collectors.toList());
        rankedList.add(0, GENE_NAMES_HEADER);
        String data = String.join("\n", rankedList);
        AnalysisClient.analyseData(data, true, false, 0, 0, new AnalysisHandler.Result() {
            @Override
            public void onAnalysisServerException(String s) {
                // TODO
            }

            @Override
            public void onAnalysisResult(org.reactome.web.analysis.client.model.AnalysisResult result, long time) {
                showReactomeResult(result);
            }

            @Override
            public void onAnalysisError(AnalysisError analysisError) {
                // TODO
            }

        });
    }

    protected void showReactomeResult(org.reactome.web.analysis.client.model.AnalysisResult result) {
        Widget table = ReactomeResultTableFactory.getTable(result);
        analysisPanel.setWidget(table);
    }
    
    /**
     * Pulls the gene symbol and FC from the given data point.
     * The FC is formatted as a string for the REST interface.
     * 
     * @param dataPoint the input data point.
     * @return the [symbol, FC] list.
     */
    private static List<String> pullRank(DataPoint dataPoint) {
        return Arrays.asList(
                dataPoint.getSymbol(),
                Double.toString(dataPoint.getFoldChange())
         );
    }
}
