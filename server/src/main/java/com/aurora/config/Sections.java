/*
 * Copyright 2015 The original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aurora.config;

import com.aurora.pos.server.ServerUI;
import org.springframework.stereotype.Component;
import org.vaadin.spring.sidebar.annotation.SideBarSection;
import org.vaadin.spring.sidebar.annotation.SideBarSections;

/**
 * This is a Spring-managed bean that does not do anything. Its only purpose is to define
 * the sections of the side bar. The reason it is configured as a bean is that it makes it possible
 * to lookup the annotations from the Spring application context.
 */
@SideBarSections({
        @SideBarSection(id = Sections.TRANSACCIONES, caption = "Transacciones"),
        @SideBarSection(id = Sections.CATALOGOS, caption = "Catalogos")
       // @SideBarSection(id = Sections.REPORTING, caption = "Reporting"),
       // @SideBarSection(id = Sections.VAADIN_FONT_ICONS, caption = "Vaadin Font Icons", ui = ServerUI.class)
})
@Component
public class Sections {

    public static final String TRANSACCIONES = "transacciones";
    public static final String CATALOGOS = "catalogos";

    //public static final String REPORTING = "reporting";
   // public static final String VAADIN_FONT_ICONS = "vaadinFontIcons";
}
