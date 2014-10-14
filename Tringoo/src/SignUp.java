import android.telephony.PhoneNumberUtils;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.validator.EmailValidator;
//import org.omg.CORBA.Environment;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by mansi on 13/10/14.
 */
public class SignUp extends UiAutomatorTestCase {

    String _PackageName = "kujo.app";
    public static Logger Tlogger = new TringooLogger().SetLogger();

   UiObject Next=GetUiObjectByResourceId("kujo.app:id/next_button");

    Boolean FName_Empty, LName_Empty, Ph_Empty, Em_Empty,Ph_InValid,Em_Invalid;
    Boolean NextButton_EnabledValid;

    public void testSignUp() throws UiObjectNotFoundException ,NumberParseException {
        Tlogger.info("Sign Up Test <------> Initiate");
        //Check Mandatory Fields(First Name, Last Name, Phone Number and Email Address)

        Tlogger.info(System.getProperty("line.separator") + "Check Mandatory Fields");

        ValidationTest_FirstName();
        ValidationTest_LastName();
        ValidationTest_Phone();
        ValidationTest_Email();

        Tlogger.info(System.getProperty("line.separator") + "Check Mandatory Fields --> Done");

        //  Check Enabled and Disabled

        if (ValidationTest_NextEnabled()) {
            if (Next.clickAndWaitForNewWindow()) {
                if (GetUiObjectByResourceId("android:id/alertTitle").exists())
                    Tlogger.severe("Invalid Credentials" + GetUiObjectByResourceId("android:id/alertTitle").getText());
                else if (GetUiObjectByText("Make a local verification call").exists()) {
                    if (GetUiObjectByText("Make a local verification call").isEnabled())
                    {
                    Tlogger.info("Making local verification Call --> Trigger");
                        if(GetUiObjectByText("Make a local verification call").click())
                        {

                        }
                        else
                        {
                            Tlogger.severe("Failure:Call Verification");
                        }
                }   else
                    {
                        Tlogger.severe("Failure:Call Verification Disabled");
                    }
                }
                else
                {
                    Tlogger.severe("Failure:Oh Snap");
                }

            }

        }
    }


    void ValidationTest_FirstName() throws  UiObjectNotFoundException
    {
        Tlogger.info("Validating First Name ---> Start");
        FName_Empty=false;

        if(GetUiObjectByResourceId("kujo.app:id/first_name").getText().isEmpty() || GetUiObjectByResourceId("kujo.app:id/first_name").getText().toLowerCase().contains("first name")) {
            FName_Empty=true;
            Tlogger.warning(System.getProperty("line.separator")+"First Name is Empty");
        }
        Tlogger.info("Validating First Name ---> End");
    }

    void ValidationTest_LastName() throws  UiObjectNotFoundException
    {
        Tlogger.info("Validating Last Name ---> Start");
        LName_Empty=false;
        if(GetUiObjectByResourceId("kujo.app:id/last_name").getText().isEmpty() || GetUiObjectByResourceId("kujo.app:id/last_name").getText().toLowerCase().contains("last name")) {
            LName_Empty=true;
            Tlogger.warning(System.getProperty("line.separator")+"Last Name is Empty");
        }
        Tlogger.info("Validating Last Name ---> End");
    }

    void ValidationTest_Phone() throws  UiObjectNotFoundException,NumberParseException
    {
        Tlogger.info("Validating Phone Number ---> Start");
        PhoneNumberUtil Validate=PhoneNumberUtil.getInstance();
        String _PhoneNumber=GetUiObjectByResourceId("kujo.app:id/phone_number").getText();
        String CountryCode=GetUiObjectByResourceId("kujo.app:id/country_code_spinner").getChild(new UiSelector().className("android.widget.TextView")).getText();
        String CCode="";
        if (CountryCode.contains("+"))
             CCode=CountryCode.replace("+","");
        String RegionCode=Validate.getRegionCodeForCountryCode(Integer.parseInt(CCode));
        Phonenumber.PhoneNumber PH= Validate.parse(CountryCode + _PhoneNumber,RegionCode);

        if (_PhoneNumber.isEmpty() || _PhoneNumber.toLowerCase().contains("phone number") ) {
            Ph_Empty = true;
            Tlogger.warning(System.getProperty("line.separator") + "Phone Number is Empty");
        } else {
            Ph_InValid = Validate.isValidNumber(PH);
            Tlogger.warning(System.getProperty("line.separator") + "Phone Number is Invalid");
        }
        Tlogger.info("Validating Phone Number ---> End");
    }

    void ValidationTest_Email() throws UiObjectNotFoundException
    {
        Tlogger.info("Validating Email ---> Start");
        if (GetUiObjectByResourceId("kujo.app:id/email").getText().isEmpty() || GetUiObjectByResourceId("kujo.app:id/email").getText().toLowerCase().contains("email address")) {
            Em_Empty = true;
            Tlogger.warning(System.getProperty("line.separator") + "Email Address is Empty");
        }
        else {
            EmailValidator Validate = EmailValidator.getInstance();
            Em_Invalid = Validate.isValid(GetUiObjectByResourceId("kujo.app:id/email").getText());
            if (Em_Invalid)
            Tlogger.warning(System.getProperty("line.separator") + "Email Address is Invalid");
        }
        Tlogger.info("Validating Email ---> End");
    }

    Boolean ValidationTest_NextEnabled() throws UiObjectNotFoundException
    {
        if (FName_Empty == true || LName_Empty == true || Ph_Empty == true || Em_Empty == true) {
            if (Next.isEnabled())
                Tlogger.severe("Next button is enabled");
            return false;
        } else if (!Next.isEnabled()) {
            Tlogger.severe("Next button is not enabled. It should have been.");
            return false;
        } else
            return true;

    }


   public UiObject GetUiObjectByText(String text)
    {
        return new UiObject(new UiSelector().packageName(_PackageName).text(text));
    }
   public UiObject GetUiObjectByResourceId(String ResId)
    {
        return new UiObject(new UiSelector().packageName(_PackageName).resourceId(ResId));
    }
    UiObject GetUiObjectByClass(String Class)
    {
        return new UiObject(new UiSelector().packageName(_PackageName).className(Class));
    }
    UiObject GetUiObjectByDescription(String Desc)
    {
        return new UiObject(new UiSelector().packageName(_PackageName).descriptionContains(Desc));
    }

}
