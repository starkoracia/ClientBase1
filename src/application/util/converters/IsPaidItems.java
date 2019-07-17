package application.util.converters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class IsPaidItems extends StringConverter<Boolean> {

    private static ObservableList<Boolean> booleans = FXCollections.observableArrayList(true,false);

    @Override
    public String toString(Boolean value) {
        if(value) {
            return "Платный";
        } else {
            return "По гарантии";
        }
    }

    @Override
    public Boolean fromString(String value) {
        if(value.equals("Платный")) {
            return true;
        } else {
            return false;
        }
    }

    public static ObservableList<Boolean> getItems() {
        return booleans;
    }
}
