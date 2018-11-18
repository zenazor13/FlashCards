import words.Noun;
import words.Prep;
import words.Verb;
import words.Word;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

public class FlashCardsGUI {
	public JPanel panelMain;
	private JButton button0;
	private JButton button1;
	private JButton button2;
	private JButton buttonNext;
	private JLabel labelQuestion;
	private JButton buttonExit;
	private JLabel timesCorrect;
	private JLabel wordsLeft;
	private JButton settings;

	private Vector<Word> wordlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>(); //keeps track of what words are used on which button
	private Word correctWord = new Word();
	private String questionType;
	private HashMap<Word, Integer> wordmap = new HashMap<>();

	private boolean prep = true;
	private boolean verb = false;
	private boolean noun = false;

	private Random rand = new Random();

	public FlashCardsGUI() {

		setWordlist(prep, verb, noun);
		setWordmap();
		resetButtons();

		buttonNext.addActionListener(e -> resetButtons());

		button0.addActionListener(e -> checkCorrect(0));

		button1.addActionListener(e -> checkCorrect(1));

		button2.addActionListener(e -> checkCorrect(2));

		settings.addActionListener(e -> settingsDialogue());

		buttonExit.addActionListener(e -> System.exit(0));

	}

	private void resetButtons() {

		buttonText.clear();

		// pick word on list to be the answer
		int randWord = rand.nextInt(wordlist.size());
		correctWord = wordlist.elementAt(randWord);

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
				break;
			}
		}
	}

	private void getQuestionType() {
		switch (rand.nextInt(3)) {
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

		labelQuestion.setText(wordlist.elementAt(randWord).getDefinition());

		timesCorrect.setText("Times correct: " + wordmap.get(correctWord));
		wordsLeft.setText("Words left: " + (wordlist.size() - 2));

	}

	private void pickDefinition(int randWord) {

		// pick random definitions to also be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());


		while (rand1 == randWord || rand2 == randWord || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		// pick a random button to be correct
		switch (rand.nextInt(3)) {
			case 0: {
				button0.setText(wordlist.elementAt(randWord).getDefinition());
				buttonText.add(wordlist.elementAt(randWord));
				button1.setText(wordlist.elementAt(rand1).getDefinition());
				buttonText.add(wordlist.elementAt(rand1));
				button2.setText(wordlist.elementAt(rand2).getDefinition());
				buttonText.add(wordlist.elementAt(rand2));

				if (wordlist.elementAt(randWord) instanceof Noun) {
					labelQuestion.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					labelQuestion.setText(wordlist.elementAt(randWord).getWord());
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
					labelQuestion.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					labelQuestion.setText(wordlist.elementAt(randWord).getWord());
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
					labelQuestion.setText(((Noun) wordlist.elementAt(randWord)).getArticle() + " " +
							wordlist.elementAt(randWord).getWord());
				}
				else {
					labelQuestion.setText(wordlist.elementAt(randWord).getWord());
				}

				break;
			}
			default: {
				System.err.println("You dun goof'd");
			}
		}

		timesCorrect.setText("Times correct: " + wordmap.get(correctWord));
		wordsLeft.setText("Words left: " + (wordlist.size() - 2));

	}

	private void pickWord(int randWord) {


		// pick random words to be displayed
		int rand1 = rand.nextInt(wordlist.size());
		int rand2 = rand.nextInt(wordlist.size());

		while (rand1 == randWord || rand2 == randWord || rand1 == rand2) {
			rand1 = rand.nextInt(wordlist.size());
			rand2 = rand.nextInt(wordlist.size());
		}

		switch (rand.nextInt(3)) {
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
							+ " " + wordlist.elementAt(rand2).getWord());
				}
				else {
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}
				labelQuestion.setText(wordlist.elementAt(randWord).getDefinition());
				break;
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
							+ " " + wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}
				else {
					button2.setText(wordlist.elementAt(rand2).getWord());
					buttonText.add(wordlist.elementAt(rand2));
				}

				labelQuestion.setText(wordlist.elementAt(randWord).getDefinition());
				break;
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

				labelQuestion.setText(wordlist.elementAt(randWord).getDefinition());
				break;
			}
		}

		timesCorrect.setText("Times correct: " + wordmap.get(correctWord));
		wordsLeft.setText("Words left: " + (wordlist.size() - 2));
	}

	private void checkCorrect(int element) {
		if (buttonText.elementAt(element).equals(correctWord)) {
			wordmap.put(correctWord, wordmap.get(correctWord) + 1);

			if (wordmap.get(correctWord) == 5) {
				wordlist.removeElement(correctWord);
			}

			if (wordlist.size() < 3) {
				winDialogue();
			}
			resetButtons();
		}
		else {
			wordmap.put(buttonText.elementAt(element), 0);
		}
	}

	private void setWordlist(boolean prep, boolean verb, boolean noun) {


	String defaultPath = "C:\\Users\\Admin\\IdeaProjects\\FlashCards\\csv";
	String prepPath = defaultPath + "\\Prep.csv";
	String verbPath = defaultPath + "\\Verb.csv";
	String nounPath = defaultPath + "\\Noun.csv";



	String line = "";
	String csvSplitBy = ",";

	if (prep) {
		try (BufferedReader br = new BufferedReader(new FileReader(prepPath))) {

			while ((line = br.readLine()) != null) {
				String[] words = line.split(csvSplitBy);

				Prep p = new Prep(words[0], words[1]);
				wordlist.addElement(p);
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

				Verb v = new Verb(words[0], words[1]);
				wordlist.addElement(v);
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

				Noun n = new Noun(words[0], words[1], words[2]);
				wordlist.addElement(n);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	}

	private void setWordmap() {

		wordmap.clear();

		for (Word w : wordlist) {
			wordmap.put(w, 4);
		}
	}

	private void winDialogue() {

		int choice = JOptionPane.showOptionDialog(null, "Reset cards?", "You Won!", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (choice == JOptionPane.YES_OPTION) {
			setWordlist(prep, verb, noun);
			setWordmap();
		}
		else {
			System.exit(0);
		}


	}

	private void settingsDialogue() {

		JFrame settingsFrame = new JFrame();
		settingsFrame.setTitle("Settings");
		settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel settingsPanel = new JPanel();

		JCheckBox prepBox = new JCheckBox("prep", prep);
		JCheckBox verbBox = new JCheckBox("verb", verb);
		JCheckBox nounBox = new JCheckBox("noun", noun);

		JButton OK = new JButton("OK");

		settingsPanel.add(prepBox);
		settingsPanel.add(verbBox);
		settingsPanel.add(nounBox);
		settingsPanel.add(OK);

		settingsFrame.add(settingsPanel);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setVisible(true);

		OK.addActionListener(e -> {
			prep = prepBox.isSelected();
			verb = verbBox.isSelected();
			noun = nounBox.isSelected();

			wordlist.clear();
			wordmap.clear();

			setWordlist(prep, verb, noun);
			setWordmap();
			resetButtons();

			settingsFrame.dispose();
		});




	}


}
