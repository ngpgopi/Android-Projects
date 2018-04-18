package com.squad.gkn.assignment2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Gopi Krishnan on 2/25/2018.
 */

public class appDialog extends AppCompatDialogFragment {
    public dListener newListen;
    EditText SongName;
    EditText SongURL;
    EditText ArtistName;
    EditText ArtistURL;
    EditText AlbumURL;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dv = inflater.inflate(R.layout.dialog,null);

        builder.setView(dv);
        builder.setTitle("New Song Details:");


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sName = SongName.getText().toString();
                String sUrl = SongURL.getText().toString();
                String aName = ArtistName.getText().toString();
                String aURL = ArtistURL.getText().toString();
                String alUrl = AlbumURL.getText().toString();

                newListen.getTexts(sName,sUrl,aName,alUrl,alUrl);
            }
        });

        SongName = dv.findViewById(R.id.editText);
        SongURL = dv.findViewById(R.id.editText3);
        ArtistName = dv.findViewById(R.id.editText4);
        ArtistURL = dv.findViewById(R.id.editText5);
        AlbumURL = dv.findViewById(R.id.editText7);

        return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        newListen = (dListener) context;
    }

    public interface dListener{
        void getTexts(String songName, String songURL, String artistName, String artistURL, String albumURL);
    }

}
