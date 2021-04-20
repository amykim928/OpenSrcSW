package midterm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class GenSnippet {
	public void inputKeyWord(String txtFileName, String keyword) {
		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			String text = "라면 밀가루 달걀 밥 생선\n라면 물 소금 반죽\n첨부 봉지면 인기\n초밥 라면 밥물 채소 소금\n초밥 종류 활어";
			doc.createComment(text);
			FileOutputStream fileStream = new FileOutputStream(txtFileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
			objectOutputStream.writeObject(doc);
			objectOutputStream.close();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
