package zerjioang.onthestreet.model.holder.contact;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import zerjioang.onthestreet.R;
import zerjioang.onthestreet.model.pojox.Contact;

/**
 * Created by .local on 21/03/2017.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactHolder> {

    private final List<Contact> list;
    private final View.OnClickListener listener;

    public ContactListAdapter(List<Contact> list, View.OnClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_contact, parent, false);
        return new ContactHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
