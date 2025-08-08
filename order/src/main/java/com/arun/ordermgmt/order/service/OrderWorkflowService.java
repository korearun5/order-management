package com.arun.ordermgmt.order.service;

// order-service/src/main/java/com/example/order/service/OrderWorkflowService.java
@ZeebeWorker(type = "initiate-order")
public void startOrderWorkflow(
        @ZeebeVariablesAsType OrderContext context
) {
    zeebeClient.newCreateInstanceCommand()
            .bpmnProcessId("order-fulfillment")
            .latestVersion()
            .variables(context)
            .send();
}