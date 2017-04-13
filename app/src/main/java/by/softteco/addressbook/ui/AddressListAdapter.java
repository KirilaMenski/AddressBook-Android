package by.softteco.addressbook.ui;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
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

    private WeakReference<Activity> mActivity;
    private WeakReference<AdapterListener> mListener;

    public AddressListAdapter(List<PlaceEntity> placeEntities, Activity activity, AdapterListener listener) {
        mPlaceEntities = placeEntities;
        mActivity = new WeakReference<>(activity);
        mListener = new WeakReference<>(listener);
    }

    @Override
    public AddressListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(LAYOUT, parent, false);
        return new AddressListHolder(view);
    }

    @Override
    public void onBindViewHolder(AddressListHolder holder, final int position) {
        final PlaceEntity placeEntity = mPlaceEntities.get(position);
        holder.bindView(placeEntity);
        holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.get().itemSelected(placeEntity.getName(), position);
            }
        });
    }

    public void removeAt(int position) {
        mPlaceEntities.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mPlaceEntities.size());
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

    public interface AdapterListener {
        void itemSelected(String name, int position);
    }

}
