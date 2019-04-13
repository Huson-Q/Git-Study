package com.huson.xml;

import com.huson.TemplateMgrException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class SplitXMLUtil {

    public static List<String> autoSplitXML(String content) throws TemplateMgrException {
        List<String> results = new ArrayList<>();
        if (isXML(content)) {
            results.add(content);
            return results;
        } else {
            String tmpContent = addRootNode(content);
            return splitXML(tmpContent);
        }
    }

    public static boolean isXML(String content) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public static List<String> splitXML(String content) throws TemplateMgrException {
        List<String> results = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content)));  // TODO 把这个换成调用方法生成doc
            Node rootNode = doc.getFirstChild();
            NodeList childNodeList = rootNode.getChildNodes();

            for (int i = 0; i < childNodeList.getLength(); i++) {
                Node node = childNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String str = nodeToString(node);
                    results.add(str);
                }
            }
        } catch (Exception e) {
            throw new TemplateMgrException("invalid-value", e);
        }
        return results;
    }

    private static String addRootNode(String content) {
        return "<root>" + content + "</root>";
    }

    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }
}
