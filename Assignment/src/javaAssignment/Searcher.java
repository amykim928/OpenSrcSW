package javaAssignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Searcher {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void calcSim(String postfileName, String query) {
		try {
			HashMap<String,Integer> kwrdMap = new HashMap();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(query, true);
			for(int i=0;i<kl.size();i++) {
				Keyword kwrd = kl.get(i);
				String kwrdStr = kwrd.getString();
				kwrdMap.put(kwrdStr, 1);
			}

			FileInputStream fileStream;
			fileStream = new FileInputStream(postfileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();

			HashMap postMap = (HashMap) object;
			Iterator<String> it = postMap.keySet().iterator();

			HashMap<Integer,Double> innerProductMap = new HashMap();
			for(int i=0;i<5;i++) {
				innerProductMap.put(i, 0.0);
			}
			double innerProductArr [] = {0,0,0,0,0};
			double innerProduct = 0;
			while(it.hasNext()) {
				String key = it.next();
				List<String> postValue = (List<String>) postMap.get(key);
				if (kwrdMap.containsKey(key)) {
					for (int i = 0; i < postValue.size(); i += 2) {
						for (int j = 0; j < 5; j++) {
							if (Integer.parseInt(postValue.get(i)) == j) {
								double postValueCal = Double.parseDouble(postValue.get(i + 1));
								innerProduct += (double) (kwrdMap.get(key) * kwrdMap.get(key));
								innerProductArr[j] +=postValueCal * postValueCal;
							}
						}
					}
					
				}
			}
			innerProduct = Math.sqrt(innerProduct);
			double calcSimArr[] = {0,0,0,0,0};
			double featureArr[] = innerProduct(postfileName, query);
			for(int i=0;i<innerProductArr.length;i++) {
				innerProductArr[i] = Math.sqrt(innerProductArr[i]);
			}
			for(int i=0;i<calcSimArr.length;i++) {
				calcSimArr[i] = featureArr[i]/(innerProduct * innerProductArr[i]);
				if(Double.isInfinite(calcSimArr[i]))
					innerProductMap.put(i, 0.0);
				else
					innerProductMap.put(i, calcSimArr[i]);
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("src/collection.xml");
			Element root = doc.getDocumentElement();
            NodeList children = root.getChildNodes();
            String titleList[] = new String[5];
			for(int i=0;i<children.getLength();i++) {
				Node node = children.item(i).getFirstChild();
				titleList[i] = node.getTextContent();
			}
		
			List<Integer> keySetList = new ArrayList<>(innerProductMap.keySet());
			Collections.sort(keySetList, (o1, o2) -> (innerProductMap.get(o2).compareTo(innerProductMap.get(o1))));
			int count =0;
			for (Integer key : keySetList) {
				if (count == 3)
					break;
				if (innerProductMap.get(key) != 0) {
					Double mapValue = (Double) innerProductMap.get(key);
					System.out.println(titleList[key] + " : " + Math.round(mapValue*100)/100.0);
					count++;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	public double[] innerProduct(String postfileName, String query) {
		try {
			HashMap<String,Integer> kwrdMap = new HashMap();
			KeywordExtractor ke = new KeywordExtractor();
			KeywordList kl = ke.extractKeyword(query, true);
			for(int i=0;i<kl.size();i++) {
				Keyword kwrd = kl.get(i);
				String kwrdStr = kwrd.getString();
				kwrdMap.put(kwrdStr, 1);
			}
			
			FileInputStream fileStream;
			fileStream = new FileInputStream(postfileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			
			HashMap postMap = (HashMap) object;
			Iterator<String> it = postMap.keySet().iterator();
			
//			HashMap<Integer,Double> innerProductMap = new HashMap();
//			for(int i=0;i<5;i++) {
//				innerProductMap.put(i, 0.0);
//			}
			double innerProductArr [] = {0,0,0,0,0};
			
			while(it.hasNext()) {
				String key = it.next();
				List<String> postValue = (List<String>) postMap.get(key);
				if (kwrdMap.containsKey(key)) {
					for (int i = 0; i < postValue.size(); i += 2) {
						for (int j = 0; j < 5; j++) {
							if (Integer.parseInt(postValue.get(i)) == j) {
								double postValueCal = Double.parseDouble(postValue.get(i + 1));
								double innerProduct = (int) kwrdMap.get(key) * postValueCal;
								innerProductArr[j] +=innerProduct;
								//innerProductMap.put(j, innerProductArr[j]);
							}
						}
					}
				}
			}
			
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			Document doc = builder.parse("src/collection.xml");
//			Element root = doc.getDocumentElement();
//            NodeList children = root.getChildNodes();
//            String titleList[] = new String[5];
//			for(int i=0;i<children.getLength();i++) {
//				Node node = children.item(i).getFirstChild();
//				titleList[i] = node.getTextContent();
//			}
//			
//			List<Integer> keySetList = new ArrayList<>(innerProductMap.keySet());
//			Collections.sort(keySetList, (o1, o2) -> (innerProductMap.get(o2).compareTo(innerProductMap.get(o1))));
//			int count =0;
//			for (Integer key : keySetList) {
//				if (count == 3)
//					break;
//				if (innerProductMap.get(key) != 0) {
//					Double mapValue = (Double) innerProductMap.get(key);
//					System.out.println(titleList[key] + " : " + Math.round(mapValue*100)/100.0);
//					count++;
//				}
//			}
			return innerProductArr;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		} catch (SAXException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	}
}
