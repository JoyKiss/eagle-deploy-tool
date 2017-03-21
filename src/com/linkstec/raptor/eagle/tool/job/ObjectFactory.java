//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.6 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.22 时间 01:41:34 PM CST 
//


package com.linkstec.raptor.eagle.tool.job;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.linkstec.raptor.eagle.tool.job package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Shell_QNAME = new QName("", "shell");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.linkstec.raptor.eagle.tool.job
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ServerRef }
     * 
     */
    public ServerRef createServerRef() {
        return new ServerRef();
    }

    /**
     * Create an instance of {@link Remoteservers }
     * 
     */
    public Remoteservers createRemoteservers() {
        return new Remoteservers();
    }

    /**
     * Create an instance of {@link Server }
     * 
     */
    public Server createServer() {
        return new Server();
    }

    /**
     * Create an instance of {@link Job }
     * 
     */
    public Job createJob() {
        return new Job();
    }

    /**
     * Create an instance of {@link Workflow }
     * 
     */
    public Workflow createWorkflow() {
        return new Workflow();
    }

    /**
     * Create an instance of {@link Step }
     * 
     */
    public Step createStep() {
        return new Step();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "shell")
    public JAXBElement<String> createShell(String value) {
        return new JAXBElement<String>(_Shell_QNAME, String.class, null, value);
    }

}
