package analyzer;

import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FileAnalyzer fileAnalyzer = new FileAnalyzer(args[0], args[1]);
        fileAnalyzer.fileAnalyzer();
    }
}
