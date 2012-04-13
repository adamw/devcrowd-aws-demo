package pl.softwaremill.devcrowd.awsdemo.controller;

import pl.softwaremill.asamal.controller.ControllerBean;
import pl.softwaremill.asamal.controller.annotation.Controller;
import pl.softwaremill.asamal.controller.annotation.Get;
import pl.softwaremill.asamal.controller.annotation.Json;
import pl.softwaremill.asamal.controller.annotation.Post;
import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.MessagesLister;
import pl.softwaremill.devcrowd.awsdemo.service.QueueService;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.*;

/**
 * Home page controller
 *
 * User: szimano
 */
@Controller("home")
public class Home extends ControllerBean implements Serializable {

    @Inject
    private MessagesLister messagesLister;

    @Inject
    private QueueService queueService;

    @Get
    public void index() {

        List<Message> messages = messagesLister.listRecentMessages("room");
        System.out.println("messages = " + messages);

        putInContext("messages", messages);
    }

    @Get
    public void addMessageByGet() {
        addNewMessage(getParameter("content"));

        redirect("index");
    }

    @Post
    public void addMessage() {
        addNewMessage(getParameter("content"));
    }

    private void addNewMessage(String content) {
        Message message = new Message(
                UUID.randomUUID(),
                "room",
                content,
                new Date(),
                null
        );

        queueService.sendMessage(message);
    }

    @Post
    public void reloadMessages() {
        List<Message> messages = messagesLister.listRecentMessages("room");
        System.out.println("messages = " + messages);

        putInContext("messages", messages);
    }

    @Json
    public Object getMessages() {
        return messagesLister.listRecentMessages(getParameter("room"));
    }
}
