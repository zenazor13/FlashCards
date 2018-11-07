import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

public class FlashCardsGUI {
	public JPanel panelMain;
	private JButton button0;
	private JButton button1;
	private JButton button2;
	private JButton buttonNext;
	private JLabel label;
	private JComboBox comboBoxChapter;
	private JComboBox comboBox2;


	private Vector<Word> wordlist = new Vector();
	private Word[] button = new Word[3];



	public FlashCardsGUI() {

		setWordlist();
		instantiateButtons();

		buttonNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setButtons();
			}
		});


		button0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (label.getText().equalsIgnoreCase(button[0].getWord())) {
					button0.setText("Correct!");

					setButtons();
				}

			}
		});

		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (label.getText().equalsIgnoreCase(button[1].getWord())) {
					button1.setText("Correct!");

					setButtons();
				}

			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (label.getText().equalsIgnoreCase(button[2].getWord())) {
					button2.setText("Correct!");

					setButtons();
				}

			}
		});



	}

	private void instantiateButtons() {
		for (int i = 0; i < 3; i++) {
			button[i] = new Word();
		}
	}

	private void setButtons() {

		Random rand = new Random();


		// pick word on list to get definition for
		int randDef = rand.nextInt(wordlist.size());

		// pick random words to also be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());


		while (rand1 == randDef || rand2 == randDef || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		// pick a random button to be correct
		int randCorrect = rand.nextInt(button.length);

		switch (randCorrect) {
			case 0: {
				button0.setText(wordlist.elementAt(randDef).getDefinition());
				button[0] = wordlist.elementAt(randDef);
				button1.setText(wordlist.elementAt(rand1).getDefinition());
				button[1] = wordlist.elementAt(rand1);
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				button[2] = wordlist.elementAt(rand2);
				label.setText(wordlist.elementAt(randDef).getWord());
				break;
			}
			case 1: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				button[0] = wordlist.elementAt(rand1);
				button1.setText(wordlist.elementAt(randDef).getDefinition());
				button[1] = wordlist.elementAt(randDef);
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				button[2] = wordlist.elementAt(rand2);
				label.setText(wordlist.elementAt(randDef).getWord());
				break;
			}
			case 2: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				button[0] = wordlist.elementAt(rand1);
				button1.setText(wordlist.elementAt(rand2).getDefinition());
				button[1] = wordlist.elementAt(rand2);
				button2.setText(wordlist.elementAt(randDef).getDefinition());
				button[2] = wordlist.elementAt(randDef);
				label.setText(wordlist.elementAt(randDef).getWord());
				break;
			}
			default: {
				System.err.println("You dun goof'd");
			}
		}



	}

	private void setWordlist() {

		/*
		TODO: 11/6/2018 add way to read from csv, to help with implementing various types of words i.e., verbs and nouns- use opencsv?
		*/

		Word word1 = new Word("durch", "through");
		Word word2 = new Word("ohne", "without");
		Word word3 = new Word("gegen", "against");
		Word word4 = new Word("für", "for");
		Word word5 = new Word("um", "around");
		Word word6 = new Word("mit", "with");

		wordlist.addElement(word1);
		wordlist.addElement(word2);
		wordlist.addElement(word3);
		wordlist.addElement(word4);
		wordlist.addElement(word5);
		wordlist.addElement(word6);

	}
}
