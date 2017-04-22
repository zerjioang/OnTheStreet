package zerjioang.onthestreet.model.holder.contact;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.pojox.Contact;

/**
 * Created by .local on 21/03/2017.
 */

public class ContactHolder extends RecyclerView.ViewHolder{

    private TextView itemContactNameTextView;

    public ContactHolder(View itemView) {
        super(itemView);
        itemContactNameTextView = (TextView)itemView.findViewById(R.id.itemContactNameTextView);
    }

    public void bind(final Contact contact, View.OnClickListener listener) {
        this.itemContactNameTextView.setText(contact.getName());
        if(listener!=null){
            itemView.setOnClickListener(listener);
        }
    }
}
