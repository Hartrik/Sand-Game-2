
package cz.hartrik.sg2.app.extension.dialog.stats;

import cz.hartrik.common.ui.javafx.TableInitializer;
import cz.hartrik.sg2.world.ChunkedArea;
import cz.hartrik.sg2.world.ElementArea;
import cz.hartrik.sg2.world.element.Air;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller ovládající panel se statistikami plátna
 *
 * @version 2016-06-21
 * @author Patrik Harag
 */
public class StatsController implements Initializable {

    @FXML private Label lWidth;
    @FXML private Label lHeight;
    @FXML private Label lPointCount;
    @FXML private Label lPointNonAirCount;

    @FXML private Label lChunkSize;
    @FXML private Label lChunks;

    @FXML private TableView<ElementStats> table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new TableInitializer<>(table)
            .addStringColumn("Class", v -> v.clazz.getName())
            .addInnerColumn("Výskyt na plátně")
                .addIntegerColumn(55, 55, "ks", v -> v.count)
                .addStringColumn(55, 55, "%",
                        v -> String.format("%.2f", v.countRate))
                .cancelInner()
            .addInnerColumn("Počet instancí")
                .addIntegerColumn(55, 55, "ks", v -> v.instances)
                .addStringColumn(55, 55, "%",
                        v -> String.format("%.2f", v.instancesRate))
                .cancelInner();

        // text při prázdné tabulce
        table.setPlaceholder(new Text("Žádné položky"));
    }

    @FXML
    public void close() {
        ((Stage) lWidth.getScene().getWindow()).close();
    }

    public void update(ElementArea area) {
        lWidth.setText(Integer.toString(area.getWidth()));
        lHeight.setText(Integer.toString(area.getHeight()));

        final int count = area.getWidth() * area.getHeight();
        lPointCount.setText(Integer.toString(count));

        int air = (int) area.stream().filter(e -> e instanceof Air).count();
        lPointNonAirCount.setText(String.format("%d (%.2f %%)",
                count - air, ((count - air) * 100. / count)));

        lChunkSize.setText(area instanceof ChunkedArea
                ? ((ChunkedArea) area).getChunkSize() + "" : "x");
        lChunks.setText(area instanceof ChunkedArea
                ? ((ChunkedArea) area).getChunkCount() + "" : "x");

        updateTable(area);
    }

    public void updateTable(ElementArea area) {
        // množina tříd
        Set<Class<?>> classes = area.stream()
                                    .map(element -> element.getClass())
                                    .collect(Collectors.toSet());
        // spočítání výskytu...
        List<ElementStats> collect = classes.stream()
                .map(c -> createStats(area, c))
                .sorted(Comparator.comparing(s -> s.clazz.hashCode()))
                .collect(Collectors.toList());

        // vypočítání poměrů
        int countSum     = collect.stream().mapToInt(i -> i.count).sum();
        int instancesSum = collect.stream().mapToInt(i -> i.instances).sum();

        collect.forEach((ElementStats s) -> {
            s.countRate     = s.count     * 100. / countSum;
            s.instancesRate = s.instances * 100. / instancesSum;
        });

        table.getItems().setAll(collect);
    }

    private ElementStats createStats(ElementArea area, Class<?> clazz) {
        long count = area.stream()
                         .filter(element -> element.getClass() == clazz)
                         .count();

        long instances = area.stream()
                             .filter(element -> element.getClass() == clazz)
                             .map(System::identityHashCode)
                             .collect(Collectors.toSet())
                                     .size();

        ElementStats stats = new ElementStats();
        stats.clazz = clazz;
        stats.count = (int) count;
        stats.instances = (int) instances;

        return stats;
    }

}