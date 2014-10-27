import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;

import java.io.IOException;

/**
 * Created by mansi on 21/10/14.
 */
public class TringooUtils {

    public static String PackageName_Stag="kujo.app.alpha";
    public static String PackageName_Prod="kujo.app";
    public static UiObject Staging_Inf = new UiObject(new UiSelector().packageName(PackageName_Stag));
    public static UiObject Prod_Inf = new UiObject(new UiSelector().packageName(PackageName_Prod));
    public static  String _packageName;
    public static String FirstName,LastName,Phone,Email;

    public TringooUtils()
    {
        SetSignUpInputs();
    }

    public void SetSignUpInputs()
    {
        FirstName="Mansi";
        LastName="Virmani";
        Phone="7042393707";
        Email="mansivirmani18@gmail.com";

    }
    public UiObject GetUiObjectByText(String text)
    {
        return new UiObject(new UiSelector().packageName(PackageName_Stag).text(text));
    }
    public UiObject GetUiObjectByResourceId(String ResId)
    {
        return new UiObject(new UiSelector().packageName(PackageName_Stag).resourceId(ResId));
    }
    UiObject GetUiObjectByClass(String Class)
    {
        return new UiObject(new UiSelector().packageName(PackageName_Stag).className(Class));
    }
    UiObject GetUiObjectByDescription(String Desc)
    {
        return new UiObject(new UiSelector().packageName(PackageName_Stag).descriptionContains(Desc));
    }

    public static boolean SetAppPackageName() throws UiObjectNotFoundException {

        if (Staging_Inf.exists()){
            _packageName = PackageName_Stag;
            return true;
        }
        else if (Prod_Inf.exists()){
            _packageName = PackageName_Prod;
            return true;
        } else return false;
    }

    public void ClearAppData() throws IOException, InterruptedException {
        String queryString = "pm clear " + _packageName;

        Runtime.getRuntime().exec(queryString);
        Thread.sleep(2000);
    }
}
