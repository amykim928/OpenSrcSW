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
			
			String text = "��� �а��� �ް� �� ����\n��� �� �ұ� ����\n÷�� ������ �α�\n�ʹ� ��� �买 ä�� �ұ�\n�ʹ� ���� Ȱ��";
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
