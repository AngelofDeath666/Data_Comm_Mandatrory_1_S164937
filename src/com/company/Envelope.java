package com.company;

import org.w3c.dom.*;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import java.io.*;
import java.net.*;
import java.util.*;

/* $Id: Envelope.java,v 1.8 1999/09/06 16:43:20 kangasha Exp $ */

/**
 * SMTP envelope for one mail message.
 *
 * @author Jussi Kangasharju
 */
public class Envelope implements com.sun.xml.internal.messaging.saaj.soap.Envelope {
    /* SMTP-sender of the message (in this case, contents of From-header. */
    public String Sender;

    /* SMTP-recipient, or contents of To-header. */
    public String Recipient;

    /* Target MX-host */
    public String DestHost;
    public InetAddress DestAddr;

    /* The actual message */
    public Message Message;

    /* Create the envelope. */
    public Envelope(Message message, String localServer) throws UnknownHostException {
        /* Get sender and recipient. */
        Sender = message.getFrom();
        Recipient = message.getTo();

	/* Get message. We must escape the message to make sure that
	   there are no single periods on a line. This would mess up
	   sending the mail. */
        Message = escapeMessage(message);

        /* Take the name of the local mailserver and map it into an
         * InetAddress */
        DestHost = localServer;
        try {
            DestAddr = InetAddress.getByName(DestHost);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + DestHost);
            System.out.println(e);
            throw e;
        }
        return;
    }

    /* Escape the message by doubling all periods at the beginning of
       a line. */
    private Message escapeMessage(Message message) {
        String escapedBody = "";
        String token;
        StringTokenizer parser = new StringTokenizer(message.Body, "\n", true);

        while(parser.hasMoreTokens()) {
            token = parser.nextToken();
            if(token.startsWith(".")) {
                token = "." + token;
            }
            escapedBody += token;
        }
        message.Body = escapedBody;
        return message;
    }

    /* For printing the envelope. Only for debug. */
    public String toString() {
        String res = "Sender: " + Sender + '\n';
        res += "Recipient: " + Recipient + '\n';
        res += "MX-host: " + DestHost + ", address: " + DestAddr + '\n';
        res += "Message:" + '\n';
        res += Message.toString();

        return res;
    }

    @Override
    public Source getContent() {
        return null;
    }

    @Override
    public void output(OutputStream outputStream) throws IOException {

    }

    @Override
    public void output(OutputStream outputStream, boolean b) throws IOException {

    }

    @Override
    public Name createName(String localName, String prefix, String uri) throws SOAPException {
        return null;
    }

    @Override
    public Name createName(String localName) throws SOAPException {
        return null;
    }

    @Override
    public SOAPHeader getHeader() throws SOAPException {
        return null;
    }

    @Override
    public SOAPBody getBody() throws SOAPException {
        return null;
    }

    @Override
    public SOAPHeader addHeader() throws SOAPException {
        return null;
    }

    @Override
    public SOAPBody addBody() throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(Name name) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(QName qname) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(String localName) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(String localName, String prefix) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(String localName, String prefix, String uri) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addChildElement(SOAPElement element) throws SOAPException {
        return null;
    }

    @Override
    public void removeContents() {

    }

    @Override
    public SOAPElement addTextNode(String text) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addAttribute(Name name, String value) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addAttribute(QName qname, String value) throws SOAPException {
        return null;
    }

    @Override
    public SOAPElement addNamespaceDeclaration(String prefix, String uri) throws SOAPException {
        return null;
    }

    @Override
    public String getAttributeValue(Name name) {
        return null;
    }

    @Override
    public String getAttributeValue(QName qname) {
        return null;
    }

    @Override
    public Iterator getAllAttributes() {
        return null;
    }

    @Override
    public Iterator getAllAttributesAsQNames() {
        return null;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return null;
    }

    @Override
    public Iterator getNamespacePrefixes() {
        return null;
    }

    @Override
    public Iterator getVisibleNamespacePrefixes() {
        return null;
    }

    @Override
    public QName createQName(String localName, String prefix) throws SOAPException {
        return null;
    }

    @Override
    public Name getElementName() {
        return null;
    }

    @Override
    public QName getElementQName() {
        return null;
    }

    @Override
    public SOAPElement setElementQName(QName newName) throws SOAPException {
        return null;
    }

    @Override
    public boolean removeAttribute(Name name) {
        return false;
    }

    @Override
    public boolean removeAttribute(QName qname) {
        return false;
    }

    @Override
    public boolean removeNamespaceDeclaration(String prefix) {
        return false;
    }

    @Override
    public Iterator getChildElements() {
        return null;
    }

    @Override
    public Iterator getChildElements(Name name) {
        return null;
    }

    @Override
    public Iterator getChildElements(QName qname) {
        return null;
    }

    @Override
    public void setEncodingStyle(String encodingStyle) throws SOAPException {

    }

    @Override
    public String getEncodingStyle() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {

    }

    @Override
    public void setParentElement(SOAPElement parent) throws SOAPException {

    }

    @Override
    public SOAPElement getParentElement() {
        return null;
    }

    @Override
    public void detachNode() {

    }

    @Override
    public void recycleNode() {

    }

    @Override
    public String getTagName() {
        return null;
    }

    @Override
    public String getAttribute(String name) {
        return null;
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException {

    }

    @Override
    public void removeAttribute(String name) throws DOMException {

    }

    @Override
    public Attr getAttributeNode(String name) {
        return null;
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        return null;
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagName(String name) {
        return null;
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        return null;
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {

    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {

    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        return null;
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        return null;
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
        return null;
    }

    @Override
    public boolean hasAttribute(String name) {
        return false;
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        return false;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        return null;
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException {

    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {

    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {

    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return null;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {

    }

    @Override
    public short getNodeType() {
        return 0;
    }

    @Override
    public Node getParentNode() {
        return null;
    }

    @Override
    public NodeList getChildNodes() {
        return null;
    }

    @Override
    public Node getFirstChild() {
        return null;
    }

    @Override
    public Node getLastChild() {
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        return null;
    }

    @Override
    public Node getNextSibling() {
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override
    public Document getOwnerDocument() {
        return null;
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        return null;
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        return null;
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        return null;
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        return null;
    }

    @Override
    public boolean hasChildNodes() {
        return false;
    }

    @Override
    public Node cloneNode(boolean deep) {
        return null;
    }

    @Override
    public void normalize() {

    }

    @Override
    public boolean isSupported(String feature, String version) {
        return false;
    }

    @Override
    public String getNamespaceURI() {
        return null;
    }

    @Override
    public String getPrefix() {
        return null;
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {

    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public String getBaseURI() {
        return null;
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException {
        return null;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {

    }

    @Override
    public boolean isSameNode(Node other) {
        return false;
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        return null;
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        return false;
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return null;
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return false;
    }

    @Override
    public Object getFeature(String feature, String version) {
        return null;
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return null;
    }

    @Override
    public Object getUserData(String key) {
        return null;
    }
}