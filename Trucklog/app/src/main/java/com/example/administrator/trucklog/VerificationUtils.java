package com.example.administrator.trucklog;



        import android.util.Patterns;
        import android.widget.EditText;

        import java.util.regex.Pattern;

/**
 * Created by rock on 1/30/17.
 */

public class VerificationUtils {
    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public static boolean isEmptyText(EditText field){
        if(field.getText().toString().isEmpty()){
            field.setError("fill out");
            return true;
        }
        return false;
    }
}
