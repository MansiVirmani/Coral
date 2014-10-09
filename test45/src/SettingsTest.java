import android.widget.LinearLayout;
import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;

/**
 * Created by mansi on 1/10/14.
 */
public class SettingsTest extends UiAutomatorTestCase {

    public void test_airplane() throws UiObjectNotFoundException {


        UiSelector settingIcon_UiSelector = new UiSelector().packageName("com.sec.android.app.launcher").text("Settings");

        UiObject setingsIcon_UiObject = new UiObject(settingIcon_UiSelector);

        if (setingsIcon_UiObject.click()) {

//Snippet to turn device into airplane mode if it is not.
//            UiCollection Linear=new UiCollection(new UiSelector().packageName("com.android.settings").className("android.widget.LinearLayout").resourceId("com.android.settings:id/header_connection_airplane_mode"));
//            UiObject AirPlane_CheckBox=Linear.getChild(new UiSelector().resourceId("com.android.settings:id/check_check"));
//            if (!AirPlane_CheckBox.isChecked()) {
//                AirPlane_CheckBox.click();
//                System.out.println("Success");
//
//            } else {
//                System.out.println("Failure");
//            }

            // Snippet to try UIScrollable
            UiScrollable ScrollDownSettings = new UiScrollable(new UiSelector().className("android.widget.ListView"));
            if (ScrollDownSettings.isScrollable()) {
                ScrollDownSettings.setAsVerticalList();
                UiObject NearByDevices = ScrollDownSettings.getChildByText(new UiSelector().className("android.widget.TextView"),"Nearby devices");
                Boolean Caught=false;
                do {
                    Caught=ScrollDownSettings.scrollIntoView(NearByDevices);
                    System.out.println("Scroll Attempt:" + Caught.toString());
                }while( Caught!= true);

                if ( Caught==true && NearByDevices.click())
                    System.out.println("Success");
                else
                    System.out.println("Failure:Could not locate object on scroll");
            }
            else
            {
                System.out.println("Failure: could not scroll");
            }
        }
        else
            System.out.println("Failure:Could not open Settings");
    }
}