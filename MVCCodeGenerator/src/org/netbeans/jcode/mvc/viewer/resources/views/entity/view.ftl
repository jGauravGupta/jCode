 <#--  Copyright [2016] Gaurav Gupta
 
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy of
  the License at
 
  http://www.apache.org/licenses/LICENSE-2.0
 
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
  License for the specific language governing permissions and limitations under
  the License.
  -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jspf"%>
<div id="page-wrapper">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h5><i class="fa fa-info-circle fa-fw"></i> <b> ${entityLabel} info</b></h5> 
                    </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="list-group">

                                            <#list entityDescriptors as entityDescriptor>
                                              <#if !entityDescriptor.generatedValue &&
                                                   !entityDescriptor.versionField &&
                                                   !entityDescriptor.readOnly &&
                                                   entityDescriptor.returnType != "invalid" &&
                                                   !(entityDescriptor.relationshipMany && entityDescriptor.relationshipOne)> 
                                <a href="#" class="list-group-item">
                                    <div class="row">
                                        <div class="col-lg-4"><span class="small">${entityDescriptor.label} :</span></div>
                                        <div class="col-lg-8">${r"${"}${entityConstant}.${entityDescriptor.propertyName}${r"}"}</div>
                                        </div>
                                    </a>             
                                              </#if>
                                          </#list>

                                </div>
                            <a href="${r"${appPath}"}/${entityName}/list" class="btn btn-default"><i class="fa fa-arrow-circle-left fa-fw"></i>Back</a>
                            </div>
                            <!-- /.col-lg-6 (nested) -->
                        </div>
                        <!-- /.row (nested) -->
                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
</div>

<%--end content--%>
<%@ include file="../common/footer.jspf"%>
