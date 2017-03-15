//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.6 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.22 时间 01:41:34 PM CST 
//


package com.linkstec.raptor.eagle.tool.job;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element ref="{}server-ref"/>
 *         &lt;element ref="{}shell"/>
 *       &lt;/sequence>
 *       &lt;attribute name="class" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
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
    "serverRef",
    "upload",
    "shell"
})
@XmlRootElement(name = "step")
public class Step {

    @XmlElement(name = "server-ref", required = false)
    protected ServerRef serverRef;
    @XmlElement(name = "upload", required = false)
    protected List<Upload> upload;
    @XmlElement(required = false)
    protected String shell;
    @XmlAttribute(name = "class", required = false)
    protected String clazz;
    @XmlAttribute(name = "includeJob", required = false)
    protected String includeJob;
    @XmlAttribute(name = "description")
    @XmlSchemaType(name = "anySimpleType")
    protected String description;
    @XmlAttribute(name = "name", required = false)
    protected String name;

    /**
     * 获取serverRef属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ServerRef }
     *     
     */
    public ServerRef getServerRef() {
        return serverRef;
    }

    /**
     * 设置serverRef属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ServerRef }
     *     
     */
    public void setServerRef(ServerRef value) {
        this.serverRef = value;
    }

    /**
     * 获取shell属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShell() {
        return shell;
    }

    /**
     * 设置shell属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShell(String value) {
        this.shell = value;
    }

    /**
     * 获取clazz属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClazz() {
        return clazz;
    }

    /**
     * 设置clazz属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClazz(String value) {
        this.clazz = value;
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

	public String getIncludeJob() {
		return includeJob;
	}

	public void setIncludeJob(String includeJob) {
		this.includeJob = includeJob;
	}

	 public List<Upload> getUpload() {
	        if (upload == null) {
	        	upload = new ArrayList<Upload>();
	        }
	        return this.upload;
	    }

}
