import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.logging.Logger;

/**
 * Created by mansi on 13/10/14.
 */
public class SignUp extends UiAutomatorTestCase {

    //String _PackageName_Stag = ";
    public static Logger Tlogger = new TringooLogger().SetLogger();
    TringooUtils Utils=new TringooUtils(TringooUtils.ENV.STAGING);

    UiObject Next=Utils.getUiObjectByResourceId("kujo.app.alpha:id/next_button");
    UiObject FirstName= Utils.getUiObjectByResourceId("kujo.app.alpha:id/first_name");
    UiObject LastName=Utils.getUiObjectByResourceId("kujo.app.alpha:id/last_name");
    UiObject Phone=Utils.getUiObjectByResourceId("kujo.app.alpha:id/phone_number");
    UiObject CountryCode=Utils.getUiObjectByResourceId("kujo.app.alpha:id/country_code_spinner");
    UiObject EmailId=Utils.getUiObjectByResourceId("kujo.app.alpha:id/email");
    Boolean FName_Empty, LName_Empty, Ph_Empty, Em_Empty,Ph_InValid,Em_Invalid;
    Boolean NextButton_EnabledValid;

    public void testSignUp() throws UiObjectNotFoundException{
        try
        {
        Tlogger.info("Sign Up Test <------> Initiate");
        //Check Mandatory Fields(First Name, Last Name, Phone Number and Email Address)

        SetSignUpInfo();

        Tlogger.info(System.getProperty("line.separator") + "Check Mandatory Fields");
        ValidationTest_FirstName();
        ValidationTest_LastName();
        ValidationTest_Phone();
        ValidationTest_Email();
        Tlogger.info(System.getProperty("line.separator") + "Check Mandatory Fields --> Done");
        //  Check Enabled and Disabled
        Validate_Next();
        }
        catch(Exception e)
        {
            Tlogger.severe(e.getMessage());
        }
    }

    private void SetSignUpInfo() throws UiObjectNotFoundException {
        Tlogger.info("Set Sign Up Info --> start");
        FirstName.setText(TringooUtils.firstName);
        LastName.setText(TringooUtils.lastName);
        Phone.setText(TringooUtils.phone);
        EmailId.setText(TringooUtils.email);
        Tlogger.info("Set Sign Up Info --> End");
    }

    private void Validate_Next() throws UiObjectNotFoundException {

        if (ValidationTest_NextEnabled()) {
            if (Next.clickAndWaitForNewWindow()) {
                if(Ph_InValid || Em_Invalid) {
                    if (Utils.getUiObjectByResourceId("android:id/alertTitle").exists()) {
                        Tlogger.info("Invalid Credentials" + Utils.getUiObjectByResourceId("android:id/alertTitle").getText());
                        UiObject OK = Utils.getUiObjectByText("OK");
                        UiObject Message = Utils.getUiObjectByResourceId("android:id/message");
                        if (OK.isEnabled() && OK.isClickable() && OK.clickAndWaitForNewWindow()) {
                            Tlogger.info("Exit SignUp Error notification -->  " + (!Message.getText().isEmpty() ? Message.getText() : ""));
                           testSignUp();
                        } else {
                            Tlogger.severe("FAILURE : Unable to go back to sign up screen to rectify info.");
                        }
                    }
                    else{
                        Tlogger.severe("FAILURE :Invalid Phone Number/Email Address getting accepted.");
                    }
                }
                else Validate_CallVerification();

            }else
            {
                Tlogger.severe("Failure:Unable to reach Call verification screen");
            }

        }else
        {
            Tlogger.severe("Failure:Next not enabled");
        }
    }

