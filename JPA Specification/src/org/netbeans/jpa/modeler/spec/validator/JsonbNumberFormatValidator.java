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
package org.netbeans.jpa.modeler.spec.validator;

import org.apache.commons.lang.StringUtils;
import org.netbeans.jpa.modeler.spec.jsonb.JsonbNumberFormat;

public class JsonbNumberFormatValidator extends MarshalValidator<JsonbNumberFormat> {

    @Override
    public JsonbNumberFormat marshal(JsonbNumberFormat jsonbFormat) throws Exception {
        if (jsonbFormat != null && isEmpty(jsonbFormat)) {
            return null;
        }
        return jsonbFormat;
    }

    public static boolean isEmpty(JsonbNumberFormat jsonbFormat) {
        boolean empty = false;
        if (StringUtils.isBlank(jsonbFormat.getLocale()) 
                && StringUtils.isBlank(jsonbFormat.getValue())) {
            empty = true;
        }
        return empty;
    }

    public static boolean isNotEmpty(JsonbNumberFormat jsonbFormat) {
        return !isEmpty(jsonbFormat);
    }

}