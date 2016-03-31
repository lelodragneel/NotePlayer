import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Music_TextReader extends JFrame implements ActionListener {

	// init variables
	private JTextField enterFile_field;
	private JButton btnPlay;

	// constructor
	public Music_TextReader() {

		// create frame
		setTitle("Musical Note");
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(0, 0, 560, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		// create root panel
		JPanel contentPane = new JPanel();
		contentPane.setBounds(0, 0, 554, 271);
		contentPane.setLayout(null);
		getContentPane().add(contentPane);

		// create label asking to input a file name
		JLabel lblEnterTxt = new JLabel("Enter the name of the song file: ");
		lblEnterTxt.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterTxt.setBounds(10, 11, 236, 19);
		contentPane.add(lblEnterTxt);

		// create textfield to input file name
		enterFile_field = new JTextField();
		enterFile_field.setBounds(256, 10, 110, 20);
		contentPane.add(enterFile_field);
		enterFile_field.setColumns(10);

		// create button to begin playing music
		btnPlay = new JButton("Play the song");
		btnPlay.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPlay.setBounds(186, 58, 182, 30);
		btnPlay.addActionListener(this);
		contentPane.add(btnPlay);

		// set frame visible
		setVisible(true);

	}

	// action listener
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnPlay))
			playMusic();

	}

	// play music
	public void playMusic() {

		Scanner scan;
		try {
			scan = new Scanner(new File(enterFile_field.getText()));

			// assign the first line to channel
			int channel = scan.nextInt();
			// assign the second line to volume
			int volume = scan.nextInt();

			// loop through all the txt file's lines and play them
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				System.out.println("New song playing..");
				new Music(line, channel, volume);
			}

		} catch (Exception e) {
		}

	}

}
