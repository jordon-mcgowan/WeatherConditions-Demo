import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import static org.testfx.api.FxAssert.*;
import static org.testfx.matcher.control.LabeledMatchers.*;
import javafx.scene.control.Label;


public class WeatherConditionsGUIControllerTest extends ApplicationTest{

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(Main.class.getClassLoader().getResource("WeatherConditionsGUILayout.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    public void setUp () throws Exception {
    }

    @AfterEach
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    @DisplayName("UserInputsCityGetBackCityName")
    public void testInputCityReturned () {
        clickOn("#citySearchTextField");
        write("Limerick, IE");
        clickOn("#searchCityButton");
        verifyThat("#locationInformation", hasText("Limerick, IE"));
    }

    @Test
    @DisplayName("UserInputsBlankCityDisplayBlankErrorMessage")
    public void testInputBlankErrorReturned () {
        clickOn("#citySearchTextField");
        write("");
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("No location provided"));
    }

    @Test
    @DisplayName("UserInputsNumberDisplayNumberErrorMessage")
    public void testInputNumberErrorReturned () {
        clickOn("#citySearchTextField");
        write("12345");
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("Can Not Contain Numeric Value"));
    }

    @Test
    @DisplayName("UserInputsSpecialCharDisplaySpecialCharErrorMessage")
    public void testInputSpecialCharErrorReturned () {
        clickOn("#citySearchTextField");
        write("()");
        clickOn("#searchCityButton");
        verifyThat("#generalInformation", hasText("Special Characters Not Allowed"));
    }
}
