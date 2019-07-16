package application.util;

import application.controllers.ClientINFOController;
import application.controllers.ClientsController;
import application.controllers.NewPaymentController;
import application.controllers.PaymentsController;

public class ControllerManager {

    private static ClientsController clientsController;
    private static PaymentsController paymentsController;
    private static NewPaymentController newPaymentController;
    private static ClientINFOController clientINFOController;

    public ControllerManager() {
    }

    public static ClientsController getClientsController() {
        return clientsController;
    }

    public static void setClientsController(ClientsController clientsController) {
        ControllerManager.clientsController = clientsController;
    }

    public static PaymentsController getPaymentsController() {
        return paymentsController;
    }

    public static void setPaymentsController(PaymentsController paymentsController) {
        ControllerManager.paymentsController = paymentsController;
    }

    public static NewPaymentController getNewPaymentController() {
        return newPaymentController;
    }

    public static void setNewPaymentController(NewPaymentController newPaymentController) {
        ControllerManager.newPaymentController = newPaymentController;
    }

    public static ClientINFOController getClientINFOController() {
        return clientINFOController;
    }

    public static void setClientINFOController(ClientINFOController clientINFOController) {
        ControllerManager.clientINFOController = clientINFOController;
    }
}
