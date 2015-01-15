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

import static org.jboss.test.selenium.support.url.URLUtils.buildUrl;

import java.net.URL;

import org.testng.annotations.Test;

public class TestTwoPushJMS extends TestTwoPush {

    @Override
    public URL getTestUrl() {
        return buildUrl(contextPath, "faces/components/a4jPush/twoPushJMS.xhtml");
    }

    @Test
    public void testBothPushes() {
        super.testBothPushes();
    }

    @Test(groups = "smoke")
    public void testOnSubscribed() {
        super.testOnSubscribed();
    }

    @Test(groups = "smoke")
    public void testPushEnable() {
        super.testPushEnable();
    }

    @Test
    public void testSimplePushEventReceive() {
       super.testSimplePushEventReceive();
    }
}
