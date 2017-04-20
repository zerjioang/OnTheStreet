package zerjioang.onthestreet.ui.activity;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.data.DataManager;
import zerjioang.onthestreet.model.pojox.Place;

public class NewPlaceActivity extends AbstractBaseActivity {

    private EditText txtPlaceName;
    private EditText txtPlaceDescription;
    private EditText txtPlaceLocation;
    private Button buttonCancelNewPlace;
    private Button buttonSaveNewPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);
        ActionBar bar = getActionBar();
        if(bar!=null){
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
            bar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));
        }

        txtPlaceName = (EditText) findViewById(R.id.txtPlaceName);
        txtPlaceDescription = (EditText) findViewById(R.id.txtPlaceDescription);
        txtPlaceLocation = (EditText) findViewById(R.id.txtPlaceLocation);
        buttonCancelNewPlace = (Button) findViewById(R.id.buttonCancelNewPlace);
        buttonSaveNewPlace = (Button) findViewById(R.id.buttonSaveNewPlace);

        //add listeners
        buttonCancelNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        buttonSaveNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewPlace();
                getActivity().finish();
            }
        });
    }

    private void saveNewPlace() {
        Place p = new Place(
                txtPlaceName.getText().toString(),
                txtPlaceDescription.getText().toString(),
                txtPlaceLocation.getText().toString()
        );
        DataManager.getInstance().addPlace(this, p);
    }
}
