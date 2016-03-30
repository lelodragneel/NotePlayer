import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Music extends JFrame implements ActionListener, DocumentListener {

	// init variables
	private JTextField textField_node;
	private JTextField textField_channel;
	private JTextField textField_volume;
	private JButton btnPlayMelody;
	private int channel = 15; // between 0 and 15
	private int volume = 100; // between 0 and 127
	private int duration = 200; // in milliseconds
	private String note;

	public Music() {

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

		// create label asking for notestring
		JLabel lblEnterNode = new JLabel("Enter the notestring:");
		lblEnterNode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterNode.setBounds(204, 11, 146, 30);
		contentPane.add(lblEnterNode);

		// create textfield that allows for inputting notestring
		textField_node = new JTextField(10);
		textField_node.setBounds(62, 59, 430, 30);
		textField_node.setColumns(10);
		textField_node.getDocument().addDocumentListener(this);
		// textField_node.addActionListener(this);
		contentPane.add(textField_node);

		// create label asking for channel
		JLabel lblEnterChannel = new JLabel("Enter the channel (0 to 15): ");
		lblEnterChannel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterChannel.setBounds(118, 100, 211, 30);
		contentPane.add(lblEnterChannel);

		// create label asking for volume
		JLabel lblEnterVolume = new JLabel("Enter the volume(0 to 127): ");
		lblEnterVolume.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterVolume.setBounds(118, 141, 211, 30);
		contentPane.add(lblEnterVolume);

		// create textfield that allows for inputting channel 0-15
		textField_channel = new JTextField();
		textField_channel.setBounds(339, 100, 70, 30);
		textField_channel.setColumns(10);
		textField_channel.getDocument().addDocumentListener(this);
		contentPane.add(textField_channel);

		// create textfield that allows for inputting volume 0-127
		textField_volume = new JTextField();
		textField_volume.setColumns(10);
		textField_volume.setBounds(339, 143, 70, 30);
		textField_volume.getDocument().addDocumentListener(this);
		contentPane.add(textField_volume);

		// create the button to play music
		btnPlayMelody = new JButton("Play the melody");
		btnPlayMelody.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPlayMelody.setBounds(187, 220, 180, 30);
		btnPlayMelody.setFocusable(false);
		btnPlayMelody.addActionListener(this);
		contentPane.add(btnPlayMelody);

		// set frame visible
		setVisible(true);

	}

	// play music notes
	public void playMusic() {

		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			MidiChannel[] channels = synth.getChannels();
			int pitch = 60;
			String num = "";
			boolean resetNum = false;
			int octaveNum = 0;
			int flat = 0;
			int sharp = 0;

			// loop through all chars of the inputed notestring
			for (int i = 0; i < note.length(); i++) {
				char ch = note.charAt(i);

				// skip loop turn if the current char is a flat/sharp
				if (ch == '!' || ch == '#')
					continue;

				// increase octave pitch then skip this loop turn
				if (ch == '>') {
					octaveNum += 12;
					continue;
				}

				// decrease octave pitch then skip this loop turn
				if (ch == '<') {
					octaveNum -= 12;
					continue;
				}

				// check for digit, if there's a digit, skip this loop turn
				if (Character.isDigit(ch)) {
					num += "" + ch;
					continue;

				} else {
					// tag the variable 'num' to be reset at the end of the loop
					resetNum = true;

					// check flats and sharps
					if (i + 1 < note.length()) {
						// check for flat
						if (note.charAt(i + 1) == '#') {
							System.out.println("activate flat");
							flat--;
						}

						// check for sharp
						if (note.charAt(i + 1) == '!') {
							System.out.println("activate sharp");
							sharp++;
						}
					}

					if (ch == 'C')
						pitch = 60 + octaveNum + flat + sharp;
					if (ch == 'D')
						pitch = 62 + octaveNum + flat + sharp;
					if (ch == 'E')
						pitch = 64 + octaveNum + flat + sharp;
					if (ch == 'F')
						pitch = 65 + octaveNum + flat + sharp;
					if (ch == 'G')
						pitch = 67 + octaveNum + flat + sharp;
					if (ch == 'A')
						pitch = 69 + octaveNum + flat + sharp;
					if (ch == 'B')
						pitch = 71 + octaveNum + flat + sharp;

					// check if pitch is out of range
					if (pitch > 108)
						pitch = 108;
					if (pitch < 21)
						pitch = 21;

					System.out.println(pitch);

				}
				channels[channel].noteOn(pitch, volume);
				// prevent duration to be multiplied by zero
				if (!num.equals(""))
					Thread.sleep(duration * Integer.parseInt(num));
				else
					Thread.sleep(duration);
				if (resetNum) {
					num = "";
					resetNum = false;
				}
				channels[channel].noteOff(pitch);

				// reset the sharp/flat pitch
				pitch = 0;
				pitch = 0;

			}
			// end the last note smoothly
			Thread.sleep(1000);
			synth.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// action listener
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(btnPlayMelody))
			playMusic();
	}

	// i used a document listener strictly for jtextfields for better ui
	public void changedUpdate(DocumentEvent e) {
		updateValues();
	}

	public void insertUpdate(DocumentEvent e) {
		updateValues();
	}

	public void removeUpdate(DocumentEvent e) {
		updateValues();
	}

	// get values from textfields then assigns them to variables
	public void updateValues() {
		if (!textField_node.getText().equals(""))
			note = textField_node.getText();
		if (!textField_channel.getText().equals(""))
			channel = Integer.parseInt(textField_channel.getText());
		if (!textField_volume.getText().equals(""))
			volume = Integer.parseInt(textField_volume.getText());
	}

}
