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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Action;
import org.richfaces.tests.metamer.ftest.attributes.AttributeEnum;
import org.richfaces.tests.metamer.ftest.extension.tester.TestChainBuilder;
import org.richfaces.tests.metamer.ftest.extension.tester.Tester;
import org.richfaces.tests.metamer.ftest.webdriver.UnsafeAttributes;

/**
 * Basic test chain for setting up attributes in Metamer, adding action check and triggering action.
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public interface BasicTestChain extends Tester, TestChainBuilder {

    TestResourcesProvider getTestResources();

    ActionsSetup setupActionsAfterTriggering();

    ActionsSetup setupActionsBeforeTriggering();

    AttributeSetup setupAttribute();

    ActionsSetup setupCheckingAction();

    ActionsSetup setupTriggeringAction();

    public interface TestResourcesProvider {

        WebDriver getWebDriver();

        JavascriptExecutor getJSExecutor();

        <T extends AttributeEnum> UnsafeAttributes<T> getAttributes(String attributeTableId);
    }

    public interface Confirmer<T> {

        T done();
    }

    public interface AttributeSetup extends Confirmer<BasicTestChain> {

        AttributeSetup attribute(String name, Object value);

        AttributeSetup attribute(String name, Object value, String attributeTableID);
    }

    public interface ActionsSetup extends Confirmer<BasicTestChain> {

        ActionsSetup addAction(Action a);

        ActionsSetup replaceByAction(Action a);
    }
}
