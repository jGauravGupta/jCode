/**
 * Copyright 2013-2018 the original author or authors from the Jeddict project (https://jeddict.github.io/).
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
package io.github.jeddict.rest.controller;

import io.github.jeddict.jcode.LayerConfigData;
import io.github.jeddict.repository.RepositoryData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.apache.commons.lang3.StringUtils.isBlank;
import io.github.jeddict.rest.applicationconfig.RestConfigData;
import io.github.jeddict.rest.filter.FilterType;
import io.github.jeddict.rest.returntype.ControllerReturnType;

/**
 *
 * @author Gaurav Gupta
 */
public class RESTData extends LayerConfigData<RepositoryData> {

    private String prefixName;
    private String suffixName;
    private String _package;
    private RestConfigData restConfigData;
    private ControllerReturnType returnType;
    private List<FilterType> filterTypes;
    private boolean metrics = true;
    private boolean logger = true;
    private boolean openAPI = true;
    private boolean testCase = true;
    private String frontendAppName = "app";
    private SecurityType securityType;

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

    @Deprecated
    public ControllerReturnType getReturnType() {
        return returnType;
    }

    @Deprecated
    public void setReturnType(ControllerReturnType returnType) {
        this.returnType = returnType;
    }

    /**
     * @return the filterTypes
     */
    @Deprecated
    public List<FilterType> getFilterTypes() {
        if (filterTypes == null) {
            filterTypes = new ArrayList<>();
        }
        return filterTypes;
    }

    /**
     * @param filterTypes the filterTypes to set
     */
    @Deprecated
    public void setFilterTypes(List<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    @Deprecated
    public boolean addFilterType(FilterType filterType) {
        return getFilterTypes().add(filterType);
    }

    @Deprecated
    public boolean removeFilterType(FilterType filterType) {
        return getFilterTypes().remove(filterType);
    }

    /**
     * @return the metrics
     */
    public boolean isMetrics() {
        return metrics;
    }

    /**
     * @param metrics the metrics to set
     */
    public void setMetrics(boolean metrics) {
        this.metrics = metrics;
    }

    /**
     * @return the openAPI
     */
    public boolean isOpenAPI() {
        return openAPI;
    }

    /**
     * @param openAPI the openAPI to set
     */
    public void setOpenAPI(boolean openAPI) {
        this.openAPI = openAPI;
    }

    /**
     * @return the frontendAppName
     */
    public String getFrontendAppName() {
        return frontendAppName;
    }

    /**
     * @param frontendAppName the frontendAppName to set
     */
    public void setFrontendAppName(String frontendAppName) {
        this.frontendAppName = frontendAppName;
    }

    /**
     * @return the testCase
     */
    public boolean isTestCase() {
        return testCase;
    }

    /**
     * @param testCase the testCase to set
     */
    public void setTestCase(boolean testCase) {
        this.testCase = testCase;
    }

    /**
     * @return the logger
     */
    public boolean isLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(boolean logger) {
        this.logger = logger;
    }
    
    @Override
    protected void onLayerConnection(){
        getParentLayerConfigData().setNamed(false);
    }
    
    /**
     * @return the securityType
     */
    public SecurityType getSecurityType() {
        return securityType;
    }

    /**
     * @param securityType the securityType to set
     */
    public void setSecurityType(SecurityType securityType) {
        this.securityType = securityType;
    }

    @Override
    public List<String> getUsageDetails() {
        return Arrays.asList(metrics ? "Metrics" : null,
                logger ? "Log-Manager" : null,
                openAPI ? "OpenAPI" : null,
                testCase ? "TestCase" : null,
                securityType.name());
    }
}
