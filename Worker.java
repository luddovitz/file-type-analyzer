package analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;

public class Worker implements Callable {

    String file;
    String directory;
    List<String> patterns;

    Worker(String file, String directory, List<String> patterns) {
        this.file = file;
        this.directory = directory;
        this.patterns = patterns;
    }

    @Override
    public String call() throws Exception {

        // text to search
        StringBuilder text = new StringBuilder();

        // read file and store into text to search
        try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(directory + "/" + file))) {
            fileReader.lines().forEach(text::append);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // run algorithm
        KnutMorrisPratt kmp = new KnutMorrisPratt();

        // Run KMP for each different pattern in order
        for (String pattern : patterns) {
            // Clean the pattern
            String[] cleanedString = pattern.split(";");
            if(kmp.execute(cleanedString[1].replaceAll("\"",""), text.toString())) {
                return cleanedString[2];
            }
        }
        return "NOT FOUND";
    }
}
