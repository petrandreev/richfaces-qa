/*******************************************************************************
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2013, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.richComponentControl;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.testng.Assert.fail;

import java.net.URL;
import java.util.ArrayList;

import org.jboss.arquillian.graphene.request.RequestGuardException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.Inject;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;

import com.google.common.collect.Lists;

/**
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public abstract class AbstractComponentControlTest extends AbstractWebDriverTest {

    private static final String BAD_VALUE = "X";
    private static final String GOOD_SELECTOR = "[id$=controlledComponent]";

    protected final Attributes<ComponentControlAttributes> attributes = getAttributes();
    @SuppressWarnings("unchecked")
    protected final ArrayList<Class<? extends Throwable>> ignoredExceptions = Lists.newArrayList(RequestGuardException.class, AssertionError.class);

    protected String[] operations = getOperations();
    @Inject
    @Use(useNull = false)
    protected String operation;

    protected WebElement getButtonWithOperation(String operationName) {
        return driver.findElement(By.cssSelector("[id$=':" + operationName + "']"));
    }

    protected abstract String getComponentName();

    protected abstract String[] getOperations();

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richComponentControl/" + getComponentName() + ".xhtml");
    }

    protected abstract void handleOperation(String operation);

    public void testOnbeforeoperation() {
        testFireEvent("onbeforeoperation", new Action() {

            @Override
            public void perform() {
                handleOperation(operations[0]);
            }
        });
    }

//  @Use(field = "operation", value = "operations")
    public void testOperation() {
        handleOperation(operation);
    }

    public void testSelector() {
        // put in bad value >>> the component control will not work
        attributes.set(ComponentControlAttributes.selector, BAD_VALUE);
        attributes.reset(ComponentControlAttributes.target);
        try {
            handleOperation(operations[0]);
        } catch (Throwable ex) {
            int count = 0;
            for (Class<? extends Throwable> ignored : ignoredExceptions) {
                if (ex.getClass().equals(ignored)) {
                    break;
                }
                count++;
            }
            if (ignoredExceptions.size() - count <= 0) {
                fail("Unexpected exception " + ex);
            }
        }

        attributes.set(ComponentControlAttributes.selector, GOOD_SELECTOR);
        handleOperation(operations[0]);
    }

    public void testTarget() {
        // put in bad value >>> the component control will not work
        attributes.set(ComponentControlAttributes.target, BAD_VALUE);
        attributes.reset(ComponentControlAttributes.selector);
        try {
            handleOperation(operations[0]);
        } catch (Throwable ex) {
            int count = 0;
            for (Class<? extends Throwable> ignored : ignoredExceptions) {
                if (ex.getClass().equals(ignored)) {
                    break;
                }
                count++;
            }
            if (ignoredExceptions.size() - count <= 0) {
                fail("Unexpected exception " + ex);
            }
        }
    }
}
