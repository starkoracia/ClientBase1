package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.util.ControllerManager;
import com.sun.org.omg.CORBA.Initializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class RootViewController implements Initializable {


    private final String clientsViewLocation = "../view/Clients.fxml";

    private final String stockViewLocation = "../view/Stock.fxml";

    private final String storeViewLocation = "../view/Store.fxml";

    private final String paymentsViewLocation = "../view/Payments.fxml";

    private final String ordersViewLocation = "../view/Orders.fxml";

    @FXML
    private URL location;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab clientsTab;

    @FXML
    private Tab stockTab;

    @FXML
    private Tab storeTab;

    @FXML
    private Tab reportsTab;

    @FXML
    private Tab paymentsTab;

    public Tab ordersTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadClientsTabContent();
        loadStockTabContent();
        loadStoreTabContent();
        loadPaymentsTabContent();
        loadOrdersTabContent();
    }

    private void loadOrdersTabContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ordersViewLocation));
            AnchorPane ordersView = loader.load();
            ordersTab.setContent(ordersView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClientsTabContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(clientsViewLocation));
            AnchorPane clientsView = loader.load();
            clientsTab.setContent(clientsView);
            ControllerManager.setClientsController(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStockTabContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(stockViewLocation));
            AnchorPane stockView = loader.load();
            stockTab.setContent(stockView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStoreTabContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(storeViewLocation));
            AnchorPane storeView = loader.load();
            storeTab.setContent(storeView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadReportsTabContent() {
    }

    private void loadPaymentsTabContent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(paymentsViewLocation));
            AnchorPane paymentsView = loader.load();
            paymentsTab.setContent(paymentsView);
            ControllerManager.setPaymentsController(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
