package zerjioang.onthestreet.controller;

import android.app.Activity;

/**
 * Created by .local on 19/04/2017.
 */

public abstract class AbstractBaseController {

    private Activity activity;

    public abstract void showSettings();
    public abstract void showAbout();

    public AbstractBaseController(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
