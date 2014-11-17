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

import java.io.File;
import java.io.FileFilter;
import java.text.MessageFormat;
import java.util.Set;

import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.richfaces.tests.qa.plugin.utils.Version;

/**
 * Detector of used JSF version. The version is detected either from WAR (for Tomcat), or from unpacked container in project build directory.
 *
 * @author <a href="mailto:jstefek@redhat.com">Jiri Stefek</a>
 */
public class JSFDetector {

    private static final String CONTAINER_JBOSS_NAME = "jboss";
    private static final String CONTAINER_WILDFLY_NAME = "wildfly";
    private static final JSFDetector INSTANCE = new JSFDetector();
    private static final String JBOSS_JSF_IMPL_DIR = "modules/system/layers/base/com/sun/jsf-impl/main/";
    private static final String[] JBOSS_JSF_IMPL_END = { "-jbossorg", ".redhat" };
    private static final String JBOSS_JSF_IMPL_START = "jsf-impl-";
    private static final String JSF_IMPL_MOJARRA = "Mojarra";
    private static final String JSF_IMPL_MYFACES = "MyFaces";
    private static final String MOJARRA_JAR_NAME = "javax.faces";
    private static final String MYFACES_JAR_NAME = "myfaces-impl";
    private static final String PROJECT_BUILD_DIRECTORY = "project.build.directory";
    private static final String WEBINF_LIB_DIR = "WEB-INF/lib";
    private final FileFilter jBossContainersFileFilter = new FileFilter() {
        private String name;

        @Override
        public boolean accept(File pathname) {
            name = pathname.getName().toLowerCase();
            return name.contains(CONTAINER_WILDFLY_NAME) || name.contains(CONTAINER_JBOSS_NAME);
        }
    };
    private String jsfImpl;
    private final FileFilter jsfImplFileFilter = new FileFilter() {
        String name;

        @Override
        public boolean accept(File file) {
            name = file.getName().toLowerCase();
            return name.contains(JBOSS_JSF_IMPL_START) && name.endsWith("jar");
        }
    };
    private Version jsfVersion;

    private JSFDetector() {
    }

    public static JSFDetector getInstance() {
        return INSTANCE;
    }

    private boolean areJbossContainersInProjectTargetDir() {
        String name;
        for (File listFile : new File(System.getProperty(PROJECT_BUILD_DIRECTORY)).listFiles()) {
            name = listFile.getName().toLowerCase();

            if (name.contains(CONTAINER_WILDFLY_NAME) || name.contains(CONTAINER_JBOSS_NAME)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private void checkInitialized() {
        if (jsfImpl == null || jsfVersion == null) {
            throw new IllegalStateException("The detector did not run. The properties are not set. Please run "
                + "JSFDetector.detect(war) in prepare WAR step");
        }
    }

    public void detect(WebArchive war) {
        String property = System.getProperty(PROJECT_BUILD_DIRECTORY);
        if (property == null) {
            throw new RuntimeException(MessageFormat.format("The <{0}> was not set.", PROJECT_BUILD_DIRECTORY));
        }
        if (areJbossContainersInProjectTargetDir()) {// WildFly, EAP
            detectJSFFromJBossContainers(property);
        } else {// Tomcat
            detectJSFFromWar(war);
        }
    }

    private void detectJSFFromJBossContainers(String property) throws RuntimeException {
        File[] containers = new File(property).listFiles(jBossContainersFileFilter);
        if (containers.length != 1) {
            throw new RuntimeException(MessageFormat.format("There were {0} containers in project build directory. Only one "
                + "container expected.", containers.length));
        }
        this.jsfImpl = JSF_IMPL_MOJARRA;

        File[] jsfImplJars = new File(containers[0], JBOSS_JSF_IMPL_DIR).listFiles(jsfImplFileFilter);
        if (jsfImplJars.length != 1) {
            throw new RuntimeException(MessageFormat.format("There were {0} jsf-impl jars present in container. Only one "
                + "jsf-impl jar expected.", jsfImplJars.length));
        }
        this.jsfVersion = parseJBossJSFVersionFromFilename(jsfImplJars[0].getName());
    }

    private void detectJSFFromWar(WebArchive war) throws IllegalArgumentException {
        Set<Node> childs = war.get(ArchivePaths.create(WEBINF_LIB_DIR)).getChildren();
        String get;
        for (Node children : childs) {
            get = children.getPath().get();
            if (get.contains(MOJARRA_JAR_NAME)) {
                this.jsfImpl = JSF_IMPL_MOJARRA;
                this.jsfVersion = parseVersionFromPath(get);
                return;
            } else if (get.contains(MYFACES_JAR_NAME)) {
                this.jsfImpl = JSF_IMPL_MYFACES;
                this.jsfVersion = parseVersionFromPath(get);
                return;
            }
        }
        throw new RuntimeException("Was not able to detect jsf-impl version from war file");
    }

    public String getJsfImpl() {
        checkInitialized();
        return jsfImpl;
    }

    public Version getJsfVersion() {
        checkInitialized();
        return jsfVersion;
    }

    public boolean isMojarra() {
        return getJsfImpl().equals(JSF_IMPL_MOJARRA);
    }

    public boolean isMyFaces() {
        return getJsfImpl().equals(JSF_IMPL_MYFACES);
    }

    private Version parseJBossJSFVersionFromFilename(String path) {
        for (String ending : JBOSS_JSF_IMPL_END) {
            if (path.contains(ending)) {
                return Version.parseVersion(
                    path.substring(path.lastIndexOf(JBOSS_JSF_IMPL_START) + JBOSS_JSF_IMPL_START.length(),
                        path.lastIndexOf(ending))
                );
            }
        }
        throw new RuntimeException(MessageFormat.format("Cannot parse the jsf-impl version from <{0}>", path));
    }

    private Version parseVersionFromPath(String path) {
        return Version.parseVersion(path.substring(path.lastIndexOf('-') + 1, path.lastIndexOf('.')));
    }
}
