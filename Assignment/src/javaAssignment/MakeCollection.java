package javaAssignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

public class MakeCollection{
	
	public void convertHTMLtoXML(String htmlFileName) {
		int num = 0;
		File path = new File(htmlFileName);
		File[] files = path.listFiles();
		int fileNum = files.length;
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.newDocument();
			Element docs = document.createElement("docs");
			document.appendChild(docs);
			for (int i = 0; i < fileNum; i++) {
				File file = files[i];
				org.jsoup.nodes.Document htmlDoc = Jsoup.parse(file, "UTF-8");
				Elements texts = htmlDoc.select("p");
				for (org.jsoup.nodes.Element text : texts) {
					text.remove();
				}

				Element doc = document.createElement("doc");
				docs.appendChild(doc);

				doc.setAttribute("id", String.valueOf(num));
				num++;
				Element title = document.createElement("title");
				String htmlTitle = htmlDoc.title();
				title.appendChild(document.createTextNode(htmlTitle));
				doc.appendChild(title);

				Element body = document.createElement("body");
				body.appendChild(document.createTextNode(texts.text()));
				doc.appendChild(body);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileOutputStream(new File("src/collection.xml")));

			transformer.transform(source, result);
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
	
}

