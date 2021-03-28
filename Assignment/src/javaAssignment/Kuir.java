package javaAssignment;

public class Kuir {

	public static void main(String[] args){
		System.out.println("args0: " + args[0]);
		System.out.println("args1: " + args[1]);
		// TODO Auto-generated method stub

		if(args[0].equals("-c")) {
			String htmlFileName = args[1];
			MakeCollection mc = new MakeCollection();
			mc.convertHTMLtoXML(htmlFileName);
		}
		else if(args[0].equals("-k")) {
			String xmlFileName = args[1];
			MakeKeyword mk = new MakeKeyword();
			mk.main(xmlFileName);
		} 
		else if(args[0].equals("-i")) {
			String indexFileName = args[1];
			Indexer ix = new Indexer();
			ix.xmltopost(indexFileName);
		}
		else {
			System.out.println("Invalid argument.");
		}
	}

}
