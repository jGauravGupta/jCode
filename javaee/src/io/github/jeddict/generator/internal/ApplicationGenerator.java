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
package io.github.jeddict.generator.internal;

import io.github.jeddict.jcode.util.POMManager;
import io.github.jeddict.jcode.AbstractGenerator;
import io.github.jeddict.jcode.annotation.ConfigData;
import io.github.jeddict.jcode.Generator;
import io.github.jeddict.jcode.TechContext;
import io.github.jeddict.jcode.ApplicationConfigData;
import io.github.jeddict.jcode.LayerConfigData;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.toSet;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import io.github.jeddict.jcode.task.progress.ProgressHandler;
import io.github.jeddict.jpa.spec.EntityMappings;
import io.github.jeddict.jpa.util.PersistenceHelper;
import io.github.jeddict.rest.util.RestUtils;
import io.github.jeddict.web.dd.util.WebDDUtil;
import org.openide.util.Exceptions;

/**
 *
 * @author Gaurav Gupta
 */
public class ApplicationGenerator extends AbstractGenerator {

    private ApplicationConfigData appConfigData;
    private ProgressHandler handler;
    private Map<Class<? extends LayerConfigData>, LayerConfigData> layerConfigData;
    
    private Project targetProject;

    private SourceGroup targetSource;

    private Project gatewayProject;

    private SourceGroup gatewaySource;
    
    @Override
    public void initialize(ApplicationConfigData applicationConfigData, ProgressHandler progressHandler) {
        this.appConfigData = applicationConfigData;
        this.handler = progressHandler;
//        this.project = applicationConfigData.getTargetProject();
        targetProject = appConfigData.getTargetProject();
        targetSource = appConfigData.getTargetSourceGroup();
        gatewayProject = appConfigData.getGatewayProject();
        gatewaySource = appConfigData.getGatewaySourceGroup();
        injectData();
    }

    @Override
    public void preGeneration() {
        TechContext bussinesLayerConfig = appConfigData.getBussinesTechContext();
        if (bussinesLayerConfig == null) {
            return;
        }
        bussinesLayerConfig.getGenerator().preExecute();
        for (TechContext context : bussinesLayerConfig.getSiblingTechContext()) {
            context.getGenerator().preExecute();
        }

        TechContext controllerLayerConfig = appConfigData.getControllerTechContext();
        if (controllerLayerConfig == null) {
            return;
        }
        controllerLayerConfig.getGenerator().preExecute();
        for (TechContext context : controllerLayerConfig.getSiblingTechContext()) {
            context.getGenerator().preExecute();
        }

        TechContext viewerLayerConfig = appConfigData.getViewerTechContext();
        if (viewerLayerConfig == null) {
            return;
        }
        viewerLayerConfig.getGenerator().preExecute();
        for (TechContext context : viewerLayerConfig.getSiblingTechContext()) {
            context.getGenerator().preExecute();
        }
    }

