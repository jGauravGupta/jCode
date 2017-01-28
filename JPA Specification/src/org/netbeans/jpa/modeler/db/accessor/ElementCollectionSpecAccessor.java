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

import static java.util.stream.Collectors.toList;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.DirectAccessor;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.ElementCollectionAccessor;
import org.netbeans.jpa.modeler.db.accessor.spec.MapKeyAccessor;
import org.netbeans.jpa.modeler.spec.ElementCollection;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.validator.override.AssociationValidator;
import org.netbeans.jpa.modeler.spec.validator.override.AttributeValidator;

/**
 *
 * @author Gaurav Gupta
 */
public class ElementCollectionSpecAccessor extends ElementCollectionAccessor implements MapKeyAccessor{

    private final ElementCollection elementCollection;

    private ElementCollectionSpecAccessor(ElementCollection elementCollection) {
        this.elementCollection = elementCollection;
    }

    public static ElementCollectionSpecAccessor getInstance(ElementCollection elementCollection) {
        ElementCollectionSpecAccessor accessor = new ElementCollectionSpecAccessor(elementCollection);
        accessor.setName(elementCollection.getName());
        accessor.setAttributeType(elementCollection.getCollectionType());
        accessor.setTargetClassName(elementCollection.getAttributeType());
        
        AccessorUtil.setEnumerated((DirectAccessor)accessor,elementCollection.getEnumerated());
        AccessorUtil.setLob(accessor, elementCollection.getLob(), elementCollection.getAttributeType(), true);
        AccessorUtil.setTemporal((DirectAccessor)accessor, elementCollection.getTemporal());
        accessor.setColumn(elementCollection.getColumn().getAccessor());
        if (elementCollection.getCollectionTable() != null) {
            accessor.setCollectionTable(elementCollection.getCollectionTable().getAccessor());
        }
        if (elementCollection.getOrderColumn() != null) {
            accessor.setOrderColumn(elementCollection.getOrderColumn().getAccessor());
        }
        AttributeValidator.filter(elementCollection);
        accessor.setAttributeOverrides(elementCollection.getAttributeOverride().stream().map(AttributeOverrideSpecMetadata::getInstance).collect(toList()));
        AssociationValidator.filter(elementCollection);
        accessor.setAssociationOverrides(elementCollection.getAssociationOverride().stream().map(AssociationOverrideSpecMetadata::getInstance).collect(toList()));

        MapKeyUtil.load(accessor, elementCollection); 
        return accessor;

    }

    @Override
    public void process() {
        super.process();
        getMapping().setProperty(Attribute.class, elementCollection);
    }

}
