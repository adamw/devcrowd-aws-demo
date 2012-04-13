package pl.softwaremill.devcrowd.awsdemo.impl.hibernate;

import pl.softwaremill.common.cdi.transaction.Transactional;
import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.Local;
import pl.softwaremill.devcrowd.awsdemo.service.MessageAdder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * User: szimano
 */
@Local
public class HibernateMessageAdder implements MessageAdder {

    @PersistenceContext
    private EntityManager entityManager;

    public HibernateMessageAdder() {
    }

    @Transactional
    public void addMessage(Message msg) {
        entityManager.joinTransaction();

        try {
            if (msg.getSaveDate() == null)
                msg.setSaveDate(new Date());

            entityManager.merge(msg);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Message added");
    }
}
