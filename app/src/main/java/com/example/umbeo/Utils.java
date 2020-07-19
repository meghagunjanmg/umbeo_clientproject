package com.example.umbeo;

import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
    public static class CustomDialog {
        private ProgressDialog mDialog;
        public void showProgress(Context context, String message, String title) {
            // mDialog = new ProgressDialog(context);

            // mDialog.setMessage(message+"");
            // mDialog.setTitle(title);
            //mDialog.show();
            try {
                if (mDialog == null) { // if dialog_precropping_generic is not created then create
                    mDialog = new ProgressDialog(context);
                    mDialog.setMessage(message+"");
                    mDialog.setTitle(title);
                }
                // check if progress dialog_precropping_generic is showing then no need show show again
                if (! mDialog.isShowing()) mDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
                mDialog = null;
            }
        }
        public void hideProgress() {
            //if (mDialog != null) {
            //    mDialog.dismiss();
            //    mDialog = null;

            try {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                    mDialog = null;

                }
            } catch (Exception e) {
                e.printStackTrace();
                mDialog = null;
            }
        }
    }

}
