import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import sample.WeatherConditionsAPIRetriever;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherConditionsAPIRetrieverTest {
    private WeatherConditionsAPIRetriever weatherConditionsAPIRetriever;

    private final String[] temperatureUnits = {"°C","°F"};

    @BeforeEach
    public void setUp() {

    }

    @RepeatedTest(2)
    @DisplayName("ValidCityInputtedReturnPopulatedHashMap")
    public void testHashMapReturnedForValidCity(RepetitionInfo repetitionInfo) throws IOException {
        int repeatIndex = repetitionInfo.getCurrentRepetition()-1;
        weatherConditionsAPIRetriever = new WeatherConditionsAPIRetriever("Limerick",
                temperatureUnits[repeatIndex]);
        boolean hashReturned = weatherConditionsAPIRetriever.getJSONInHashMap().isEmpty();
        assertFalse(hashReturned, "HashMap Should Be Returned For Valid Input");
    }

    @RepeatedTest(2)
    @DisplayName("InvalidCityInputtedThrowFileNotFoundException")
    public void testThrowFileNotFoundException(RepetitionInfo repetitionInfo) {
        int repeatIndex = repetitionInfo.getCurrentRepetition()-1;
        weatherConditionsAPIRetriever = new WeatherConditionsAPIRetriever("spooflol",
                temperatureUnits[repeatIndex]);

        assertThrows(FileNotFoundException.class, weatherConditionsAPIRetriever::getJSONInHashMap);
    }




    //Write tests to check exceptions are thrown
}
