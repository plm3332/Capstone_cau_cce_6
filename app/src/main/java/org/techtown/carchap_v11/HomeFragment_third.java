package org.techtown.carchap_v11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static org.techtown.carchap_v11.HomeFragment_second.place_name_3;
import static org.techtown.carchap_v11.R.id.main_frame;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment_third.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class HomeFragment_third extends Fragment {

    private OnFragmentInteractionListener mListener;

    public HomeFragment_third() {
        // Required empty public constructor
    }

    static String startname;
    static String finishname;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View)inflater.inflate(R.layout.fragment_home_third, container, false);
        final TextView place_name_text = (TextView) view.findViewById(R.id.place_name_text);
        place_name_text.setText(place_name_3);
        Button makestart=(Button)view.findViewById(R.id.makestart);
        makestart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentTransaction replace = fragmentTransaction.replace(main_frame, new HomeFragment());
                startname=place_name_3;
                replace.commit();

            }
        });

        Button makefinish=(Button)view.findViewById(R.id.makefinish);
        makefinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                FragmentTransaction replace = fragmentTransaction.replace(main_frame, new HomeFragment());
                finishname=place_name_3;
                replace.commit();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
