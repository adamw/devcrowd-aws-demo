package pl.softwaremill.devcrowd.awsdemo.service;

import pl.softwaremill.devcrowd.awsdemo.entity.Message;

import java.util.List;

/**
 * User: szimano
 */
public interface MessagesLister {
    List<Message> listRecentMessages(String room);
}
