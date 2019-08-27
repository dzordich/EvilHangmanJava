import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class EvilHangman {
    public static String correctGuesses;
    public static String incorrectGuesses = "";
    public static String validGuess = "abcdefghijklmnopqrstuvwxyz";
    public static ArrayList<String> words = new ArrayList<>();
    public static String hiddenWord;
    public static int numGuesses = 26;
    public static int wordLength = 4;


    static ArrayList<String> readFile (int length) throws IOException{
        Path path = Paths.get("test_words.txt");
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
        ArrayList<String> n = new ArrayList<>();
        ArrayList<String> noGuess = new ArrayList<>();
        int index = 0;

        for (String x : words){
            if (x.charAt(0) == guess.charAt(0)){
                longest.add(x);
            }
        }
        for (int i = 1; i<wordLength; i++){
            for (String x : words){
                if (x.charAt(i) == guess.charAt(0)){
                    n.add(x);
                }
            }
            if (n.size() > longest.size()){
                longest = n;
                index = i;
            }
        }
        
        for (String x : words){
            if (!x.contains(guess)){
                noGuess.add(x);
            }
        }
        if (longest.size() > noGuess.size()){
            words = longest;
            return index;
        }
        words = noGuess;
        return -1;
    }

    static ArrayList<String> round (ArrayList<String> word_list) {
        Scanner scanner = new Scanner(System.in);
        String guess;

        System.out.println("You have " + numGuesses + " guesses remaining. ");
        System.out.println("Incorrect guesses: " + incorrectGuesses);
        System.out.println(hiddenWord);
        System.out.println(word_list);

        System.out.println("Guess a letter. ");
        guess = scanner.nextLine().toLowerCase();
        if(guess.length() != 1 || !validGuess.contains(guess) || incorrectGuesses.contains(guess) || hiddenWord.contains(guess)){
            System.out.println("Invalid guess. ");
            return round(word_list);
        }
        int index = takeGuess(guess);
        if (index == -1){
            incorrectGuesses = incorrectGuesses + guess;
            System.out.println("Incorrect guess. ");
            return words;
        }
        hiddenWord = revealLetter(index, guess);
        System.out.println(hiddenWord);
        System.out.println("Correct guess!");

        return word_list;
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

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        
        

        words = readFile(wordLength);
        hiddenWord = "----";
        
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
            System.out.println("You lost. ");
        }
    }
}
