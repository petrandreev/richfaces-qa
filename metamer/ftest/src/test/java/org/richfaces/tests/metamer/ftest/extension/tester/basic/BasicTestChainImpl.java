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
package org.richfaces.tests.metamer.ftest.extension.tester.basic;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.interactions.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class BasicTestChainImpl implements BasicTestChain {

    private static final Logger LOG = LoggerFactory.getLogger("BasicTestChain");

    private final List<Action> attributeActions = Lists.newArrayList();
    private final List<Action> beforeActions = Lists.newArrayList();
    private final List<Action> triggerAction = Lists.newArrayList();
    private final List<Action> checkActions = Lists.newArrayList();
    private final List<Action> afterActions = Lists.newArrayList();
    private final TestResourcesProvider provider;

    public BasicTestChainImpl(TestResourcesProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<Action> build() {
        ArrayList<Action> result = Lists.newArrayList();
        result.addAll(getAttributeActions());
        result.addAll(getBeforeActions());
        result.addAll(getTriggerAction());
        result.addAll(getCheckActions());
        result.addAll(getAfterActions());
        return result;
    }

    protected List<Action> getAfterActions() {
        return afterActions;
    }

    protected List<Action> getAttributeActions() {
        return attributeActions;
    }

    protected List<Action> getBeforeActions() {
        return beforeActions;
    }

    protected List<Action> getCheckActions() {
        return checkActions;
    }

    @Override
    public TestResourcesProvider getTestResources() {
        return provider;
    }

    protected List<Action> getTriggerAction() {
        return triggerAction;
    }

    @Override
    public ActionsSetup setupActionsAfterTriggering() {
        return new ActionSetupImpl(afterActions);
    }

    @Override
    public ActionsSetup setupActionsBeforeTriggering() {
        return new ActionSetupImpl(beforeActions);
    }

    @Override
    public AttributeSetup setupAttribute() {
        return new AttributeSetupImpl();
    }

    @Override
    public ActionsSetup setupCheckingAction() {
        return new ActionSetupImpl(checkActions);
    }

    @Override
    public ActionsSetup setupTriggeringAction() {
        return new ActionSetupImpl(triggerAction);
    }

    @Override
    public void test() {
        for (Action build : build()) {
            build.perform();
        }
    }

    private class ActionSetupImpl implements ActionsSetup {

        private final List<Action> actions;

        public ActionSetupImpl(List<Action> actions) {
            this.actions = actions;
        }

        @Override
        public ActionsSetup addAction(Action a) {
            actions.add(a);
            return this;
        }

        @Override
        public ActionsSetup replaceByAction(Action a) {
            actions.clear();
            actions.add(a);
            return this;
        }

        @Override
        public BasicTestChain done() {
            return BasicTestChainImpl.this;
        }
    }

    private class AttributeSetupImpl implements AttributeSetup {

        private class AttributeSettingAction implements Action {

            private final String attributeTableId;
            private final String name;
            private final Object value;

            public AttributeSettingAction(String name, Object value, String attributeTableId) {
                this.name = name;
                this.value = value;
                this.attributeTableId = attributeTableId;
            }

            @Override
            public void perform() {
                LOG.debug(MessageFormat.format("Setting attribute <{0}> to <{1}>", name, value));
                getTestResources().getAttributes(attributeTableId).set(name, value);
            }
        }

        @Override
        public AttributeSetup attribute(String name, Object value, String attributeTableID) {
            getAttributeActions().add(new AttributeSettingAction(name, value, attributeTableID));
            return this;
        }

        @Override
        public AttributeSetup attribute(String name, Object value) {
            return attribute(name, value, "");
        }

        @Override
        public BasicTestChain done() {
            return BasicTestChainImpl.this;
        }
    }
}
