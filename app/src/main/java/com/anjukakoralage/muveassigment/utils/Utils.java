package com.anjukakoralage.muveassigment.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Anjuka Koralage on 08,December,2019
 */
public class Utils {

    public static void showSnackbar(View view, String msg, int duration) {

        final View rootView = view;
        final Snackbar snackbar = Snackbar.make(rootView, msg, duration);
        snackbar.show();
    }

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE
        );
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
