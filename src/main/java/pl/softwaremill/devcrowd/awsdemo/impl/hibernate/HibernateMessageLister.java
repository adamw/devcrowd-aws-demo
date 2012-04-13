package pl.softwaremill.devcrowd.awsdemo.impl.hibernate;

import pl.softwaremill.common.cdi.transaction.Transactional;
import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.Local;
import pl.softwaremill.devcrowd.awsdemo.service.MessagesLister;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: szimano
 */
@Local
public class HibernateMessageLister implements MessagesLister {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    public HibernateMessageLister() {
    }

    @Transactional
    public List<Message> listRecentMessages(String room) {
        //noinspection unchecked
        return entityManager.createQuery("select m from Message m where m.room = :room order by m.date desc")
                .setParameter("room", room).setMaxResults(10).getResultList();
    }
}
