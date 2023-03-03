package analyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileAnalyzer {

    private List<String> patterns = new ArrayList<>();
    String directory;
    String[] fileList;

    FileAnalyzer(String directory, String pathToPatterns) {
        this.directory = directory;
        fileList = new File(this.directory).list();
        setPatterns(pathToPatterns);
    }

    public void fileAnalyzer() throws ExecutionException, InterruptedException{

        // creates a new worker, a thread pool and a hash map of futures
        Worker worker;
        ExecutorService executor = Executors.newFixedThreadPool(10);
        HashMap<String, Future<String>> workers = new HashMap<>();

        // pass each file into worker and submit to executor
        for (String f : fileList) {
            worker = new Worker(f, directory, patterns);
            workers.put(f, (Future<String>) executor.submit(worker));
        }

        // print result
        for (Map.Entry<String, Future<String>> set : workers.entrySet()) {
            String response = set.getValue().get();
            if (response.equalsIgnoreCase("NOT FOUND")) {
                System.out.println(set.getKey() + ": " + "Unknown file type");
            } else {
                System.out.println(set.getKey() + ": " + response.replaceAll("\"",""));
            }
        }
    }

    public void setPatterns(String path) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(path))) {
            patterns = fileReader.lines().sorted(Comparator.reverseOrder()).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
