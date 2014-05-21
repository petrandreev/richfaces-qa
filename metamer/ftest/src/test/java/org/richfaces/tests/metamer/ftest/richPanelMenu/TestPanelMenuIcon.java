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
package org.richfaces.tests.metamer.ftest.richPanelMenu;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.richfaces.fragment.common.Icon;
import org.richfaces.tests.metamer.ftest.checker.IconsChecker;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.annotation.Templates;
import org.richfaces.tests.metamer.ftest.webdriver.Attributes;
import org.richfaces.ui.common.Mode;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:lfryc@redhat.com">Lukas Fryc</a>
 * @author <a href="mailto:jjamrich@redhat.com">Jan Jamrich</a>
 * @since 4.3.1.Final
 */
public class TestPanelMenuIcon extends AbstractPanelMenuTest {

    private final Attributes<PanelMenuAttributes> panelMenuAttributes = getAttributes();
    private final IconsChecker<PanelMenuAttributes> iconsChecker = new IconsChecker<PanelMenuAttributes>(panelMenuAttributes, "rf-ico-", "");

    @BeforeMethod(alwaysRun = true)
    public void initializePage() {
        // to keep group 2 expanded after setting of the panelMenu attribute (HTTP request), so the icons are present
        // WARNING it depends on the right methods call order, super.BeforeMethods should be called firstly
        panelMenuAttributes.set(PanelMenuAttributes.groupMode, Mode.ajax);
    }

    @Test
    @Templates("plain")
    public void testGroupCollapsedLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.groupCollapsedLeftIcon, page.getGroup24().advanced().getLeftIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testGroupCollapsedRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.groupCollapsedRightIcon, page.getGroup24().advanced().getRightIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testGroupDisabledLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.groupDisabledLeftIcon, page.getGroup26().advanced().getLeftIcon(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testGroupDisabledRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.groupDisabledRightIcon, page.getGroup26().advanced().getRightIcon(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testGroupExpandedLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        guardAjax(page.getPanelMenu()).expandGroup("Group 2.4");
        verifyStandardIcons(PanelMenuAttributes.groupExpandedLeftIcon, page.getGroup24().advanced().getLeftIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testGroupExpandedRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        guardAjax(page.getPanelMenu()).expandGroup("Group 2.4");
        verifyStandardIcons(PanelMenuAttributes.groupExpandedRightIcon, page.getGroup24().advanced().getRightIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testItemDisabledLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.itemDisabledLeftIcon, page.getItem25().advanced().getLeftIconElement(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testItemDisabledRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.itemDisabledRightIcon, page.getItem25().advanced().getRightIconElement(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testItemLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.itemLeftIcon, page.getItem22().advanced().getLeftIconElement(), "");
    }

    @Test
    @Templates("plain")
    public void testItemRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 2");
        verifyStandardIcons(PanelMenuAttributes.itemRightIcon, page.getItem22().advanced().getRightIconElement(), "");
    }

    @Test
    @Templates("plain")
    public void testTopGroupCollapsedLeftIcon() {
        verifyStandardIcons(PanelMenuAttributes.topGroupCollapsedLeftIcon, page.getGroup1().advanced().getLeftIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testTopGroupCollapsedRightIcon() {
        verifyStandardIcons(PanelMenuAttributes.topGroupCollapsedRightIcon, page.getGroup1().advanced().getRightIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testTopGroupDisabledLeftIcon() {
        verifyStandardIcons(PanelMenuAttributes.topGroupDisabledLeftIcon, page.getGroup4().advanced().getLeftIcon(), "-hdr-dis");
    }

    @Test
    @Templates("plain")
    public void testTopGroupDisabledRightIcon() {
        verifyStandardIcons(PanelMenuAttributes.topGroupDisabledRightIcon, page.getGroup4().advanced().getRightIcon(), "-hdr-dis");
    }

    @Test
    @Templates("plain")
    public void testTopGroupExpandedLeftIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 1");
        verifyStandardIcons(PanelMenuAttributes.topGroupExpandedLeftIcon, page.getGroup1().advanced().getLeftIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testTopGroupExpandedRightIcon() {
        guardAjax(page.getPanelMenu()).expandGroup("Group 1");
        verifyStandardIcons(PanelMenuAttributes.topGroupExpandedRightIcon, page.getGroup1().advanced().getRightIcon(), "");
    }

    @Test
    @Templates("plain")
    public void testTopItemDisabledLeftIcon() {
        verifyStandardIcons(PanelMenuAttributes.topItemDisabledLeftIcon, page.getItem4().advanced().getLeftIconElement(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testTopItemDisabledRightIcon() {
        verifyStandardIcons(PanelMenuAttributes.topItemDisabledRightIcon, page.getItem4().advanced().getRightIconElement(), "-dis");
    }

    @Test
    @Templates("plain")
    public void testTopItemLeftIcon() {
        verifyStandardIcons(PanelMenuAttributes.topItemLeftIcon, page.getItem3().advanced().getLeftIconElement(), "");
    }

    @Test
    @Templates("plain")
    public void testTopItemRightIcon() {
        verifyStandardIcons(PanelMenuAttributes.topItemRightIcon, page.getItem3().advanced().getRightIconElement(), "");
    }

    private void verifyStandardIcons(PanelMenuAttributes attribute, Icon icon, String classSuffix) {
        if (icon instanceof PanelMenuItemIcon) {
            iconsChecker.checkAll(attribute, (PanelMenuItemIcon) icon, classSuffix);
        } else {
            iconsChecker.checkAll(attribute, icon, classSuffix);
        }
//        iconsChecker.checkAll(attribute, (icon instanceof PanelMenuItemIcon ? (PanelMenuItemIcon) icon : icon), classSuffix);
    }

    private void verifyStandardIcons(PanelMenuAttributes attribute, WebElement icon, String classSuffix) {
        verifyStandardIcons(attribute, Graphene.createPageFragment(PanelMenuItemIcon.class, icon), classSuffix);
    }

    public static class PanelMenuItemIcon extends Icon {
    }
}
