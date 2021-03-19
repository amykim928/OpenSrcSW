package javaAssignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MakeKeyword {
	private static void kkmaXML(String xmlString, Node xmlNode, Document xmlDoc) {
		String nodeXML = xmlString;
		String bodyXML = "";
		Element xmlElement = (Element) xmlNode;
		KeywordExtractor ke = new KeywordExtractor();
		KeywordList kl = ke.extractKeyword(nodeXML, true);
		for (int i = 0; i < kl.size(); i++) {
			Keyword kwrd = kl.get(i);
			bodyXML += kwrd.getString() + ":" + kwrd.getCnt() + "#";
		}
		xmlElement.setTextContent(bodyXML);
	}
    public void main(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document docs = null;
        try {
            Path fileName = Path.of(filePath);
            String result = Files.readString(fileName);
            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            docs = builder.parse(is);
            Element root = docs.getDocumentElement();
            NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node node = children.item(i);
				Element ele = (Element) node;
				NodeList secChildren = ele.getChildNodes();
				for (int j = 0; j < secChildren.getLength(); j++) {
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Node secNode = secChildren.item(j);
						Element secEle = (Element) secNode;
						String nodeName = secEle.getNodeName();
						if (nodeName.equals("body")) {
							kkmaXML(secEle.getTextContent(), secNode, docs);
						}
					}
				}
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(docs);
			StreamResult xmlResult = new StreamResult(new FileOutputStream(new File("src/index.xml")));

			transformer.transform(source, xmlResult);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
