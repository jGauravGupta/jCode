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
package org.netbeans.jpa.modeler.spec.validator.column;

import org.apache.commons.lang.StringUtils;
import org.netbeans.jpa.modeler.spec.Column;
import org.netbeans.jpa.modeler.spec.validator.MarshalValidator;

public class ColumnValidator<E extends Column> extends MarshalValidator<E> {

    @Override
    public E marshal(E column) throws Exception {
        if (column != null && isEmpty(column)) {
            return null;
        }
        return column;
    }

    public static boolean isEmpty(Column column) {
        return StringUtils.isBlank(column.getName()) && StringUtils.isBlank(column.getColumnDefinition()) && StringUtils.isBlank(column.getTable())
                && column.getNullable() && column.getInsertable() && column.getUpdatable() && !column.getUnique()
                && column.getLength() == 255 && column.getScale() == 0 && column.getPrecision() == 0;
    }

}
