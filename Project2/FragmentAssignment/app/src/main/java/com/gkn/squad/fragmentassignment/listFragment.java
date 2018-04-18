package com.gkn.squad.fragmentassignment;

import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class listFragment extends ListFragment {

    private onListItemSelectInterface mListener;
    private String[] listArray;

    public listFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);


        Bundle b1 = getArguments();
        listArray = b1.getStringArray("name");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onListItemSelectInterface) {
            mListener = (onListItemSelectInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(
                getActivity(),R.layout.listitem,listArray
        ));

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        getListView().setItemChecked(position,true);
        mListener.onListItemSelect(position);
    }

    public interface onListItemSelectInterface {
        void onListItemSelect(int index);
    }
}
