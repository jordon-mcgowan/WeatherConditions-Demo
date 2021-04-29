# WeatherConditions-Demo
A Java GUI project, built with JavaFX, that displays the weather conditions for a given location, using the OpenWeatherMap API.

## Libraries/Tools Used

* OpenWeatherMap API
* JUnit 5 For Testing
* Jackson JSON parsing library
* JavaFX 
* TestFX for JavaFX GUI testing

## How To Setup And Run
The following setup guide will deomonstrate how to run the project on a Windows 10 machine, with IntelliJ IDEA.

### Downloading The Repository

  1) In IntelliJ, select 'File' -> 'New' -> 'Project From Version Control'.
  
  2) Select Version Control as 'Git' and provide the repository URL as: https://github.com/jordon-mcgowan/WeatherConditions-Demo
  
  3) Select a directory on your local machine for the project to be saved to.
  
  4) Click "Clone".
  
The project should now be stored on your local machine


### Running The Application
Most of the additional libraries required to run the project are pulled through Maven. However, currently there is an issue
with JDK 16.0.1 and both the JavaFX and TestFX libraries when trying to include them via Maven. Therefore, both libraries should
be included by storing them locally and pointing your IntelliJ environment to them. This can be achieved in the following steps:

  1) Download the JavaFX library from https://gluonhq.com/products/javafx/
  
  2) Download the TestFX library (testfx-junit5 from group org.testfx (version 4.0.16-alpha)) from https://jar-download.com/artifacts/org.testfx
  
  3) Extract both files to seperate directories. TestFX when extracted should just contain JAR files, while the JAR files for the JavaFX library are contained in the 'lib' folder of the extracted zip.
  
  4) In IntelliJ, with the project open, select 'File' -> 'Project Structure' -> 'Libraries' -> '+(New Project Library)' -> 'Java'
  
  5) For the JavaFX library, select the 'lib' folder, where you have stored the libary on your local machine.
  
  6) For the TestFX library, select the folder you have extracted the JAR files to on your local machine.
  
  7) After both libraries have been added, attempt to build and run the 'WeatherConditionsMain' file in the project. 
  
  8) The build should fail, if it does, select 'Run' -> 'Edit Configurations' -> 'Application' -> 'Main' -> 'Modify Options' -> 'Add VM Options'
  
  9) Add the following VM option:
     --module-path "/Path/To/Directory/Where/JavaFX/lib/Folder/Is/Stored" --add-modules javafx.controls,javafx.fxml 
     
     For example, on my environment this VM option is:
     --module-path "/Users/MY PC/JavaFxLib/javafx-sdk-11.0.2/lib" --add-modules javafx.controls,javafx.fxml 
     
  10) Once added, hit 'apply' and 'ok'. The 'WeatherConditionsMain' file should now build. 
  
  ## Functionality
  The requirements for the project were as follows:
  
    Create some software that will accept a location and return information about the weather conditions at that location, using a public weather web service.
    
  The following functionality was achieved:
  
  * User can input location, application displays weather conditions (current temperature, temperature range and weather description), of inputted location. 
  * User can select what unit of measurement the temperature is displayed to them in.
  * GUI Interface.
  * Error handling for invalid input, bad responses from the API and device not being connected to the internet.
  * Testing suite for happy path and various error scenarios.
  
  ## Further Enhancements:
  
  If this was a bigger project, I would make the following enhancements:
  
  1) Store the API Key securely. For this project, I have stored the API key in source code. This is a poor practice as the key can be exposed easily and is a security risk. If I
     was to work on this project further, I would look into a cloud service, such as AWS Secrets Manager, to store the key.
     
  2) Improve the GUI. Given more time, I would add further enhancements to the look of the GUI, using CSS techniques in JavaFX.
  
  3) Improve unit select functionality. When the user inputs a city, the application queries the API and retrieves the weather data. If the user then selects a different unit of measurement to display the data in,
     the API is queried again. I feel this can be improved. Rather than querying the API again, the application could convert and store the additonal unit of measurement after the first query. This way, the application
     can update the unit of measurement when requested, without making another API request.
  
  4) Larger scale enhancements: Currently, the API key used for the project can only process 60 requests per minute, with 1000000 requests per month. To bring this project to a larger scale, a more scalable API subscription,
     or different API, would be required.
  