    @Override
    public void generate() {
        try {
            if (handler != null) {
                initProgressReporting(handler);
            }
            EntityMappings entityMappings = appConfigData.getEntityMappings();
            Set<String> entities = entityMappings.getFQEntity().collect(toSet());
            //Make necessary changes to the persistence.xml
            if (appConfigData.isMonolith() || appConfigData.isMicroservice()) {
                new PersistenceHelper(targetProject).configure(entities, !RestUtils.hasJTASupport(targetProject));
            }
            if (appConfigData.isGateway()) {
                new PersistenceHelper(gatewayProject).configure(entities, !RestUtils.hasJTASupport(gatewayProject));
            }
            
            generateCRUD();
            if (appConfigData.isMonolith() || appConfigData.isMicroservice()) {
                if (POMManager.isMavenProject(appConfigData.getTargetProject())) {
                    POMManager.reload(appConfigData.getTargetProject());
                }
            }
            if (appConfigData.isGateway() || appConfigData.isMicroservice()) {
                if (POMManager.isMavenProject(appConfigData.getGatewayProject())) {
                    POMManager.reload(appConfigData.getGatewayProject());
                }
            }
                        
            appConfigData.getWebDescriptorContent().forEach((project, webDescriptorContent) -> {
                if (!webDescriptorContent.toString().isEmpty()) {
                    WebDDUtil.createDD(project, "/io/github/jeddict/template/web/descriptor/_web.xml.ftl", Collections.singletonMap("content", webDescriptorContent));
                }
            });
            appConfigData.getWebDescriptorTestContent().forEach((project, webDescriptorTestContent) -> {
                if (!webDescriptorTestContent.toString().isEmpty()) {
                    WebDDUtil.createTestDD(project, "/io/github/jeddict/template/web/descriptor/_web.xml.ftl", Collections.singletonMap("content", webDescriptorTestContent));
                }
            });
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void postGeneration() {
        
        TechContext bussinesLayerConfig = appConfigData.getBussinesTechContext();
        if (bussinesLayerConfig == null) {
            return;
        }
        bussinesLayerConfig.getGenerator().postExecute();
        for (TechContext context : bussinesLayerConfig.getSiblingTechContext()) {
            context.getGenerator().postExecute();
        }

        TechContext controllerLayerConfig = appConfigData.getControllerTechContext();
        if (controllerLayerConfig == null) {
            return;
        }
        controllerLayerConfig.getGenerator().postExecute();
        for (TechContext context : controllerLayerConfig.getSiblingTechContext()) {
            context.getGenerator().postExecute();
        }

        TechContext viewerLayerConfig = appConfigData.getViewerTechContext();
        if (viewerLayerConfig == null) {
            return;
        }
        viewerLayerConfig.getGenerator().postExecute();
        for (TechContext context : viewerLayerConfig.getSiblingTechContext()) {
            context.getGenerator().postExecute();
        }
        
        String profiles = appConfigData.getProfiles();
        String goals = appConfigData.getGoals();
        handler.addDynamicVariable("profiles", profiles.isEmpty() ? "" : "-P " + profiles);
        handler.addDynamicVariable("buildProperties", appConfigData.getBuildProperties());
        handler.addDynamicVariable("goals", goals.isEmpty() ? "" : goals);
        finishProgressReporting();
    }

    private void injectData() {
        layerConfigData = new HashMap<>();
        TechContext bussinesLayerConfig = appConfigData.getBussinesTechContext();
        if (bussinesLayerConfig == null) {
            return;
        }
        storeLayerConfigData(bussinesLayerConfig);
        
        TechContext controllerLayerConfig = appConfigData.getControllerTechContext();
        TechContext viewerLayerConfig = null;
        if (controllerLayerConfig != null) {
            controllerLayerConfig.getPanel().getConfigData().setParentLayerConfigData(bussinesLayerConfig.getPanel().getConfigData());
            storeLayerConfigData(controllerLayerConfig);

            viewerLayerConfig = appConfigData.getViewerTechContext();
            if (viewerLayerConfig != null) {
                viewerLayerConfig.getPanel().getConfigData().setParentLayerConfigData(controllerLayerConfig.getPanel().getConfigData());
                storeLayerConfigData(viewerLayerConfig);
            }
        }

        inject(bussinesLayerConfig);
        if (controllerLayerConfig != null) {
            inject(controllerLayerConfig);
        }
        if (viewerLayerConfig != null) {
            inject(viewerLayerConfig);
        }
    }
    
    private void storeLayerConfigData(TechContext rootTechContext) {
        layerConfigData.put(rootTechContext.getPanel().getConfigData().getClass(), rootTechContext.getPanel().getConfigData());
        for (TechContext context : rootTechContext.getSiblingTechContext()) {
            storeLayerConfigData(context);
        }
    }
    
    private void inject(TechContext rootTechContext) {
        inject(rootTechContext.getGenerator(), appConfigData, layerConfigData, handler);
        for (TechContext context : rootTechContext.getSiblingTechContext()) {
            inject(context);
        }
    }

    private void execute(TechContext rootTechContext) throws IOException{
        rootTechContext.getGenerator().execute();
        for (TechContext siblingTechContext : rootTechContext.getSiblingTechContext()) {
            execute(siblingTechContext);
        }
    }
    
    private void generateCRUD() throws IOException {
        TechContext bussinesLayerConfig = appConfigData.getBussinesTechContext();
        if (bussinesLayerConfig == null) {
            return;
        }
        execute(bussinesLayerConfig);

        TechContext controllerLayerConfig = appConfigData.getControllerTechContext();
        if (controllerLayerConfig == null) {
            return;
        }
        execute(controllerLayerConfig);

        TechContext viewerLayerConfig = appConfigData.getViewerTechContext();
        if (viewerLayerConfig == null) {
            return;
        }
        execute(viewerLayerConfig);

//        PersistenceUtil.getPersistenceUnit(getProject(), applicationConfigData.getEntityMappings().getPersistenceUnitName()).ifPresent(pud -> {
//            try {
//                ProviderUtil.getPUDataObject(getProject()).save();
//            } catch (InvalidPersistenceXmlException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        });
    }

    private List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass()!= Object.class) {
            fields = getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    private void inject(Generator instance, ApplicationConfigData applicationConfigData, Map<Class<? extends LayerConfigData>, LayerConfigData> layerConfigData, ProgressHandler handler) {
        List<Field> fields = getAllFields(new LinkedList<>(), instance.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(ConfigData.class)) {
                field.setAccessible(true);
                try {
                    if (field.getGenericType() == ApplicationConfigData.class) {
                        field.set(instance, applicationConfigData);
                    } else if (field.getGenericType() == EntityMappings.class) {
                        field.set(instance, applicationConfigData.getEntityMappings());
                    } else if (field.getType().isAssignableFrom(handler.getClass())) {
                        field.set(instance, handler);
                    } else if (LayerConfigData.class.isAssignableFrom(field.getType())) {
                        field.set(instance, layerConfigData.get(field.getType()));
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected int getTotalWorkUnits() {
        float unit = 1.5f;
        float webUnit = 5f;
        float count = appConfigData.getEntityMappings().getGeneratedEntity().count();
        if (appConfigData.getBussinesTechContext() != null) {
            count = count + count * unit;
        }
        if (appConfigData.getControllerTechContext() != null) {
            count = count + count * unit;
        }
        if (appConfigData.getViewerTechContext() != null) {
            count = count + count * webUnit;
        }
        return (int) count;
    }

}