    private void Validate_CallVerification() throws UiObjectNotFoundException {
        UiObject LocalVerificationCall=Utils.getUiObjectByText("Make a local verification call");
        if (LocalVerificationCall.exists()) {
            if (Utils.getUiObjectByText("Make a local verification call").isEnabled())
            {
            Tlogger.info("Making local verification Call --> Trigger");
                if(Utils.getUiObjectByText("Make a local verification call").clickAndWaitForNewWindow(60000))
                {
                    Tlogger.info("Trying Call verification");
                    if (new UiObject(new UiSelector().className("android.widget.LinearLayout").childSelector(new UiSelector().text("Local"))).exists())
                        Tlogger.info("Success : Tring Launched");
                    else
                        Tlogger.severe("Failure:App could not launch");
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


    void ValidationTest_FirstName() throws  UiObjectNotFoundException
    {
        Tlogger.info("Validating First Name ---> Start");
        FName_Empty=false;

        if(FirstName.getText().isEmpty() || FirstName.getText().toLowerCase().contains("first name")) {
            FName_Empty=true;
            Tlogger.warning(System.getProperty("line.separator")+"First Name is Empty");
        }
        Tlogger.info("Validating First Name ---> End");
    }

    void ValidationTest_LastName() throws  UiObjectNotFoundException
    {
        Tlogger.info("Validating Last Name ---> Start");
        LName_Empty=false;
        if(LastName.getText().isEmpty() || LastName.getText().toLowerCase().contains("last name")) {
            LName_Empty=true;
            Tlogger.warning(System.getProperty("line.separator")+"Last Name is Empty");
        }
        Tlogger.info("Validating Last Name ---> End");
    }

    void ValidationTest_Phone() throws  UiObjectNotFoundException
    {
        Tlogger.info("Validating Phone Number ---> Start");
        PhoneNumberUtil Validate=PhoneNumberUtil.getInstance();
        String _PhoneNumber=Phone.getText();
        String Code=CountryCode.getChild(new UiSelector().className("android.widget.TextView")).getText();
        String CCode="";
        if (Code.contains("+")) {
            CCode = Code.replace("+", "");
            Tlogger.info("Validating 1");
        }
        try {

            if (_PhoneNumber.isEmpty() || _PhoneNumber.toLowerCase().contains("phone number")) {
                Tlogger.info("Validating 2");
                Ph_Empty = true;
                Tlogger.warning(System.getProperty("line.separator") + "Phone Number is Empty");
            } else {
                Tlogger.info("Validating 3");
                String RegionCode = Validate.getRegionCodeForCountryCode(Integer.parseInt(CCode));
                Tlogger.info("4");
                Tlogger.info(RegionCode);
                Tlogger.info(Code + _PhoneNumber);
                Phonenumber.PhoneNumber PH = Validate.parse(Code + _PhoneNumber, RegionCode);
                Tlogger.info("5");
                Ph_InValid = Validate.isValidNumber(PH);

                Tlogger.warning(System.getProperty("line.separator") + "Phone Number is Invalid");
            }
        }
        catch(Exception e)
        {
            Tlogger.severe(e.toString());
        }
        Tlogger.info("Validating Phone Number ---> End");
    }

    void ValidationTest_Email() throws UiObjectNotFoundException
    {
        Tlogger.info("Validating Email ---> Start");
        if (EmailId.getText().isEmpty() || EmailId.getText().toLowerCase().contains("email address")) {
            Em_Empty = true;
            Tlogger.warning(System.getProperty("line.separator") + "Email Address is Empty");
        }
        else {
          /* EmailValidator Validate = EmailValidator.getInstance();
            Em_Invalid = Validate.isValid(EmailId.getText());
            if (Em_Invalid)
            */
            if (!(EmailId.getText().contains("@") && EmailId.getText().contains(".") && !EmailId.getText().substring(EmailId.getText().indexOf("@"), EmailId.getText().indexOf(".")).isEmpty()
                    && !EmailId.getText().substring(0, EmailId.getText().indexOf("@")).isEmpty()
                    && !EmailId.getText().substring(EmailId.getText().indexOf("."), EmailId.getText().length() - 1).isEmpty())) {
                Em_Invalid=false;
                Tlogger.warning(System.getProperty("line.separator") + "Email Address is Invalid");
            }
        }
        Tlogger.info("Validating Email ---> End");
    }

    Boolean ValidationTest_NextEnabled() throws UiObjectNotFoundException {
        Tlogger.info("ValidationTest_NextEnabled ---> Start");
        if (FName_Empty || LName_Empty || Ph_Empty || Em_Empty) {
            if (Next.isEnabled()){
                Tlogger.severe("One of the fields is empty , next enabled");
            return false;
            }
            else
            return true;
        } else
        {
            if (!Next.isEnabled()) {
            Tlogger.severe("Next button is not enabled. It should have been.");
            return false;
        } else {
            Tlogger.info("ValidationTest_NextEnabled ---> End");
            return true;
        }

    }
    }




}
