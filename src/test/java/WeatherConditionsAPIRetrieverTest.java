import com.jordonmcgowan.weatherpackage.WeatherConditionsAPIRetriever;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * The WeatherConditionsAPIRetrieverTest class is
 * responsible for testing the WeatherConditionsAPIRetriever
 * class.
 *
 * @author  Jordon McGowan
 */
public class WeatherConditionsAPIRetrieverTest {
    private final WeatherConditionsAPIRetriever apiRetriever = new WeatherConditionsAPIRetriever();

    //Create array to iterate over different temperature selections
    private final String[] temperatureUnits = {"°C","°F"};

    @RepeatedTest(2)
    @DisplayName("(getJsonInHashMap): Valid City Inputted -> Returns HashMap")
    public void testHashMapReturnedForValidCity(RepetitionInfo repetitionInfo) throws IOException {
        int repeatIndex = repetitionInfo.getCurrentRepetition()-1;
        apiRetriever.setInputtedUserData("Limerick", temperatureUnits[repeatIndex]);
        boolean hashReturned = apiRetriever.getJSONInHashMap().isEmpty();

        assertFalse(hashReturned, "HashMap Should Be Returned For Valid Input");
    }

    @RepeatedTest(2)
    @DisplayName("(getJsonInHashMap): Invalid City Inputted -> Throw File Not Found Exception")
    public void testThrowFileNotFoundException(RepetitionInfo repetitionInfo) {
        int repeatIndex = repetitionInfo.getCurrentRepetition()-1;
        apiRetriever.setInputtedUserData("NotAValidCity", temperatureUnits[repeatIndex]);

        assertThrows(FileNotFoundException.class, apiRetriever::getJSONInHashMap);
    }
}
