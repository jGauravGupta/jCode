/**
 * Copyright [2014] Gaurav Gupta
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
package org.netbeans.jpa.modeler.spec.extend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import static java.util.function.Function.identity;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.XMLAttributes;
import org.netbeans.jcode.core.util.AttributeType.Type;
import static org.netbeans.jcode.core.util.AttributeType.Type.OTHER;
import static org.netbeans.jcode.core.util.AttributeType.getArrayType;
import static org.netbeans.jcode.core.util.AttributeType.getType;
import static org.netbeans.jcode.core.util.AttributeType.isArray;
import org.netbeans.jcode.core.util.JavaIdentifiers;
import org.netbeans.jpa.modeler.db.accessor.BasicSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.ElementCollectionSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.EmbeddedSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.ManyToManySpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.ManyToOneSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.OneToManySpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.OneToOneSpecAccessor;
import org.netbeans.jpa.modeler.spec.Basic;
import org.netbeans.jpa.modeler.spec.Convert;
import org.netbeans.jpa.modeler.spec.ElementCollection;
import org.netbeans.jpa.modeler.spec.Embedded;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.ManagedClass;
import org.netbeans.jpa.modeler.spec.ManyToMany;
import org.netbeans.jpa.modeler.spec.ManyToOne;
import org.netbeans.jpa.modeler.spec.OneToMany;
import org.netbeans.jpa.modeler.spec.OneToOne;
import org.netbeans.jpa.modeler.spec.Transient;
import org.netbeans.jpa.modeler.spec.workspace.WorkSpace;

/**
 *
 * @author Shiwani Gupta <jShiwaniGupta@gmail.com>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class PersistenceAttributes<T extends ManagedClass> extends Attributes<T> implements IPersistenceAttributes {

    @XmlElement(name = "basic")
    private List<Basic> basic;
    @XmlElement(name = "element-collection")
    private List<ElementCollection> elementCollection;
    @XmlElement(name = "embedded")
    private List<Embedded> embedded;
    @XmlElement(name = "transient")
    private List<Transient> _transient;

    @XmlElement(name = "many-to-one")
    private List<ManyToOne> manyToOne;
    @XmlElement(name = "one-to-many")
    private List<OneToMany> oneToMany;
    @XmlElement(name = "one-to-one")
    private List<OneToOne> oneToOne;
    @XmlElement(name = "many-to-many")
    private List<ManyToMany> manyToMany;

    @Override
    public List<Basic> getBasic() {
        if (basic == null) {
            this.basic = new ArrayList<>();
        }
        return this.basic;
    }

    @Override
    public void setBasic(List<Basic> basic) {
        this.basic = basic;
    }

    @Override
    public Optional<Basic> getBasic(String id) {
        if (basic != null) {
            return basic.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addBasic(Basic basic) {
        this.getBasic().add(basic);
        notifyListeners(basic, "addAttribute", null, null);
        basic.setAttributes(this);
    }

    @Override
    public void removeBasic(Basic basic) {
        this.getBasic().remove(basic);
        notifyListeners(basic, "removeAttribute", null, null);
    }

    @Override
    public List<Basic> getSuperBasic() {
        List<Basic> superVersion = new ArrayList();
        JavaClass currentClass = getJavaClass();
        do {
            if (currentClass instanceof ManagedClass) {
                ManagedClass<IPersistenceAttributes> managedClass = (ManagedClass) currentClass;
                superVersion.addAll(managedClass.getAttributes().getBasic());
            }
            currentClass = currentClass.getSuperclass();
        } while (currentClass != null);
        return superVersion;
    }

    @Override
    public List<ElementCollection> getElementCollection() {
        if (elementCollection == null) {
            this.elementCollection = new ArrayList<>();
        }
        return this.elementCollection;
    }

    @Override
    public void setElementCollection(List<ElementCollection> elementCollection) {
        this.elementCollection = elementCollection;
    }

    @Override
    public Optional<ElementCollection> getElementCollection(String id) {
        if (elementCollection != null) {
            return elementCollection.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addElementCollection(ElementCollection elementCollection) {
        this.getElementCollection().add(elementCollection);
        notifyListeners(elementCollection, "addAttribute", null, null);
        elementCollection.setAttributes(this);
    }

    @Override
    public void removeElementCollection(ElementCollection elementCollection) {
        this.getElementCollection().remove(elementCollection);
        notifyListeners(elementCollection, "removeAttribute", null, null);
    }

    @Override
    public List<Embedded> getEmbedded() {
        if (embedded == null) {
            this.embedded = new ArrayList<>();
        }
        return this.embedded;
    }

    @Override
    public void setEmbedded(List<Embedded> embedded) {
        this.embedded = embedded;
    }

    @Override
    public Optional<Embedded> getEmbedded(String id) {
        if (embedded != null) {
            return embedded.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addEmbedded(Embedded embedded) {
        if (embedded != null) {
            this.getEmbedded().add(embedded);
            notifyListeners(embedded, "addAttribute", null, null);
            embedded.setAttributes(this);
        }
    }

    @Override
    public void removeEmbedded(Embedded embedded) {
        this.getEmbedded().remove(embedded);
        notifyListeners(embedded, "removeAttribute", null, null);
    }

    @Override
    public List<Transient> getTransient() {
        if (_transient == null) {
            this._transient = new ArrayList<>();
        }
        return this._transient;
    }

    @Override
    public void setTransient(List<Transient> _transient) {
        this._transient = _transient;
    }

    @Override
    public Optional<Transient> getTransient(String id) {
        if (_transient != null) {
            return _transient.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addTransient(Transient _transient) {
        this.getTransient().add(_transient);
        notifyListeners(_transient, "addAttribute", null, null);
        _transient.setAttributes(this);
    }

    @Override
    public void removeTransient(Transient _transient) {
        this.getTransient().remove(_transient);
        notifyListeners(_transient, "removeAttribute", null, null);
    }

    @Override
    public List<ManyToOne> getManyToOne() {
        if (manyToOne == null) {
            this.manyToOne = new ArrayList<>();
        }
        return this.manyToOne;
    }

    @Override
    public void setManyToOne(List<ManyToOne> manyToOne) {
        this.manyToOne = manyToOne;
    }

    @Override
    public Optional<ManyToOne> getManyToOne(String id) {
        if (manyToOne != null) {
            return manyToOne.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addManyToOne(ManyToOne manyToOne) {
        getManyToOne().add(manyToOne);
        manyToOne.setAttributes(this);
    }

    @Override
    public void removeManyToOne(ManyToOne manyToOne) {
        getManyToOne().remove(manyToOne);
        manyToOne.setAttributes(null);
    }

    @Override
    public List<OneToMany> getOneToMany() {
        if (oneToMany == null) {
            this.oneToMany = new ArrayList<>();
        }
        return this.oneToMany;
    }

    @Override
    public void setOneToMany(List<OneToMany> oneToMany) {
        this.oneToMany = oneToMany;
    }

    @Override
    public Optional<OneToMany> getOneToMany(String id) {
        if (oneToMany != null) {
            return oneToMany.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addOneToMany(OneToMany oneToMany) {
        getOneToMany().add(oneToMany);
        oneToMany.setAttributes(this);
    }

    @Override
    public void removeOneToMany(OneToMany oneToMany) {
        getOneToMany().remove(oneToMany);
        oneToMany.setAttributes(null);
    }

    @Override
    public List<OneToOne> getOneToOne() {
        if (oneToOne == null) {
            this.oneToOne = new ArrayList<>();
        }
        return this.oneToOne;
    }

    @Override
    public void setOneToOne(List<OneToOne> oneToOne) {
        this.oneToOne = oneToOne;
    }

    @Override
    public Optional<OneToOne> getOneToOne(String id) {
        if (oneToOne != null) {
            return oneToOne.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addOneToOne(OneToOne oneToOne) {
        getOneToOne().add(oneToOne);
        oneToOne.setAttributes(this);
    }

    @Override
    public void removeOneToOne(OneToOne oneToOne) {
        getOneToOne().remove(oneToOne);
        oneToOne.setAttributes(null);
    }

    @Override
    public List<ManyToMany> getManyToMany() {
        if (manyToMany == null) {
            this.manyToMany = new ArrayList<>();
        }
        return this.manyToMany;
    }

    @Override
    public void setManyToMany(List<ManyToMany> manyToMany) {
        this.manyToMany = manyToMany;
    }

    @Override
    public Optional<ManyToMany> getManyToMany(String id) {
        if (manyToMany != null) {
            return manyToMany.stream().filter(a -> a.getId().equals(id)).findFirst();
        }
        return null;
    }

    @Override
    public void addManyToMany(ManyToMany manyToMany) {
        getManyToMany().add(manyToMany);
        manyToMany.setAttributes(this);
    }

    @Override
    public void removeManyToMany(ManyToMany manyToMany) {
        getManyToMany().remove(manyToMany);
        manyToMany.setAttributes(null);
    }

    @Override
    public List<RelationAttribute> getRelationAttributes() {
        List<RelationAttribute> relationAttributes = new ArrayList<>(this.getOneToOne());
        relationAttributes.addAll(this.getManyToOne());
        relationAttributes.addAll(this.getOneToMany());
        relationAttributes.addAll(this.getManyToMany());
        return relationAttributes;
    }

    @Override
    public List<SingleRelationAttribute> getDerivedRelationAttributes() {
        List<SingleRelationAttribute> relationAttribute = new ArrayList<>();
        relationAttribute.addAll(
                getOneToOne().stream()
                        .filter(SingleRelationAttribute::isPrimaryKey)
                        .collect(toList())
        );
        relationAttribute.addAll(
                getManyToOne().stream()
                        .filter(SingleRelationAttribute::isPrimaryKey)
                        .collect(toList())
        );
        return relationAttribute;
    }

    @Override
    public Set<String> getConnectedClass(Set<String> javaClasses) {
        javaClasses.add(getJavaClass().getFQN());
        if (getJavaClass().getSuperclass() != null && getJavaClass().getSuperclass() instanceof ManagedClass) {
            javaClasses.addAll(((ManagedClass) getJavaClass().getSuperclass()).getAttributes().getConnectedClass(javaClasses));
        }
        javaClasses.addAll(getBasicConnectedClass(javaClasses));
        javaClasses.addAll(getRelationConnectedClass(javaClasses));
        javaClasses.addAll(getEmbeddedConnectedClass(javaClasses));
        javaClasses.addAll(getElementCollectionConnectedClass(javaClasses));
        return javaClasses;
    }

    public Set<String> getRelationConnectedClass(Set<String> javaClasses) {
        Map<ManagedClass, String> releationClasses = getRelationAttributes().stream()
                .map(RelationAttribute::getConnectedEntity)
                .distinct()
                .filter(jc -> !javaClasses.contains(jc.getFQN()))
                .collect(toMap(identity(), JavaClass::getFQN));
        javaClasses.addAll(releationClasses.values());
        for (ManagedClass releationClass : releationClasses.keySet()) {
            javaClasses.addAll(releationClass.getAttributes().getConnectedClass(javaClasses));
        }
        return javaClasses;
    }

    @Override
    public Set<Entity> getRelationConnectedClassRef() {
        Set<Entity> javaClasses = getRelationAttributes().stream()
                .map(RelationAttribute::getConnectedEntity)
                .collect(toSet());
        javaClasses.addAll(getEmbedded().stream()
                .map(Embedded::getConnectedClass)
                .flatMap(c -> c.getAttributes().getRelationConnectedClassRef().stream())
                .collect(toSet()));
        return javaClasses;
    }

    public Set<String> getEmbeddedConnectedClass(Set<String> javaClasses) {
        Map<ManagedClass, String> releationClasses = getEmbedded().stream()
                .map(Embedded::getConnectedClass)
                .distinct()
                .filter(jc -> !javaClasses.contains(jc.getFQN()))
                .collect(toMap(identity(), JavaClass::getFQN));
        javaClasses.addAll(releationClasses.values());
        for (ManagedClass releationClass : releationClasses.keySet()) {
            javaClasses.addAll(releationClass.getAttributes().getConnectedClass(javaClasses));
        }
        return javaClasses;
    }

    public Set<String> getElementCollectionConnectedClass(Set<String> javaClasses) {
        Map<ManagedClass, String> elementCollectionClasses = getElementCollection().stream()
                .filter(ec -> ec.getConnectedClass() != null)
                .map(ElementCollection::getConnectedClass)
                .distinct()
                .filter(jc -> !javaClasses.contains(jc.getFQN()))
                .collect(toMap(identity(), JavaClass::getFQN));
        javaClasses.addAll(elementCollectionClasses.values());
        for (ManagedClass elementCollectionClass : elementCollectionClasses.keySet()) {
            javaClasses.addAll(elementCollectionClass.getAttributes().getConnectedClass(javaClasses));
        }
        return javaClasses;
    }

    public Set<String> getBasicConnectedClass(Set<String> javaClasses) {
        List<String> basicClasses = getBasic().stream()
                .map(BaseAttribute::getDataTypeLabel)
                .filter(dataType -> {
                    if (StringUtils.isNotEmpty(dataType)) {
                        dataType = isArray(dataType) ? getArrayType(dataType) : dataType;
                        Type type = getType(dataType);
                        if (type == OTHER) {
                            return !JavaIdentifiers.getPackageName(dataType).startsWith("java");
                        }
                    }
                    return false;
                })
                .distinct()
                .collect(Collectors.toList());
        javaClasses.addAll(basicClasses);
        return javaClasses;
    }

    @Override
    public Optional<RelationAttribute> getRelationAttribute(String id) {
        return getRelationAttributes().stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    @Override
    public void removeRelationAttribute(RelationAttribute relationAttribute) {
        if (relationAttribute instanceof ManyToMany) {
            this.getManyToMany().remove((ManyToMany) relationAttribute);
            notifyListeners(relationAttribute, "removeAttribute", null, null);
        } else if (relationAttribute instanceof OneToMany) {
            this.getOneToMany().remove((OneToMany) relationAttribute);
            notifyListeners(relationAttribute, "removeAttribute", null, null);
        } else if (relationAttribute instanceof ManyToOne) {
            this.getManyToOne().remove((ManyToOne) relationAttribute);
            notifyListeners(relationAttribute, "removeAttribute", null, null);
        } else if (relationAttribute instanceof OneToOne) {
            this.getOneToOne().remove((OneToOne) relationAttribute);
            notifyListeners(relationAttribute, "removeAttribute", null, null);
        } else {
            throw new IllegalStateException("Invalid Type Relation Attribute");
        }
    }

    @Override
    public void addRelationAttribute(RelationAttribute relationAttribute) {
        if (relationAttribute instanceof ManyToMany) {
            this.addManyToMany((ManyToMany) relationAttribute);
            notifyListeners(relationAttribute, "addAttribute", null, null);
        } else if (relationAttribute instanceof OneToMany) {
            this.addOneToMany((OneToMany) relationAttribute);
            notifyListeners(relationAttribute, "addAttribute", null, null);
        } else if (relationAttribute instanceof ManyToOne) {
            this.addManyToOne((ManyToOne) relationAttribute);
            notifyListeners(relationAttribute, "addAttribute", null, null);
        } else if (relationAttribute instanceof OneToOne) {
            this.addOneToOne((OneToOne) relationAttribute);
            notifyListeners(relationAttribute, "addAttribute", null, null);
        } else {
            throw new IllegalStateException("Invalid Type Relation Attribute");
        }
    }

    @Override
    public List<Attribute> getAllAttribute(boolean includeParentClassAttibute) {
        List<Attribute> attributes = super.getAllAttribute(includeParentClassAttibute);
        attributes.addAll(this.getBasic());
        attributes.addAll(this.getElementCollection());
        attributes.addAll(this.getEmbedded());
        attributes.addAll(this.getRelationAttributes());
        attributes.addAll(this.getTransient());
        return attributes;
    }

    @Override
    public XMLAttributes getAccessor(WorkSpace workSpace) {
        XMLAttributes attr = new XMLAttributes();
        attr.setBasicCollections(new ArrayList<>());
        attr.setBasicMaps(new ArrayList<>());
        attr.setTransformations(new ArrayList<>());
        attr.setVariableOneToOnes(new ArrayList<>());
        attr.setStructures(new ArrayList<>());
        attr.setArrays(new ArrayList<>());
        attr.setBasics(new ArrayList<>());
        attr.setElementCollections(new ArrayList<>());
        attr.setEmbeddeds(new ArrayList<>());
        attr.setTransients(new ArrayList<>());
        attr.setManyToManys(new ArrayList<>());
        attr.setManyToOnes(new ArrayList<>());
        attr.setOneToManys(new ArrayList<>());
        attr.setOneToOnes(new ArrayList<>());
        return attr;
    }

    @Override
    public XMLAttributes updateAccessor(WorkSpace workSpace, XMLAttributes attr, boolean inherit) {
        attr.getBasics().addAll(getBasic()
                .stream()
                .map(bsc -> BasicSpecAccessor.getInstance(bsc, inherit))
                .collect(toList()));
        attr.getElementCollections().addAll(getElementCollection()
                .stream()
                .filter(ec -> workSpace == null || ec.getConnectedClass() == null || workSpace.hasItem(ec.getConnectedClass()))
                .map(ec -> ElementCollectionSpecAccessor.getInstance(ec, inherit))
                .collect(toList()));
        attr.getEmbeddeds().addAll(getEmbedded()
                .stream()
                .filter(emb -> workSpace == null || workSpace.hasItem(emb.getConnectedClass()))
                .map(emb -> EmbeddedSpecAccessor.getInstance(emb, inherit))
                .collect(toList()));
//      Skip Transient
        attr.getManyToManys().addAll(getManyToMany()
                .stream()
                .filter(mtm -> workSpace == null || workSpace.hasItem(mtm.getConnectedEntity()))
                .map(mtm -> ManyToManySpecAccessor.getInstance(mtm, inherit))
                .collect(toList()));
        attr.getManyToOnes().addAll(getManyToOne()
                .stream()
                .filter(mto -> workSpace == null || workSpace.hasItem(mto.getConnectedEntity()))
                .map(mto -> ManyToOneSpecAccessor.getInstance(mto, inherit))
                .collect(toList()));
        attr.getOneToManys().addAll(getOneToMany()
                .stream()
                .filter(otm -> workSpace == null || workSpace.hasItem(otm.getConnectedEntity()))
                .map(otm -> OneToManySpecAccessor.getInstance(otm, inherit))
                .collect(toList()));
        attr.getOneToOnes().addAll(getOneToOne()
                .stream()
                .filter(oto -> workSpace == null || workSpace.hasItem(oto.getConnectedEntity()))
                .map(oto -> OneToOneSpecAccessor.getInstance(oto, inherit))
                .collect(toList()));
        return attr;
    }

    @Override
    public List<Attribute> getNonRelationAttributes() {
        List<Attribute> attributes = new ArrayList<>(this.getBasic());
        attributes.addAll(this.getElementCollection().stream().filter(ec -> ec.getConnectedClass() == null).collect(toList()));
        return attributes;
    }

    @Override
    public Set<String> getAllConvert() {
        Set<String> converts = new HashSet();
        for (Basic bc : getBasic()) {
            Convert convert = bc.getConvert();
            if (StringUtils.isNotBlank(convert.getConverter())) {
                converts.add(convert.getConverter());
            }
        }
        for (ElementCollection ec : getElementCollection()) {
            converts.addAll(ec.getConverts().stream().filter(con -> StringUtils.isNotBlank(con.getConverter())).map(Convert::getConverter).collect(toSet()));
            converts.addAll(ec.getMapKeyConverts().stream().filter(con -> StringUtils.isNotBlank(con.getConverter())).map(Convert::getConverter).collect(toSet()));
        }
        for (Embedded ec : getEmbedded()) {
            converts.addAll(ec.getConverts().stream().filter(con -> con.getConverter() != null).map(Convert::getConverter).collect(toSet()));
        }
        for (OneToMany otm : getOneToMany()) {
            converts.addAll(otm.getMapKeyConverts().stream().filter(con -> con.getConverter() != null).map(Convert::getConverter).collect(toSet()));
        }
        for (ManyToMany mtm : getManyToMany()) {
            converts.addAll(mtm.getMapKeyConverts().stream().filter(con -> con.getConverter() != null).map(Convert::getConverter).collect(toSet()));
        }
        return converts;
    }

    @Override
    public void removeNonOwnerAttribute(Set<JavaClass> filterJavaClasses) {
        Predicate<RelationAttribute> filterOwner = attr -> attr.isOwner()
                || (attr.getConnectedAttribute() != null && filterJavaClasses.contains(attr.getConnectedEntity()));//either owner or contains in specified class set

        setOneToOne(
                getOneToOne()
                        .stream()
                        .filter(filterOwner)
                        .collect(toList())
        );
        setOneToMany(
                getOneToMany()
                        .stream()
                        .filter(filterOwner)
                        .collect(toList())
        );
        setManyToOne(
                getManyToOne()
                        .stream()
                        .filter(filterOwner)
                        .collect(toList())
        );
        setManyToMany(
                getManyToMany()
                        .stream()
                        .filter(filterOwner)
                        .collect(toList())
        );
    }
}
