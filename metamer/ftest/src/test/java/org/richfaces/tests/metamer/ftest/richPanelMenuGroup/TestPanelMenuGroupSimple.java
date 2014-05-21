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
package org.richfaces.tests.metamer.ftest.richPanelMenuGroup;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.condition.element.WebElementConditionFactory;
import org.openqa.selenium.interactions.Action;
import org.richfaces.fragment.common.Icon;
import org.richfaces.tests.metamer.ftest.BasicAttributes;
import org.richfaces.tests.metamer.ftest.annotations.RegressionTest;
import org.richfaces.tests.metamer.ftest.checker.IconsChecker;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @since 4.3.1
 */
public class TestPanelMenuGroupSimple extends AbstractPanelMenuGroupTest {

    private final Attributes<PanelMenuGroupAttributes> panelMenuGroupAttributes = getAttributes();
    private final IconsChecker<PanelMenuGroupAttributes> iconsChecker = new IconsChecker<PanelMenuGroupAttributes>(panelMenuGroupAttributes, "rf-ico-", "");
    final Action collapseFirstGroupAction = new Action() {
        @Override
        public void perform() {
            Graphene.guardAjax(page.getMenu()).collapseGroup(1);
        }
    };

    @Test
    public void testData() {
        testData(collapseFirstGroupAction);
    }

    @Test
    public void testDisabled() {
        assertFalse(page.getTopGroup().advanced().isDisabled());

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);

        assertFalse(page.getTopGroup().advanced().isSelected());
        assertTrue(page.getTopGroup().advanced().isDisabled());

        Graphene.guardNoRequest(page.getTopGroup().advanced().getHeaderElement()).click();

        assertFalse(page.getTopGroup().advanced().isSelected());
        assertTrue(page.getTopGroup().advanced().isDisabled());
    }

    @Test
    @Templates(value = "plain")
    public void testDisabledClass() {
        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);
        testStyleClass(page.getTopGroup().advanced().getRootElement(), BasicAttributes.disabledClass);
    }

    @Test
    @Templates(value = "plain")
    public void testLeftDisabledIcon() {
        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);

        verifyStandardIcons(PanelMenuGroupAttributes.leftDisabledIcon, page.getTopGroup().advanced().getLeftIcon(), "");
    }

    @Test
    @Templates(value = "plain")
    public void testLeftCollapsedIcon() {
        Graphene.guardAjax(page.getMenu()).collapseGroup(1);

        verifyStandardIcons(PanelMenuGroupAttributes.leftCollapsedIcon, page.getTopGroup().advanced().getLeftIcon(), "");

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);
        // both icon should be "transparent" - invisible
        assertTrue(page.getTopGroup().advanced().isTransparent(page.getTopGroup().advanced().getLeftIcon().getIconElement()));
    }

    @Test
    @Templates(value = "plain")
    public void testLeftExpandedIcon() {

        verifyStandardIcons(PanelMenuGroupAttributes.leftExpandedIcon, page.getTopGroup().advanced().getLeftIcon(), "");

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);
        assertTrue(page.getTopGroup().advanced().isTransparent(page.getTopGroup().advanced().getRightIcon().getIconElement()));
    }

    @Test
    public void testLimitRender() {
        testLimitRender(collapseFirstGroupAction);
    }

    @Test
    @Templates(value = "plain")
    public void testRendered() {
        assertTrue(new WebElementConditionFactory(page.getTopGroup().advanced().getRootElement()).isVisible().apply(driver));

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.rendered, false);

        assertFalse(new WebElementConditionFactory(page.getTopGroup().advanced().getRootElement()).isVisible().apply(driver));
    }

    @Test
    @Templates(value = "plain")
    public void testRightDisabledIcon() {
        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);

        verifyStandardIcons(PanelMenuGroupAttributes.rightDisabledIcon, page.getTopGroup().advanced().getRightIcon(), "");
    }

    @Test
    @Templates(value = "plain")
    public void testRightExpandedIcon() {
        verifyStandardIcons(PanelMenuGroupAttributes.rightExpandedIcon, page.getTopGroup().advanced().getRightIcon(), "");

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);
        assertTrue(page.getTopGroup().advanced().isTransparent(page.getTopGroup().advanced().getRightIcon().getIconElement()));
    }

    @Test
    @Templates(value = "plain")
    public void testRightCollapsedIcon() {
        Graphene.guardAjax(page.getMenu()).collapseGroup(1);

        verifyStandardIcons(PanelMenuGroupAttributes.rightCollapsedIcon, page.getTopGroup().advanced().getRightIcon(), "");

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.disabled, true);
        assertTrue(page.getTopGroup().advanced().isTransparent(page.getTopGroup().advanced().getRightIcon().getIconElement()));
    }

    @Test
    public void testSelectable() {
        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.selectable, false);
        guardAjax(page.getMenu()).collapseGroup(1);
        assertFalse(page.getTopGroup().advanced().isSelected());

        panelMenuGroupAttributes.set(PanelMenuGroupAttributes.selectable, true);
        guardAjax(page.getMenu()).expandGroup(1);
        assertTrue(page.getTopGroup().advanced().isSelected());
    }

    @Test
    public void testStatus() {
        testStatus(collapseFirstGroupAction);
    }

    @Test
    @Templates(value = "plain")
    public void testStyle() {
        testStyle(page.getTopGroup().advanced().getRootElement());
    }

    @Test
    @RegressionTest("https://issues.jboss.org/browse/RF-10485")
    @Templates(value = "plain")
    public void testStyleClass() {
        testStyleClass(page.getTopGroup().advanced().getRootElement());
    }

    private void verifyStandardIcons(PanelMenuGroupAttributes attribute, Icon icon, String classSuffix) {
        iconsChecker.checkAll(attribute, icon, classSuffix);
    }
}
