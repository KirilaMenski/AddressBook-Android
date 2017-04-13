package by.softteco.addressbook.ui;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import by.softteco.addressbook.R;
import by.softteco.addressbook.database.dao.PlaceDao;
import by.softteco.addressbook.database.daoimpl.PlaceDaoImpl;
import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 13.4.17.
 */

public class AddressListFragment extends Fragment {

    private final int LAYOUT = R.layout.fragment_address_list;

    private PlaceDao mPlaceDao;
    private AddressListAdapter mAddressListAdapter;

    private RecyclerView mAddressRecycler;

    public static AddressListFragment newInstance() {
        AddressListFragment fragment = new AddressListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(LAYOUT, container, false);
        mPlaceDao = PlaceDaoImpl.getInstance();
        mAddressRecycler = (RecyclerView) view.findViewById(R.id.address_recycler);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateRecycler(mPlaceDao.getAllPlaces());
    }

    private void updateRecycler(List<PlaceEntity> placeEntities) {
        mAddressListAdapter = new AddressListAdapter(placeEntities, getContext());
        mAddressRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAddressRecycler.setAdapter(mAddressListAdapter);
    }

}
