package com.laozhang.maxjpa.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskLogListener implements TaskListener {

    Logger logger = LoggerFactory.getLogger(TaskLogListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        String businessKey = delegateTask.getExecution().getProcessBusinessKey();
        final List<String> as = new ArrayList<>();
        if (delegateTask.getCandidates() != null) {
            final Set<IdentityLink> candidates = delegateTask.getCandidates();
            for (final IdentityLink il : candidates) {
                final String userId = il.getUserId();
                as.add(userId);
            }
        }
        if (delegateTask.getAssignee() != null) {
            as.add(delegateTask.getAssignee());
        }
        logger.info("***** process task *****");
        logger.info("task ==> productId is :" + businessKey);
        logger.info("task ==> task name is :" + delegateTask.getName());
        logger.info("task ==> task eventname is :" + delegateTask.getEventName());
        logger.info("task ==> task userId is :" + as);
        logger.info("***** process task *****");
    }
}
