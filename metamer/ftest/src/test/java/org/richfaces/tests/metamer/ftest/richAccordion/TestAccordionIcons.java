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
package org.richfaces.tests.metamer.ftest.richAccordion;

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;
import static org.testng.Assert.assertFalse;

import java.net.URL;

import org.jboss.arquillian.graphene.page.Page;
import org.richfaces.fragment.common.Icon;
import org.richfaces.tests.metamer.ftest.AbstractWebDriverTest;
import org.richfaces.tests.metamer.ftest.annotations.IssueTracking;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.checker.IconsChecker;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.testng.annotations.Test;

/**
 * Test case for page /faces/components/richAccordion/simple.xhtml
 *
 * @author <a href="https://community.jboss.org/people/ppitonak">Pavol Pitonak</a>
 * @author <a href="mailto:jpapouse@redhat.com">Jan Papousek</a>
 */
public class TestAccordionIcons extends AbstractWebDriverTest {

    private final Attributes<AccordionAttributes> accordionAttributes = getAttributes();

    @Page
    private AccordionPage page;

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/richAccordion/simple.xhtml");
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-10352")
    @Templates(value = "plain")
    public void testItemActiveLeftIcon() {
        assertFalse(page.getLeftActiveIcon().getIconElement().isPresent(), "Left icon of active item should not be present on the page.");
        verifyStandardIcons(AccordionAttributes.itemActiveLeftIcon, page.getLeftActiveIcon(), "");
    }

    @Test
    @Templates(value = "plain")
    public void testItemActiveRightIcon() {
        assertFalse(page.getRightActiveIcon().getIconElement().isPresent(), "Right icon of active item should not be present on the page.");
        verifyStandardIcons(AccordionAttributes.itemActiveRightIcon, page.getRightActiveIcon(), "");
    }

    @Test
    @Templates(value = "plain")
    public void testItemDisabledLeftIcon() {
        verifyStandardIcons(AccordionAttributes.itemDisabledLeftIcon, page.getLeftDisabledIcon(), "-dis");
    }

    @Test
    @Templates(value = "plain")
    public void testItemDisabledRightIcon() {
        verifyStandardIcons(AccordionAttributes.itemDisabledRightIcon, page.getRightDisabledIcon(), "-dis");
    }

    @Test
    @IssueTracking("https://issues.jboss.org/browse/RF-10352")
    @Templates(value = "plain")
    public void testItemInactiveLeftIcon() {
        verifyStandardIcons(AccordionAttributes.itemInactiveLeftIcon, page.getLeftInactiveIcon(), "");
    }

    @Test
    @Templates(value = "plain")
    public void testItemInactiveRightIcon() {
        verifyStandardIcons(AccordionAttributes.itemInactiveRightIcon, page.getRightInactiveIcon(), "");
    }

    private void verifyStandardIcons(AccordionAttributes attribute, Icon icon, String classSuffix) {
        new IconsChecker<AccordionAttributes>(accordionAttributes, "rf-ico-", "-hdr").checkAll(attribute, icon, classSuffix);
    }
}
