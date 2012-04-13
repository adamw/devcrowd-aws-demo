package pl.softwaremill.devcrowd.awsdemo.impl.sdb;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import com.xerox.amazonws.simpledb.SDBException;
import pl.softwaremill.devcrowd.awsdemo.MessageMappingConstants;
import pl.softwaremill.devcrowd.awsdemo.entity.Message;
import pl.softwaremill.devcrowd.awsdemo.service.AWS;
import pl.softwaremill.devcrowd.awsdemo.service.MessageAdder;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author Adam Warski (adam at warski dot org)
 */
@AWS
public class SDBMessageAdder implements MessageAdder {
    private final MessagesDomainProvider messagesDomainProvider;

    private DateFormatter dateFormatter;

    @Inject
    public SDBMessageAdder(MessagesDomainProvider messagesDomainProvider, DateFormatter dateFormatter) {
        this.messagesDomainProvider = messagesDomainProvider;
        this.dateFormatter = dateFormatter;
    }

    @Override
    public void addMessage(Message msg) {
        System.out.println("Adding message: " + msg);

        if (msg.getSaveDate() == null)
            msg.setSaveDate(new Date());

        SetMultimap<String, String> attrs = HashMultimap.create();
        attrs.put(MessageMappingConstants.ROOM, msg.getRoom());
        attrs.put(MessageMappingConstants.CONTENT, msg.getContent());
        attrs.put(MessageMappingConstants.DATE,
                dateFormatter.formatDate(msg.getDate()));
        attrs.put(MessageMappingConstants.SAVE_DATE,
                dateFormatter.formatDate(msg.getSaveDate()));

        try {
            String itemId = msg.getUuid();
            Map<String,Set<String>> itemAttr = (Map<String,Set<String>>) (Map) attrs.asMap();

            messagesDomainProvider.getDomain().addItem(itemId, itemAttr, null);
        } catch (SDBException e) {
            throw new RuntimeException(e);
        }
    }
}
