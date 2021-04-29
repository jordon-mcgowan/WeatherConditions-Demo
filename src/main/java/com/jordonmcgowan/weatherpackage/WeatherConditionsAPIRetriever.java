package com.jordonmcgowan.weatherpackage;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;
import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.io.InputStreamReader;

/**
 * The WeatherConditionsAPIRetriever class is responsible
 * for retrieving json data, in a HashMap, from the
 * OpenWeatherMap API.
 *
 * @author  Jordon McGowan
 * @version 1.0
 */
public class WeatherConditionsAPIRetriever {
    private String units;
    private String location;

    /**
     * Method that sets the instance variables for city
     * and temperature unit to be requested from API.
     * @param inputLocation String variable indicating
     *                      what City the user has inputted
     * @param inputUnit String variable indicating
     *                  what unit of temperature the
     *                  API should be requested for.
     */
    public void setInputtedUserData(String inputLocation, String inputUnit) {
        location = inputLocation;

        //Assign appropriate unit request for API, based on what user has selected
        if(inputUnit.equals("°C")) {
            units = "&units=metric";
        } else if(inputUnit.equals("°F")){
            units = "&units=imperial";
        }
    }

    /**
     * Method that converts String of JSON
     * data to a HashMap,using the Jackson
     * Library.
     * @return HashMap Returns HashMap of
     *         Json data from the API.
     */
    @SuppressWarnings("unchecked")
    public HashMap <String, Object> getJSONInHashMap() throws IOException {
        String jsonData = getJSONWeatherData();
        HashMap<String, Object> jsonMap = new HashMap<>();
        if(!jsonData.isEmpty()) {
                jsonMap = new ObjectMapper().readValue(jsonData, HashMap.class);
        }
        return jsonMap;
    }

    /**
     * Method that gets request data from
     * the OpenWeatherMapAPI. Data retrieved
     * is returned as a JSON string.
     * @return String Returns String of
     *         retrieved Json data from
     *         the API.
     */
    private String getJSONWeatherData() throws IOException {
        String lineFromInputStream;
        StringBuilder readJson = new StringBuilder();
        URLConnection apiConnection = getAPIConnector();
        try (InputStreamReader myReader = new InputStreamReader(apiConnection.getInputStream());
            BufferedReader rd = new BufferedReader(myReader)) {
            while ((lineFromInputStream = rd.readLine()) != null) {
                readJson.append(lineFromInputStream);
            }
        }
        return readJson.toString();
    }

    /**
     * Method that creates URLConnection object
     * for the OpenWeatherMap API
     * @return URLConnection Returns connection to API
     */
    private URLConnection getAPIConnector() throws IOException {
        final String WEATHER_API_KEY = "1fb9c93543bb08cce7c8d3bdc1803f34";
        final String URL_STRING = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid="
                 + WEATHER_API_KEY + units;

        URL url = new URL(URL_STRING);
        return url.openConnection();
    }
}
