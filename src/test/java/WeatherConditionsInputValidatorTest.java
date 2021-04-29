import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import com.jordonmcgowan.weatherpackage.WeatherConditionsInputValidator;

/**
 * The WeatherConditionsInputValidatorTest class is
 * responsible for testing the WeatherConditionsInputValidator
 * class.
 *
 * @author  Jordon McGowan
 */
public class WeatherConditionsInputValidatorTest {
    private final WeatherConditionsInputValidator inputValidator = new WeatherConditionsInputValidator();

    //Create some string arrays containing input data for various scenarios
    private final String[] blankInputVariations = {""," ","          "};
    private final String[] specialCharsInputVariations = {"*","&", "L!merick", "!£$%"};
    private final String[] numericInputVariations = {"1","20","100","Limerick 200", "200 Limerick","5Cork",
            "Cork5", "L1merick"};

    @Test
    @DisplayName("(checkInputIsValid): Valid String Inputted -> Return Empty String")
        public void testValidValue() {
            assertEquals("",
                    inputValidator.checkInputIsValid("Limerick"),
                    "No blank value allowed");
    }

    @RepeatedTest(3)
    @DisplayName("(checkInputIsValid): Empty String Inputted -> Return Error String")
    public void testBlankValue(RepetitionInfo repetitionInfo) {
        assertEquals("No location provided",
                inputValidator.checkInputIsValid(
                        blankInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "No blank value allowed");
    }

    @RepeatedTest(8)
    @DisplayName("(checkInputIsValid): Numeric String Inputted -> Return Error String")
    public void testNumericValue(RepetitionInfo repetitionInfo) {
        assertEquals("Can Not Contain Numeric Value",
                inputValidator.checkInputIsValid(
                        numericInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "Numeric Value Not Allowed");
    }

    @RepeatedTest(4)
    @DisplayName("(checkInputIsValid): Special Character String Inputted -> Return Error String")
    public void testSpecialCharValue(RepetitionInfo repetitionInfo) {
        assertEquals("Special Characters Not Allowed",
                inputValidator.checkInputIsValid(
                        specialCharsInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "Special Characters Not Allowed");
    }
}
