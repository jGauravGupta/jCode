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
import org.eclipse.persistence.internal.jpa.metadata.columns.AssociationOverrideMetadata;
import org.netbeans.jpa.modeler.spec.AssociationOverride;
import org.netbeans.jpa.modeler.spec.JoinColumn;
import org.netbeans.jpa.modeler.spec.validator.column.JoinColumnValidator;
import org.netbeans.jpa.modeler.spec.validator.table.JoinTableValidator;

/**
 *
 * @author Gaurav Gupta
 */
public class AssociationOverrideSpecMetadata extends AssociationOverrideMetadata {

    private AssociationOverrideSpecMetadata() {
    }

    public static AssociationOverrideSpecMetadata getInstance(AssociationOverride associationOverride) {
        AssociationOverrideSpecMetadata accessor = new AssociationOverrideSpecMetadata();
        accessor.setName(associationOverride.getName());
        if (!JoinTableValidator.isEmpty(associationOverride.getJoinTable())) {
            accessor.setJoinTable(associationOverride.getJoinTable().getAccessor());
        }
        JoinColumnValidator.filter(associationOverride.getJoinColumn());
        accessor.setJoinColumns(associationOverride.getJoinColumn().stream().map(JoinColumn::getAccessor).collect(toList()));

        return accessor;
    }

}
