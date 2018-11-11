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
	private JButton buttonExit;

	// TODO: 11/11/2018 add separate panels for each type of question to simplify button action listeners

	private Vector<Word> wordlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>(); //keeps track of what words are used where, for checking answers
	private String questionType;



	public FlashCardsGUI() {

		setWordlist(true, false, true);
		resetButtons();

		buttonNext.addActionListener(e -> resetButtons());


		button0.addActionListener(e -> {
			switch (questionType) {
				case "pickArticle": {
					if (button0.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(0)).getArticle()
							+ " " + buttonText.elementAt(0).getWord())) {
						resetButtons();
					}
				}
				case "pickDefinition": {
					if (buttonText.elementAt(0) instanceof Noun) {
						if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(0)).getArticle()
								+ " " + buttonText.elementAt(0).getWord())) {
							resetButtons();
						}
					}
					if (label.getText().equalsIgnoreCase(buttonText.elementAt(0).getWord())) {
						resetButtons();
					}
				}
				case "pickWord": {

				}
			}
		});

		button1.addActionListener(e -> {
			switch (questionType) {
				case "pickArticle": {
					if (button1.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(1)).getArticle()
							+ " " + buttonText.elementAt(1).getWord())) {
						resetButtons();
					}
				}
				case "pickDefinition": {
					if (buttonText.elementAt(1) instanceof Noun) {
						if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(1)).getArticle()
								+ " " + buttonText.elementAt(1).getWord())) {
							resetButtons();
						}
					}
					if (label.getText().equalsIgnoreCase(buttonText.elementAt(1).getWord())) {
						resetButtons();
					}
				}
				case "pickWord": {

				}
			}
		});

		button2.addActionListener(e -> {
			switch (questionType) {
				case "pickArticle": {
					if (button2.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(2)).getArticle()
							+ " " + buttonText.elementAt(2).getWord())) {
						resetButtons();
					}
				}
				case "pickDefinition": {
					if (buttonText.elementAt(2) instanceof Noun) {
						if (label.getText().equalsIgnoreCase(((Noun) buttonText.elementAt(2)).getArticle()
								+ " " + buttonText.elementAt(2).getWord())) {
							resetButtons();
						}
					}
					if (label.getText().equalsIgnoreCase(buttonText.elementAt(2).getWord())) {
						resetButtons();
					}
				}
				case "pickWord": {

				}
			}
		});


		buttonExit.addActionListener(e -> System.exit(0));

	}

	 
	private void resetButtons() {

		Random rand = new Random();

		buttonText.clear();

		// pick word on list to be the answer
		int randWord = rand.nextInt(wordlist.size());

		// pick scenario
		getQuestionType();

		// choose between various output scenarios
		switch (questionType) {
			case "pickArticle": {
				if (wordlist.elementAt(randWord) instanceof Noun) {
					if (((Noun) wordlist.elementAt(randWord)).getArticle().equalsIgnoreCase(" ")) {
						resetButtons();
					}
					pickArticle(randWord);
				}
				else {
					resetButtons();
				}
				break;
			}
			case "pickDefinition": {
				pickDefinition(randWord);
				break;
			}
			case "pickWord": {
				pickWord(randWord);
				//resetButtons();
				break;
			}
		}
	}

	private void getQuestionType() {
		Random rand = new Random();
		int type = rand.nextInt(2);
		switch (type) {
			case 0: {
				questionType = "pickArticle";
				break;
			}
			case 1: {
				questionType = "pickDefinition";
				break;
			}
			case 2: {
				questionType = "pickWord";
				break;
			}
		}
	}

	private void pickArticle(int randWord) {

		button0.setText("der " + wordlist.elementAt(randWord).getWord());
		buttonText.add(wordlist.elementAt(randWord));
		button1.setText("die " + wordlist.elementAt(randWord).getWord());
		buttonText.add(wordlist.elementAt(randWord));
		button2.setText("das " + wordlist.elementAt(randWord).getWord());
		buttonText.add(wordlist.elementAt(randWord));

		label.setText(wordlist.elementAt(randWord).getDefinition());
	}

	private void pickDefinition(int randWord) {

		Random rand = new Random();

		// pick random definitions to also be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());


		while (rand1 == randWord || rand2 == randWord || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		// pick a random button to be correct
		int randCorrect = rand.nextInt(3);

		switch (randCorrect) {
			case 0: {
				button0.setText(wordlist.elementAt(randWord).getDefinition());
				buttonText.add(wordlist.elementAt(randWord));
				button1.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));

				if (wordlist.elementAt(randWord) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randWord).getWord());
				}

				break;
			}
			case 1: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button1.setText(wordlist.elementAt(randWord).getDefinition());
				buttonText.add(wordlist.elementAt(randWord));
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));

				if (wordlist.elementAt(randWord) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randWord).getWord());
				}

				break;
			}
			case 2: {
				button0.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button1.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));
				button2.setText(wordlist.elementAt(randWord).getDefinition());
				buttonText.add(wordlist.elementAt(randWord));

				if (wordlist.elementAt(randWord) instanceof Noun) {
					label.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					label.setText(wordlist.elementAt(randWord).getWord());
				}

				break;
			}
			default: {
				System.err.println("You dun goof'd");
			}
		}


	}

	private void pickWord(int randWord) {

		Random rand = new Random();

		// pick random words to be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());

		while (rand1 == randWord || rand2 == randWord || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		int randCorrect = rand.nextInt(3);

		switch (randCorrect) {
			case 0: {
				if (wordlist.elementAt(randWord) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(randWord)).getArticle()
							+ " " + wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}
				else {
					button0.setText(wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}

				if (wordlist.elementAt(rand1) instanceof Noun) {
					button1.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}
				else {
					button1.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}

				if (wordlist.elementAt(rand2) instanceof Noun) {
					button2.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2));
					buttonText.add(wordlist.elementAt(rand2));
				}
				else {
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}

				label.setText(wordlist.elementAt(randWord).getDefinition());
			}
			case 1: {
				if (wordlist.elementAt(rand1) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}
				else {
					button0.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}

				if (wordlist.elementAt(randWord) instanceof Noun) {
					button1.setText(((Noun) wordlist.elementAt(randWord)).getArticle()
							+ " " + wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}
				else {
					button1.setText(wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}

				if (wordlist.elementAt(rand2) instanceof Noun) {
					button2.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2));
					buttonText.add(wordlist.elementAt(rand2));
				}
				else {
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}

				label.setText(wordlist.elementAt(randWord).getDefinition());
			}
			case 2: {
				if (wordlist.elementAt(rand1) instanceof Noun) {
					button0.setText(((Noun) wordlist.elementAt(rand1)).getArticle()
							+ " " + wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}
				else {
					button0.setText(wordlist.elementAt(rand1).getWord());
					buttonText.add(wordlist.elementAt(rand1));
				}

				if (wordlist.elementAt(rand2) instanceof Noun) {
					button1.setText(((Noun) wordlist.elementAt(rand2)).getArticle()
							+ " " + wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}
				else {
					button1.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}

				if (wordlist.elementAt(randWord) instanceof Noun) {
					button2.setText(((Noun) wordlist.elementAt(randWord)).getArticle()
							+ " " + wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}
				else {
					button2.setText(wordlist.elementAt(randWord).getWord());
					buttonText.add(wordlist.elementAt(randWord));
				}

				label.setText(wordlist.elementAt(randWord).getDefinition());
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
