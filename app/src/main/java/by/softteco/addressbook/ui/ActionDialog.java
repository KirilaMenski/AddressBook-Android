package by.softteco.addressbook.ui;

import java.lang.ref.WeakReference;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import by.softteco.addressbook.R;
import by.softteco.addressbook.database.dao.PlaceDao;
import by.softteco.addressbook.database.daoimpl.PlaceDaoImpl;
import by.softteco.addressbook.database.entity.PlaceEntity;

/**
 * Created by kirila on 13.4.17.
 */

public class ActionDialog extends DialogFragment {

    private final int LAYOUT = R.layout.dialog;
    private static final String EXTRA_NAME = "by.softteco.addressbook.ui.name";
    private static final String EXTRA_POSITION = "by.softteco.addressbook.ui.position";

    private Dialog mDialog;
    private PlaceDao mPlaceDao;
    private WeakReference<DialogListener> mDialogListener;

    private TextView mName, mCoordinates, mDelete, mCancel;

    public static ActionDialog newInstance(String name, int position) {
        ActionDialog dialog = new ActionDialog();
        Bundle args = new Bundle();
        args.putString(EXTRA_NAME, name);
        args.putInt(EXTRA_POSITION, position);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(LAYOUT, null);
        mDialog = new AlertDialog.Builder(getActivity()).setView(view).create();
        mPlaceDao = PlaceDaoImpl.getInstance();
        final PlaceEntity placeEntity = mPlaceDao.getPlaceByName(getArguments().getString(EXTRA_NAME));
        final int position = getArguments().getInt(EXTRA_POSITION);

        mName = (TextView) view.findViewById(R.id.name);
        mName.setText(placeEntity.getName());
        mCoordinates = (TextView) view.findViewById(R.id.coordinates);
        mCoordinates.setText(getString(R.string.coordinates, placeEntity.getLatitude(), placeEntity.getLongitude()));

        mDelete = (TextView) view.findViewById(R.id.delete);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogListener.get().delete(placeEntity.getName(), position);
                mDialog.dismiss();
            }
        });

        mCancel = (TextView) view.findViewById(R.id.cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.cancel();
            }
        });

        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mDialog.dismiss();
                }
                return true;
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }

    public void setListener(DialogListener listener) {
        mDialogListener = new WeakReference<>(listener);
    }

    public interface DialogListener {
        void delete(String name, int position);
    }

}
