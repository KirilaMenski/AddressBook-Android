package by.softteco.addressbook.ui.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import by.softteco.addressbook.R;
import by.softteco.addressbook.database.dao.PlaceDao;
import by.softteco.addressbook.database.daoimpl.PlaceDaoImpl;
import by.softteco.addressbook.database.entity.PlaceEntity;
import by.softteco.addressbook.ui.adapters.AddressListAdapter;
import by.softteco.addressbook.ui.dialogs.ActionDialog;

/**
 * Created by kirila on 13.4.17.
 */

public class AddressListFragment extends BaseFragment implements ActionDialog.DialogListener, AddressListAdapter.AdapterListener {

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
        mAddressListAdapter = new AddressListAdapter(placeEntities, getActivity(), this);
        mAddressRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mAddressRecycler.setAdapter(mAddressListAdapter);
    }

    @Override
    public void delete(String name, int position) {
        PlaceEntity placeEntity = mPlaceDao.getPlaceByName(name);
        mPlaceDao.deletePlace(placeEntity);
        mAddressListAdapter.removeAt(position);
    }

    @Override
    public void itemSelected(String name, int position) {
        ActionDialog dialog = ActionDialog.newInstance(name, position);
        dialog.setListener(this);
        dialog.show(getActivity().getSupportFragmentManager(), "dialog");
    }
}
