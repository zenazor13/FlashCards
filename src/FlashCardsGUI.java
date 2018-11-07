import javax.swing.*;
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
	private JComboBox comboBoxWord;


	private Vector<Word> wordlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>();



	public FlashCardsGUI() {

		setWordlist();
		resetButtons();

		buttonNext.addActionListener(e -> resetButtons());


		button0.addActionListener(e -> {
				if (buttonText.elementAt(0) instanceof Noun) {
					if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(0)).getArticle()
							+ " " + buttonText.elementAt(0).getWord())) {
						button0.setText("Correct!");
						resetButtons();
					}
				}
				else if (label.getText().equalsIgnoreCase(buttonText.elementAt(0).getWord())) {
					button0.setText("Correct!");
					resetButtons();
			}
			});
		

		button1.addActionListener(e -> {
				if (buttonText.elementAt(1) instanceof Noun) {
					if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(1)).getArticle()
							+ " " + buttonText.elementAt(1).getWord())) {
						button0.setText("Correct!");
						resetButtons();
					}
				}
				else if (label.getText().equalsIgnoreCase(buttonText.elementAt(1).getWord())) {
					button1.setText("Correct!");
					resetButtons();
				}
		});

		button2.addActionListener(e -> {
				if (buttonText.elementAt(2) instanceof Noun) {
					if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(2)).getArticle()
							+ " " + buttonText.elementAt(2).getWord())) {
						button0.setText("Correct!");
						resetButtons();
					}
				}
				if (label.getText().equalsIgnoreCase(buttonText.elementAt(2).getWord())) {
					button2.setText("Correct!");
					resetButtons();
				}
		});



	}

	// TODO: 11/7/2018 add chance to switch words and definitions

	private void resetButtons() {

		Random rand = new Random();

		buttonText.clear();

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
		int randCorrect = rand.nextInt(3);

		switch (randCorrect) {
			case 0: {
				button0.setText(wordlist.elementAt(randDef).getDefinition());
				buttonText.add(wordlist.elementAt(randDef));
				button1.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));

				if (wordlist.elementAt(randDef) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randDef)).getArticle() + " " +
							wordlist.elementAt(randDef).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randDef).getWord());
				}

				break;
			}
			case 1: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button1.setText(wordlist.elementAt(randDef).getDefinition());
				buttonText.add(wordlist.elementAt(randDef));
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));

				if (wordlist.elementAt(randDef) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randDef)).getArticle() + " " +
							wordlist.elementAt(randDef).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randDef).getWord());
				}

				break;
			}
			case 2: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button1.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));
				button2.setText(wordlist.elementAt(randDef).getDefinition());
				buttonText.add(wordlist.elementAt(randDef));

				if (wordlist.elementAt(randDef) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randDef)).getArticle() + " " +
							wordlist.elementAt(randDef).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randDef).getWord());
				}

				break;
			}
			default: {
				System.err.println("You dun goof'd");
			}
		}



	}

	private void setWordlist() {

		/*
		TODO: 11/6/2018 add way to read from csv
		*/

		Preposition word1 = new Preposition("durch", "through");
		Preposition word2 = new Preposition("ohne", "");
		Preposition word3 = new Preposition("gegen", "against");
		Preposition word4 = new Preposition("für", "for");
		Preposition word5 = new Preposition("um", "around");
		Preposition word6 = new Preposition("mit", "with");

		Noun word7 = new Noun("der", "Tag", "day");
		Noun word8 = new Noun("das", "Jahr", "year");
		Noun word9 = new Noun("die", "Straße", "street, road");

		Adjective word10 = new Adjective("bald", "soon");

		word2.setDefinition("without");
		wordlist.addElement(word1);
		wordlist.addElement(word2);
		wordlist.addElement(word3);
		wordlist.addElement(word4);
		wordlist.addElement(word5);
		wordlist.addElement(word6);
		wordlist.addElement(word7);
		wordlist.addElement(word8);
		wordlist.addElement(word9);

	}
}
