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

import java.text.MessageFormat;

import org.richfaces.tests.metamer.ftest.extension.configurator.skip.On.JSF.Implementation.Mojarra;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.On.JSF.Implementation.MyFaces;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.On.JSF.Version.LowerThan_2_1_29;
import org.richfaces.tests.metamer.ftest.extension.configurator.skip.On.JSF.Version.LowerThan_2_2;

/**
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class On {

    static boolean resolve(Class<? extends IsOn> klass) {
        if (OnOS.class.isAssignableFrom(klass)) {
            return OS.resolveOS(klass);
        } else if (OnBrowser.class.isAssignableFrom(klass)) {
            return Browser.resolveBrowser(klass);
        } else if (OnJSF.class.isAssignableFrom(klass)) {
            return JSF.resolveJSFVersion(klass);
        } else {
            throw new UnsupportedOperationException(MessageFormat.format("Not supported option <{0}> ", klass));
        }
    }

    private static boolean systemPropertyIsContaing(String systemProperty, String defaultValue, String... options) {
        String property = System.getProperty(systemProperty, defaultValue).toLowerCase();
        if (property.isEmpty()) {
            return Boolean.FALSE;
        }
        for (String option : options) {
            if (property.contains(option)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static class Browser {

        private static boolean browserPropertyIsContaing(String... possibleBrowserName) {
            return systemPropertyIsContaing("browser", "firefox", possibleBrowserName);
        }

        private static boolean resolveBrowser(Class<? extends IsOn> klass) {
            if (klass.isAssignableFrom(Firefox.class)) {
                return browserPropertyIsContaing("firefox", "ff");
            } else if (klass.isAssignableFrom(IE.class)) {
                return browserPropertyIsContaing("ie", "internet", "explorer");
            } else if (klass.isAssignableFrom(Chrome.class)) {
                return browserPropertyIsContaing("chrome", "cr");
            }
            throw new UnsupportedOperationException(MessageFormat.format("Not supported option <{0}> ", klass));
        }

        public interface Chrome extends IsOn, OnBrowser {
        }

        public interface Firefox extends IsOn, OnBrowser {
        }

        public interface IE extends IsOn, OnBrowser {
        }
    }

    public static class JSF {

        private static final JSFDetector jsfDetector = JSFDetector.getInstance();

        private static boolean resolveJSFVersion(Class<? extends IsOn> klass) {
            if (klass.isAssignableFrom(Mojarra.class)) {
                return jsfDetector.isMojarra();
            } else if (klass.isAssignableFrom(MyFaces.class)) {
                return jsfDetector.isMyFaces();
            } else if (klass.isAssignableFrom(LowerThan_2_2.class)) {
                org.richfaces.tests.qa.plugin.utils.Version v = org.richfaces.tests.qa.plugin.utils.Version.parseVersion("2.2.0");
                return jsfDetector.getJsfVersion().compareTo(v) < 0;
            } else if (klass.isAssignableFrom(LowerThan_2_1_29.class)) {
                org.richfaces.tests.qa.plugin.utils.Version v = org.richfaces.tests.qa.plugin.utils.Version.parseVersion("2.1.29");
                return jsfDetector.getJsfVersion().compareTo(v) < 0;
            }
            throw new UnsupportedOperationException(MessageFormat.format("Not supported option <{0}> ", klass));
        }

        public interface Version {

            public interface LowerThan_2_2 extends IsOn, OnJSF {
            }

            public interface LowerThan_2_1_29 extends IsOn, OnJSF {
            }
        }

        public interface Implementation {

            public interface Mojarra extends IsOn, OnJSF {
            }

            public interface MyFaces extends IsOn, OnJSF {
            }
        }
    }

    public static class OS {

        private static boolean resolveOS(Class<? extends IsOn> klass) {
            if (klass.isAssignableFrom(Windows.class)) {
                return osNamePropertyIsContaining("windows", "win");
            } else if (klass.isAssignableFrom(Linux.class)) {
                return osNamePropertyIsContaining("linux");
            } else if (klass.isAssignableFrom(Solaris.class)) {
                return osNamePropertyIsContaining("sunos", "sun", "solaris");
            }
            throw new UnsupportedOperationException(MessageFormat.format("Not supported option <{0}> ", klass));
        }

        private static boolean osNamePropertyIsContaining(String... possibleOSName) {
            return systemPropertyIsContaing("os.name", "", possibleOSName);
        }

        public interface Windows extends IsOn, OnOS {
        }

        public interface Linux extends IsOn, OnOS {
        }

        public interface Solaris extends IsOn, OnOS {
        }

    }

    private interface OnBrowser {
    }

    private interface OnOS {
    }

    private interface OnJSF {
    }
}
