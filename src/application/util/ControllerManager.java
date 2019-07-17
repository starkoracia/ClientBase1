package application.util;

import application.controllers.*;

public class ControllerManager {

    private static ClientsController clientsController;
    private static PaymentsController paymentsController;
    private static NewPaymentController newPaymentController;
    private static ClientINFOController clientINFOController;
    private static OrdersController ordersController;
    private static RootViewController rootViewController;

    public ControllerManager() {
    }

    public static ClientsController getClientsController() {
        return clientsController;
    }

    public static void setClientsController(ClientsController clientsController) {
        if(ControllerManager.clientsController == null) {
            ControllerManager.clientsController = clientsController;
        }
    }

    public static PaymentsController getPaymentsController() {
        return paymentsController;
    }

    public static void setPaymentsController(PaymentsController paymentsController) {
        if(ControllerManager.paymentsController == null) {
            ControllerManager.paymentsController = paymentsController;
        }
    }

    public static NewPaymentController getNewPaymentController() {
        return newPaymentController;
    }

    public static void setNewPaymentController(NewPaymentController newPaymentController) {
        if(ControllerManager.newPaymentController == null) {
            ControllerManager.newPaymentController = newPaymentController;
        }
    }

    public static ClientINFOController getClientINFOController() {
        return clientINFOController;
    }

    public static void setClientINFOController(ClientINFOController clientINFOController) {
        if(ControllerManager.clientINFOController == null) {
            ControllerManager.clientINFOController = clientINFOController;
        }
    }

    public static OrdersController getOrdersController() {
        return ordersController;
    }

    public static void setOrdersController(OrdersController ordersController) {
        if(ControllerManager.ordersController == null) {
            ControllerManager.ordersController = ordersController;
        }
    }

    public static RootViewController getRootViewController() {
        return rootViewController;
    }

    public static void setRootViewController(RootViewController rootViewController) {
        if(ControllerManager.rootViewController == null) {
            ControllerManager.rootViewController = rootViewController;
        }
    }
}
