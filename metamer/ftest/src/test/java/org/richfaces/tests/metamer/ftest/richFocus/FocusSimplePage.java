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

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.common.TextInputComponentImpl;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 */
public class FocusSimplePage extends AbstractFocusPage {

    @FindBy(css = "input[name$='name']")
    private TextInputComponentImpl nameInput;

    @FindBy(css = "input[name$='age']")
    private TextInputComponentImpl ageInput;

    @FindBy(css = "input[name$='address']")
    private TextInputComponentImpl addressInput;

    @FindBy(css = "input[id$='validateButton']")
    private WebElement ajaxValidateButton;

    @FindBy(css = "input[id$='preserveAttribute']")
    private WebElement preserveAttributeCheckBox;

    public void ajaxValidateInputs() {
        guardAjax(ajaxValidateButton).click();
    }

    public TextInputComponentImpl getNameInput() {
        return nameInput;
    }

    public void setNameInput(TextInputComponentImpl nameInput) {
        this.nameInput = nameInput;
    }

    public TextInputComponentImpl getAgeInput() {
        return ageInput;
    }

    public void setAgeInput(TextInputComponentImpl ageInput) {
        this.ageInput = ageInput;
    }

    public TextInputComponentImpl getAddressInput() {
        return addressInput;
    }

    public void setAddressInput(TextInputComponentImpl addressInput) {
        this.addressInput = addressInput;
    }

    public WebElement getPreserveAttributeCheckBox() {
        return preserveAttributeCheckBox;
    }

    public void setPreserveAttributeCheckBox(WebElement preserveAttributeCheckBox) {
        this.preserveAttributeCheckBox = preserveAttributeCheckBox;
    }
}
