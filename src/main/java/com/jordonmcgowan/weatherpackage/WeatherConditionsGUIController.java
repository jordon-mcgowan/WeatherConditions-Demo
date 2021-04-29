package com.jordonmcgowan.weatherpackage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;


/**
 * The WeatherConditionsGUIController class is responsible
 * for handling when a user interacts with the GUI.
 *
 * @author  Jordon McGowan
 * @version 1.0
 */
public class WeatherConditionsGUIController {

    private boolean dataRetrieved;
    private String requestedCity;

    //Initialise some constant error message strings
    private static final String NO_RESULTS_FOUND = "No Result Found";
    private static final String CHECK_INTERNET_CONNECTION = "Check Internet Connection";
    private static final String ERROR_OCCURRED = "Error Processing Request";
    private static final String LOG_READING_FILE_EXCEPTION = "An error occurred when reading data from the API: ";
    private static final String LOG_CONNECTION_EXCEPTION = "An error has occurred when connecting to the API: ";
    private static final String LOG_URL_MALFORMED_EXCEPTION = "An error has occurred with URL request: ";
    private static final String LOG_SERVER_EXCEPTION = "An error has occurred with API connection: ";

    //Declare references to JavaFX UI elements
    @FXML private Label generalInformation;
    @FXML private Label locationInformation;
    @FXML private ToggleButton imperialToggle;
    @FXML private Label temperatureInformation;
    @FXML private TextField citySearchTextField;
    @FXML private Label weatherConditionInformation;

    //Initialise logger, input validator and API retriever objects
    private final static Logger exceptionLogger = Logger.getLogger("");
    private final static WeatherConditionsInputValidator inputValidator = new WeatherConditionsInputValidator();
    private final static WeatherConditionsAPIRetriever apiDataRetriever = new WeatherConditionsAPIRetriever();

    /**
     * This method is used to handle presses of the search
     * button.
     */
    @FXML
    private void handleSearchButtonPress() {
        displayDataFromAPIToScreen();
    }

    /**
     * This method is used to handle presses of the temperature
     * unit toggle buttons. displayDataFromAPIToScreen() is
     * called, if data has been retrieved and the user has not
     * changed their initial request in the search bar, so output
     * from the API is displayed to the user.
     */
    @FXML
    private void handleTemperatureUnitButtonPress() {
        if(dataRetrieved && !citySearchTextField.getText().isEmpty() &&
                citySearchTextField.getText().equals(requestedCity)) {
            displayDataFromAPIToScreen();
        }
    }

