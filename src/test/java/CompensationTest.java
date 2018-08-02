import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.Execution;
import org.junit.Test;

import myProject.FlowableHandler;

public class CompensationTest {

    @Test
    public void testCompensation() {

        FlowableHandler flowableHandler = FlowableHandler.getInstance();
        flowableHandler.createDeployment();
        String taskId;
        TaskService taskService = flowableHandler.getTaskService();

        final String PROCESS_INSTANCE_ID = flowableHandler.getRuntimeService().startProcessInstanceByKey("bookingProcess").getId();


        taskId = taskService.createTaskQuery().taskDefinitionKey("sendInvitation").singleResult().getId();
        taskService.complete(taskId);

        taskId = taskService.createTaskQuery().taskDefinitionKey("receivePayment").singleResult().getId();
        taskService.complete(taskId);

        for (Execution e : flowableHandler.getRuntimeService().createExecutionQuery().list()) {
            System.out.println(e);
        }
        taskId = taskService.createTaskQuery().taskDefinitionKey("handleCancellation").singleResult().getId();
        taskService.complete(taskId);

        for (Execution e : flowableHandler.getRuntimeService().createExecutionQuery().list()) {
            System.out.println(e);
        }
    }
}
