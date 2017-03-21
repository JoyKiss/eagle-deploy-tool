//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.6 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.22 时间 01:41:34 PM CST 
//


package com.linkstec.raptor.eagle.tool.job;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}remoteservers"/>
 *         &lt;element ref="{}workflow"/>
 *       &lt;/sequence>
 *       &lt;attribute name="description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "remoteservers",
    "workflow"
})
@XmlRootElement(name = "job")
public class Job {

    @XmlElement(required = true)
    protected Remoteservers remoteservers;
    @XmlElement(required = true)
    protected Workflow workflow;
    @XmlAttribute(name = "description", required = true)
    protected String description;
    @XmlAttribute(name = "name", required = true)
    protected String name;

    /**
     * 获取remoteservers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Remoteservers }
     *     
     */
    public Remoteservers getRemoteservers() {
        return remoteservers;
    }

    /**
     * 设置remoteservers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Remoteservers }
     *     
     */
    public void setRemoteservers(Remoteservers value) {
        this.remoteservers = value;
    }

    /**
     * 获取workflow属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Workflow }
     *     
     */
    public Workflow getWorkflow() {
        return workflow;
    }

    /**
     * 设置workflow属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Workflow }
     *     
     */
    public void setWorkflow(Workflow value) {
        this.workflow = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
