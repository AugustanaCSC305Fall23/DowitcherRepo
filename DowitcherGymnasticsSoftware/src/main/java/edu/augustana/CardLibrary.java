package edu.augustana;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CardLibrary {
    private static int numCards;
    public static ArrayList<String> cardList;

    public CardLibrary() {
        cardList = new ArrayList<String>();
        numCards = 0;
    }

    public void readInCards(String fileName) {
        try{
            CSVReader reader = new CSVReaderBuilder(new FileReader(fileName)).build();
            CSVIterator iterator = new CSVIterator(reader);
            String[] firstLine = iterator.next(); // skip first line labels
            for (CSVIterator it = iterator; it.hasNext(); ) {
                String[] nextLine = it.next();
                String code = nextLine[0];
                String event = nextLine[1];
                String category = nextLine[2];
                String title = nextLine[3];
                String packFolder = nextLine[4];
                String image = nextLine[5];
                String gender = nextLine[6];
                String modelSex = nextLine[7];
                String level = nextLine[8];
                String equipment = nextLine[9];
                String[] keywords = nextLine[10].split(",");

                System.out.println(String.format("CODE: %s, EVENT: %s, CATEGORY: %s, TITLE: %s, PACK FOLDER: %s, IMAGE: %s, GENDER: %s, MODEL SEX: %s, LEVEL: %s, EQUIPMENT: %s, KEYWORDS: %s", code, event, category, title, packFolder, image, gender, modelSex, level, equipment, keywords));

            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
