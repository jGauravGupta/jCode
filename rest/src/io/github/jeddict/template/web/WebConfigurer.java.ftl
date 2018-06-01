package ${package};

import java.util.EnumSet;
import javax.inject.Inject;
import static javax.servlet.DispatcherType.ASYNC;
import static javax.servlet.DispatcherType.FORWARD;
import static javax.servlet.DispatcherType.REQUEST;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
<#if metrics>import ${appPackage}${InstrumentedFilter_FQN};</#if>

/**
 * Configuration of web application
 */
@WebListener
public class WebConfigurer implements ServletContextListener {

    @Inject
    private Logger log;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        <#if metrics>initMetrics(sce.getServletContext());</#if>
        log.info("Web application fully configured");
    }

    <#if metrics>
    /**
     * Initializes Metrics.
     */
    private void initMetrics(ServletContext servletContext) {
        log.debug("Registering Metrics Filter");
        FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("Instrumented Metrics Filter",InstrumentedFilter.class);
        metricsFilter.addMappingForUrlPatterns(EnumSet.of(REQUEST, FORWARD, ASYNC), true, "/*");
        metricsFilter.setAsyncSupported(true);
    }
    </#if>

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
