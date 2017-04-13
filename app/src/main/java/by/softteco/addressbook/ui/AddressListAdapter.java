package by.softteco.addressbook.ui;

import java.lang.ref.WeakReference;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import by.softteco.addressbook.R;
import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 13.4.17.
 */

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressListHolder> {

    private final int LAYOUT = R.layout.item_address;

    private List<PlaceEntity> mPlaceEntities;

    private WeakReference<Context> mContext;

    public AddressListAdapter(List<PlaceEntity> placeEntities, Context context) {
        mPlaceEntities = placeEntities;
        mContext = new WeakReference<>(context);
    }

    @Override
    public AddressListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new AddressListHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressListHolder holder, int position) {
        PlaceEntity placeEntity = mPlaceEntities.get(position);
        holder.bindView(placeEntity);
    }

    @Override
    public int getItemCount() {
        return mPlaceEntities.size();
    }

    public class AddressListHolder extends RecyclerView.ViewHolder {

        LinearLayout mLinearLayout;
        TextView mName;

        public AddressListHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.address_ll);
            mName = (TextView) itemView.findViewById(R.id.name);
        }

        public void bindView(PlaceEntity placeEntity) {
            mName.setText(placeEntity.getName());
        }

    }
}
