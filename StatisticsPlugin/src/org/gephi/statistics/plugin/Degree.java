/*
Copyright 2008-2011 Gephi
Authors : Patick J. McSweeney <pjmcswee@syr.edu>, Sebastien Heymann <seb@gephi.org>
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.statistics.plugin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import org.gephi.data.attributes.api.AttributeTable;
import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeOrigin;
import org.gephi.data.attributes.api.AttributeRow;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.HierarchicalDirectedGraph;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.statistics.spi.Statistics;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Degree implements Statistics, LongTask {

    public static final String INDEGREE = "indegree";
    public static final String OUTDEGREE = "outdegree";
    public static final String DEGREE = "degree";
    private boolean isDirected; // only set inside this class
    /** Remembers if the Cancel function has been called. */
    private boolean isCanceled;
    /** Keep track of the work done. */
    private ProgressTicket progress;
    /**     */
    private double avgDegree;
    private Map<Integer, Integer> inDegreeDist;
    private Map<Integer, Integer> outDegreeDist;
    private Map<Integer, Integer> degreeDist;

    /**
     *
     * @return
     */
    public double getAverageDegree() {
        return avgDegree;
    }

    /**
     *
     * @param graphModel
     */
    public void execute(GraphModel graphModel, AttributeModel attributeModel) {
        HierarchicalGraph graph = graphModel.getHierarchicalGraphVisible();
        execute(graph, attributeModel);
    }

    public void execute(HierarchicalGraph graph, AttributeModel attributeModel) {
        isDirected = graph instanceof DirectedGraph;
        isCanceled = false;
        inDegreeDist = new HashMap<Integer, Integer>();
        outDegreeDist = new HashMap<Integer, Integer>();
        degreeDist = new HashMap<Integer, Integer>();

        //Attributes cols
        AttributeTable nodeTable = attributeModel.getNodeTable();
        AttributeColumn inCol = nodeTable.getColumn(INDEGREE);
        AttributeColumn outCol = nodeTable.getColumn(OUTDEGREE);
        AttributeColumn degCol = nodeTable.getColumn(DEGREE);
        if (isDirected) {
            if (inCol == null) {
                inCol = nodeTable.addColumn(INDEGREE, "In-Degree", AttributeType.INT, AttributeOrigin.COMPUTED, 0);
            }
            if (outCol == null) {
                outCol = nodeTable.addColumn(OUTDEGREE, "Out-Degree", AttributeType.INT, AttributeOrigin.COMPUTED, 0);
            }
        }
        if (degCol == null) {
            degCol = nodeTable.addColumn(DEGREE, "Degree", AttributeType.INT, AttributeOrigin.COMPUTED, 0);
        }

        int i = 0;

        graph.readLock();

        Progress.start(progress, graph.getNodeCount());
        
        HierarchicalDirectedGraph directedGraph = null;
        if(isDirected) {
            directedGraph = graph.getGraphModel().getHierarchicalDirectedGraphVisible();
        }

        for (Node n : graph.getNodes()) {
            AttributeRow row = (AttributeRow) n.getNodeData().getAttributes();
            if (isDirected) {
                int inDegree = directedGraph.getTotalInDegree(n);
                int outDegree = directedGraph.getTotalOutDegree(n);
                row.setValue(inCol, inDegree);
                row.setValue(outCol, outDegree);
                if (!inDegreeDist.containsKey(inDegree)) {
                    inDegreeDist.put(inDegree, 0);
                }
                inDegreeDist.put(inDegree, inDegreeDist.get(inDegree) + 1);
                if (!outDegreeDist.containsKey(outDegree)) {
                    outDegreeDist.put(outDegree, 0);
                }
                outDegreeDist.put(outDegree, outDegreeDist.get(outDegree) + 1);
            }
            int degree = graph.getTotalDegree(n);
            row.setValue(degCol, degree);
            avgDegree += degree;
            if (!degreeDist.containsKey(degree)) {
                degreeDist.put(degree, 0);
            }
            degreeDist.put(degree, degreeDist.get(degree) + 1);

            if (isCanceled) {
                break;
            }
            i++;
            Progress.progress(progress, i);
        }

        avgDegree /= (isDirected) ? 2 * graph.getNodeCount() : graph.getNodeCount();

        graph.readUnlockAll();
    }

    /**
     *
     * @return
     */
    public String getReport() {
        String report = "";
        if (isDirected) {
            report = getDirectedReport();
        } else {
            //Distribution series
            XYSeries dSeries = ChartUtils.createXYSeries(degreeDist, "Degree Distribution");

            XYSeriesCollection dataset1 = new XYSeriesCollection();
            dataset1.addSeries(dSeries);

            JFreeChart chart1 = ChartFactory.createXYLineChart(
                    "Degree Distribution",
                    "Value",
                    "Count",
                    dataset1,
                    PlotOrientation.VERTICAL,
                    true,
                    false,
                    false);
            chart1.removeLegend();
            ChartUtils.decorateChart(chart1);
            ChartUtils.scaleChart(chart1, dSeries, false);
            String degreeImageFile = ChartUtils.renderChart(chart1, "degree-distribution.png");

            NumberFormat f = new DecimalFormat("#0.000");

            report = "<HTML> <BODY> <h1>Degree Report </h1> "
                    + "<hr>"
                    + "<br> <h2> Results: </h2>"
                    + "Average Degree: " + f.format(avgDegree)
                    + "<br /><br />"+degreeImageFile
                    + "</BODY></HTML>";
        }
        return report;
    }

    public String getDirectedReport() {
        //Distribution series
        XYSeries dSeries = ChartUtils.createXYSeries(degreeDist, "Degree Distribution");
        XYSeries idSeries = ChartUtils.createXYSeries(inDegreeDist, "In-Degree Distribution");
        XYSeries odSeries = ChartUtils.createXYSeries(outDegreeDist, "Out-Degree Distribution");

        XYSeriesCollection dataset1 = new XYSeriesCollection();
        dataset1.addSeries(dSeries);

        XYSeriesCollection dataset2 = new XYSeriesCollection();
        dataset2.addSeries(idSeries);

        XYSeriesCollection dataset3 = new XYSeriesCollection();
        dataset3.addSeries(odSeries);

        JFreeChart chart1 = ChartFactory.createXYLineChart(
                "Degree Distribution",
                "Value",
                "Count",
                dataset1,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        chart1.removeLegend();
        ChartUtils.decorateChart(chart1);
        ChartUtils.scaleChart(chart1, dSeries, false);
        String degreeImageFile = ChartUtils.renderChart(chart1, "degree-distribution.png");

        JFreeChart chart2 = ChartFactory.createXYLineChart(
                "In-Degree Distribution",
                "Value",
                "Count",
                dataset2,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        chart2.removeLegend();
        ChartUtils.decorateChart(chart2);
        ChartUtils.scaleChart(chart2, dSeries, false);
        String indegreeImageFile = ChartUtils.renderChart(chart2, "indegree-distribution.png");

        JFreeChart chart3 = ChartFactory.createXYLineChart(
                "Out-Degree Distribution",
                "Value",
                "Count",
                dataset3,
                PlotOrientation.VERTICAL,
                true,
                false,
                false);
        chart3.removeLegend();
        ChartUtils.decorateChart(chart3);
        ChartUtils.scaleChart(chart3, dSeries, false);
        String outdegreeImageFile = ChartUtils.renderChart(chart3, "outdegree-distribution.png");
        
        NumberFormat f = new DecimalFormat("#0.000");

        String report = "<HTML> <BODY> <h1>Degree Report </h1> "
                + "<hr>"
                + "<br> <h2> Results: </h2>"
                + "Average Degree: " + f.format(avgDegree)
                + "<br /><br />"+degreeImageFile
                + "<br /><br />"+indegreeImageFile
                + "<br /><br />"+outdegreeImageFile
                + "</BODY></HTML>";
        
        return report;
    }

    /**
     *
     * @return
     */
    public boolean cancel() {
        this.isCanceled = true;
        return true;
    }

    /**
     *
     * @param progressTicket
     */
    public void setProgressTicket(ProgressTicket progressTicket) {
        this.progress = progressTicket;
    }
}
