package pl.softwaremill.devcrowd.awsdemo;

import pl.softwaremill.devcrowd.awsdemo.impl.sdb.AwsAccessKeys;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: szimano
 */
@WebListener
public class Bootstrap implements ServletContextListener {

    @Inject
    private QueueListener queueListener;

    @Inject
    private AwsAccessKeys awsAccessKeys;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        queueListener.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        queueListener.stop();
    }
}
