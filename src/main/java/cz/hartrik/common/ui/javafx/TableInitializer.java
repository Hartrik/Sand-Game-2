
package cz.hartrik.common.ui.javafx;

import java.util.function.Consumer;
import java.util.function.Function;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

/**
 * Pomocná třída pro pohodlnější inicializaci tabulky. Podporuje pouze jednu
 * úroveň vnořených sloupců. Data v tabulce není možné editovat.
 *
 * @version 2015-09-06
 * @author Patrik Harag
 * @param <T> content type
 */
public class TableInitializer<T> {

    private final TableView<T> table;
    private TableColumn<T, TableColumn<T, ?>> inner;

    public TableInitializer(TableView<T> table) {
        this.table = table;
    }

    // add column

    public TableInitializer<T> addColumn(TableColumn<T, ?> column) {
        if (inner == null)
            table.getColumns().add(column);
        else
            inner.getColumns().add(column);

        return this;
    }

    public <U> TableInitializer<T> addObjectColumn(int minWidth, int maxWidth,
            String name, Function<T, U> convert, Function<U, String> toString) {

        TableColumn<T, U> column = new TableColumn<>(name);
        column.setMinWidth(minWidth);
        column.setMaxWidth(maxWidth);

        column.setCellFactory(TextFieldTableCell.forTableColumn(
                new StringConverter<U>() {

            @Override public String toString(U value) {
                return toString.apply(value);
            }

            @Override public U fromString(String string) {
                throw new UnsupportedOperationException("Not supported");
            }
        }));

        column.setCellValueFactory(
                (p) -> new SimpleObjectProperty<>(convert.apply(p.getValue())));

        return addColumn(column);
    }

    public <U> TableInitializer<T> addObjectColumn(int width,
            String name, Function<T, U> convert, Function<U, String> toString) {

        return addObjectColumn(width, width, name, convert, toString);
    }

    public <U> TableInitializer<T> addObjectColumn(
            String name, Function<T, U> convert, Function<U, String> toString) {

        return addObjectColumn(0, Integer.MAX_VALUE, name, convert, toString);
    }

    // add string column

    public TableInitializer<T> addStringColumn(
            String name, Function<T, Object> function) {

        return addStringColumn(0, Integer.MAX_VALUE, name, function);
    }

    public TableInitializer<T> addStringColumn(int width, String name,
            Function<T, Object> function) {

        return addStringColumn(width, width, name, function);
    }

    public TableInitializer<T> addStringColumn(int minWidth, int maxWidth,
            String name, Function<T, Object> function) {

        TableColumn<T, String> column = new TableColumn<>(name);
        column.setMinWidth(minWidth);
        column.setMaxWidth(maxWidth);

        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setCellValueFactory(p -> new SimpleStringProperty(
                String.valueOf(function.apply(p.getValue()))));

        return addColumn(column);
    }

    // add int column

    public TableInitializer<T> addIntegerColumn(String name,
            Function<T, Integer> function) {

        return addIntegerColumn(0, Integer.MAX_VALUE, name, function);
    }

    public TableInitializer<T> addIntegerColumn(int width, String name,
            Function<T, Integer> function) {

        return addIntegerColumn(width, width, name, function);
    }

    public TableInitializer<T> addIntegerColumn(int minWidth, int maxWidth,
            String name, Function<T, Integer> function) {

        TableColumn<T, Integer> column = new TableColumn<>(name);
        column.setMinWidth(minWidth);
        column.setMaxWidth(maxWidth);

        column.setCellFactory(TextFieldTableCell.forTableColumn(
                new StringConverter<Integer>() {

            @Override public String toString(Integer integer) {
                return integer == null ? "0" : integer.toString();
            }

            @Override public Integer fromString(String string) {
                return string == null ? 0 : Integer.parseInt(string);
            }
        }));

        column.setCellValueFactory(p ->
                new SimpleObjectProperty<>(function.apply(p.getValue())));

        return addColumn(column);
    }

    // add long column

    public TableInitializer<T> addLongColumn(String name,
            Function<T, Long> function) {

        return addLongColumn(0, Integer.MAX_VALUE, name, function);
    }

    public TableInitializer<T> addLongColumn(int width, String name,
            Function<T, Long> function) {

        return addLongColumn(width, width, name, function);
    }

    public TableInitializer<T> addLongColumn(int minWidth, int maxWidth,
            String name, Function<T, Long> function) {

        TableColumn<T, Long> column = new TableColumn<>(name);
        column.setMinWidth(minWidth);
        column.setMaxWidth(maxWidth);

        column.setCellFactory(TextFieldTableCell.forTableColumn(
                new StringConverter<Long>() {

            @Override public String toString(Long object) {
                return object == null ? "0" : object.toString();
            }

            @Override public Long fromString(String string) {
                return string == null ? 0L : Long.parseLong(string);
            }
        }));

        column.setCellValueFactory(p ->
                new SimpleObjectProperty<>(function.apply(p.getValue())));

        return addColumn(column);
    }

    // add inner column

    public TableInitializer<T> addInnerColumn(String name) {
        return addInnerColumn(0, Integer.MAX_VALUE, name);
    }

    public TableInitializer<T> addInnerColumn(int width, String name) {
        return addInnerColumn(width, width, name);
    }

    public TableInitializer<T> addInnerColumn(int minWidth, int maxWidth,
            String name) {

        inner = new TableColumn<>(name);
        inner.setMinWidth(minWidth);
        inner.setMaxWidth(maxWidth);

        table.getColumns().add(inner);
        return this;
    }

    /**
     * Zruší přidávání vnořených sloupců. <p>
     * Tuto metodu není nutné volat, pokud následuje další sloupec se
     * vnořenými sloupci. (Je podporována pouze jedna úroveň vnořených sloupců.)
     *
     * @return TableInitializer
     */
    public TableInitializer<T> cancelInner() {
        inner = null;

        return this;
    }

    /**
     * Upraví poslední sloupec.
     * Slouží hlavně k tomu, aby se kvůli jedné přímé úpravě nerozbila jinak
     * přehledná tvorba tabulky.
     *
     * @param consumer funkce, která bude něco provádět se posledním sloupcem
     * @throws IndexOutOfBoundsException v tabulce není žádný sloupec
     * @return TableInitializer
     */
    public TableInitializer<T> init(Consumer<TableColumn<T, ?>> consumer) {
        if (inner == null || inner.getColumns().isEmpty()) {
            int lastIndex = table.getColumns().size() - 1;
            consumer.accept(table.getColumns().get(lastIndex));
        } else {
            int lastIndex = inner.getColumns().size() - 1;
            consumer.accept(inner.getColumns().get(lastIndex));
        }

        return this;
    }

    // ---- STATICKÉ METODY ----

    public static <T> TableInitializer<T> of(TableView<T> tableView) {
        return new TableInitializer<>(tableView);
    }

}