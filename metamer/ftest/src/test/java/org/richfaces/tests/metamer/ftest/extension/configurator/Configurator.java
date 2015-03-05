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
package org.richfaces.tests.metamer.ftest.extension.configurator;

import java.lang.reflect.Method;
import java.util.List;

import org.richfaces.tests.metamer.ftest.extension.configurator.config.Config;
import org.richfaces.tests.metamer.ftest.extension.configurator.repeater.RepeaterConfigurator;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.SkipConfigurator;
import org.richfaces.tests.metamer.ftest.extension.configurator.templates.TemplatesConfigurator;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.UseForAllTestsConfigurator;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.UseWithFieldConfigurator;
import org.richfaces.tests.metamer.ftest.extension.configurator.use.UsesConfigurator;

import com.google.common.collect.Lists;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class Configurator {

    private final ConfigManager manager = new ConfigManager();

    public Configurator() {
        // FIFO queue
        if (isSkipConfiguratorEnabled()) {
            manager.addExtension(new SkipConfigurator());
        }
        manager.addExtension(new TemplatesConfigurator());
        manager.addExtension(new UseForAllTestsConfigurator());
        manager.addExtension(new UseWithFieldConfigurator());
        manager.addExtension(new UsesConfigurator());
        manager.addExtension(new RepeaterConfigurator());
    }

    /**
     * Apply and return the next configuration step.
     */
    public Config configureNextStep() {
        Config step = manager.getNextConfigurationStep();
        step.configure();
        return step;
    }

    private Object[][] createInvocationsOfTestMethod(int count) {
        return new Object[count][0];
    }

    private boolean isSkipConfiguratorEnabled() {
        return Boolean.getBoolean("configurator.skip.enabled");
    }

    /**
     * Prepare all configurations for method and return the invocations count of this method.
     */
    public Object[][] prepareConfigurationsForMethod(Method m, Object testInstance) {
        return createInvocationsOfTestMethod(manager.createAllConfigurations(m, testInstance));
    }

    private static class ConfigManager {

        private final List<List<Config>> configurations = Lists.newLinkedList();
        private final List<ConfiguratorExtension> extensions = Lists.newArrayList();

        public void addExtension(ConfiguratorExtension extension) {
            extensions.add(extension);
        }

        public int createAllConfigurations(Method m, Object testInstance) {
            List<Config> currentConfigurations;
            configurations.clear();
            for (ConfiguratorExtension extension : extensions) {
                currentConfigurations = extension.createConfigurations(m, testInstance);
                // skip test method if configuration returns no configurations and its skipIfEmpty return true
                if (extension.skipTestIfNoConfiguration() && (currentConfigurations == null || currentConfigurations.isEmpty())) {
                    return 0;
                }
                if (currentConfigurations != null && !extension.ignoreConfigurations()) {
                    configurations.add(currentConfigurations);
                }
            }
            ConfiguratorUtils.mergeAllConfigsToOne(configurations);
            return configurations.get(0).size();
        }

        public Config getNextConfigurationStep() {
            return configurations.get(0).remove(0);
        }
    }
}
