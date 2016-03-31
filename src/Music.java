import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public class Music {

	// init variables
	private int duration = 200; // in milliseconds
	private int channel;
	private int volume;
	private String note;

	// constructor
	public Music(String note, int channel, int volume) {
		// instantiate variables
		this.note = note;
		this.channel = channel;
		this.volume = volume;
		playMusic();
	}

	// method containing all music functionalities
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
						if (note.charAt(i + 1) == '#')
							flat--;

						// check for sharp
						if (note.charAt(i + 1) == '!')
							sharp++;
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
				flat = 0;
				sharp = 0;

			}
			// end the last note smoothly
			Thread.sleep(1000);
			synth.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
