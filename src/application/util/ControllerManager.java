package application.util;

import application.controllers.*;

public class ControllerManager {

    private static ClientsController clientsController;
    private static PaymentsController paymentsController;
    private static NewPaymentController newPaymentController;
    private static ClientINFOController clientINFOController;
    private static OrdersController ordersController;
    private static OrderINFOController orderINFOController;
    private static RootViewController rootViewController;
    private static NewJobAndMatController newJobAndMatController;
    private static JobAndMatINFOController jobAndMatINFOController;

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

    public static NewJobAndMatController getNewJobAndMatController() {
        return newJobAndMatController;
    }

    public static void setNewJobAndMatController(NewJobAndMatController newJobAndMatController) {
        if(ControllerManager.newJobAndMatController == null) {
            ControllerManager.newJobAndMatController = newJobAndMatController;
        }
    }

    public static OrderINFOController getOrderINFOController() {
        return orderINFOController;
    }

    public static void setOrderINFOController(OrderINFOController orderINFOController) {
        if(ControllerManager.orderINFOController == null) {
            ControllerManager.orderINFOController = orderINFOController;
        }
    }

    public static JobAndMatINFOController getJobAndMatINFOController() {
        return jobAndMatINFOController;
    }

    public static void setJobAndMatINFOController(JobAndMatINFOController jobAndMatINFOController) {
        if(ControllerManager.jobAndMatINFOController == null) {
            ControllerManager.jobAndMatINFOController = jobAndMatINFOController;
        }
    }
}
