package zerjioang.onthestreet.controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.ui.activity.settings.SettingsActivity;

/**
 * Created by .local on 19/04/2017.
 */

public abstract class AbstractBaseController {

    private Activity activity;

    public AbstractBaseController(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getRootView() {
        return getActivity().findViewById(android.R.id.content);
    }


    public void showSettings() {
        Intent t = new Intent(getActivity(), SettingsActivity.class);
        getActivity().startActivity(t);
    }

    public void showAbout() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.diaog_about_body)
                .setTitle(R.string.dialog_about_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void search(final AbstractCode runAfter) {
        //https://stackoverflow.com/questions/10903754/input-text-dialog-android
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title_search_plac);

        // Set up the input
        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        //add padding programatically. cool ui
        // https://stackoverflow.com/questions/9685658/add-padding-on-view-programmatically
        float density = getActivity().getResources().getDisplayMetrics().density;
        int paddingPixel = 10;
        int paddingDp = (int)(paddingPixel * density);
        input.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString();
                runAfter.execute(name);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
