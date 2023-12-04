package edu.augustana;

import com.opencsv.CSVIterator;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class CardLibrary {
    public static ArrayList<Card> cardList;
    public static Map<String, Card> cardMap;

    public CardLibrary() {
        cardList = new ArrayList<Card>();
        cardMap = new TreeMap<String, Card>();
    }

    public void readInCards(String fileName) {
        for (File folder : new File(fileName).listFiles()) {
            System.out.println(folder.getName());
            for (File file : folder.listFiles()) {
                System.out.println(file.getName());
                fileName = file.getAbsolutePath();
                if (fileName.endsWith(".csv")) {
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
                            String[] level = nextLine[8].split(",");
                            String[] equipment = nextLine[9].split(",");
                            String[] keywords = nextLine[10].split(",");
                            Card card = new Card(code, event, category, title, packFolder, image, gender, modelSex, level, equipment, keywords);
                            addCard(card);
                            System.out.println(card);
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

        }
    }

    private void addCard(Card card) {
        cardList.add(card);
        cardMap.put(card.getCode(), card);
    }

    public Card getCardByCode(String code) {
        return (Card) cardMap.get(code);
    }
}
