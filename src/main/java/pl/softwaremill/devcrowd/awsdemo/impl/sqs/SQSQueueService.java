package pl.softwaremill.devcrowd.awsdemo.impl.sqs;

import pl.softwaremill.common.sqs.SQSManager;
import pl.softwaremill.common.sqs.util.SQSAnswer;
import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.AWS;
import pl.softwaremill.devcrowd.awsdemo.service.QueueService;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@AWS
public class SQSQueueService implements QueueService {
    private static final String SQS_MESSAGES_QUEUE = "messages";

    @Override
    public void sendMessage(Message message) {
        System.out.println("Sending message using SQS");
        SQSManager.sendMessage(SQS_MESSAGES_QUEUE, message);
    }

    @Override
    public Message readMessage() {
        SQSAnswer answer = SQSManager.receiveMessage(SQS_MESSAGES_QUEUE);
        if (answer != null) {
            System.out.println("Received SQS message");
            SQSManager.deleteMessage(SQS_MESSAGES_QUEUE, answer.getReceiptHandle());
            return (Message) answer.getMessage();
        } else {
            return null;
        }
    }
}
