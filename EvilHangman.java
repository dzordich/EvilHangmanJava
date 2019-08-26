import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;

public class EvilHangman {
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

    static void round (ArrayList<String> words) {

    }

    public static void main(String[] args) throws IOException {
          
    }
}
