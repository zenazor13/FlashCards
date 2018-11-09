import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	private JButton buttonExit;
	private JLabel labelRight;
	private JLabel labelWrong;


	private Vector<Word> wordlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>();	//keeps track of what words are used where, for checking answers



	public FlashCardsGUI() {

		setWordlist(true, false, true);
		resetButtons();

		buttonNext.addActionListener(e -> resetButtons());


		//button listeners for pickDefinition cases

		button0.addActionListener(e -> {

			if (buttonText.elementAt(0) instanceof Noun) {
				if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(0)).getArticle()
						+ " " + buttonText.elementAt(0).getWord())) {
					resetButtons();
				}
				else if (button0.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(0)).getArticle()
						+ " " + buttonText.elementAt(0).getWord())) {
					resetButtons();
				}
			}
			else if (label.getText().equalsIgnoreCase(buttonText.elementAt(0).getWord())) {
				resetButtons();
			}



		});

		button1.addActionListener(e -> {

			if (buttonText.elementAt(1) instanceof Noun) {
				if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(1)).getArticle()
						+ " " + buttonText.elementAt(1).getWord())) {
					resetButtons();
				}
				else if (button1.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(1)).getArticle()
						+ " " + buttonText.elementAt(1).getWord())) {
					resetButtons();
				}
			}
			else if (label.getText().equalsIgnoreCase(buttonText.elementAt(1).getWord())) {
				resetButtons();
			}



		});

		button2.addActionListener(e -> {

			if (buttonText.elementAt(2) instanceof Noun) {
				if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(2)).getArticle()
						+ " " + buttonText.elementAt(2).getWord())) {
					resetButtons();
				}
				else if (button2.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(2)).getArticle()
						+ " " + buttonText.elementAt(2).getWord())) {
					resetButtons();
				}
			}
			else if (label.getText().equalsIgnoreCase(buttonText.elementAt(2).getWord())) {
				resetButtons();
			}



		});



		buttonExit.addActionListener(e -> System.exit(0));

	}

	 
	private void resetButtons() {

		Random rand = new Random();

		buttonText.clear();

		// pick word on list to get definition for
		int randDef = rand.nextInt(wordlist.size());

		// pick scenario
		int randScen = rand.nextInt(3);

		// choose between various output scenarios
		switch (randScen) {
			case 0: {
				if (wordlist.elementAt(randDef) instanceof Noun) {
					if (((Noun) wordlist.elementAt(randDef)).getArticle().equalsIgnoreCase(" ")) {
						resetButtons();
					}
					pickArticle(randDef);
				}
				else {
					resetButtons();
				}
				break;
			}
			case 1: {
				pickDefinition(randDef);
				break;
			}
			case 2: {
				//pickWord(randDef); pickword is broken
				resetButtons();
				break;
			}
		}




	}
	private void pickArticle(int randDef) {

		button0.setText("der " + wordlist.elementAt(randDef).getWord());
		buttonText.add(wordlist.elementAt(randDef));
		button1.setText("die " + wordlist.elementAt(randDef).getWord());
		buttonText.add(wordlist.elementAt(randDef));
		button2.setText("das " + wordlist.elementAt(randDef).getWord());
		buttonText.add(wordlist.elementAt(randDef));

		label.setText(wordlist.elementAt(randDef).getDefinition());
	}

	private void pickDefinition(int randDef) {

		Random rand = new Random();

		// pick random definitions to also be displayed
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


	// TODO: 11/9/2018 fix pickWord 
	private void pickWord(int randDef) {

		Random rand = new Random();

		// pick random words to be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());

		while (rand1 == randDef || rand2 == randDef || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		int randCorrect = rand.nextInt(3);

		//should only set text as a noun if wordlist.elementAt(____) is a noun
		//not just if randDef is a noun


		switch (randCorrect) {
			case 0: {
				if (wordlist.elementAt(randDef) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(randDef)).getArticle()
							+ " " + wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));
					button1.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button2.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2));
					buttonText.add(wordlist.elementAt(rand2));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
				else {
					button0.setText(wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));
					button1.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
			}
			case 1: {
				if (wordlist.elementAt(randDef) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button1.setText(((Noun) wordlist.elementAt(randDef)).getArticle()
							+ " " + wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));
					button2.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2));
					buttonText.add(wordlist.elementAt(rand2));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
				else {
					button0.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button1.setText(wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
			}
			case 2: {
				if (wordlist.elementAt(randDef) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button1.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
					button2.setText(((Noun) wordlist.elementAt(randDef)).getArticle()
							+ " " + wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
				else {
					button0.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
					button1.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
					button2.setText(wordlist.elementAt(randDef).getWord());
					buttonText.add(wordlist.elementAt(randDef));

					label.setText(wordlist.elementAt(randDef).getDefinition());
				}
			}
		}
	}

	private void setWordlist(boolean preposition, boolean verb, boolean noun) {


	String defaultPath = "C:\\Users\\Admin\\IdeaProjects\\FlashCards\\csv";
	String prepositionPath = defaultPath + "\\Preposition.csv";
	String verbPath = defaultPath + "\\Verb.csv";
	String nounPath = defaultPath + "\\Noun.csv";



	String line = "";
	String csvSplitBy = ",";

	if (preposition) {
		try (BufferedReader br = new BufferedReader(new FileReader(prepositionPath))) {

			while ((line = br.readLine()) != null) {
				String[] words = line.split(csvSplitBy);

				Preposition prep = new Preposition(words[0], words[1]);
				wordlist.addElement(prep);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	if (verb) {
		try (BufferedReader br = new BufferedReader(new FileReader(verbPath))) {

			while ((line = br.readLine()) != null) {
				String[] words = line.split(csvSplitBy);

				Verb prep = new Verb(words[0], words[1]);
				wordlist.addElement(prep);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	if (noun) {
		try (BufferedReader br = new BufferedReader(new FileReader(nounPath))) {

			while ((line = br.readLine()) != null) {
				String[] words = line.split(csvSplitBy);

				Noun prep = new Noun(words[0], words[1], words[2]);
				wordlist.addElement(prep);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	}
}