    /**
     * This method is used to display weather data results
     * from the API to the user. The method preforms input
     * validation before making a query to the API.
     */
    @SuppressWarnings("unchecked")
    private void displayDataFromAPIToScreen() {

        HashMap<String, Object> retrievedJsonMap;
        HashMap<String, Object> mainMap;
        HashMap<String, Object> sysMap;
        HashMap<String, String> weatherMap;
        ArrayList<Object> weatherArrayList;

        //Get the input data for City to search and units selected by the user
        String cityToSearch  = citySearchTextField.getText();
        String temperatureUnit = getSelectedTempUnit();

        //Call the input validation method to see if user has inputted valid query data to search
        String isInputValidResult = inputValidator.checkInputIsValid(cityToSearch);

        if(!(isInputValidResult.isEmpty())) {
            //Invalid input from the user, so display result of validation check to user
            clearUIData();
            generalInformation.setText(isInputValidResult);
        } else {
            //Valid input from the user, retrieve data for location from the API in a HashMap
            cityToSearch = cityToSearch.trim();
            apiDataRetriever.setInputtedUserData(cityToSearch,temperatureUnit);
            try {
                //Attempt to get API data in HashMap
                retrievedJsonMap = apiDataRetriever.getJSONInHashMap();

                //Break the required JSON fields into further HashMaps
                mainMap          = (HashMap<String, Object>) retrievedJsonMap.get("main");
                sysMap           = (HashMap<String, Object>) retrievedJsonMap.get("sys");
                weatherArrayList = (ArrayList<Object>) retrievedJsonMap.get("weather");
                weatherMap       = jsonStringToHash(weatherArrayList.get(0).toString());

                //Set string variables with data from HashMaps
                String temperatureFromAPI    = mainMap.get("temp").toString();
                String minTemperatureFromAPI = mainMap.get("temp_min").toString();
                String maxTemperatureFromAPI = mainMap.get("temp_max").toString();

                //Set the required JFX labels with data from HashMaps
                locationInformation.setText(retrievedJsonMap.get("name") + ", " + sysMap.get("country"));
                temperatureInformation.setText(temperatureFromAPI + temperatureUnit);
                generalInformation.setText("Ranging From " + minTemperatureFromAPI + temperatureUnit + " to "
                        + maxTemperatureFromAPI + temperatureUnit);

                //Description value received from API is lower case, so convert first letter to upper case
                String weatherDescription = weatherMap.get("description");
                weatherConditionInformation.setText(weatherDescription.substring(0, 1).toUpperCase() +
                        weatherDescription.substring(1));

                //Set global variables so class is aware data has been retrieved and what city was queried
                requestedCity = cityToSearch;
                dataRetrieved = true;
            } catch (FileNotFoundException fileNotFoundException) {
                //Catch IOExceptions thrown by the APIRetriever class, display error to the user and log
                handleException(NO_RESULTS_FOUND, LOG_READING_FILE_EXCEPTION + fileNotFoundException);
            } catch (UnknownHostException | NoRouteToHostException connectionException) {
                handleException(CHECK_INTERNET_CONNECTION, LOG_CONNECTION_EXCEPTION + connectionException);
            } catch (MalformedURLException malformedURLException) {
                handleException(ERROR_OCCURRED, LOG_URL_MALFORMED_EXCEPTION + malformedURLException);
            } catch (IOException ioException) {
                handleException(ERROR_OCCURRED, LOG_SERVER_EXCEPTION + ioException);
            }
        }
    }

    /**
     * Method used to display error messages to
     * a user, when an IOException from APIRetriever class
     * is thrown. Exception is also logged.
     */
    private void handleException(String displayMessage, String logMessage) {
        clearUIData();
        dataRetrieved = false;
        generalInformation.setText(displayMessage);
        exceptionLogger.log(Level.WARNING, logMessage);
    }

    /**
     * Method to check what temperature unit
     * has been selected by the user, depending on what
     * toggle button has been selected. If no toggle button
     * is selected, the unit returned defaults to metric.
     * @return String This returns String of units the API
     *         needs to be queried with.
     */
    private String getSelectedTempUnit() {
        String temperatureUnit;
        if(imperialToggle.isSelected()) {
            temperatureUnit = "°F";
        } else {
            temperatureUnit = "°C";
        }
        return temperatureUnit;
    }

    /**
     * Method that clears the UI text fields
     */
    private void clearUIData() {
        locationInformation.setText("");
        weatherConditionInformation.setText("");
        temperatureInformation.setText("");
    }

    /**
     * Method that converts a String, in JSON
     * format, to a HashMap.
     * @param str This is the string to be
     *            converted to HashMap
     * @return HashMap Returns the HashMap
     *         of the provided string
     */
    private HashMap<String, String> jsonStringToHash(String str) {
        String value = str;

        //Remove curly brackets and split the string to create key-value pairs
        value = value.substring(1, value.length()-1);
        String[] keyValuePairs = value.split(",");

        HashMap<String,String> hashedMap = new HashMap<>();

        //Iterate over the pairs
        for(String pair : keyValuePairs) {
            //Split the pairs to get key and value
            String[] entry = pair.split("=");
            hashedMap.put(entry[0].trim(), entry[1].trim());
        }
        return hashedMap;
    }
}
