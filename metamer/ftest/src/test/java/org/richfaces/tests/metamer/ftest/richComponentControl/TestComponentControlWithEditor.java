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

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.Utils;
import org.richfaces.fragment.editor.RichFacesEditor;
import org.richfaces.tests.metamer.ftest.annotations.Templates;
import org.richfaces.tests.metamer.ftest.annotations.Use;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class TestComponentControlWithEditor extends AbstractComponentControlTest {

    private static final String[] OPERATIONS = new String[]{ "setValue", "focus", "blur",/* "setReadonly" */ };

    @FindBy(className = "controlledComponent")
    private RichFacesEditor editor;

    @Override
    protected String getComponentName() {
        return "editor";
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
            getButtonWithOperation(operation).click();
            assertEquals(editor.getText(), "new value");
        } else if ("focus".equals(operation)) {
            getButtonWithOperation(operation).click();
            assertEquals(expectedReturnJS("return metamerEvents;", operation), operation);
        } else if ("blur".equals(operation)) {
            getButtonWithOperation("focus").click();// have to focus before blur
            executeJS("metamerEvents = '';");
            mouseoverButton(getButtonWithOperation(operation));
            assertEquals(expectedReturnJS("return metamerEvents;", operation), operation);
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
