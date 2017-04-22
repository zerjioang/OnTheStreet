package zerjioang.onthestreet.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.controller.NewPlaceController;
import zerjioang.onthestreet.model.pojox.Place;

public class NewPlaceActivity extends AbstractBaseActivity {

    private EditText txtPlaceName;
    private EditText txtPlaceDescription;
    private EditText txtPlaceLocation;
    private Button buttonCancelNewPlace;
    private Button buttonSaveNewPlace;
    private Button btnAddContactNewPlace;

    private NewPlaceController controller;

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

        //add listeners
        buttonCancelNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.cancel();
            }
        });

        buttonSaveNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = new Place(
                        txtPlaceName.getText().toString(),
                        txtPlaceLocation.getText().toString(),
                        txtPlaceDescription.getText().toString()
                );
                if(controller.isValidPlace(p)){
                    controller.save(p);
                }
            }
        });

        btnAddContactNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.addContact(p);
            }
        });
    }

    //code
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        controller.onActivityResult(reqCode, resultCode, data);
    }
}
