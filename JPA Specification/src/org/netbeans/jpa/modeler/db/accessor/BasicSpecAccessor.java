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
package org.netbeans.jpa.modeler.db.accessor;

import java.util.Collections;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.BasicAccessor;
import org.netbeans.jpa.modeler.spec.Basic;
import org.netbeans.jpa.modeler.spec.Inheritance;
import org.netbeans.jpa.modeler.spec.extend.Attribute;

/**
 *
 * @author Gaurav Gupta
 */
public class BasicSpecAccessor extends BasicAccessor {

    private final Basic basic;
    private boolean inherit;

    private BasicSpecAccessor(Basic basic) {
        this.basic = basic;
    }

    public static BasicSpecAccessor getInstance(Basic basic, boolean inherit) {
        BasicSpecAccessor accessor = new BasicSpecAccessor(basic);
        accessor.inherit = inherit;

        accessor.setAttributeType(basic.getAttributeType());
        
        AccessorUtil.setEnumerated(accessor, basic.getEnumerated());
        AccessorUtil.setLob(accessor, basic.getLob(), basic.getAttributeType(), false);
        AccessorUtil.setTemporal(accessor, basic.getTemporal());
        
        accessor.setConverts(Collections.singletonList(basic.getConvert().getAccessor()));
        accessor.setName(basic.getName());
        accessor.setColumn(basic.getColumn().getAccessor());
        return accessor;
    }

    @Override
    public void process() {
        super.process();
        getMapping().setProperty(Attribute.class, basic);
        getMapping().setProperty(Inheritance.class, inherit);//Remove inherit functionality , once eclipse support dynamic mapped super class
    }

}
