package pl.softwaremill.devcrowd.awsdemo.service;


import pl.softwaremill.devcrowd.awsdemo.entity.Message;

/**
 * User: szimano
 */
public interface QueueService {

    void sendMessage(Message message);

    Message readMessage();
}
