package midterm;

public class Midterm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���� Ŭ���� ���� �� txt���� ����� �� �����߽��ϴ�. index.txt�� �ƴ϶� src/index.txt�� ���ڰ��� �־����ϴ�.
		if(args[0].equals("-f") && args[2].equals("-q")) {
			String txtFileName = args[1];
			String keyword = args[3];
			GenSnippet gs = new GenSnippet();
			gs.inputKeyWord(txtFileName, keyword);
		}
		else {
			System.out.println("invalid argument");
		}
	}

}
