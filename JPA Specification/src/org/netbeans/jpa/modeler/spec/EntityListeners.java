//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.netbeans.jpa.modeler.spec.extend.ReferenceClass;
import org.netbeans.jpa.modeler.spec.validator.EntityListenersValidator;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * @Target({TYPE}) @Retention(RUNTIME) public @interface EntityListeners {
 * Class[] value(); }
 *
 *
 *
 * <p>
 * Java class for entity-listeners complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="entity-listeners">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entity-listener" type="{http://java.sun.com/xml/ns/persistence/orm}entity-listener" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlJavaTypeAdapter(value = EntityListenersValidator.class)
public class EntityListeners {

    @XmlElement(name = "ln")
    private Set<ReferenceClass> entityListener;

    public static EntityListeners load(Element element, AnnotationMirror annotationMirror) {
        EntityListeners entityListeners = null;
        List entityListenersMirrorList = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "value");
        if (entityListenersMirrorList != null) {
            entityListeners = new EntityListeners();
            for (Object entityListenerObj : entityListenersMirrorList) {
                entityListeners.getEntityListener().add(new ReferenceClass(entityListenerObj.toString()));
            }
        }
        return entityListeners;
    }

    /**
     * Gets the value of the entityListener property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the entityListener property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEntityListener().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EntityListener }
     *
     * @return 
     */
    public Set<ReferenceClass> getEntityListener() {
        if (entityListener == null) {
            entityListener = new LinkedHashSet<>();
        }
        return this.entityListener;
    }

    /**
     * @param entityListener the entityListener to set
     */
    public void setEntityListener(Set<ReferenceClass> entityListener) {
        this.entityListener = entityListener;
    }

    public void addEntityListener(ReferenceClass entityListener) {
        this.getEntityListener().add(entityListener);
    }

    public void removeEntityListener(ReferenceClass entityListener) {
        this.getEntityListener().remove(entityListener);
    }

}
