package com.example.umbeo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

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



    public static String BitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap Base64ToBitmap(String encodedImage)
    {
        byte[] imageAsBytes = Base64.decode(encodedImage.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
