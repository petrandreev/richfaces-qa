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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.Utils;
import org.richfaces.fragment.common.picker.ChoicePickerHelper;
import org.richfaces.fragment.orderingList.RichFacesOrderingList;
import org.richfaces.fragment.orderingList.SelectableListItem;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestComponentControlWithOrderingList extends AbstractComponentControlTest {

    private static final String[] OPERATIONS = new String[]{ "moveUp", "moveDown", "moveFirst", "moveLast", "selectItem", "selectAll", "unselectAll", "unSelectItem" /* "getSelected" */ };

    @FindBy(className = "controlledComponent")
    private RichFacesOrderingList list;

    @Override
    protected String getComponentName() {
        return "orderingList";
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
        if ("moveUp".equals(operation)) {
            list.select(1);
            String itemText = list.advanced().getList().getItem(1).getText();
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getList().getItem(0).getText(), itemText);
        } else if ("moveDown".equals(operation)) {
            list.select(1);
            String itemText = list.advanced().getList().getItem(1).getText();
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getList().getItem(2).getText(), itemText);
        } else if ("moveFirst".equals(operation)) {
            list.select(1);
            String itemText = list.advanced().getList().getItem(1).getText();
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getList().getItem(0).getText(), itemText);
        } else if ("moveLast".equals(operation)) {
            list.select(1);
            String itemText = list.advanced().getList().getItem(1).getText();
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getList().getItem(ChoicePickerHelper.byIndex().last()).getText(), itemText);
        } else if ("selectItem".equals(operation)) {
            SelectableListItem item = list.advanced().getList().getItem(0);
            assertFalse(item.isSelected());
            getButtonWithOperation(operation).click();
            assertTrue(item.isSelected());
        } else if ("selectAll".equals(operation)) {
            assertEquals(list.advanced().getSelectedItemsElements().size(), 0);
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getSelectedItemsElements().size(), 50);
        } else if ("unselectAll".equals(operation)) {
            handleOperation("selectAll");
            getButtonWithOperation(operation).click();
            assertEquals(list.advanced().getSelectedItemsElements().size(), 0);
        } else if ("unSelectItem".equals(operation)) {
            handleOperation("selectItem");
            SelectableListItem item = list.advanced().getList().getItem(0);
            getButtonWithOperation(operation).click();
            assertFalse(item.isSelected());
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
