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
package org.richfaces.tests.metamer.ftest.richFocus;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;

import java.net.URL;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.test.selenium.support.ui.ElementIsFocused;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.autocomplete.RichFacesAutocomplete;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="https://developer.jboss.org/people/ppitonak">Pavol Pitonak</a>
 */
public class TestFocusManagerWithAutocomplete extends AbstractWebDriverTest {

    @FindBy(css = "[id$=buttonAutocomplete]")
    WebElement buttonAutocomplete;

    @FindBy(css = "[id$=buttonInputText]")
    WebElement buttonInputText;

    @FindBy(css = "[id$=inputTextSimple]")
    WebElement inputText;

    @FindBy(css = "[id$=autocomplete]")
    RichFacesAutocomplete autocomplete;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richFocus/rf-13251.xhtml");
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-13251")
    public void testFocusToAutocomplete() {
        Assert.assertFalse(
            new ElementIsFocused(autocomplete.advanced().getInput().advanced().getInputElement()).apply(driver),
            "Autocomplete's input should not be focused");
        Graphene.guardAjax(buttonAutocomplete).click();
        Assert.assertTrue(
            new ElementIsFocused(autocomplete.advanced().getInput().advanced().getInputElement()).apply(driver),
            "Autocomplete's input is not focused");
    }

    @Test
    public void testFocusToTextInput() {
        Assert.assertFalse(new ElementIsFocused(inputText).apply(driver), "Text input should not be focused");
        Graphene.guardAjax(buttonInputText).click();
        Assert.assertTrue(new ElementIsFocused(inputText).apply(driver), "Autocomplete's input is not focused");
    }
}
