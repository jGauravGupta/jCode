<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:f="http://xmlns.jcp.org/jsf/core"
      xmlns:p="http://primefaces.org/ui">

    <ui:composition template="/template.xhtml">

        <ui:define name="title">
            <h:outputText value="List ${Entity}"></h:outputText>
        </ui:define>

        <ui:define name="header" >
        </ui:define>

        <ui:define name="body" >
            <div>
                <h1 style="margin-left: 2%">${Entity} Registration <p:commandButton id="createButton" icon="ui-icon-plus" value="Create ${Entity} for registration" style="float:right; margin-right: 2%" action="${hash}{${EntityController}.prepareCreate}"></p:commandButton></h1>

            </div>
            <h:form id="StudentListForm" style="margin-left: 2%; margin-right: 2%">
                <p:dataTable id="datalist" value="${hash}{${EntityController}.items}" var="item"
                             selection="${hash}{${EntityController}.selected}"
                             rowKey="${hash}{item.id}"
                >
                    <#list attributes as attribute>
                    <p:column>
                        <f:facet name="header">
                            <h:outputText value="${attribute.name}"/>
                        </f:facet>
                        <h:outputText value="${hash}{item.${attribute.name}}"/>
                    </p:column>
                    </#list>
                    <p:column>
                        <p:commandButton id="viewButton" icon="ui-icon-document"  title="View"  value="View" action="${hash}{${EntityController}.prepareView}" style="width:58px;height:20px"/>
                        <p:commandButton id="editButton" icon="ui-icon-pencil"  title="Edit"  value="Edit" action="${hash}{${EntityController}.prepareEdit}"  style="width:58px;height:20px"/>
                        <p:commandButton id="deleteButton" icon="ui-icon-trash"  title="Delete" value="Delete" actionListener="${hash}{${EntityController}.destroy}" immediate="true" update="datalist" style="width:68px;height:20px">
                            <p:confirm header="Delete ${Entity}" message="Are you sure to delete this ${Entity} ?" icon="ui-icon-alert" />
                        </p:commandButton>

                        <p:confirmDialog global="true" showEffect="fade" hideEffect="fade">
                            <p:commandButton value="Yes" type="button" styleClass="ui-confirmdialog-yes" icon="ui-icon-check" />
                            <p:commandButton value="No" type="button" styleClass="ui-confirmdialog-no" icon="ui-icon-close" />
                        </p:confirmDialog>
                    </p:column>
                </p:dataTable>
            </h:form>

        </ui:define>
    </ui:composition>

</html>
