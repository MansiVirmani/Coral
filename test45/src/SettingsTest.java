import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * Created by mansi on 1/10/14.
 */
public class SettingsTest extends UiAutomatorTestCase {

    public void test_airplane() throws UiObjectNotFoundException {


        UiSelector settingIcon_UiSelector = new UiSelector().packageName("com.sec.android.app.launcher").text("Settings");

        UiObject setingsIcon_UiObject = new UiObject(settingIcon_UiSelector);

        if (setingsIcon_UiObject.click()) {
            UiSelector CheckBox = new UiSelector().packageName("com.android.settings").className("android.widget.CheckBox");
            UiObject AirPlane_CheckBox = new UiObject(CheckBox);
            if (!AirPlane_CheckBox.isChecked()) {
                AirPlane_CheckBox.click();
                System.out.println("Success");

            } else {
                System.out.println("Failure");
            }
        }
    }
}