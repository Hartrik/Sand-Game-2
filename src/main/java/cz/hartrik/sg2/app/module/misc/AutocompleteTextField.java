package cz.hartrik.sg2.app.module.misc;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * @author Patrik Harag
 * @version 2017-08-09
 */
public class AutocompleteTextField extends TextField {

    // inspiration:
    // https://stackoverflow.com/questions/36861056/javafx-textfield-auto-suggestions

    private final Supplier<Collection<String>> items;
    private final ContextMenu popup;

    public AutocompleteTextField(Supplier<Collection<String>> items) {
        super();
        this.items = items;
        this.popup = new ContextMenu();

        init();
    }

    private void init() {
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            if (enteredText == null || enteredText.isEmpty()) {
                popup.hide();
            } else {
                List<String> filteredEntries = items.get().stream()
                        .filter(e -> e.toLowerCase().contains(enteredText.toLowerCase()))
                        .collect(Collectors.toList());

                if (!filteredEntries.isEmpty()) {
                    populatePopup(filteredEntries, enteredText);
                    if (!popup.isShowing()) {
                        popup.show(AutocompleteTextField.this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    popup.hide();
                }
            }
        });

        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            popup.hide();
        });
    }

    private void populatePopup(List<String> searchResult, String request) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);

        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);

            Label entryLabel = new Label();
            entryLabel.setGraphic(buildTextFlow(result, request));
            entryLabel.setPrefHeight(10);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            menuItems.add(item);

            item.setOnAction(actionEvent -> {
                setText(result);
                positionCaret(result.length());
                popup.hide();
            });
        }

        popup.getItems().clear();
        popup.getItems().addAll(menuItems);
    }

    private TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex, filterIndex + filter.length()));
        textFilter.setFill(Color.RED);
        return new TextFlow(textBefore, textFilter, textAfter);
    }

}