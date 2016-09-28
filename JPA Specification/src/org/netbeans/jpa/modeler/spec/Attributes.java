//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.internal.jpa.metadata.accessors.classes.XMLAttributes;
import static org.netbeans.jcode.jpa.JPAConstants.BASIC_FQN;
import static org.netbeans.jcode.jpa.JPAConstants.EMBEDDED_FQN;
import static org.netbeans.jcode.jpa.JPAConstants.EMBEDDED_ID_FQN;
import static org.netbeans.jcode.jpa.JPAConstants.ID_FQN;
import org.netbeans.jpa.modeler.db.accessor.EmbeddedIdSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.IdSpecAccessor;
import org.netbeans.jpa.modeler.db.accessor.VersionSpecAccessor;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.extend.BaseAttributes;
import org.netbeans.jpa.modeler.spec.extend.IPersistenceAttributes;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * This element contains the entity field or property mappings. It may be
 * sparsely populated to include only a subset of the fields or properties. If
 * metadata-complete for the entity is true then the remainder of the attributes
 * will be defaulted according to the default rules.
 *
 *
 *
 * <p>
 * Java class for attributes complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="attributes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="id" type="{http://java.sun.com/xml/ns/persistence/orm}id" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="embedded-id" type="{http://java.sun.com/xml/ns/persistence/orm}embedded-id" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="basic" type="{http://java.sun.com/xml/ns/persistence/orm}basic" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="version" type="{http://java.sun.com/xml/ns/persistence/orm}version" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="many-to-one" type="{http://java.sun.com/xml/ns/persistence/orm}many-to-one" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="one-to-many" type="{http://java.sun.com/xml/ns/persistence/orm}one-to-many" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="one-to-one" type="{http://java.sun.com/xml/ns/persistence/orm}one-to-one" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="many-to-many" type="{http://java.sun.com/xml/ns/persistence/orm}many-to-many" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="element-collection" type="{http://java.sun.com/xml/ns/persistence/orm}element-collection" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="embedded" type="{http://java.sun.com/xml/ns/persistence/orm}embedded" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transient" type="{http://java.sun.com/xml/ns/persistence/orm}transient" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "attributes", propOrder = {
    "description",
    "id",
    "embeddedId",
    "version"
})
public class Attributes extends BaseAttributes implements IPersistenceAttributes {

    protected String description;
    protected List<Id> id;
    @XmlElement(name = "embedded-id")
    protected EmbeddedId embeddedId; 
    protected List<Version> version;

