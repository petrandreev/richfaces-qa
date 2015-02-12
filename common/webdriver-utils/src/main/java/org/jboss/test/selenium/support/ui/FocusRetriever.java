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
package org.jboss.test.selenium.support.ui;

import org.jboss.arquillian.graphene.context.GrapheneContext;
import org.jboss.arquillian.graphene.javascript.JSInterfaceFactory;
import org.jboss.arquillian.graphene.javascript.JavaScript;
import org.jboss.arquillian.graphene.proxy.GrapheneProxyInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author <a href="mailto:jhuska@redhat.com">Juraj Huska</a>
 */
@JavaScript("document")
public abstract class FocusRetriever {
    public abstract WebElement getActiveElement();

    /**
     * Returns active (focused) element - if no element is focused (it means body element is active), null is returned
     */
    public static WebElement retrieveActiveElement(WebDriver browser) {
        GrapheneContext context = ((GrapheneProxyInstance) browser).getGrapheneContext();
        WebElement element = JSInterfaceFactory.create(context, FocusRetriever.class).getActiveElement();
        if ("body".equals(element.getTagName())) {
            return null;
        }
        return element;
    }
}
