package pl.softwaremill.devcrowd.awsdemo.producers;

import pl.softwaremill.devcrowd.awsdemo.service.*;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * User: szimano
 */
public class Producers {

    @Inject
    @Local
    Instance<MessagesLister> localMessageLister;

    @Inject
    @AWS
    Instance<MessagesLister> awsMessageLister;

    @Inject
    @Local
    Instance<MessageAdder> localMessageAdder;

    @Inject
    @AWS
    Instance<MessageAdder> awsMessageAdder;

    @Inject
    @Local
    Instance<QueueService> localQueueService;

    @Inject
    @AWS
    Instance<QueueService> awsQueueService;

    @Produces
    private MessageAdder createMessageAdder() {
        if (System.getProperty("local") != null) {
            return localMessageAdder.get();
        } else {
            return awsMessageAdder.get();
        }
    }

    @Produces
    private MessagesLister createMessageLister() {
        if (System.getProperty("local") != null) {
            return localMessageLister.get();
        } else {
            return awsMessageLister.get();
        }
    }

    @Produces
    private QueueService createQueueService() {
        if (System.getProperty("local") != null) {
            return localQueueService.get();
        } else {
            return awsQueueService.get();
        }
    }
}
