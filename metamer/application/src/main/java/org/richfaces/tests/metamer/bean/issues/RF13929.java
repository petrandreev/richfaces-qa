/**
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2015, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.richfaces.tests.metamer.bean.issues;

import java.io.Serializable;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;

import org.richfaces.application.push.MessageException;
import org.richfaces.application.push.TopicKey;
import org.richfaces.application.push.TopicsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
@Named("rf13929")
@SessionScoped
public class RF13929 implements Serializable {

    private static final String JMS_ADDRESS = "jmsSampleAddress1";
    private static final String JMS_ADDRESS_JNDI = "topic/jmsSampleAddress1";
    private static final Logger LOGGER = LoggerFactory.getLogger(RF13929.class);
    private static final String PUSH_TOPICS_CONTEXT_ADDRESS_1 = "topic1@tcSampleAddress1";
    private static final String PUSH_TOPICS_CONTEXT_ADDRESS_2 = "topic2@tcSampleAddress1";
    private static final String PUSH_TOPICS_CONTEXT_ADDRESS_3 = "tcSampleAddress1";
    private static final String SUBTOPIC1_ADDRESS = "subtopic1";
    private static final String SUBTOPIC2_ADDRESS = "subtopic2";
    private static final long serialVersionUID = 1L;
    @Inject
    private transient JMSContext jmsContext;
    @Resource(mappedName = JMS_ADDRESS_JNDI)
    private Topic jmsTopic;

    private transient TopicsContext topicsContext;

    private int update1;
    private int update2;
    private int update3;

    public String getJMSAddress1() {
        return MessageFormat.format("{0}@{1}", SUBTOPIC1_ADDRESS, JMS_ADDRESS);
    }

    public String getJMSAddress2() {
        return MessageFormat.format("{0}@{1}", SUBTOPIC2_ADDRESS, JMS_ADDRESS);
    }

    public String getJMSAddress3() {
        return JMS_ADDRESS;
    }

    private TopicsContext getTopicsContext() {
        if (topicsContext == null) {
            topicsContext = TopicsContext.lookup();
        }
        return topicsContext;
    }

    public String getTopicsContextAddress1() {
        return PUSH_TOPICS_CONTEXT_ADDRESS_1;

    }

    public String getTopicsContextAddress2() {
        return PUSH_TOPICS_CONTEXT_ADDRESS_2;
    }

    public String getTopicsContextAddress3() {
        return PUSH_TOPICS_CONTEXT_ADDRESS_3;
    }

    @PostConstruct
    public void init() {
        update1 = 0;
        update2 = 0;
        update3 = 0;
    }

    public void pushWithJMS1() throws JMSException {
        ObjectMessage message = jmsContext.createObjectMessage(MessageFormat.format("data #{0}", ++update1));
        // subtopic workaround https://developer.jboss.org/wiki/CreatingSubtopicWithJMSPush
        message.setStringProperty("rf_push_subtopic", SUBTOPIC1_ADDRESS);
        jmsContext.createProducer().send(jmsTopic, message);
        LOGGER.debug("jms push event 1");
    }

    public void pushWithJMS2() throws JMSException {
        ObjectMessage message = jmsContext.createObjectMessage(MessageFormat.format("data #{0}", ++update2));
        // subtopic workaround https://developer.jboss.org/wiki/CreatingSubtopicWithJMSPush
        message.setStringProperty("rf_push_subtopic", SUBTOPIC2_ADDRESS);
        jmsContext.createProducer().send(jmsTopic, message);
        LOGGER.debug("jms push event 2");
    }

    public void pushWithJMS3() throws JMSException {
        ObjectMessage message = jmsContext.createObjectMessage(MessageFormat.format("data #{0}", ++update3));
        // no subtopic
        jmsContext.createProducer().send(jmsTopic, message);
        LOGGER.debug("jms push event 3");
    }

    public void pushWithTopicsContext1() throws MessageException {
        TopicKey topicKey = new TopicKey(getTopicsContextAddress1());
        getTopicsContext().publish(topicKey, MessageFormat.format("data #{0}", ++update1));
        LOGGER.debug("push event 1");
    }

    public void pushWithTopicsContext2() throws MessageException {
        TopicKey topicKey = new TopicKey(getTopicsContextAddress2());
        getTopicsContext().publish(topicKey, MessageFormat.format("data #{0}", ++update2));
        LOGGER.debug("push event 2");
    }

    public void pushWithTopicsContext3() throws MessageException {
        TopicKey topicKey = new TopicKey(getTopicsContextAddress3());
        getTopicsContext().publish(topicKey, MessageFormat.format("data #{0}", ++update3));
        LOGGER.debug("push event 3");
    }
}
