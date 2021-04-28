package sample;

/*
  The WeatherConditionsAPIRetriever class is responsible
  for retrieving json data, in a HashMap, from the
  OpenWeatherMap API.

 * @author  Jordon McGowan
 * @version 1.0
 * @since   2021-04-26
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;

public final class WeatherConditionsAPIRetriever {
    String location;
    String units;

    /**
     * Parameterized constructor for the class. Sets
     * the location and temperature units instance
     * variables.
     * @param location String variable indicating
     *                 what City the user has inputted
     * @param unit     String variable indicating.#
     *                 what unit of temperature the
     *                 API should be requested for.
     */
    public WeatherConditionsAPIRetriever(String location, String unit){

        this.location = location;
        if(unit.equals("°C")) {
            this.units = "&units=metric";
        } else if(unit.equals("°F")){
            this.units = "&units=imperial";
        }
    }

    /**
     * This method is used to call the getJSONWeatherData()
     * function to obtain a String of the returned Json data
     * from the API. This method then converts the Json string
     * into a HashMap, using the Jackson library.
     * @return HashMap This returns HashMap of retrieved
     * Json data from the API.
     */
    public HashMap <String, Object> getJSONInHashMap() throws IOException {
        String jsonData = getJSONWeatherData();
        HashMap<String, Object> jsonMap = new HashMap<>();
        if(!jsonData.isEmpty()) {
                jsonMap = new ObjectMapper().readValue(jsonData,HashMap.class);
        }
        return jsonMap;
    }

    /**
     * This method is used to obtain a String of
     * the returned Json data from the API. This
     * method then converts the Json string into
     * a HashMap, using the Jackson library.
     * @return HashMap This returns HashMap of retrieved
     * Json data from the API. If nothing is retrieved from
     * the API, an empty HashMap is returned.
     */
    private String getJSONWeatherData() throws IOException {
        String line;
        StringBuilder readJson = new StringBuilder();
        URLConnection apiConnection = getAPIConnector();
        try (InputStreamReader myReader = new InputStreamReader(apiConnection.getInputStream());
            BufferedReader rd = new BufferedReader(myReader)) {
            while ((line = rd.readLine()) != null) {
                readJson.append(line);
            }
        }
        return readJson.toString();
    }

    private URLConnection getAPIConnector() throws IOException {
        final String WEATHER_API_KEY = "1fb9c93543bb08cce7c8d3bdc1803f34";
        final String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + WEATHER_API_KEY
                +units;
        URL url = new URL(urlString);
        return url.openConnection();
    }
}