package midterm;

public class Midterm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//구동 클래스 만든 후 txt파일 만드는 중 제출했습니다. index.txt가 아니라 src/index.txt로 인자값에 넣었습니다.
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
