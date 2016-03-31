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
public class Music_UserInput extends JFrame implements ActionListener, DocumentListener {

	// init variables
	private JTextField textField_node;
	private JTextField textField_channel;
	private JTextField textField_volume;
	private JButton btnPlayMelody;
	private String note;
	private int channel = 15; // between 0 and 15
	private int volume = 100; // between 0 and 127

	public Music_UserInput() {

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
		JLabel lblEnterVolume = new JLabel("Enter the volume (0 to 127): ");
		lblEnterVolume.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblEnterVolume.setBounds(110, 141, 222, 30);
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

	// action listener
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource().equals(btnPlayMelody))
			new Music(note, channel, volume);
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
