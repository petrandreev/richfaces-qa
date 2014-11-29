/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2014, Red Hat, Inc. and individual contributors
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
 *******************************************************************************/
package org.richfaces.tests.metamer.ftest.extension.tester.events;

import static org.testng.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.List;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.richfaces.fragment.common.Actions;
import org.richfaces.fragment.common.Event;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest.FutureTarget;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest.FutureWebElement;
import org.richfaces.tests.metamer.ftest.extension.tester.basic.BasicTestChainImpl;
import org.richfaces.tests.metamer.ftest.extension.tester.events.TriggerSingleEventTestChain.EventTesterBasicConfig;
import org.richfaces.tests.metamer.ftest.extension.tester.events.TriggerSingleEventTestChain.EventTesterOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TriggerSingleEventTestChainImpl extends BasicTestChainImpl implements TriggerSingleEventTestChain, EventTesterOn, EventTesterBasicConfig {

    private static final String CLEAN_METAMER_EVENTS = "metamerEvents = \"\";";
    private static final Logger LOG = LoggerFactory.getLogger("TriggerSingleEventTestChain");
    private static final String[] POSSIBLE_EVENT_PREFIXES = { "header", "input", "row", "list", "item", "down", "up", "source",
        "target", "mask" };
    private static final String PREFIX_ON = "on";
    private static final String RETURN_METAMER_EVENTS = "return metamerEvents;";
    private static final String TESTED_ATTRIBUTE_VALUE_TEMPLATE = "metamerEvents += \"{0} \";";
    private static final String TEST_METHOD_PREFIX = "teston";

    private String attributeName;

    private final Action checkAction = new Action() {
        private final ObjectWrapper<String> wrappedString = new ObjectWrapper<String>();

        @Override
        public void perform() {
            try {
                Graphene.waitAjax().until(new Predicate<WebDriver>() {
                    Object executedScriptResult;

                    @Override
                    public boolean apply(WebDriver t) {
                        executedScriptResult = getTestResources().getJSExecutor().executeScript(RETURN_METAMER_EVENTS);
                        if (executedScriptResult != null) {
                            wrappedString.setObject(((String) executedScriptResult).trim());
                            LOG.debug(MessageFormat.format("This was triggered: <{0}>.", wrappedString.getObject()));
                            return wrappedString.getObject().equals(attributeName);
                        }
                        return Boolean.FALSE;
                    }
                });
            } catch (TimeoutException e) {
                assertEquals(wrappedString.getObject(), attributeName, MessageFormat.format("Event {0} does not work.", event));
            }
        }
    };

    private final Action cleanEventsAction = new Action() {

        @Override
        public void perform() {
            getTestResources().getJSExecutor().executeScript(CLEAN_METAMER_EVENTS);
        }
    };
    private Event event;
    private FutureTarget<WebElement> onElement;
    private boolean testByWDOtherwiseJS = Boolean.TRUE;
    private final Action triggeringJSAction = new Action() {

        @Override
        public void perform() {
            new Actions(getTestResources().getWebDriver()).triggerEventByJS(event, onElement.getTarget()).perform();
        }
    };
    private final Action triggeringWDAction = new Action() {

        @Override
        public void perform() {
            new Actions(getTestResources().getWebDriver()).triggerEventByWD(event, onElement.getTarget()).perform();
            if (event.getEventName().contains("mousedown")) {
                new Actions(getTestResources().getWebDriver()).triggerEventByWD(Event.MOUSEUP, onElement.getTarget()).perform();
            }
        }
    };

    public TriggerSingleEventTestChainImpl(TestResourcesProvider provider) {
        super(provider);
    }

    @Override
    public List<Action> build() {
        return testByWDOtherwiseJS ? createStepsForWDOtherwiseJSTriggering() : super.build();
    }

    private List<Action> createStepsForWDOtherwiseJSTriggering() {
        // when testing by WD_OTHERWISE_JS, first test with WD, then, only if failed, test with JS

        // we need to create custom list of actions
        // build actions with triggering the event with WD
        setupTriggeringAction().replaceByAction(triggeringWDAction);
        final List<Action> buildWithWDTriggering = super.build();
        // build actions with triggering the event with JS
        setupTriggeringAction().replaceByAction(triggeringJSAction);
        final List<Action> buildWithJSTriggering = super.build();
        List<Action> result = Lists.newArrayList();
        result.add(new Action() {
            @Override
            public void perform() {
                // try the triggering with WD
                try {
                    for (Action a : buildWithWDTriggering) {
                        a.perform();
                    }
                    return;
                } catch (Throwable e) {
                    LOG.info("Triggering of the event with WD was not succesfull, trying to trigger the event with JS. Failed "
                        + "with exception: " + e.getMessage());
                }
                // try the triggering with JS
                for (Action a : buildWithJSTriggering) {
                    a.perform();
                }
            }
        });
        return result;
    }

    private String getEventNameFromStackTrace() {
        String methodName;
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();// faster than Threat.currentThread().getStackTrace()
        for (StackTraceElement ste : stackTrace) {
            methodName = ste.getMethodName();
            if (methodName.toLowerCase().startsWith(TEST_METHOD_PREFIX)) {
                return methodName.substring(TEST_METHOD_PREFIX.length());
            }
        }
        throw new RuntimeException("Was not able to obtain event name");
    }

    @Override
    public EventTesterBasicConfig onElement(FutureTarget<WebElement> visibleAfterSomeActionElement) {
        onElement = visibleAfterSomeActionElement;
        return this;
    }

    @Override
    public EventTesterBasicConfig onElement(WebElement visibleElement) {
        onElement = FutureWebElement.of(visibleElement);
        return this;
    }

    @Override
    public EventTesterBasicConfig onElement(final SearchContext context, final By by) {
        onElement = new FutureTarget<WebElement>() {

            @Override
            public WebElement getTarget() {
                return context.findElement(by);
            }
        };
        return this;
    }

    private Event parseEventFromString(String s) {
        String eventName = s.toLowerCase();
        if (eventName.startsWith(PREFIX_ON)) {
            eventName = eventName.substring(PREFIX_ON.length());
        }
        for (String prefix : POSSIBLE_EVENT_PREFIXES) {
            if (eventName.startsWith(prefix)) {
                return new Event(eventName.substring(prefix.length()));
            }
        }
        return new Event(eventName);
    }

    @Override
    public EventTesterOn testEvent(String eventName) {
        String attName = eventName.toLowerCase();
        if (!attName.startsWith(PREFIX_ON)) {
            attName = MessageFormat.format("{0}{1}", PREFIX_ON, attName);
        }
        return testEvent(parseEventFromString(eventName), attName);
    }

    @Override
    public EventTesterOn testEvent(Event e) {
        return testEvent(e, MessageFormat.format("{0}{1}", PREFIX_ON, e.getEventName()));
    }

    @Override
    public EventTesterOn testEvent(Event e, String attributeName) {
        this.event = e;
        this.attributeName = attributeName;
        setupAttribute().attribute(attributeName, MessageFormat.format(TESTED_ATTRIBUTE_VALUE_TEMPLATE, attributeName));
        setupActionsBeforeTriggering().addAction(cleanEventsAction);
        setupCheckingAction().addAction(checkAction);
        return this;
    }

    @Override
    public EventTesterOn testEventObtainedFromTestMethodName() {
        return testEvent(getEventNameFromStackTrace());
    }

    @Override
    public TriggerSingleEventTestChain triggerEventByJS() {
        this.testByWDOtherwiseJS = Boolean.FALSE;
        setupTriggeringAction().replaceByAction(triggeringJSAction);
        return this;
    }

    @Override
    public TriggerSingleEventTestChain triggerEventByWD() {
        this.testByWDOtherwiseJS = Boolean.FALSE;
        setupTriggeringAction().replaceByAction(triggeringWDAction);
        return this;
    }

    @Override
    public TriggerSingleEventTestChain triggerEventWithWDIfFailedThenJS() {
        this.testByWDOtherwiseJS = Boolean.TRUE;
        return this;
    }

    @Override
    public TriggerSingleEventTestChain withCustomAction(Action customAction) {
        this.testByWDOtherwiseJS = Boolean.FALSE;
        setupTriggeringAction().replaceByAction(customAction);
        return this;
    }

    public static class ObjectWrapper<T> {

        private T object;

        public T getObject() {
            return object;
        }

        public ObjectWrapper<T> setObject(T object) {
            this.object = object;
            return this;
        }
    }
}
