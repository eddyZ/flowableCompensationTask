package myProject;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;

public class FlowableHandler {

    private static final FlowableHandler singletonInstance = new FlowableHandler();

    private final String bookingProcessFile;

    private final RepositoryService repositoryService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
//    private final String accommodationProcessFile;

    private FlowableHandler() {
        this.bookingProcessFile =  //"TransactionCancelling.bpmn";
            "CompensationTest.bpmn";
            // "BookingItemProcess.bpmn";
        // "BookingProcessWithTransaction2.bpmn";
        // "JoinGatewayTest.bpmn";
        // "BookingProcessWithTransaction.bpmn";
        // "Transaction.bpmn";
        // "SimpleBookingProcess.bpmn";


//        this.accommodationProcessFile = "Child.bpmn"; // "AccomodationBookingProcess.bpmn";

        ProcessEngineConfiguration cfg =
            new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(
                    ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//                .setJpaPersistenceUnitName("flowable-jpa-pu");



        ProcessEngine processEngine = cfg.buildProcessEngine();

        this.taskService = processEngine.getTaskService();
        this.repositoryService = processEngine.getRepositoryService();
        this.runtimeService = processEngine.getRuntimeService();
    }

    public static FlowableHandler getInstance() {
        return singletonInstance;
    }

    public void createDeployment() {
        repositoryService
            .createDeployment()
            .addClasspathResource(bookingProcessFile)
//            .addClasspathResource(accommodationProcessFile)
            .deploy();
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }
}
