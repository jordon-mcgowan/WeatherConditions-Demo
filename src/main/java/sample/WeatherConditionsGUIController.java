package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherConditionsGUIController {

    private boolean dataRetrieved;
    private String requestedCity;

    //Initialise some constant error message strings
    private static final String NO_RESULTS_FOUND = "No Result Found";
    private static final String CHECK_INTERNET_CONNECTION = "Check Internet Connection";
    private static final String ERROR_OCCURRED = "Error Processing Request";

    //Declare references to JavaFX UI elements
    @FXML private Label locationInformation;
    @FXML private Label temperatureInformation;
    @FXML private Label generalInformation;
    @FXML private Label weatherConditionInformation;
    @FXML private TextField citySearchTextField;
    @FXML private ToggleButton imperialToggle;

    private final static Logger exceptionLogger = Logger.getLogger("");
    private final static WeatherConditionsInputValidator inputValidator = new WeatherConditionsInputValidator();

    /**
     * This method is used to handle presses of the search
     * button. displayDataFromAPIToScreen() is called so
     * output from the API is displayed to the user
     */
    @FXML
    private void handleSearchButtonPress()
    {
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
    private void handleTemperatureUnitButtonPress(){
        if(dataRetrieved && !citySearchTextField.getText().isEmpty() &&
                citySearchTextField.getText().equals(requestedCity)) {
            System.out.println("Here");
            displayDataFromAPIToScreen();
        }
    }

    /**
     * This method is used to display weather data results
     * from the API to the user. The method preforms input
     * validation before making a query to the API.
     */
    private void displayDataFromAPIToScreen(){

        // Button was clicked, do something...
        HashMap<String, Object> myHash;
        HashMap<String, Object> mainMap;
        HashMap<String, Object> sysMap;
        HashMap<String, String> weatherMap;
        ArrayList<Object> weatherArrayList;

        //Get the input data for City to search and units selected by the user
        String city  = citySearchTextField.getText();
        String units = getSelectedTempUnit();

        //Call the input validation method to see if user has inputted valid query data to search
        String isInputValidResult = inputValidator.checkInputIsValid(city);

        if(!(isInputValidResult.isEmpty())) {
            //Invalid input from the user, so display result of validation check to user
            clearUIData();
            generalInformation.setText(isInputValidResult);
        } else{
            //Valid input from the user, retrieve data for location from the API in a HashMap
            city = city.trim();
            final WeatherConditionsAPIRetriever myRetriever = new WeatherConditionsAPIRetriever(city,units);
            try {
                //Attempt to get API data in HashMap
                myHash     = myRetriever.getJSONInHashMap();



                //Break the required JSON fields into further HashMaps
                mainMap          = (HashMap<String, Object>) myHash.get("main");
                sysMap           = (HashMap<String, Object>) myHash.get("sys");
                weatherArrayList = (ArrayList) myHash.get("weather");
                weatherMap       = jsonStringToHash(weatherArrayList.get(0).toString());

                //Set global variables so handleTemperatureUnitButtonPress has access to temperature value
                String temperatureFromAPI    = mainMap.get("temp").toString();
                String minTemperatureFromAPI = mainMap.get("temp_min").toString();
                String maxTemperatureFromAPI = mainMap.get("temp_max").toString();

                //Set the required JFX labels with data from HashMaps
                locationInformation.setText(myHash.get("name") + ", " + sysMap.get("country"));
                temperatureInformation.setText(temperatureFromAPI + units);
                generalInformation.setText("Ranging From " + minTemperatureFromAPI + units + " to "
                        + maxTemperatureFromAPI + units);

                //Description value received from API is lower case, so convert first letter to upper case
                String weatherDescription = weatherMap.get("description");
                weatherConditionInformation.setText(weatherDescription.substring(0, 1).toUpperCase() +
                        weatherDescription.substring(1));

                //Set global variables so class is aware data has been retrieved and what city was queried
                requestedCity = city;
                dataRetrieved = true;
            }catch(FileNotFoundException fileNotFoundException){
                //Catch IOExceptions thrown by the APIRetriever class, display error to the user and log
                clearUIData();
                dataRetrieved = false;
                generalInformation.setText(NO_RESULTS_FOUND);
                exceptionLogger.log(Level.WARNING, fileNotFoundException + " No Data Received From API");
            } catch(UnknownHostException | NoRouteToHostException unknownHostException){
                clearUIData();
                dataRetrieved = false;
                generalInformation.setText(CHECK_INTERNET_CONNECTION);
                exceptionLogger.log(Level.WARNING, unknownHostException + "Could not connect or connection interrupted to API");
            } catch(MalformedURLException malformedURLException){
                clearUIData();
                dataRetrieved = false;
                generalInformation.setText(ERROR_OCCURRED);
                exceptionLogger.log(Level.WARNING, malformedURLException.toString());
            } catch(IOException ioException){
                clearUIData();
                dataRetrieved = false;
                generalInformation.setText(ERROR_OCCURRED);
                exceptionLogger.log(Level.WARNING, ioException +"");
            }
        }
    }

    /**
     * This method is used to check what temperature unit
     * has been selected by the user, depending on what
     * toggle button has been selected. If no toggle button
     * is selected, the unit returned defaults to metric.
     * @return String This returns String of units the API
     * needs to be queried with
     */
    private String getSelectedTempUnit(){
        String tempUnit;
        if(imperialToggle.isSelected()){
            tempUnit = "°F";
        }else{
            tempUnit = "°C";
        }
        return tempUnit;
    }

    /**
     * This method is used to clear the UI text fields
     */
    private void clearUIData(){
        locationInformation.setText("");
        weatherConditionInformation.setText("");
        temperatureInformation.setText("");
    }

    /**
     * This method is used to convert a String, in JSON
     * format, to a HashMap.
     * @param str This is the string to be converted to HashMap
     * @return HashMap Returns the HashMap of the provided string
     */
    private HashMap<String, String> jsonStringToHash(String str){
        String value = str;

        //Remove curly brackets and split the string to create key-value pairs
        value = value.substring(1, value.length()-1);
        String[] keyValuePairs = value.split(",");

        HashMap<String,String> hashedMap = new HashMap<>();

        //Iterate over the pairs
        for(String pair : keyValuePairs)
        {
            //Split the pairs to get key and value
            String[] entry = pair.split("=");
            hashedMap.put(entry[0].trim(), entry[1].trim());
        }
        return hashedMap;
    }
}
