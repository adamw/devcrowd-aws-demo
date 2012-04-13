package pl.softwaremill.devcrowd.awsdemo.service;

import pl.softwaremill.devcrowd.awsdemo.entity.Message;

/**
 * User: szimano
 */
public interface MessageAdder {
    void addMessage(Message msg);
}
