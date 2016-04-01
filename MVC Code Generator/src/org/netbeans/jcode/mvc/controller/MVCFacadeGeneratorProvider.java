/**
 * Copyright [2016] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jcode.mvc.controller;

import org.netbeans.modules.j2ee.persistence.wizard.fromdb.FacadeGenerator;
import org.netbeans.modules.j2ee.persistence.wizard.fromdb.FacadeGeneratorProvider;
import org.netbeans.jpa.modeler.rest.codegen.model.EntityResourceBeanModel;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Gaurav Gupta
 */
@ServiceProvider(service=FacadeGeneratorProvider.class)
public class MVCFacadeGeneratorProvider implements FacadeGeneratorProvider {

    @Override
    public String getGeneratorType() {
        return "mvc_controller"; // NOI18N
    }

    @Override
    public FacadeGenerator createGenerator() {
        return new MVCControllerGenerator();
    }
    
    public FacadeGenerator createGenerator(EntityResourceBeanModel model) {
        return new MVCControllerGenerator(model);
    }
}
