package com.laozhang.maxjpa.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessEndListener implements ExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(ProcessEndListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String businessKey = execution.getProcessBusinessKey();
        logger.info("process ==> end : product id is " + businessKey);
    }
}
