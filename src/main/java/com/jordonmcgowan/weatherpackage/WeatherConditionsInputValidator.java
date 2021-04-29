package com.jordonmcgowan.weatherpackage;

/**
 * The WeatherConditionsInputValidator class
 * is responsible for checking a user has
 * inputted valid data.
 *
 * @author  Jordon McGowan
 * @version 1.0
 */

public class WeatherConditionsInputValidator {

    //Initialise some constant error message strings
    private static final String BLANK_INPUT = "No location provided";
    private static final String CONTAINS_NUMBER= "Can Not Contain Numeric Value";
    private static final String SPECIAL_CHARACTER="Special Characters Not Allowed";

    /**
     * Method that checks user has inputted valid data.
     * @param input String of the inputted data from user
     *              to be validated.
     * @return String Returns string with error message if
     *         input data is invalid, otherwise returns
     *         blank string
     */
    public String checkInputIsValid(String input) {
        String errorString = "";

        if (input.isBlank()) {
            // Inform user they have provided a blank value
            errorString = BLANK_INPUT;
        } else if (checkStringContainsNumber(input)) {
            // Inform user they have provided a numeric value
            errorString = CONTAINS_NUMBER;
        } else if (!input.matches("[a-zA-Z, ]*")) {
            // Inform user they have provided an illegal special character
            errorString = SPECIAL_CHARACTER;
        }
        return errorString;
    }

    /**
     * Method that checks for presence of number in
     * a String.
     * @param stringToCheck String to check for number.
     * @return Boolean True returned if string contains
     *                 number, otherwise false.
     */
    private boolean checkStringContainsNumber(String stringToCheck) {
        boolean containsNum = false;

        //Loop through a character array of the string, return true if number is found
        for (char c : stringToCheck.toCharArray()) {
            if (containsNum = Character.isDigit(c)) {
                break;
            }
        }
        return containsNum;
    }
}
