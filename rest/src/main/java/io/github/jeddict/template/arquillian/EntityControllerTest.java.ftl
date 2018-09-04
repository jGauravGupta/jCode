package ${package};

<#assign eid = pkStrategy == "EmbeddedId" >
import ${appPackage}${EntityRepository_FQN};
<#list connectedFQClasses as connectedFQClass>
import ${connectedFQClass};
</#list>
<#if !eid || pkStrategy != "IdClass">
import static java.util.Collections.singletonMap;
</#if>
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.hamcrest.CoreMatchers.*;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasContentType;
import static org.valid4j.matchers.http.HttpResponseMatchers.hasStatus;

/**
 * Test class for the ${controllerClass} REST controller.
 *
 */
@RunWith(Arquillian.class)
public class ${controllerClass}Test extends <#if microservices>Abstract<#else>Application</#if>Test {

<#if eid>
<#list allIdAttributes as attribute>
    private static final ${attribute.dataType} DEFAULT_${attribute.NAME} = ${attribute.defaultValue};
</#list>
<#else>
<#list idAttributes as attribute>
    private static final ${attribute.dataType} DEFAULT_${attribute.NAME} = ${attribute.defaultValue};
</#list>
</#if>
<#list attributes as attribute>
    private static final ${attribute.dataType} DEFAULT_${attribute.NAME} = ${attribute.defaultValue};
    private static final ${attribute.dataType} UPDATED_${attribute.NAME} = ${attribute.updatedValue};
