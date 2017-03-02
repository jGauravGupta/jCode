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
package org.netbeans.jpa.modeler.spec.validator.override;

import java.util.Arrays;
import java.util.Optional;
import org.netbeans.jpa.modeler.spec.AssociationOverride;
import org.netbeans.jpa.modeler.spec.ElementCollection;
import org.netbeans.jpa.modeler.spec.Embeddable;
import org.netbeans.jpa.modeler.spec.Embedded;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.ManagedClass;
import org.netbeans.jpa.modeler.spec.OneToMany;
import org.netbeans.jpa.modeler.spec.extend.IPersistenceAttributes;
import org.netbeans.jpa.modeler.spec.extend.MultiRelationAttribute;
import org.netbeans.jpa.modeler.spec.extend.RelationAttribute;
import org.netbeans.jpa.modeler.spec.extend.SingleRelationAttribute;
import org.netbeans.jpa.modeler.spec.validator.MarshalValidator;
import org.netbeans.jpa.modeler.spec.validator.column.JoinColumnValidator;
import org.netbeans.jpa.modeler.spec.validator.table.JoinTableValidator;

public class AssociationValidator extends MarshalValidator<AssociationOverride> {

    @Override
    public AssociationOverride marshal(AssociationOverride associationOverride) throws Exception {
        if (associationOverride != null && isEmpty(associationOverride)) {
            return null;
        }
        return associationOverride;
    }

    public static boolean isEmpty(AssociationOverride associationOverride) {
        JoinColumnValidator.filter(associationOverride.getJoinColumn());
        return JoinTableValidator.isEmpty(associationOverride.getJoinTable())
                && associationOverride.getJoinColumn().isEmpty();
    }

    /**
     * Used to remove all stale AssociationOverride (ex : if Parent JavaClass
     * association is removed then AssociationOverride reference should be
     * removed) There are two way : either to remove on attribute deletion or at
     * the time of DB Modeler creation
     *
     */
    public static void filter(Entity entity) {
        ManagedClass parentclass = entity.getSuperclass() instanceof ManagedClass ? (ManagedClass) entity.getSuperclass() : null;
        entity.getAssociationOverride().removeIf(associationOverride
                -> !isExist(associationOverride.getName(), parentclass)
                || AssociationValidator.isEmpty(associationOverride)
        );
    }

    /**
     *
     * @param key key of AttributeOverride
     * @param managedClass parent class of entity to search AttributeOverride's key
     * @return
     */
    private static boolean isExist(String key, ManagedClass<IPersistenceAttributes> managedClass) {
        if (managedClass == null) {
            return false;
        }
        Optional<RelationAttribute> attrOptional = managedClass.getAttributes()
                .getRelationAttributes()
                .stream()
                .filter(e -> e.getName().equalsIgnoreCase(key))
                .findAny();
        if (attrOptional.isPresent()) {
            return true;
        } else if (managedClass.getSuperclass() instanceof ManagedClass) {
            return isExist(key, (ManagedClass) managedClass.getSuperclass());
        } else {
            return false;
        }
    }

    public static void filter(Embedded embedded) {
        embedded.getAssociationOverride().removeIf(associationOverride
                -> !isExist(associationOverride.getName().split("\\."), embedded.getConnectedClass(), associationOverride)
                || AssociationValidator.isEmpty(associationOverride)
        );
    }

    public static void filter(ElementCollection elementCollection) {
        elementCollection.getAssociationOverride().removeIf(associationOverride
                -> !isExist(associationOverride.getName().split("\\."), elementCollection.getConnectedClass(), associationOverride)
                || AssociationValidator.isEmpty(associationOverride)
        );
    }

    /**
     *
     * @param keys arrays path to managedAttr separated by dots
     * @param embeddable next intrinsic element , incremented in each recursion
     */
    private static boolean isExist(String[] keys, Embeddable embeddable, AssociationOverride associationOverride) {
        if (keys.length > 1) {
            Optional<Embedded> embeddedOptional = embeddable.getAttributes().getEmbedded().stream().filter(e -> e.getName().equalsIgnoreCase(keys[0])).findAny();
            if (embeddedOptional.isPresent()) {
                return isExist(Arrays.copyOfRange(keys, 1, keys.length), embeddedOptional.get().getConnectedClass(), associationOverride);
            } else {
                return false;
            }
        } else {
            Optional<RelationAttribute> attrOptional = embeddable.getAttributes().getRelationAttributes().stream().filter(e -> e.getName().equalsIgnoreCase(keys[0])).findAny();
            if (attrOptional.isPresent()) {
                RelationAttribute attribute = attrOptional.get();
                if (attribute instanceof SingleRelationAttribute) {
                    associationOverride.getJoinTable().clear();
                } else if (attribute instanceof MultiRelationAttribute) {
                    if (attribute instanceof OneToMany) {
                        associationOverride.getJoinColumn().clear();
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

}
