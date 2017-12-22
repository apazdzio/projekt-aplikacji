package com.crud.tasks.service;

import com.crud.tasks.trello.config.ActuatorConfig;
import com.crud.tasks.trello.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyMailTasksCreator implements MailBuilder{

    @Autowired
    private ActuatorConfig actuatorConfig;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildEmailMessage(String message){

        List<String> functionality = new ArrayList<>();
        functionality.add("You can menage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit webside");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("preview", "New message from CrudApp");
        context.setVariable("goodbye", "Regards");
        context.setVariable("company_name", actuatorConfig.getCompanyName());
        context.setVariable("company_email", actuatorConfig.getCompanyEmail());
        context.setVariable("company_phone", actuatorConfig.getCompanyPhone());
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/daily-mail-tasks", context);
    }


}
