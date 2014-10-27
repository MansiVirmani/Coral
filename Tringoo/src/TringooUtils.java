import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import java.io.IOException;

/**
 * Created by mansi on 21/10/14.
 */
public class TringooUtils {

    public static final String PACKAGE_NAME_STAG ="kujo.app.alpha";
    public static final String PACKAGE_NAME_PROD ="kujo.app";
    public static UiObject _stagingInf = new UiObject(new UiSelector().packageName(PACKAGE_NAME_STAG));
    public static UiObject prodInf = new UiObject(new UiSelector().packageName(PACKAGE_NAME_PROD));
    public static  String _packageName;
    public static String firstName, lastName, phone, email;

    public TringooUtils(ENV env)
    {
        setSignUpInputs(env);
    }

    public void setSignUpInputs(ENV env)
    {
        firstName ="Mansi";
        lastName ="Virmani";
        phone ="7042393707";
        email ="mansivirmani18@gmail.com";
        _packageName = getPackageName(env);
    }

    private String getPackageName(ENV env) {
        if(env == ENV.PROD){
            return PACKAGE_NAME_PROD;
        }
        else
        {
            return PACKAGE_NAME_STAG;
        }
    }

    public UiObject getUiObjectByText(String text)
    {
        return new UiObject(new UiSelector().packageName(_packageName).text(text));
    }
    public UiObject getUiObjectByResourceId(String ResId)
    {
        return new UiObject(new UiSelector().packageName(_packageName).resourceId(ResId));
    }
    UiObject GetUiObjectByClass(String Class)
    {
        return new UiObject(new UiSelector().packageName(_packageName).className(Class));
    }
    UiObject getUiObjectByDescription(String Desc)
    {
        return new UiObject(new UiSelector().packageName(_packageName).descriptionContains(Desc));
    }

    public static boolean selectAppPackageName() throws UiObjectNotFoundException {

        if (_stagingInf.exists()){
            _packageName = PACKAGE_NAME_STAG;
            return true;
        }
        else if (prodInf.exists()){
            _packageName = PACKAGE_NAME_PROD;
            return true;
        } else return false;
    }

    public void clearAppData() throws IOException, InterruptedException {
        String queryString = "pm clear " + _packageName;

        Runtime.getRuntime().exec(queryString);
        Thread.sleep(2000);
    }

    public enum ENV{
        STAGING, PROD
    }
}
