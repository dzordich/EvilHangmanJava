import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.Random;

public class EvilHangman {
    public static String correctGuesses;
    public static String incorrectGuesses = "";
    public static String validGuess = "abcdefghijklmnopqrstuvwxyz";
    public static ArrayList<String> words = new ArrayList<>();
    public static String hiddenWord;
    public static int numGuesses = 8;
    public static int wordLength = 4;


    static ArrayList<String> readFile (int length) throws IOException{
        Path path = Paths.get("words.txt");
        Scanner scanner = new Scanner(path);
        ArrayList<String> words = new ArrayList<>();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.length() == length){
                words.add(line);
            }
        }
        return words;
    }

    static int takeGuess (String guess) {
        ArrayList<String> longest = new ArrayList<>();
        ArrayList<String> noGuess = new ArrayList<>();
        ArrayList<String> leftovers = new ArrayList<>();
        ArrayList<String> longestLeftovers = new ArrayList<>();

        int index = 0;

        for (String x : words){
            if (x.charAt(0) == guess.charAt(0) && wordContainsIncorrectGuess(x)){
                if(indexesOfGuess(guess, x) == 1){
                    longest.add(x);
                }
                else {
                    leftovers.add(x);
                }
            }
        }
        for (int i = 1; i<wordLength; i++){
            ArrayList<String> n = new ArrayList<>();
            for (String x : words){
                if (x.charAt(i) == guess.charAt(0) && wordContainsIncorrectGuess(x)){
                    if(indexesOfGuess(guess, x) == 1){
                        n.add(x);
                    }
                    else {
                        leftovers.add(x);
                    }
                }
            }
            if (n.size() > longest.size()){
                longest = n;
                index = i;
            }
        }
        
        for (String x : words){
            if (!x.contains(guess) && wordContainsIncorrectGuess(x)){
                noGuess.add(x);
            }
        }
        leftovers = removeDupes(leftovers);
        for (String x : leftovers){
            ArrayList<String> n = new ArrayList<>();
            n = sortLeftovers(leftovers, x, guess);
            if (n.size() > longestLeftovers.size()){
                longestLeftovers = n;
            }
        }

        if (longest.size() > noGuess.size()){
            words = longest;
            return index;
        }
        if (longestLeftovers.size() > noGuess.size()){
            words = longestLeftovers;
            return -2;
        }
        words = noGuess;
        return -1;
    }

    static ArrayList<String> round (ArrayList<String> word_list) {
        Scanner scanner = new Scanner(System.in);
        String guess;

        System.out.println("You have " + numGuesses + " guesses remaining. ");
        System.out.println(" ");
        if (incorrectGuesses.length() > 0){
            System.out.println("Incorrect guesses: " + incorrectGuesses);
        }
        System.out.println(hiddenWord);

        System.out.println("Guess a letter. ");
        guess = scanner.nextLine().toLowerCase();
        if(guess.length() != 1 || !validGuess.contains(guess) || incorrectGuesses.contains(guess) || hiddenWord.contains(guess)){
            System.out.println(" ");
            System.out.println("Invalid guess. ");
            return round(word_list);
        }
        int index = takeGuess(guess);

        if (index == -1){
            incorrectGuesses = incorrectGuesses + guess;
            System.out.println(" ");
            System.out.println("Incorrect guess. ");
            return words;
        }
        if (index == -2){
            hiddenWord = revealMultipleLetters(guess);
            System.out.println(" ");
            System.out.println("Correct guess!");
            return words;
        }
        hiddenWord = revealLetter(index, guess);
        System.out.println(" ");
        System.out.println("Correct guess!");

        return words;
    }

    static String revealLetter (int index, String guess){
        String word = "";
        for (int i = 0; i < wordLength; i++){
            if (i == index){
                word = word.concat(guess);
            }
            else if (hiddenWord.charAt(i) != "-".charAt(0)){
                word = word.concat(Character.toString(hiddenWord.charAt(i)));
            }
            else {
                word = word.concat("-");
            }
        }
        return word;
    }
    static String revealMultipleLetters (String guess){
        String word = "";
        for (int i = 0; i < wordLength; i++){
            if (words.get(0).charAt(i) == guess.charAt(0)){
                word = word.concat(guess);
            }
            else if (hiddenWord.charAt(i) != "-".charAt(0)){
                word = word.concat(Character.toString(hiddenWord.charAt(i)));
            }
            else {
                word = word.concat("-");
            }
        }
        return word;
    }

    static boolean wordContainsIncorrectGuess(String word){
        for (int i = 0; i < word.length(); i++){
            if (incorrectGuesses.contains(Character.toString(word.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    static int indexesOfGuess(String guess, String word){
        int i = 0;

        for (int x = 0; x < word.length(); x++){
            if (word.charAt(x) == guess.charAt(0)){
                i++;
            }
        }

        return i;
    }
    static ArrayList<String> sortLeftovers(ArrayList<String> leftovers, String word, String guess){
        ArrayList<String> same = new ArrayList<>();

        for (String x : leftovers) {
            if (listOfIndexes(guess, word).equals(listOfIndexes(guess, x))){
                same.add(x);
            }
        }
        return same;
    }
    static ArrayList<Integer> listOfIndexes (String guess, String word){
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < word.length(); i++){
            if (word.charAt(i) == guess.charAt(0)){
                indexes.add(i);
            }
        }
        return indexes;
    }
    static ArrayList<String> removeDupes (ArrayList<String> og){
        ArrayList<String> noDupes = new ArrayList<>();
        for (String x : og){
            if (!noDupes.contains(x)){
                noDupes.add(x);
            }
        }
        return noDupes;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Choose your difficulty. Enter 1 for easy, 2 for normal, or 3 for hard: ");
        int diff = scanner.nextInt();
        if (diff == 1){
            wordLength = 4;
        }
        else if (diff == 2){
            wordLength = 6;
        }
        else {
            wordLength = 8;
        }

        words = readFile(wordLength);
        hiddenWord = "";
        for (int i = 0; i < wordLength; i++){
            hiddenWord = hiddenWord + "-";
        }
        
        System.out.println("Get ready. The word is 4 letters long. ");

        while (numGuesses > 0){
            round(words);
            numGuesses--;
            if (!hiddenWord.contains("-")){
                System.out.println("Congratulations! You won!");
                break;
            }
        }
        if (numGuesses <= 0){
            Random rand = new Random();
            int r = rand.nextInt(words.size() - 1);
            String correctWord = words.get(r);
            System.out.println("You lost. ");
            System.out.println("The correct word was " + correctWord);
        }
    }
}
