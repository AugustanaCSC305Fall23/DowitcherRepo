import static edu.augustana.CardLibrary.cardList;
import static org.junit.jupiter.api.Assertions.*;

import edu.augustana.Card;
import edu.augustana.SearchFunction;
import org.junit.jupiter.api.Test;
import edu.augustana.CardLibrary;

import java.util.List;


public class testSearchFunction {
    //this method will create a sample search function which will allow me to test the perform
    //search method
    //this method will be used in the testPerformSearch method and should have all the cards inside by default
    private SearchFunction makeSampleSearch() {
        CardLibrary cardLibrary = new CardLibrary();
        cardLibrary.readInCards("src/main/resources/csv/DEMO1.csv");
        cardLibrary.readInCards("src/main/resources/csv/DEMO2.csv");
        List<Card> cards = cardList;
        return new SearchFunction(cards);
    }


    //this method tests the performSearch method in SearchFunction.java
    //it checks that the search function returns the correct number of cards
    //when searching for a specific card code by title or category
    @Test
    void testPerformSearch() {
        SearchFunction test = makeSampleSearch();
        assertEquals(38, test.performSearch("").size());
        System.out.println(test.performSearch("").size());
        assertEquals(2, test.performSearch("beam").size());
        System.out.println(test.performSearch("beam").size());
        assertEquals(7, test.performSearch("handstand").size());
        System.out.println(test.performSearch("handstand").size());

    }

}