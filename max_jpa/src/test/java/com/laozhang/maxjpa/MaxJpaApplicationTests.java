package com.laozhang.maxjpa;

import com.laozhang.maxjpa.entity.Product;
import com.laozhang.maxjpa.entity.User;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxJpaApplicationTests {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    Map<String, User> usersMap = new HashMap<>();

    @Before
    public void getUsers() {
        User user1 = new User(1L, "张三", "123456", 28);
        User user2 = new User(2L, "李四", "123456", 29);
        User user3 = new User(3L, "王五", "123456", 30);
        User user4 = new User(4L, "赵六", "123456", 31);
        usersMap.put("1", user1);
        usersMap.put("2", user2);
        usersMap.put("3", user3);
        usersMap.put("4", user4);
    }

    @Test
    public void contextLoads() {
    }

    /**
     * 申报产品
     */
    @Test
    public void apply() {
        // 产品
        Product product = new Product();
        product.setId(1L);
        product.setName("5G");
        Map<String, Object> variables = new HashMap<>();
        variables.put("submitUser", new Long(usersMap.get("1").getId()).toString());
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(
                "product_process", String.valueOf(product.getId()), variables);
        String stsTaskId = taskService.createTaskQuery().processInstanceId(pi.getProcessInstanceId()).active()
                .singleResult().getId();
        variables.put("declareFlag", "1");
        variables.put("techUsers", "2,3");
        taskService.complete(stsTaskId, variables);
    }

    /**
     * 获取任务列表
     */
    @Test
    public void getTasks() {
        String username = "1";
        List<Task> currentAllTasks = taskService.createTaskQuery().active().taskCandidateOrAssigned(username)
                .list();
    }

    /**
     * 查询流程变量的历史表,可以根据变量名称查询该变量的所有历史信息
     */
    @Test
    public void findHistoryProcessVariables(){
        List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery()//创建一个历史的流程变量查询对象
                .variableName("请假天数")
                .list();
        if (list!=null &&list.size()>0) {
            for (HistoricVariableInstance hvi : list) {
                System.out.println(hvi.getId()+"     "+hvi.getProcessInstanceId()+"   "+hvi.getVariableName()
                        +"   "+hvi.getVariableTypeName()+"    "+hvi.getValue());
                System.out.println("########################################");
            }
        }

    }

    /**
     *  历史流程实例查询
     */
    @Test
    public void findHistoricProcessInstance() {
        // 查询已完成的流程
        List<HistoricProcessInstance> datas = historyService
                .createHistoricProcessInstanceQuery().finished().list();
        System.out.println("使用finished方法：" + datas.size());
        // 根据流程定义ID查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionId("processDefinitionId").list();
        System.out.println("使用processDefinitionId方法： " + datas.size());
        // 根据流程定义key（流程描述文件的process节点id属性）查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("processDefinitionKey").list();
        System.out.println("使用processDefinitionKey方法： " + datas.size());
        // 根据业务主键查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey("processInstanceBusinessKey").list();
        System.out.println("使用processInstanceBusinessKey方法： " + datas.size());
        // 根据流程实例ID查询
        datas = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId("processInstanceId").list();
        System.out.println("使用processInstanceId方法： " + datas.size());
        // 查询没有完成的流程实例
        historyService.createHistoricProcessInstanceQuery().unfinished().list();
        System.out.println("使用unfinished方法： " + datas.size());
    }

    /**
     *  历史任务查询
     * @throws ParseException
     */
    @Test
    public void findHistoricTasks() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //历史数据查询
        List<HistoricTaskInstance> datas = historyService.createHistoricTaskInstanceQuery()
                .finished().list();
        System.out.println("使用finished方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionId("processDefinitionId").list();
        System.out.println("使用processDefinitionId方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionKey("testProcess").list();
        System.out.println("使用processDefinitionKey方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processDefinitionName("testProcess2").list();
        System.out.println("使用processDefinitionName方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processFinished().list();
        System.out.println("使用processFinished方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId("processInstanceId").list();
        System.out.println("使用processInstanceId方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .processUnfinished().list();
        System.out.println("使用processUnfinished方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("crazyit").list();
        System.out.println("使用taskAssignee方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskAssigneeLike("%zy%").list();
        System.out.println("使用taskAssigneeLike方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDefinitionKey("usertask1").list();
        System.out.println("使用taskDefinitionKey方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueAfter(sdf.parse("2020-10-11 06:00:00")).list();
        System.out.println("使用taskDueAfter方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueBefore(sdf.parse("2022-10-11 06:00:00")).list();
        System.out.println("使用taskDueBefore方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .taskDueDate(sdf.parse("2020-10-11 06:00:00")).list();
        System.out.println("使用taskDueDate方法查询：" + datas.size());
        datas = historyService.createHistoricTaskInstanceQuery()
                .unfinished().list();
        System.out.println("使用unfinished方法查询：" + datas.size());
    }
    /**
     *  历史行为查询
     *  流程在进行过程中，每每走一个节点，都会记录流程节点的信息，包括节点的id，名称、类型、时间等，保存到ACT_HI_ACTINST表中。
     */
    @Test
    public void findHistoricActivityInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //查询数据
        List<HistoricActivityInstance> datas = historyService.createHistoricActivityInstanceQuery()
                .activityId("endevent1").list();
        System.out.println("使用activityId查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery()
                .activityInstanceId(datas.get(0).getId()).list();
        System.out.println("使用activityInstanceId查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery()
                .activityType("intermediateSignalCatch").list();
        System.out.println("使用activityType查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery()
                .executionId("executionId").list();
        System.out.println("使用executionId查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery().finished().list();
        System.out.println("使用finished查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId("processInstanceId").list();
        System.out.println("使用processInstanceId查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery()
                .taskAssignee("crazyit").list();
        System.out.println("使用taskAssignee查询：" + datas.size());
        datas = historyService.createHistoricActivityInstanceQuery().unfinished().list();
        System.out.println("使用unfinished查询：" + datas.size());
    }

    /**
     *  历史流程明细查询
     *  在流程进行的过程中，会产生许多明细数据，只有将History设置为最高级别的时候，才会被记录到ACT_HI_DETAIL表中。
     */
    @Test
    public void findHistoricDetail() {
        // 查询历史行为
        HistoricActivityInstance act = historyService.createHistoricActivityInstanceQuery()
                .activityName("First Task").finished().singleResult();
        List<HistoricDetail> datas = historyService.createHistoricDetailQuery()
                .activityInstanceId(act.getId()).list();
        System.out.println("使用activityInstanceId方法查询：" + datas.size());
        datas = historyService.createHistoricDetailQuery().excludeTaskDetails().list();
        System.out.println("使用excludeTaskDetails方法查询：" + datas.size());
        datas = historyService.createHistoricDetailQuery().formProperties().list();
        System.out.println("使用formProperties方法查询：" + datas.size());
        datas = historyService.createHistoricDetailQuery().processInstanceId("processInstanceId").list();
        System.out.println("使用processInstanceId方法查询：" + datas.size());
        datas = historyService.createHistoricDetailQuery().taskId("taskId").list();
        System.out.println("使用taskId方法查询：" + datas.size());
        datas = historyService.createHistoricDetailQuery().variableUpdates().list();
        System.out.println("使用variableUpdates方法查询：" + datas.size());
    }

    private String getCurrentTaskIdWithProductId(final Integer productId) {
        List<Task> list = taskService.createTaskQuery().active().processInstanceBusinessKey(String.valueOf(productId)).list();
        Task t = taskService.createTaskQuery().active().processInstanceBusinessKey(String.valueOf(productId))
                .list().get(0);
        return t.getId();
    }

    private String getCurrentTaskIdByUserWithProductId(String username, Integer productId) {

        List<Task> currentAllTasks = taskService.createTaskQuery().active().taskCandidateOrAssigned(username)
                .list();
        String currentTaskId = "";
        for (Task t : currentAllTasks) {
            String businessKey = runtimeService.createProcessInstanceQuery().active()
                    .processInstanceId(t.getProcessInstanceId()).singleResult().getBusinessKey();
            if (businessKey.equals(productId.toString())) {
                currentTaskId = t.getId();
                break;
            }
        }
        return currentTaskId;
    }

}

