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
package org.richfaces.tests.metamer.ftest.a4jPush;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;

/**
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 */
public class TwoPushPage extends MetamerPage {

    @FindBy(css = "span[id$=outputDate]")
    private WebElement output1Element;
    @FindBy(css = "span[id$=outputDate2]")
    private WebElement output2Element;

    @FindBy(css = "input[id$=performPushEvent1]")
    private WebElement push1BtnElement;
    @FindBy(css = "input[id$=performPushEvent2]")
    private WebElement push2BtnElement;
    @FindBy(css = "input[type=checkbox][id$=enablePush1]")
    private WebElement pushEnabledChckBoxElement;

    /**
     * @return the output1Element
     */
    public WebElement getOutput1Element() {
        return output1Element;
    }

    /**
     * @return the output2Element
     */
    public WebElement getOutput2Element() {
        return output2Element;
    }

    /**
     * @return the push1BtnElement
     */
    public WebElement getPush1BtnElement() {
        return push1BtnElement;
    }

    /**
     * @return the push2BtnElement
     */
    public WebElement getPush2BtnElement() {
        return push2BtnElement;
    }

    /**
     * @return the pushEnabledChckBoxElement
     */
    public WebElement getPushEnabledChckBoxElement() {
        return pushEnabledChckBoxElement;
    }
}
