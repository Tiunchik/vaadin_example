package org.myvaadin.ui.main.children;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.myvaadin.server.player.ChessPlayerService;
import org.myvaadin.ui.main.MainVew;

import java.util.Map;

@Route(value = "dashboard", layout = MainVew.class)
@PageTitle("Elo histograms")
public class EloHistogram extends VerticalLayout {

    private ChessPlayerService playerService;

    private Map<String, Integer> info;

    public EloHistogram(ChessPlayerService playerService) {
        this.playerService = playerService;
        addClassName("historgam-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        info = playerService.getHistogrammInfo();
        setWidthFull();
        add(getElo(), getEloChart());
    }

    private Component getElo() {
        Span stats = new Span(info
                .values()
                .stream()
                .reduce((f, s) -> f + s)
                .orElse(0)
                .toString() + " overall Elo scope");
        stats.addClassName("elo-stats");
        return stats;
    }

    private Chart getEloChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        info.forEach((school, elo) ->
                dataSeries.add(
                        new DataSeriesItem(school + " overall ELO is " + elo, elo)));
        chart.getConfiguration()
                .setSeries(dataSeries);
        return chart;
    }

}
