package com.example.administrator.trucklog;



        import android.content.Context;

        import com.kaopiz.kprogresshud.KProgressHUD;


/**
 * Created by Rock on 2016.11.09.
 */

public class ShowProgressDialog {
    public static KProgressHUD progressHUD;
    public static void showProgressDialog(Context context, String title){
        progressHUD = KProgressHUD.create(context)
                .setLabel(title)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();
    }
    public static void hideProgressDialog(){
        if(progressHUD == null) return;
        if(progressHUD.isShowing())
            progressHUD.dismiss();
    }
}
