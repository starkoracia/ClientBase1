package application.util;

import javafx.application.Platform;
import javafx.scene.Node;

public class FocusRepeater {
    public static void repeat(Node node) {
        Platform.runLater(() -> {
            if (!node.isFocused()) {
                node.requestFocus();
                repeat(node);
            }
        });
    }
}
