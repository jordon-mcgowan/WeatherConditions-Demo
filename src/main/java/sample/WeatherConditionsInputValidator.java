package sample;

public final class WeatherConditionsInputValidator {
    private static final String BLANK_INPUT = "No location provided";
    private static final String CONTAINS_NUMBER= "Can Not Contain Numeric Value";
    private static final String SPECIAL_CHARACTER="Special Characters Not Allowed";

    public String checkInputIsValid(String input){
        String errorString = "";

        if(input.isBlank())
        {
            // Inform user they have provided a blank value- API is not called.
            errorString = BLANK_INPUT;
        } else if(checkStringContainsNumber(input))
        {
            // Inform user they have provided a blank value- API is not called.
            errorString = CONTAINS_NUMBER;
        } else if(!input.matches("[a-zA-Z, ]*")){

            // Inform user they have provided a blank value- API is not called.
            errorString = SPECIAL_CHARACTER;
        }
        return errorString;
    }

    private boolean checkStringContainsNumber(String s){
        boolean containsNum = false;

        for (char c : s.toCharArray()) {
            if (containsNum = Character.isDigit(c)) {
                break;
            }
        }
        return containsNum;
    }
}
