package xyz.taylorchyi.poketto.File;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColumnReaders {

    public static List<String> readDoiColumn(String csvFilePath, int doiColumnIndex) {
        List<String> dois = new ArrayList<>();
        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFilePath));
            List<String[]> allRows = csvReader.readAll();
            Set<String> uniqueDois = new HashSet<>();

            for (int i = 1; i < allRows.size(); i++) {
                String doi = allRows.get(i)[doiColumnIndex].trim();
                if (!doi.isEmpty() && !uniqueDois.contains(doi)) {
                    uniqueDois.add(doi);
                    dois.add(doi);
                }
            }

        } catch (IOException | CsvException e) {
            System.err.println("Failed to read CSV file.");
            e.printStackTrace();
        }

        return dois;
    }

    public static void main(String[] args) {
        String csvFilePath = "p";
        int doiColumnIndex = 0; // Assuming DOI column is the first column
        List<String> dois = readDoiColumn(csvFilePath, doiColumnIndex);
        System.out.println("DOIs:");
        dois.forEach(System.out::println);
    }
}


