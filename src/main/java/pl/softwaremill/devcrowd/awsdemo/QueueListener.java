package pl.softwaremill.devcrowd.awsdemo;

import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.MessageAdder;
import pl.softwaremill.devcrowd.awsdemo.service.QueueService;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * User: szimano
 */
@Singleton
public class QueueListener implements Runnable {

    boolean runs = true;

    @Inject
    private MessageAdder messageAdder;
    @Inject
    private QueueService queueService;

    public QueueListener() {
    }

    public void start() {
        Thread t = new Thread(this);

        t.start();
    }

    public void run() {
        while (runs) {
            Message messageReceived;

            // read from the queue
            try {
                if ((messageReceived = queueService.readMessage()) != null) {
                    System.out.println("Message read: " + messageReceived);

                    System.out.println("Processing");

                    //Simulate message processing
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //
                    }

                    System.out.println("Adding message");

                    messageAdder.addMessage(messageReceived);
                    System.out.println("Message added");
                }
            } catch (RuntimeException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                throw e;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //
            }
        }
    }

    public void stop() {
        runs = false;
    }
}

