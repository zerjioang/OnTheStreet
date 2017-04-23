package zerjioang.onthestreet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.NewPlaceController;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;

public class NewPlaceActivity extends AbstractBaseActivity {

    private EditText txtPlaceName;
    private EditText txtPlaceDescription;
    private EditText txtPlaceLocation;
    private Button buttonCancelNewPlace;
    private Button buttonSaveNewPlace;
    private Button btnAddContactNewPlace;

    private Place p;

    private boolean editMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        editMode = false;
        txtPlaceName = (EditText) findViewById(R.id.txtPlaceName);
        txtPlaceDescription = (EditText) findViewById(R.id.txtPlaceDescription);
        txtPlaceLocation = (EditText) findViewById(R.id.txtPlaceLocation);
        buttonCancelNewPlace = (Button) findViewById(R.id.buttonCancelNewPlace);
        buttonSaveNewPlace = (Button) findViewById(R.id.buttonSaveNewPlace);
        btnAddContactNewPlace = (Button) findViewById(R.id.btnAddContactNewPlace);

        //create controller
        controller = new NewPlaceController(this);

        p = new Place();
        editMode = getFromExtras("editmode", false);

        if(editMode){
            //get place to be edited
            p = DataManager.getInstance().getLastViewedPlace();
            //update ui with values;
            txtPlaceName.setText(p.getName());
            txtPlaceLocation.setText(p.getLocation());
            txtPlaceDescription.setText(p.getDescription());
        }

        //add listeners
        buttonCancelNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getThisController().cancel();
            }
        });

        buttonSaveNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p.setName(txtPlaceName.getText().toString());
                p.setDescription(txtPlaceDescription.getText().toString());
                p.setLocation(txtPlaceLocation.getText().toString());
                if(getThisController().isValidPlace(p)){
                    if(editMode){
                        int position = DataManager.getInstance().getLastViewedPlacePosition();
                        getThisController().updateAndSave(getActivity(), position, p);
                    }
                    else{
                        getThisController().save(p);
                    }
                }
            }
        });

        btnAddContactNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getThisController().addContact(p);
            }
        });
    }

    @Override
    public boolean showSearchIcon() {
        return false;
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        getThisController().onActivityResult(reqCode, resultCode, data);
    }

    private NewPlaceController getThisController() {
        return (NewPlaceController)controller;
    }
}
