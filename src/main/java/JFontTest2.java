import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class JFontTest2 {

	public static void main(String[] args) {
		JFontTest2 frame = new JFontTest2();
	}

	JFontTest2() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Font fonts[] = ge.getAllFonts();
		for(Font f : fonts) {
			System.out.println(f.getFontName());
			System.out.println(f.getName());
			System.out.println(f.getPSName());
			System.out.println(f.getFamily());

			System.out.println("***********");
		}

		// String fontFamilyNames[] = ge.getAvailableFontFamilyNames();
	}
}