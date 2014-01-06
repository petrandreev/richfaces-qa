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

import static org.testng.Assert.assertEquals;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.dataScroller.RichFacesDataScroller;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestComponentControlWithDataScroller extends AbstractComponentControlTest {

    private static final String[] OPERATIONS = new String[]{ "first", "last", "fastRewind", "fastForward", "next", "previous", "switchToPage" };

    @FindBy(className = "controlledComponent")
    private RichFacesDataScroller dataScroller;

    @Override
    protected String getComponentName() {
        return "dataScroller";
    }

    @Override
    protected String[] getOperations() {
        return OPERATIONS;
    }

    @Override
    protected void handleOperation(String operation) {
        int activePageNumber = dataScroller.getActivePageNumber();
        if (activePageNumber != 3) {
            Graphene.guardAjax(dataScroller).switchTo(3);
            activePageNumber = dataScroller.getActivePageNumber();
        }

        Graphene.guardAjax(getButtonWithOperation(operation)).click();
        if ("first".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), 1);
        } else if ("last".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), 20);
        } else if ("fastRewind".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), threshold(activePageNumber - 2));
        } else if ("fastForward".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), threshold(activePageNumber + 2));
        } else if ("next".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), threshold(activePageNumber + 1));
        } else if ("previous".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), threshold(activePageNumber - 1));
        } else if ("switchToPage".equals(operation)) {
            assertEquals(dataScroller.getActivePageNumber(), 10);
        }
    }

    @Test
    @Templates("plain")
    public void testOnbeforeoperation() {
        super.testOnbeforeoperation();
    }

    @Test
    @Use(field = "operation", value = "operations")
    @IssueTracking({ "https://issues.jboss.org/browse/RFPL-1187", "https://issues.jboss.org/browse/RF-9306" })
    public void testOperation() {
        super.testOperation();
    }

    @Test
    @Templates("plain")
    public void testSelector() {
        super.testSelector();
    }

    @Test
    @Templates("plain")
    public void testTarget() {
        super.testTarget();
    }

    private int threshold(int value) {
        return (value < 1 ? 1 : (value > 20 ? 20 : value));
    }
}
