import javax.swing.SwingUtilities;

public class MusicSynthesizer {

	public static void main(String[] args) {

		// safely instantiate both music frames
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Music_TextReader();
				new Music_UserInput();
			}
		});

	}

}
