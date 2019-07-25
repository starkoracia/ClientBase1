package application.util.combobox;

import application.sql.entitys.work.Job;
import application.util.ControllerManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class AutoCompletionJobCBox {

    public static void autoCompleteComboBoxPlus(ComboBox<Job> comboBox, ComboBoxAutoCompletioner.AutoCompleteComparator<Job> comparatorMethod) {
        ObservableList<Job> data = comboBox.getItems();
        String t;

        comboBox.setEditable(true);

        comboBox.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {

            private boolean moveCaretToPos = false;
            private int caretPos;

            String t = null;
            ObservableList<Job> listConains = FXCollections.observableArrayList();
            ObservableList<Job> listEquals = FXCollections.observableArrayList();

            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.UP) {
                    caretPos = -1;
                    if (comboBox.getEditor().getText() != null) {
                        moveCaret(comboBox.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (!comboBox.isShowing()) {
                        comboBox.show();
                    }
                    caretPos = -1;
                    if (comboBox.getEditor().getText() != null) {
                        moveCaret(comboBox.getEditor().getText().length());
                    }
                    return;
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    comboBox.setValue(null);
                    if (comboBox.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = comboBox.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.DELETE) {
                    if (comboBox.getEditor().getText() != null) {
                        moveCaretToPos = true;
                        caretPos = comboBox.getEditor().getCaretPosition();
                    }
                } else if (event.getCode() == KeyCode.ENTER) {
                    if (t != null) {
                        if (listConains.isEmpty()) {
                            comboBox.getEditor().setText(t);
                            ControllerManager.getOrderINFOController().openNewJobAndMatWindow(t);
                        }
                    }
                    return;
                }

                if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT || event.getCode().equals(KeyCode.SHIFT) || event.getCode().equals(KeyCode.CONTROL)
                        || event.isControlDown() || event.getCode() == KeyCode.HOME
                        || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                    return;
                }

                listConains.clear();
                for (Job aData : data) {
                    if (aData != null && comboBox.getEditor().getText() != null
                            && comparatorMethod.matches(comboBox.getEditor().getText(), aData)) {
                        listConains.add(aData);
                    }
                }

//                String t = "";
                if (comboBox.getEditor().getText() != null) {
                    t = comboBox.getEditor().getText();
                }


                comboBox.setItems(listConains);
                comboBox.getEditor().setText(t);
                if (!moveCaretToPos) {
                    caretPos = -1;
                }
                moveCaret(t.length());
                if (!listConains.isEmpty()) {
                    comboBox.show();
                }
            }

            private void moveCaret(int textLength) {
                if (caretPos == -1) {
                    comboBox.getEditor().positionCaret(textLength);
                } else {
                    comboBox.getEditor().positionCaret(caretPos);
                }
                moveCaretToPos = false;
            }
        });
    }

    public static Job getComboBoxValue(ComboBox<Job> comboBox) {
        if (comboBox.getSelectionModel().getSelectedIndex() < 0) {
            return null;
        } else {
            return comboBox.getItems().get(comboBox.getSelectionModel().getSelectedIndex());
        }
    }
}
