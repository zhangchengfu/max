package com.laozhang.maxjpa.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessStartListener implements ExecutionListener {

    public static final Logger logger = LoggerFactory.getLogger(ProcessStartListener.class);

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String businessKey = execution.getProcessBusinessKey();
        logger.info("process ==> start : product id is " + businessKey);
    }
}
