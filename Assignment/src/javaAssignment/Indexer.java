package javaAssignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Indexer {
	@SuppressWarnings({"rawtypes","unchecked", "resource"})
	public void xmltopost(String xmlFileName){
		try {
			FileOutputStream fileStream = new FileOutputStream("src/index.post");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
			HashMap postMap = new HashMap();
			HashMap freqMap = new HashMap();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder doc = factory.newDocumentBuilder();
			Document parser = doc.parse(xmlFileName);
			NodeList list = parser.getElementsByTagName("body");
			int i,j;
			for (i = 0; i < list.getLength(); i++) {
				Node freqBody = list.item(i);
				String[] splitBody = freqBody.getTextContent().split(":|#");
				for (j = 0; j < splitBody.length; j += 2) {
					if (freqMap.containsKey(splitBody[j])) {
						int value = (int) freqMap.get(splitBody[j]) + 1;
						freqMap.put(splitBody[j], value);
					} else {
						freqMap.put(splitBody[j], 1);
					}

				}
			}
			for(i=0;i<list.getLength();i++) {
				Node body = list.item(i);
				String[]splitBody = body.getTextContent().split(":|#");
				for(j=0;j<splitBody.length;j+=2) {
					double log = list.getLength()/Double.parseDouble(String.valueOf((int) freqMap.get(splitBody[j])));
					double freqBody = Double.parseDouble(splitBody[j+1])*Math.log(log);
					String roundBody = String.format("%.2f", freqBody);
					List<String> testList = new ArrayList<String>();
					testList.add(Integer.toString(i));
					testList.add(roundBody);
					
					if(postMap.containsKey(splitBody[j])) {
						List<String> valueList = (List<String>) postMap.get(splitBody[j]);
						valueList.addAll(testList);
						postMap.put(splitBody[j], valueList);
					}
					else {
						postMap.put(splitBody[j], testList);
					}
				}
			}
			objectOutputStream.writeObject(postMap);
			objectOutputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