    @Override
    public void load(EntityMappings entityMappings, TypeElement typeElement, boolean fieldAccess) {
        Set<String> mapsId = new HashSet<>();
        VariableElement embeddedIdVariableElement = null;
        List<Element> elements = new ArrayList<>();
        for (ExecutableElement method : JavaSourceParserUtil.getMethods(typeElement)) {
            try {
            String methodName = method.getSimpleName().toString();
            if (methodName.startsWith("get") || methodName.startsWith("is")) {
                Element element;
                VariableElement variableElement = JavaSourceParserUtil.guessField(method);
                 // Issue Fix #5976 Start
                /**
                 * #5976 FIX fixed NPE when method is not attached to field
                 * Transient or in                 *
                 * @author Juraj Balaz <georgeeb@java.net>
                 * @since Thu, 17 Apr 2014 14:07:11 +0000
                 */
                // skip processing if the method is not joined with field
                // might be transient method or method implementation from some interface
                if (variableElement == null) {
                    continue;
                }
                // Issue Fix #5976 End
                if (fieldAccess) {
                    element = variableElement;
                } else {
                    element = method;
                }
                elements.add(element);
            } 
            }catch(TypeNotPresentException ex){
                //Ignore Erroneous variable Type : ClassA have relation with List<ClassB>. And ClassB does not exist on classpath 
                //LOG TODO access to IO
            }
        }
        //this is not manadatory but provided support for blog snippet which have no method
        if(!fieldAccess && elements.isEmpty()){//if no elements then add all fields
            elements.addAll(JavaSourceParserUtil.getFields(typeElement));
        }
        for(Element element : elements){
                VariableElement variableElement ;
                if(element instanceof VariableElement){
                   variableElement = (VariableElement)element;;
                } else {
                    variableElement = JavaSourceParserUtil.guessField((ExecutableElement)element);
                }
                 
                if (JavaSourceParserUtil.isAnnotatedWith(element, ID_FQN)
                        && !(JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.OneToOne")
                        || JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.ManyToOne"))) {
                    this.addId(Id.load(element, variableElement));
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, BASIC_FQN)) {
                    this.addBasic(Basic.load(element, variableElement));
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.Transient")) {
                    this.addTransient(Transient.load(element, variableElement));
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.Version")) {
                    this.addVersion(Version.load(element, variableElement));
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.ElementCollection")) {
                    this.addElementCollection(ElementCollection.load(entityMappings, element, variableElement));
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.OneToOne")) {
                    OneToOne oneToOneObj = new OneToOne().load(entityMappings, element, variableElement, null);
                    this.addOneToOne(oneToOneObj);
                    if(StringUtils.isNotBlank(oneToOneObj.getMapsId())){
                        mapsId.add(oneToOneObj.getMapsId());
                    } else {
                        mapsId.add(oneToOneObj.getName());
                    }
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.ManyToOne")) {
                    ManyToOne manyToOneObj = new ManyToOne().load(entityMappings, element, variableElement, null);
                    this.addManyToOne(manyToOneObj);
                    if(StringUtils.isNotBlank(manyToOneObj.getMapsId())){
                        mapsId.add(manyToOneObj.getMapsId());
                    } else {
                        mapsId.add(manyToOneObj.getName());
                    }
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.OneToMany")) {
                    OneToMany oneToManyObj = new OneToMany().load(entityMappings, element, variableElement, null);
                    this.addOneToMany(oneToManyObj);
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, "javax.persistence.ManyToMany")) {
                    ManyToMany manyToManyObj = new ManyToMany().load(entityMappings, element, variableElement, null);
                    this.addManyToMany(manyToManyObj);
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, EMBEDDED_ID_FQN)) {
                    this.setEmbeddedId(EmbeddedId.load(entityMappings, element, variableElement));
                    embeddedIdVariableElement = variableElement;
                } else if (JavaSourceParserUtil.isAnnotatedWith(element, EMBEDDED_FQN)) {
                    this.addEmbedded(Embedded.load(entityMappings, element, variableElement));
                } else {
                    this.addBasic(Basic.load(element, variableElement)); //Default Annotation
                }

        }
        

        if (this.getEmbeddedId() != null) {
            for (VariableElement variableElement : JavaSourceParserUtil.getFields(JavaSourceParserUtil.getAttributeTypeElement(embeddedIdVariableElement))) {
                if (!mapsId.contains(variableElement.getSimpleName().toString())) {
                    this.addId(Id.load(variableElement, variableElement));
                }
            }
        }

    }

    @Override
    public List<Attribute> findAllAttribute(String name) {
        return findAllAttribute(name,false);
    }
    
    @Override
    public List<Attribute> findAllAttribute(String name,boolean includeParentClassAttibute) {
       List<Attribute> attributes = super.findAllAttribute(name,includeParentClassAttibute);
        if (id != null) {
            for (Id id_TMP : id) {
                if (id_TMP.getName() != null && id_TMP.getName().equals(name)) {
                    attributes.add(id_TMP);
                }
            }
        }
        if (version != null) {
            for (Version version_TMP : version) {
                if (version_TMP.getName() != null && version_TMP.getName().equals(name)) {
                    attributes.add(version_TMP);
                }
            }
        }
        if (embeddedId != null) {
            if (embeddedId.getName() != null && embeddedId.getName().equals(name)) {
                attributes.add(embeddedId);
            }
        }

        return attributes;
    }

    @Override
    public boolean isAttributeExist(String name) {
        if (super.isAttributeExist(name)) {
            return true;
        }
        if (id != null) {
            for (Id id_TMP : id) {
                if (id_TMP.getName() != null && id_TMP.getName().equals(name)) {
                    return true;
                }
            }
        }
        if (version != null) {
            for (Version version_TMP : version) {
                if (version_TMP.getName() != null && version_TMP.getName().equals(name)) {
                    return true;
                }
            }
        }
        if (embeddedId != null) {
            if (embeddedId.getName() != null && embeddedId.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    @Override
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the id property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the id property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Id }
     *
     *
     */
    @Override
    public List<Id> getId() {
        if (id == null) {
            id = new ArrayList<Id>();
        }
        return this.id;
    }

    @Override
    public void addId(Id id) {
        this.getId().add(id);
        notifyListeners(id, "addAttribute", null, null);
        id.setAttributes(this);
    }

    @Override
    public void removeId(Id id) {
        this.getId().remove(id);
        notifyListeners(id, "removeAttribute", null, null);
    }

    public Optional<Id> getId(String id_) {
        if (id != null) {
            return id.stream().filter(a -> a.getId().equals(id_)).findFirst();
        }
        return null;
    }

    /**
     * Gets the value of the embeddedId property.
     *
     * @return possible object is {@link EmbeddedId }
     *
     */
    @Override
    public EmbeddedId getEmbeddedId() {
        return embeddedId;
    }

    /**
     * Sets the value of the embeddedId property.
     *
     * @param value allowed object is {@link EmbeddedId }
     *
     */
    @Override
    public void setEmbeddedId(EmbeddedId value) {
        this.embeddedId = value;
        if (value == null) {
            notifyListeners(null, "removeAttribute", null, null);
        } else {
            notifyListeners(embeddedId, "addAttribute", null, null);
            value.setAttributes(this);
        }

    }

    /**
     * Gets the value of the version property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the version property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVersion().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link Version }
     *
     *
     */
    @Override
    public List<Version> getVersion() {
        if (version == null) {
            version = new ArrayList<Version>();
        }
        return this.version;
    }

    @Override
    public void addVersion(Version version) {
        this.getVersion().add(version);
        notifyListeners(version, "addAttribute", null, null);
        version.setAttributes(this);
    }

    @Override
    public void removeVersion(Version version) {
        this.getVersion().remove(version);
        notifyListeners(version, "removeAttribute", null, null);
    }

    public Optional<Version> getVersion(String id_) {
        if (version != null) {
            return version.stream().filter(a -> a.getId().equals(id_)).findFirst();
        }
        return null;
    }

    @Override
    public List<Attribute> getAllAttribute() {//#ATTRIBUTE_SEQUENCE_FLOW#
        List<Attribute> attributes = new ArrayList<>();
        if (this.getEmbeddedId() != null) {
            attributes.add(this.getEmbeddedId());
        }
        attributes.addAll(this.getId());
        attributes.addAll(super.getAllAttribute());
        attributes.addAll(this.getVersion());
        return attributes;
    }

    @Override
    public XMLAttributes getAccessor() {
        return getAccessor(false);
    }

    /**
     * Remove inherit functionality , once eclipse support dynamic mapped super
     * class
     *
     */
    public XMLAttributes getAccessor(boolean inherit) {
        XMLAttributes attr = super.getAccessor();
        attr.setIds(new ArrayList<>());
        attr.setVersions(new ArrayList<>());
        return updateAccessor(attr, inherit);
    }

    public XMLAttributes updateAccessor(XMLAttributes attr) {
        return updateAccessor(attr, false);
    }

    @Override
    public XMLAttributes updateAccessor(XMLAttributes attr, boolean inherit) {
        super.updateAccessor(attr, inherit);
        return processAccessor(attr, inherit);
    }

    private XMLAttributes processAccessor(XMLAttributes attr, boolean inherit) {
        if (getEmbeddedId() != null) {
            attr.setEmbeddedId(EmbeddedIdSpecAccessor.getInstance(getEmbeddedId()));
        } else {
            attr.getIds().addAll(getId().stream().map(id -> IdSpecAccessor.getInstance(id, inherit)).collect(toList()));
        }
        attr.getVersions().addAll(getVersion().stream().map(VersionSpecAccessor::getInstance).collect(toList()));

        return attr;
    }

    public List<Attribute> getNonRelationAttributes() {
        List<Attribute> attributes = new ArrayList<Attribute>(this.getId());
        attributes.addAll(this.getBasic());
        attributes.addAll(this.getElementCollection().stream().filter(ec -> ec.getConnectedClass() == null).collect(toList()));
        attributes.addAll(this.getVersion());
        return attributes;
    }
    
    public boolean hasCompositePrimaryKey(){
        return this.getId().size() + (this.getEmbeddedId()!=null? 1: 0) +
        this.getOneToOne().stream().filter(attr -> attr.isPrimaryKey()).count() +
        this.getManyToOne().stream().filter(attr -> attr.isPrimaryKey()).count() > 1;
    }
    
    public Attribute getIdField(){
         if(this.getId().size()>1){
             if(this.getEmbeddedId()!=null){
                 return this.getEmbeddedId();
             } else {
                 IdClass idClass = ((IdentifiableClass)this.getJavaClass()).getIdClass();
                 Id attribute = new Id();
                 attribute.setName(idClass.getClazz());
                 attribute.setAttributeType(idClass.getClazz());
                 return attribute;
             }
         } else if(this.getId().size() == 1){
             return this.getId().get(0);
         } else {
             return null;
         }
    }
    
    
}