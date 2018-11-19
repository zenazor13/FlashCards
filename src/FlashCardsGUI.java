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
	private Vector<Word> preplist = new Vector<>();
	private Vector<Word> verblist = new Vector<>();
	private Vector<Word> nounlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>(); //keeps track of what words are used on which button
	private Word correctWord = new Word();
	private Word rand1 = new Word();
	private Word rand2 = new Word();
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

		getRandomWords();

		// pick scenario
		getQuestionType();

		// choose between various output scenarios
		switch (questionType) {
			case "pickArticle": {
				if (correctWord instanceof Noun) {
					if (((Noun) correctWord).getArticle().equalsIgnoreCase(" ")) {
						resetButtons();
					}
					pickArticle();
				}
				else {
					resetButtons();
				}
				break;
			}
			case "pickDefinition": {
				pickDefinition();
				break;
			}
			case "pickWord": {
				pickWord();
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

	private void getRandomWords() {

		correctWord = wordlist.elementAt(rand.nextInt(wordlist.size()));

		if (correctWord instanceof Prep) {
			rand1 = preplist.elementAt(rand.nextInt(preplist.size()));
			rand2 = preplist.elementAt(rand.nextInt(preplist.size()));

			while (rand1.equals(correctWord) || rand2.equals(correctWord) || rand1.equals(rand2)) {
				rand1 = preplist.elementAt(rand.nextInt(preplist.size()));
				rand2 = preplist.elementAt(rand.nextInt(preplist.size()));
			}
		}
		else if (correctWord instanceof Verb) {
			rand1 = verblist.elementAt(rand.nextInt(verblist.size()));
			rand2 = verblist.elementAt(rand.nextInt(verblist.size()));

			while (rand1.equals(correctWord) || rand2.equals(correctWord) || rand1 == rand2) {
				rand1 = verblist.elementAt(rand.nextInt(verblist.size()));
				rand2 = verblist.elementAt(rand.nextInt(verblist.size()));
			}
		}
		else if (correctWord instanceof Noun) {
			rand1 = nounlist.elementAt(rand.nextInt(nounlist.size()));
			rand2 = nounlist.elementAt(rand.nextInt(nounlist.size()));

			while (rand1.equals(correctWord) || rand2.equals(correctWord) || rand1 == rand2) {
				rand1 = nounlist.elementAt(rand.nextInt(nounlist.size()));
				rand2 = nounlist.elementAt(rand.nextInt(nounlist.size()));
			}
		}

	}

	private void pickArticle() {

		button0.setText("der " + correctWord.getWord());
		buttonText.add(correctWord);
		button1.setText("die " + correctWord.getWord());
		buttonText.add(correctWord);
		button2.setText("das " + correctWord.getWord());
		buttonText.add(correctWord);

		labelQuestion.setText(correctWord.getDefinition());

		timesCorrect.setText("Times correct: " + wordmap.get(correctWord));
		wordsLeft.setText("Words left: " + (wordlist.size() - 2));

	}

	private void pickDefinition() {

		// pick a random button to be correct
		switch (rand.nextInt(3)) {
			case 0: {
				button0.setText(correctWord.getDefinition());
				buttonText.add(correctWord);
				button1.setText(rand1.getDefinition());
				buttonText.add(rand1);
				button2.setText(rand2.getDefinition());
				buttonText.add(rand2);

				if (correctWord instanceof Noun) {
					labelQuestion.setText(((Noun) correctWord).getArticle() + " " +
							correctWord.getWord());
				}
				else {
					labelQuestion.setText(correctWord.getWord());
				}

				break;
			}
			case 1: {
				button0.setText(rand1.getDefinition());
				buttonText.add(rand1);
				button1.setText(correctWord.getDefinition());
				buttonText.add(correctWord);
				button2.setText(rand2.getDefinition());
				buttonText.add(rand2);

				if (correctWord instanceof Noun) {
					labelQuestion.setText(((Noun) correctWord).getArticle() + " " +
							correctWord.getWord());
				}
				else {
					labelQuestion.setText(correctWord.getWord());
				}

				break;
			}
			case 2: {
				button0.setText(rand1.getDefinition());
				buttonText.add(rand1);
				button1.setText(rand2.getDefinition());
				buttonText.add(rand2);
				button2.setText(correctWord.getDefinition());
				buttonText.add(correctWord);

				if (correctWord instanceof Noun) {
					labelQuestion.setText(((Noun) correctWord).getArticle() + " " +
							correctWord.getWord());
				}
				else {
					labelQuestion.setText(correctWord.getWord());
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

	private void pickWord() {

		switch (rand.nextInt(3)) {
			case 0: {
				if (correctWord instanceof Noun) {
					button0.setText(((Noun) correctWord).getArticle()
							+ " " + correctWord.getWord());
					buttonText.add(correctWord);
				}
				else {
					button0.setText(correctWord.getWord());
					buttonText.add(correctWord);
				}

				if (rand1 instanceof Noun) {
					button1.setText(((Noun) rand1).getArticle()
							+ " " + rand1.getWord());
					buttonText.add(rand1);
				}
				else {
					button1.setText(rand1.getWord());
					buttonText.add(rand1);
				}

				if (rand2 instanceof Noun) {
					button2.setText(((Noun) rand2).getArticle()
							+ " " + rand2.getWord());
				}
				else {
					button2.setText(rand2.getWord());
					buttonText.add(rand2);
				}
				labelQuestion.setText(correctWord.getDefinition());
				break;
			}
			case 1: {
				if (rand1 instanceof Noun) {
					button0.setText(((Noun) rand1).getArticle()
							+ " " + rand1.getWord());
					buttonText.add(rand1);
				}
				else {
					button0.setText(rand1.getWord());
					buttonText.add(rand1);
				}

				if (correctWord instanceof Noun) {
					button1.setText(((Noun) correctWord).getArticle()
							+ " " + correctWord.getWord());
					buttonText.add(correctWord);
				}
				else {
					button1.setText(correctWord.getWord());
					buttonText.add(correctWord);
				}

				if (rand2 instanceof Noun) {
					button2.setText(((Noun) rand2).getArticle()
							+ " " + rand2.getWord());
					buttonText.add(rand2);
				}
				else {
					button2.setText(rand2.getWord());
					buttonText.add(rand2);
				}

				labelQuestion.setText(correctWord.getDefinition());
				break;
			}
			case 2: {
				if (rand1 instanceof Noun) {
					button0.setText(((Noun) rand1).getArticle()
							+ " " + rand1.getWord());
					buttonText.add(rand1);
				}
				else {
					button0.setText(rand1.getWord());
					buttonText.add(rand1);
				}

				if (rand2 instanceof Noun) {
					button1.setText(((Noun) rand2).getArticle()
							+ " " + rand2.getWord());
					buttonText.add(rand2);
				}
				else {
					button1.setText(rand2.getWord());
					buttonText.add(rand2);
				}

				if (correctWord instanceof Noun) {
					button2.setText(((Noun) correctWord).getArticle()
							+ " " + correctWord.getWord());
					buttonText.add(correctWord);
				}
				else {
					button2.setText(correctWord.getWord());
					buttonText.add(correctWord);
				}

				labelQuestion.setText(correctWord.getDefinition());
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
				preplist.addElement(p);
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
				verblist.addElement(v);
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
				nounlist.addElement(n);
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
