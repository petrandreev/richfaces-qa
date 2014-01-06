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
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.joda.time.DateTime;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.calendar.RichFacesAdvancedPopupCalendar;
import org.richfaces.fragment.calendar.RichFacesCalendar;
import org.richfaces.fragment.common.Utils;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestComponentControlWithCalendar extends AbstractComponentControlTest {

    private static final String[] OPERATIONS = new String[]{ "showPopup", "hidePopup", "switchPopup", "setValue", "resetValue", "today",
        // "getValue", "getValueAsString", "getCurrentYear", "getCurrentMonth", "showSelectedDate",
        "showDateEditor", "hideDateEditor", "showTimeEditor", "hideTimeEditor" };

    @FindBy(className = "controlledComponent")
    private RichFacesCalendar calendar;
    @FindBy(className = "controlledComponent")
    private RichFacesAdvancedPopupCalendar popupCalendar;

    @Override
    protected String getComponentName() {
        return "calendar";
    }

    @Override
    protected String[] getOperations() {
        return OPERATIONS;
    }

    private void mouseoverButton(WebElement button) {
        Utils.triggerJQ("mouseover", button);
    }

    @Override
    protected void handleOperation(String operation) {
        if ("setValue".equals(operation)) {
            mouseoverButton(getButtonWithOperation(operation));
            calendar.advanced().setupDatePattern("MMM d, yyyy HH:mm");
            DateTime dt = calendar.getDate();
            assertEquals(dt.getYear(), 2012);
            assertEquals(dt.getMonthOfYear(), 10);
            assertEquals(dt.getDayOfMonth(), 10);
            assertEquals(dt.getHourOfDay(), 12);
        } else if ("resetValue".equals(operation)) {
            mouseoverButton(getButtonWithOperation("setValue"));
            mouseoverButton(getButtonWithOperation(operation));
            assertEquals(popupCalendar.getInput().getStringValue(), "");
        } else if ("today".equals(operation)) {
            mouseoverButton(getButtonWithOperation("setValue"));
            mouseoverButton(getButtonWithOperation(operation));
            DateTime today = new DateTime().withHourOfDay(12).withMinuteOfHour(0).withSecondOfMinute(0);
            calendar.advanced().setupDatePattern("MMM d, yyyy HH:mm");
            DateTime dt = calendar.getDate();
            assertEquals(dt.getYear(), today.getYear());
            assertEquals(dt.getMonthOfYear(), today.getMonthOfYear());
            assertEquals(dt.getDayOfMonth(), today.getDayOfMonth());
            assertEquals(dt.getHourOfDay(), today.getHourOfDay());
        } else if ("showPopup".equals(operation)) {
            assertFalse(Utils.isVisible(popupCalendar.getPopup().getRoot()));
            mouseoverButton(getButtonWithOperation(operation));
            assertTrue(Utils.isVisible(popupCalendar.getPopup().getRoot()));
        } else if ("hidePopup".equals(operation)) {
            assertFalse(Utils.isVisible(popupCalendar.getPopup().getRoot()));
            popupCalendar.openPopup();
            assertTrue(Utils.isVisible(popupCalendar.getPopup().getRoot()));
            mouseoverButton(getButtonWithOperation(operation));
            assertFalse(Utils.isVisible(popupCalendar.getPopup().getRoot()));
        } else if ("switchPopup".equals(operation)) {
            assertFalse(Utils.isVisible(popupCalendar.getPopup().getRoot()));
            mouseoverButton(getButtonWithOperation(operation));
            assertTrue(Utils.isVisible(popupCalendar.getPopup().getRoot()));
            mouseoverButton(getButtonWithOperation(operation));
            assertFalse(Utils.isVisible(popupCalendar.getPopup().getRoot()));
        } else if ("showDateEditor".equals(operation)) {
            mouseoverButton(getButtonWithOperation("setValue"));
            mouseoverButton(getButtonWithOperation(operation));
            assertTrue(popupCalendar.getPopup().getHeaderControls().getYearAndMonthEditor().isVisible());
        } else if ("hideDateEditor".equals(operation)) {
            handleOperation("showDateEditor");
            mouseoverButton(getButtonWithOperation(operation));
            assertFalse(popupCalendar.getPopup().getHeaderControls().getYearAndMonthEditor().isVisible());
        } else if ("showTimeEditor".equals(operation)) {
            mouseoverButton(getButtonWithOperation("setValue"));
            mouseoverButton(getButtonWithOperation(operation));
            assertTrue(popupCalendar.getPopup().getFooterControls().getTimeEditor().isVisible());
        } else if ("hideTimeEditor".equals(operation)) {
            handleOperation("showTimeEditor");
            mouseoverButton(getButtonWithOperation(operation));
            assertFalse(popupCalendar.getPopup().getFooterControls().getTimeEditor().isVisible());
        } else {
            throw new UnsupportedOperationException("Not supported operation " + operation);
        }
    }

    @Test
    @Templates("plain")
    public void testOnbeforeoperation() {
        super.testOnbeforeoperation();
    }

    @Test
    @Use(field = "operation", value = "operations")
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
}
