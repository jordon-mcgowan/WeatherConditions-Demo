import com.sun.tools.javac.Main;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class WeatherConditionsGUIControllerTest extends ApplicationTest {
    //Create some string arrays containing input data for various scenarios
    private final String[] blankInputVariations = {""," ","          "};
    private final String[] specialCharsInputVariations = {"*","&", "L!merick", "!£$%"};
    private final String[] numericInputVariations = {"1","20","100","Limerick 200", "200 Limerick","5Cork",
            "Cork5", "L1merick"};

    @Override
    public void start (Stage stage) throws IOException {
        Parent mainNode = FXMLLoader.load(Objects.requireNonNull(Main.class.getClassLoader().getResource("WeatherConditionsGUILayout.fxml")));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @AfterEach
    public void tearDown() throws TimeoutException {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    @DisplayName("(displayDataFromAPIToScreen): Valid String Inputted -> Display Result To GUI")
    public void testInputCityReturned () {
        clickOn("#citySearchTextField");
        write("Limerick, IE");
        clickOn("#searchCityButton");
        verifyThat("#locationInformation", hasText("Limerick, IE"));
    }

    @RepeatedTest(3)
    @DisplayName("(displayDataFromAPIToScreen): Empty String Inputted -> Display Blank Input Error To GUI")
    public void testInputBlankErrorReturned (RepetitionInfo repetitionInfo) {
        clickOn("#citySearchTextField");
        write(blankInputVariations[repetitionInfo.getCurrentRepetition()-1]);
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("No location provided"));
    }

    @RepeatedTest(8)
    @DisplayName("(displayDataFromAPIToScreen): Number String Inputted -> Display Number Input Error To GUI")
    public void testInputNumberErrorReturned(RepetitionInfo repetitionInfo) {
        clickOn("#citySearchTextField");
        write(numericInputVariations[repetitionInfo.getCurrentRepetition()-1]);
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("Can Not Contain Numeric Value"));
    }

    @RepeatedTest(4)
    @DisplayName("(displayDataFromAPIToScreen): Special Character String Inputted -> Display Special Character Input Error To GUI")
    public void testInputSpecialCharErrorReturned(RepetitionInfo repetitionInfo) {
        clickOn("#citySearchTextField");
        write(specialCharsInputVariations[repetitionInfo.getCurrentRepetition()-1]);
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("Special Characters Not Allowed"));
    }
}
