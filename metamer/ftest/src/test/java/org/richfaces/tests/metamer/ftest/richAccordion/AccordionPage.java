/**
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
 */
package org.richfaces.tests.metamer.ftest.richAccordion;

import org.jboss.arquillian.graphene.condition.element.WebElementConditionFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.richfaces.fragment.accordion.RichFacesAccordion;
import org.richfaces.fragment.common.Icon;
import org.richfaces.tests.metamer.ftest.webdriver.MetamerPage;

/**
 * @author <a href="https://community.jboss.org/people/ppitonak">Pavol Pitonak</a>
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 * @since 4.3.0.M2
 */
public class AccordionPage extends MetamerPage {

    @FindBy(css = "div[id$=accordion]")
    private RichFacesAccordion accordion;

    @FindBy(css = "div[id$=item1] td.rf-ac-itm-ico")
    private Icon leftActiveIcon;
    @FindBy(css = "div[id$=item1] td.rf-ac-itm-exp-ico")
    private Icon rightActiveIcon;
    @FindBy(css = "div[id$=item4] td.rf-ac-itm-ico")
    private Icon leftDisabledIcon;
    @FindBy(css = "div[id$=item4] td.rf-ac-itm-exp-ico ")
    private Icon rightDisabledIcon;
    @FindBy(css = "div[id$=item3] td.rf-ac-itm-ico")
    private Icon leftInactiveIcon;
    @FindBy(css = "div[id$=item3] td.rf-ac-itm-exp-ico")
    private Icon rightInactiveIcon;

    public RichFacesAccordion getAccordion() {
        return accordion;
    }

    public WebElement getAccordionRootElement() {
        return accordion.advanced().getRootElement();
    }

    public Icon getLeftActiveIcon() {
        return leftActiveIcon;
    }

    public Icon getRightActiveIcon() {
        return rightActiveIcon;
    }

    public Icon getLeftDisabledIcon() {
        return leftDisabledIcon;
    }

    public Icon getRightDisabledIcon() {
        return rightDisabledIcon;
    }

    public Icon getLeftInactiveIcon() {
        return leftInactiveIcon;
    }

    public Icon getRightInactiveIcon() {
        return rightInactiveIcon;
    }

    public boolean isAccordionVisible() {
        return new WebElementConditionFactory(accordion.advanced().getRootElement()).isPresent().apply(driver)
            && accordion.advanced().getRootElement().isDisplayed();
    }
}
