import words.*;

import javax.swing.*;
import java.io.*;
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
	private Vector<Word> adlist = new Vector<>();
	private Vector<Word> otherlist = new Vector<>();
	private Vector<Word> buttonText = new Vector<>(); //keeps track of what words are used on which button
	private Word correctWord = new Word();
	private Word randWord1 = new Word();
	private Word randWord2 = new Word();
	private String questionType;
	private HashMap<Word, Integer> wordmap = new HashMap<>(); //keeps track of the amount of correct answers per word

	private int correctGuesses = 2;  //how many times needed to guess correctly before the word is removed from wordmap

	private int chapter = 0;
	private boolean prep = true;
	private boolean verb = false;
	private boolean noun = true;
	private boolean ad = false;
	private boolean other = false;


	private Random rand = new Random();

	public FlashCardsGUI() {

		setWordlist(prep, verb, noun, ad, other);
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

		Vector<Word> list = new Vector<>();
		correctWord = wordlist.elementAt(rand.nextInt(wordlist.size()));

		if (correctWord instanceof Prep) {
			list = preplist;
		}
		else if (correctWord instanceof Verb) {
			list = verblist;
		}
		else if (correctWord instanceof Noun) {
			list = nounlist;
		}
		else if (correctWord instanceof Ad) {
			list = adlist;
		}
		else if (correctWord instanceof Other) {
			list = otherlist;
		}

		randWord1 = list.elementAt(rand.nextInt(list.size()));
		randWord2 = list.elementAt(rand.nextInt(list.size()));

		while (randWord1.equals(correctWord) || randWord2.equals(correctWord) || randWord1.equals(randWord2)
				|| randWord1.getDefinition().equalsIgnoreCase(randWord2.getDefinition())) {
			randWord1 = list.elementAt(rand.nextInt(list.size()));
			randWord2 = list.elementAt(rand.nextInt(list.size()));
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
		wordsLeft.setText("Words left: " + (wordlist.size()));

	}

	private void pickDefinition() {

		// pick a random button to be correct
		switch (rand.nextInt(3)) {
			case 0: {
				button0.setText(correctWord.getDefinition());
				buttonText.add(correctWord);
				button1.setText(randWord1.getDefinition());
				buttonText.add(randWord1);
				button2.setText(randWord2.getDefinition());
				buttonText.add(randWord2);

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
				button0.setText(randWord1.getDefinition());
				buttonText.add(randWord1);
				button1.setText(correctWord.getDefinition());
				buttonText.add(correctWord);
				button2.setText(randWord2.getDefinition());
				buttonText.add(randWord2);

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
				button0.setText(randWord1.getDefinition());
				buttonText.add(randWord1);
				button1.setText(randWord2.getDefinition());
				buttonText.add(randWord2);
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
		wordsLeft.setText("Words left: " + (wordlist.size()));

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

				if (randWord1 instanceof Noun) {
					button1.setText(((Noun) randWord1).getArticle()
							+ " " + randWord1.getWord());
					buttonText.add(randWord1);
				}
				else {
					button1.setText(randWord1.getWord());
					buttonText.add(randWord1);
				}

				if (randWord2 instanceof Noun) {
					button2.setText(((Noun) randWord2).getArticle()
							+ " " + randWord2.getWord());
				}
				else {
					button2.setText(randWord2.getWord());
					buttonText.add(randWord2);
				}
				labelQuestion.setText(correctWord.getDefinition());
				break;
			}
			case 1: {
				if (randWord1 instanceof Noun) {
					button0.setText(((Noun) randWord1).getArticle()
							+ " " + randWord1.getWord());
					buttonText.add(randWord1);
				}
				else {
					button0.setText(randWord1.getWord());
					buttonText.add(randWord1);
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

				if (randWord2 instanceof Noun) {
					button2.setText(((Noun) randWord2).getArticle()
							+ " " + randWord2.getWord());
					buttonText.add(randWord2);
				}
				else {
					button2.setText(randWord2.getWord());
					buttonText.add(randWord2);
				}

				labelQuestion.setText(correctWord.getDefinition());
				break;
			}
			case 2: {
				if (randWord1 instanceof Noun) {
					button0.setText(((Noun) randWord1).getArticle()
							+ " " + randWord1.getWord());
					buttonText.add(randWord1);
				}
				else {
					button0.setText(randWord1.getWord());
					buttonText.add(randWord1);
				}

				if (randWord2 instanceof Noun) {
					button1.setText(((Noun) randWord2).getArticle()
							+ " " + randWord2.getWord());
					buttonText.add(randWord2);
				}
				else {
					button1.setText(randWord2.getWord());
					buttonText.add(randWord2);
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
		wordsLeft.setText("Words left: " + (wordlist.size()));
	}

	private void checkCorrect(int element) {
		if (questionType.equalsIgnoreCase("pickArticle")) {
			switch (element) {
				case 0:
					if (((Noun) correctWord).getArticle().equalsIgnoreCase("der")) {
						wordmap.put(correctWord, wordmap.get(correctWord) + 1);
						resetButtons();
					}
					else {
						wordmap.put(correctWord, 0);
					}
					break;
				case 1:
					if (((Noun) correctWord).getArticle().equalsIgnoreCase("die")) {
						wordmap.put(correctWord, wordmap.get(correctWord) + 1);
						resetButtons();
					}
					else {
						wordmap.put(correctWord, 0);
					}
					break;
				case 2:
					if (((Noun) correctWord).getArticle().equalsIgnoreCase("das")) {
						wordmap.put(correctWord, wordmap.get(correctWord) + 1);
						resetButtons();
					}
					else {
						wordmap.put(correctWord, 0);
					}
			}
		}
		else if (buttonText.elementAt(element).equals(correctWord)) {
			wordmap.put(correctWord, wordmap.get(correctWord) + 1);
			resetButtons();
		}
		else {
				wordmap.put(buttonText.elementAt(element), 0);
			}


		if (wordmap.get(correctWord) == correctGuesses) {
			wordlist.removeElement(correctWord);
		}

		if (wordlist.size() == 0) {
			winDialogue();
		}

	}

	private void setWordlist(boolean prep, boolean verb, boolean noun, boolean ad, boolean other) {


		wordlist.clear();

		String defaultPath = "C:\\Users\\Admin\\IdeaProjects\\FlashCards\\csv\\Ch" + chapter + "_";
		String prepPath = defaultPath + "Prep.csv";
		String verbPath = defaultPath + "Verb.csv";
		String nounPath = defaultPath + "Noun.csv";
		String adPath = defaultPath + "Ad.csv";
		String otherPath = defaultPath + "Other.csv";

		File prepFile = new File(prepPath);
		File verbFile = new File(verbPath);
		File nounFile = new File(nounPath);
		File adFile = new File(adPath);
		File otherFile = new File(otherPath);


		String line = "";
		String csvSplitBy = ",";


		if (prep && prepFile.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(prepFile))) {

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

		if (verb && verbFile.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(verbFile))) {

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

		if (noun && nounFile.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(nounFile))) {

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

		if (ad && adFile.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(adFile))) {

				while ((line = br.readLine()) != null) {
					String[] words = line.split(csvSplitBy);

					Ad a = new Ad(words[0], words[1]);
					wordlist.addElement(a);
					adlist.addElement(a);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (other && otherFile.isFile()) {
			try (BufferedReader br = new BufferedReader(new FileReader(otherFile))) {

				while ((line = br.readLine()) != null) {
					String[] words = line.split(csvSplitBy);

					Other o = new Other(words[0], words[1]);
					wordlist.addElement(o);
					otherlist.addElement(o);
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
			wordmap.put(w, 0);
		}
	}

	private void winDialogue() {

		int choice = JOptionPane.showOptionDialog(null, "Reset cards?", "You Won!", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (choice == JOptionPane.YES_OPTION) {
			setWordlist(prep, verb, noun, ad, other);
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

		String[] chapters = { "All Chapters", "Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5",
				"Chapter 6", "Chapter 7", "Chapter 8", "Chapter 9", "Chapter 10" };

		String[] correctGuessesNeeded = { "1", "2", "3", "4", "5" };

		JComboBox chaptersBox = new JComboBox(chapters);
		JComboBox correctGuessesNeededBox = new JComboBox(correctGuessesNeeded);
		JCheckBox prepBox = new JCheckBox("prep", prep);
		JCheckBox verbBox = new JCheckBox("verb", verb);
		JCheckBox nounBox = new JCheckBox("noun", noun);
		JCheckBox adBox = new JCheckBox("ad", ad);
		JCheckBox otherBox = new JCheckBox("other", other);

		JButton allButton = new JButton("All");
		JButton okButton = new JButton("OK");

		settingsPanel.add(chaptersBox);
		chaptersBox.setSelectedIndex(chapter);

		settingsPanel.add(correctGuessesNeededBox);
		correctGuessesNeededBox.setSelectedIndex(correctGuesses);

		settingsPanel.add(allButton);

		settingsPanel.add(prepBox);
		settingsPanel.add(verbBox);
		settingsPanel.add(nounBox);
		settingsPanel.add(adBox);
		settingsPanel.add(otherBox);
		settingsPanel.add(okButton);

		settingsFrame.add(settingsPanel);
		settingsFrame.pack();
		settingsFrame.setLocationRelativeTo(null);
		settingsFrame.setVisible(true);

		allButton.addActionListener(e -> {

			if (prepBox.isSelected() && verbBox.isSelected() && nounBox.isSelected()
					&& adBox.isSelected() && otherBox.isSelected()) {
				prepBox.setSelected(false);
				verbBox.setSelected(false);
				nounBox.setSelected(false);
				adBox.setSelected(false);
				otherBox.setSelected(false);
			}
			else {
				prepBox.setSelected(true);
				verbBox.setSelected(true);
				nounBox.setSelected(true);
				adBox.setSelected(true);
				otherBox.setSelected(true);
			}
		});

		okButton.addActionListener(e -> {
			chapter = chaptersBox.getSelectedIndex();
			correctGuesses = correctGuessesNeededBox.getSelectedIndex();
			prep = prepBox.isSelected();
			verb = verbBox.isSelected();
			noun = nounBox.isSelected();
			ad = adBox.isSelected();
			other = otherBox.isSelected();

			setWordlist(prep, verb, noun, ad, other);
			setWordmap();

			if (wordlist.size() == 0) {
				JOptionPane.showMessageDialog(null, "Invalid selection.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				settingsFrame.dispose();
			}

			resetButtons();
		});




	}


}
