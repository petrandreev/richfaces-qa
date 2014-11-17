/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010-2015, Red Hat, Inc. and individual contributors
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
package org.richfaces.tests.metamer.ftest.extension.configurator.skip;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.richfaces.tests.metamer.ftest.extension.configurator.ConfiguratorExtension;
import org.richfaces.tests.metamer.ftest.extension.configurator.config.Config;
import org.richfaces.tests.metamer.ftest.extension.configurator.config.EmptyConfig;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.annotation.AndExpression;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.annotation.Skip;

/**
 * Configurator for not running test on specified circumstances. To use this extension mark test method with @Skip.<br/>
 * Some examples:<br/>
 * To skip the test on Firefox browser:<br/>
 * <code>@Skip(On.Browser.Firefox.class)</code><br/>
 * To skip the test on Firefox browser on the Windows OS:<br/>
 * <code>@Skip({ On.Browser.Firefox.class, On.OS.Windows.class })</code>
 * To skip the test on Firefox or Chrome browser and on the Windows OS:<br/>
 * <code>@Skip(expressions = { @AndExpression({ On.Browser.Firefox.class, On.OS.Windows.class }), @AndExpression({ On.Browser.Chrome.class, On.OS.Windows.class }) })</code>
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class SkipConfigurator implements ConfiguratorExtension {

    private static final List<Config> LIST_WITH_EMPTY_CONFIG = new LinkedList<Config>();

    static {
        LIST_WITH_EMPTY_CONFIG.add(EmptyConfig.getInstance());
    }

    @Override
    public List<Config> createConfigurations(Method m, Object testInstance) {
        Skip annotationOnMethod = m.getAnnotation(Skip.class);
        if (annotationOnMethod == null) {
            return getNotSkippedConfiguration();
        }
        // the value takes precedence over expressions
        if (annotationOnMethod.value() != null && annotationOnMethod.value().length > 0) {
            if (handleAndExpressions(annotationOnMethod.value())) {
                return getSkippedConfiguration();
            }
        } else if (annotationOnMethod.expressions() != null && annotationOnMethod.expressions().length > 0) {
            boolean willSkip = false;
            for (AndExpression expression : annotationOnMethod.expressions()) {
                willSkip = willSkip || handleAndExpressions(expression.value());
            }
            return willSkip ? getSkippedConfiguration() : getNotSkippedConfiguration();
        } else {
            throw new RuntimeException("The Skip annotation cannot be empty. Either specify value or expressions!");
        }
        return getNotSkippedConfiguration();
    }

    private List<Config> getNotSkippedConfiguration() {
        return LIST_WITH_EMPTY_CONFIG;
    }

    @SuppressWarnings("unchecked")
    private List<Config> getSkippedConfiguration() {
        return Collections.EMPTY_LIST;
    }

    private boolean handleAndExpressions(Class<? extends IsOn>[] andValues) {
        if (andValues.length > 0) {
            boolean b = true;
            for (Class<? extends IsOn> andVal : andValues) {
                b = b && On.resolve(andVal);
            }
            return b;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean ignoreConfigurations() {
        return Boolean.TRUE;
    }

    @Override
    public boolean skipTestIfNoConfiguration() {
        return Boolean.TRUE;
    }
}