</#list>

    private static ${instanceType} ${instanceName};

    @Inject
    private ${EntityRepository} ${entityRepository};

    private ${controllerClass}Client client;

    @Deployment
    public static WebArchive createDeployment() {
        return <#if microservices>buildArchive<#else>buildApplication</#if>()
                .addClasses(
                        <#if microservices>AbstractTest.class,</#if>
                        <#list connectedClasses as connectedClass>${connectedClass}.class,
                        </#list>
                        ${EntityRepository}.class,
                        ${controllerClass}.class,
                        ${controllerClass}Client.class);
    }

    @Before
    public void buildClient() throws Exception {
        client = buildClient(${controllerClass}Client.class);
    }

    @Test
    @InSequence(1)
    public void create${EntityClass}() throws Exception {
        int databaseSizeBeforeCreate = ${entityRepository}.findAll().size();

        // Create the ${instanceType}
        ${instanceName} = new ${instanceType}();
<#if eid>
        ${pkType} ${pkName} = new ${pkType}();
<#list allIdAttributes as attribute>
        ${pkName}.${attribute.setter}(DEFAULT_${attribute.NAME});
</#list>
        ${instanceName}.${pkSetter}(${pkName});
<#else>
<#list idAttributes as attribute>
        ${instanceName}.${attribute.setter}(DEFAULT_${attribute.NAME});
</#list>
</#if>
<#list attributes as attribute>
        ${instanceName}.${attribute.setter}(DEFAULT_${attribute.NAME});
</#list>
        Response response = client.create${EntityClass}(${instanceName});
        assertThat(response, hasStatus(CREATED));
        ${instanceName} = response.readEntity(${instanceType}.class);

        // Validate the ${EntityClass} in the database
        List<${EntityClass}> ${entityInstancePlural} = ${entityRepository}.findAll();
        assertThat(${entityInstancePlural}.size(), is(databaseSizeBeforeCreate + 1));
        ${EntityClass} test${EntityClass} = ${entityInstancePlural}.get(${entityInstancePlural}.size() - 1);
<#list idAttributes as attribute>
        assertThat(test${EntityClass}<#if eid>.${pkGetter}()</#if>.${attribute.getter}(), is(DEFAULT_${attribute.NAME}));
</#list>
<#list attributes as attribute>
        assertThat(test${EntityClass}.${attribute.getter}(), is(DEFAULT_${attribute.NAME}));
</#list>
    }

    @Test
    @InSequence(2)
    public void getAll${EntityClassPlural}() throws Exception {
        int databaseSize = ${entityRepository}.findAll().size();

        // Get all the ${entityInstancePlural}
        List<${instanceType}> ${entityInstancePlural} = client.getAll${EntityClassPlural}(<#if pagination!= "no">0, 10</#if>);
        assertThat(${entityInstancePlural}.size(), is(databaseSize));
    }

    @Test
    @InSequence(3)
    public void get${EntityClass}() throws Exception {
        // Get the ${instanceName}
<#if allIdAttributes?size=1>
        Response response = client.get${EntityClass}(${instanceName}.${pkGetter}());
<#else>
        Response response = client.get${EntityClass}(
        <#list allIdAttributes as attribute>
                ${instanceName}<#if eid>.${pkGetter}()</#if>.${attribute.getter}()<#if attribute_has_next>,</#if>
        </#list>
        );
</#if>
        ${instanceType} test${instanceType} = response.readEntity(${instanceType}.class);
        assertThat(response, hasStatus(OK));
        assertThat(response, hasContentType(MediaType.APPLICATION_JSON_TYPE));
<#list allIdAttributes as attribute>
        assertThat(test${instanceType}<#if eid>.${pkGetter}()</#if>.${attribute.getter}(), is(${instanceName}<#if eid>.${pkGetter}()</#if>.${attribute.getter}()));
</#list>
<#list attributes as attribute>
        assertThat(test${instanceType}.${attribute.getter}(), is(DEFAULT_${attribute.NAME}));
</#list>
    }

    @Test
    @InSequence(4)
    public void getNonExisting${EntityClass}() throws Exception {
        // Get the ${instanceName}
<#if allIdAttributes?size=1>
        assertWebException(NOT_FOUND, () -> client.get${EntityClass}(Long.MAX_VALUE));
<#else>
        assertWebException(NOT_FOUND, () -> client.get${EntityClass}(
        <#list allIdAttributes as attribute>
                Long.MAX_VALUE<#if attribute_has_next>,</#if>
        </#list>
        ));
</#if>
    }

    @Test
    @InSequence(5)
    public void update${EntityClass}() throws Exception {
        int databaseSizeBeforeUpdate = ${entityRepository}.findAll().size();

        // Update the ${instanceName}
        ${instanceType} updated${instanceType} = new ${instanceType}();
<#if eid>
        updated${instanceType}.${pkSetter}(${instanceName}.${pkGetter}());
<#else>
<#list allIdAttributes as attribute>
        updated${instanceType}.${attribute.setter}(${instanceName}.${attribute.getter}());
</#list>
</#if>
<#list versionAttributes as attribute>
        updated${instanceType}.${attribute.setter}(${instanceName}.${attribute.getter}());
</#list>
<#list attributes as attribute>
        updated${instanceType}.${attribute.setter}(UPDATED_${attribute.NAME});
</#list>

        Response response = client.update${EntityClass}(updated${instanceType});
        assertThat(response, hasStatus(OK));

        // Validate the ${EntityClass} in the database
        List<${EntityClass}> ${entityInstancePlural} = ${entityRepository}.findAll();
        assertThat(${entityInstancePlural}.size(), is(databaseSizeBeforeUpdate));
        ${EntityClass} test${EntityClass} = ${entityInstancePlural}.get(${entityInstancePlural}.size() - 1);
<#list idAttributes as attribute>
        assertThat(test${EntityClass}<#if eid>.${pkGetter}()</#if>.${attribute.getter}(), is(${instanceName}<#if eid>.${pkGetter}()</#if>.${attribute.getter}()));
</#list>
<#list attributes as attribute>
        assertThat(test${EntityClass}.${attribute.getter}(), is(UPDATED_${attribute.NAME}));
</#list>
    }

    @Test
    @InSequence(6)
    public void remove${EntityClass}() throws Exception {
        int databaseSizeBeforeDelete = ${entityRepository}.findAll().size();

        // Delete the ${instanceName}
<#if allIdAttributes?size=1>
        Response response = client.remove${EntityClass}(${instanceName}.${pkGetter}());
<#else>
        Response response = client.remove${EntityClass}(
            <#list allIdAttributes as attribute>
                    ${instanceName}<#if eid>.${pkGetter}()</#if>.${attribute.getter}()<#if attribute_has_next>,</#if>
            </#list>
        );
</#if>
        assertThat(response, hasStatus(OK));

        // Validate the database is empty
        List<${EntityClass}> ${entityInstancePlural} = ${entityRepository}.findAll();
        assertThat(${entityInstancePlural}.size(), is(databaseSizeBeforeDelete - 1));
    }

}