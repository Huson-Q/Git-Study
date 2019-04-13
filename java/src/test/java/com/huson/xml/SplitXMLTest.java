package com.huson.xml;

import com.huson.TemplateMgrException;
import org.junit.Test;
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
import java.util.List;

/**
 * xml1
 * <node>
 * 	<node1>
 * 		<node11>
 * 			<node111>text1</node111>
 * 		</node11>
 * 		<node12>
 * 		</node12>
 * 	</node1>
 * 	<node2>
 * 		<node21>
 * 			<node211>text2</node211>
 * 		</node21>
 * 		<node22>
 * 		</node22>
 * 	</node2>
 * 	<node3>
 * 		text3
 * 	</node3>
 * </node>
 */

/**
 * 	<node1>
 * 		<node11>
 * 			<node111>text1</node111>
 * 		</node11>
 * 		<node12>
 * 		</node12>
 * 	</node1>
 * 	<node2>
 * 		<node21>
 * 			<node211>text2</node211>
 * 		</node21>
 * 		<node22>
 * 		</node22>
 * 	</nod2>
 * 	<node3>
 * 		text3
 * 	</node3>
 */
public class SplitXMLTest {
    private String xml1 = "\n<node>\n" +
            "\t<node1>\n" +
            "\t\t<node11>\n" +
            "\t\t\t<node111>text1</node111>\n" +
            "\t\t</node11>\n" +
            "\t\t<node12>\n" +
            "\t\t</node12>\t\t\n" +
            "\t</node1>\n" +
            "\t<node2>\n" +
            "\t\t<node21>\n" +
            "\t\t\t<node211>text2</node211>\n" +
            "\t\t</node21>\n" +
            "\t\t<node22>\n" +
            "\t\t</node22>\t\t\n" +
            "\t</node2>\n" +
            "\t<node3>\n" +
            "\t\ttext3\n" +
            "\t</node3>\n" +
            "</node>";

    private String str2 = "\n\t<node1>\n" +
            "\t\t<node11>\n" +
            "\t\t\t<node111>text1</node111>\n" +
            "\t\t</node11>\n" +
            "\t\t<node12>\n" +
            "\t\t</node12>\t\t\n" +
            "\t</node1>\n" +
            "\t<node2>\n" +
            "\t\t<node21>\n" +
            "\t\t\t<node211>text2</node211>\n" +
            "\t\t</node21>\n" +
            "\t\t<node22>\n" +
            "\t\t</node22>\t\t\n" +
            "\t</node2>\n" +
            "\t<node3>\n" +
            "\t\ttext3\n" +
            "\t</node3>";

    private String str3 = "\n\t<node1>\t\n" +
            "\t</node1>\n" +
            "\t<node2>\t\t\n" +
            "\t</node2>\n" +
            "\t<node3>\n" +
            "\t\ttext3\n" +
            "\t</node3>";
    @Test
    public void test1()
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml1)));
            Node rootNode = doc.getFirstChild();
            NodeList childNodeList = rootNode.getChildNodes();
            int nodeLength = childNodeList.getLength();
            int elementLength = 0;
            System.out.println("nodeLength = " + nodeLength);

            for (int i = 0; i < childNodeList.getLength(); i++)
            {
                Node node = childNodeList.item(i);
                String nodeName = node.getNodeName();

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    elementLength++;
                    System.out.println("ELEMENT_NODE = "+ nodeName);

                    String context =nodeToString(node);
                    System.out.println("context = "+ context);

                }

                //System.out.println("nodeName = " + nodeName);
            }
            System.out.println("elementNode number = " + elementLength);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testAutoSplitXML1()
    {
        try {
            List<String> result1 = SplitXMLUtil.autoSplitXML(xml1);
            System.out.println("result1=\n"+ result1);
        } catch (TemplateMgrException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAutoSplitXML2()
    {
        try {
            List<String> result1 = SplitXMLUtil.autoSplitXML(str2);
            System.out.println("result2=\n"+ result1);
        } catch (TemplateMgrException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAutoSplitXML3()
    {
        try {
            List<String> result3 = SplitXMLUtil.autoSplitXML(str3);
            System.out.println("result3=\n"+ result3);
        } catch (TemplateMgrException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAutoSplitXML4()
    {
        try {
            List<String> result1 = SplitXMLUtil.autoSplitXML(xml1);
            System.out.println("result1=\n"+ result1);
        } catch (TemplateMgrException e) {
            e.printStackTrace();
        }
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
