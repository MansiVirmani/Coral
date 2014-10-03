import com.android.uiautomator.core.UiCollection;
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
            UiCollection Linear=new UiCollection(new UiSelector().packageName("com.android.settings").className("android.widget.LinearLayout").resourceId("com.android.settings:id/header_connection_airplane_mode"));
            UiObject AirPlane_CheckBox=Linear.getChild(new UiSelector().resourceId("com.android.settings:id/check_check"));
            if (!AirPlane_CheckBox.isChecked()) {
                AirPlane_CheckBox.click();
                System.out.println("Success");

            } else {
                System.out.println("Failure");
            }
        }
    }
}