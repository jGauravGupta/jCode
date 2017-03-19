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
import org.eclipse.persistence.exceptions.ValidationException;
import org.eclipse.persistence.internal.jpa.metadata.accessors.mappings.OneToOneAccessor;
import org.netbeans.db.modeler.exception.DBValidationException;
import org.netbeans.jpa.modeler.spec.PrimaryKeyAttributes;
import org.netbeans.jpa.modeler.spec.IdClass;
import org.netbeans.jpa.modeler.spec.Inheritance;
import org.netbeans.jpa.modeler.spec.JoinColumn;
import org.netbeans.jpa.modeler.spec.ManagedClass;
import org.netbeans.jpa.modeler.spec.OneToOne;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.extend.IAttributes;
import org.netbeans.jpa.modeler.spec.validator.column.JoinColumnValidator;
import org.netbeans.jpa.modeler.spec.validator.table.JoinTableValidator;

/**
 *
 * @author Gaurav Gupta
 */
public class OneToOneSpecAccessor extends OneToOneAccessor {

    private final OneToOne oneToOne;
    private boolean inherit;

    private OneToOneSpecAccessor(OneToOne oneToOne) {
        this.oneToOne = oneToOne;
    }

    public static OneToOneSpecAccessor getInstance(OneToOne oneToOne, boolean inherit) {
        OneToOneSpecAccessor accessor = new OneToOneSpecAccessor(oneToOne);
        accessor.inherit = inherit;
        accessor.setName(oneToOne.getName());
        accessor.setTargetEntityName(oneToOne.getTargetEntity());
        if (oneToOne.isPrimaryKey()) {
            IdClass idClass = oneToOne.getIdClass();
            if (idClass != null) {
                accessor.setId(Boolean.TRUE);
            } else {
                IAttributes attributes = ((ManagedClass) oneToOne.getJavaClass()).getAttributes();
                if (attributes instanceof PrimaryKeyAttributes && !((PrimaryKeyAttributes) attributes).hasCompositePrimaryKey()) { //Ex 4.a Derived Identity
                    accessor.setId(Boolean.TRUE);
                } else {
                    accessor.setMapsId("");//oneToOne.getName());
                }
            }
        }
        accessor.setMappedBy(oneToOne.getMappedBy());
        if (!JoinTableValidator.isEmpty(oneToOne.getJoinTable())) {
            accessor.setJoinTable(oneToOne.getJoinTable().getAccessor());
        }
        JoinColumnValidator.filter(oneToOne.getJoinColumn());
        accessor.setJoinColumns(oneToOne.getJoinColumn().stream().map(JoinColumn::getAccessor).collect(toList()));

        return accessor;

    }

    @Override
    public void process() {
        try{
        super.process();
        getMapping().setProperty(Attribute.class, oneToOne);
        getMapping().setProperty(Inheritance.class, inherit);//Remove inherit functionality , once eclipse support dynamic mapped super class
        } catch (ValidationException ex) {
            DBValidationException exception = new DBValidationException(ex);
            exception.setJavaClass(oneToOne.getJavaClass());
            exception.setAttribute(oneToOne);
            throw exception;
        }
    }

}
