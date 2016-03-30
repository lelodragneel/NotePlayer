import javax.swing.SwingUtilities;

public class MusicSynthesizer {

	public static void main(String[] args) {

		// safely instantiate the music frame
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Music();
			}
		});

	}

}
