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
package org.richfaces.tests.metamer.bean.rich;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ManagedBean(name = "richValidatorBean2")
@SessionScoped
public class RichValidatorBean2 implements Serializable {

    private static final String expression = "[a-z]+";
    private static final long serialVersionUID = -1L;

    @NotNull
    private String notEmptyValue;
    @Pattern(regexp = expression)
    private String regexValue;

    public String getExpression() {
        return expression;
    }

    public String getNotEmptyValue() {
        return notEmptyValue;
    }

    public String getRegexValue() {
        return regexValue;
    }

    public void setNotEmptyValue(String notEmptyValue) {
        this.notEmptyValue = notEmptyValue;
    }

    public void setRegexValue(String regexValue) {
        this.regexValue = regexValue;
    }
}
