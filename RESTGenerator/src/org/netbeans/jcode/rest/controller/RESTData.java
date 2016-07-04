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
package org.netbeans.jcode.rest.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import static org.apache.commons.lang.StringUtils.isBlank;
import org.netbeans.jcode.rest.applicationconfig.RestConfigData;
import org.netbeans.jcode.rest.filter.FilterType;
import org.netbeans.jcode.rest.returntype.ControllerReturnType;
import org.netbeans.jcode.stack.config.data.LayerConfigData;

/**
 *
 * @author Gaurav Gupta
 */
public class RESTData extends LayerConfigData {

    private String prefixName;
    private String suffixName;
    private String _package;
    private RestConfigData restConfigData;
    private ControllerReturnType returnType;
    private List<FilterType> filterTypes;

    /**
     * @return the _package
     */
    public String getPackage() {
        return _package;
    }

    /**
     * @param _package the _package to set
     */
    public void setPackage(String _package) {
        this._package = _package;
    }

    /**
     * @return the suffixName
     */
    public String getSuffixName() {
        if (isBlank(suffixName)) {
            suffixName = "Controller";
        }
        return suffixName;
    }

    /**
     * @param suffixName the suffixName to set
     */
    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    /**
     * @return the prefixName
     */
    public String getPrefixName() {
        return prefixName;
    }

    /**
     * @param prefixName the prefixName to set
     */
    public void setPrefixName(String prefixName) {
        this.prefixName = prefixName;
    }

    /**
     * @return the restConfigData
     */
    public RestConfigData getRestConfigData() {
        return restConfigData;
    }

    /**
     * @param restConfigData the restConfigData to set
     */
    public void setRestConfigData(RestConfigData restConfigData) {
        this.restConfigData = restConfigData;
    }

    public ControllerReturnType getReturnType() {
        return returnType;
    }

    public void setReturnType(ControllerReturnType returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the filterTypes
     */
    public List<FilterType> getFilterTypes() {
        if (filterTypes == null) {
            filterTypes = new ArrayList<>();
        }
        return filterTypes;
    }

    /**
     * @param filterTypes the filterTypes to set
     */
    public void setFilterTypes(List<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public boolean addFilterType(FilterType filterType) {
        return getFilterTypes().add(filterType);
    }

    public boolean removeFilterType(FilterType filterType) {
        return getFilterTypes().remove(filterType);
    }

}