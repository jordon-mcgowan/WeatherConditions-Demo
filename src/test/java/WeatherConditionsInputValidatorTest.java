import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import sample.WeatherConditionsInputValidator;


public class WeatherConditionsInputValidatorTest {
    private WeatherConditionsInputValidator weatherConditionsInputValidator;

    //Create some string arrays containing input data for various scenarios
    private final String[] blankInputVariations = {""," ","          "};
    private final String[] specialCharsInputVariations = {"*","&", "L!merick", "!£$%"};
    private final String[] numericInputVariations = {"1","20","100","Limerick 200", "200 Limerick","5Cork",
            "Cork5", "L1merick"};

    @BeforeEach
    public void setUp() {
        weatherConditionsInputValidator = new WeatherConditionsInputValidator();
    }

    @Test
    @DisplayName("Valid City Should Return Empty String")
        public void testValidValue(){
            assertEquals("",
                    weatherConditionsInputValidator.checkInputIsValid("Limerick"),
                    "No blank value allowed");
    }

    @RepeatedTest(3)
    @DisplayName("Blank Input Should Be Rejected")
    public void testBlankValue(RepetitionInfo repetitionInfo){
        assertEquals("No location provided",
                weatherConditionsInputValidator.checkInputIsValid(
                        blankInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "No blank value allowed");
    }
    @RepeatedTest(8)
    @DisplayName("Numbers Should Be Rejected")
    public void testNumericValue(RepetitionInfo repetitionInfo){
        assertEquals("Can Not Contain Numeric Value",
                weatherConditionsInputValidator.checkInputIsValid(
                        numericInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "Numeric Value Not Allowed");
    }

    @RepeatedTest(4)
    @DisplayName("Special Characters Should Be Rejected")
    public void testSpecialCharValue(RepetitionInfo repetitionInfo){
        assertEquals("Special Characters Not Allowed",
                weatherConditionsInputValidator.checkInputIsValid(
                        specialCharsInputVariations[repetitionInfo.getCurrentRepetition()-1]),
                "Special Characters Not Allowed");
    }
}
