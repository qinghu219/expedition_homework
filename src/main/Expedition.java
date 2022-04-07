package main;

import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class Expedition {
    // This is a small task
    private static final String inputFilePath = "../../expeditions.csv";
    private static final String outputFilePath = "../../minerals.csv";

    public List<String[]> readMineralData(String inputFilePath) throws IOException {
        Map<String, Integer> mineralMap = new TreeMap<>();
        List<String[]> outputData = new ArrayList<>();

        try {
            CSVReader reader = new CSVReader(new FileReader(inputFilePath));
            String[] firstLine = reader.readNext();
            String[] nextLine;
            //read one line at a time
            while ((nextLine = reader.readNext()) != null) {

                String mineralName = nextLine[4];
                int quantity = Integer.parseInt(nextLine[3]);
                mineralMap.put(mineralName, mineralMap.getOrDefault(mineralName, 0) + quantity);
            }

            String[] subject = {firstLine[4], firstLine[3]};
            outputData.add(subject);

            for (Map.Entry<String, Integer> entry : mineralMap.entrySet()) {
                String[] curr = {entry.getKey(), entry.getValue().toString()};
                outputData.add(curr);
            }
        } catch (Exception e) {
            // log exception
            e.printStackTrace();
            throw e;
        }

        return outputData;
    }

    public boolean writeData(String filePath, List<String[]> data) throws IOException {
        File file = new File(filePath);
        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file);

            // create CSVWriter object fileWriter object as parameter
            CSVWriter writer = new CSVWriter(outputFile);

            writer.writeAll(data);

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return true;
    }

    public static void main(String[] args) throws IOException {
        // Retrieve mineral data and its total quantities
        Expedition expedition = new Expedition();
        List<String[]> outputData = expedition.readMineralData(inputFilePath);
        expedition.writeData(outputFilePath, outputData);
    }
}

