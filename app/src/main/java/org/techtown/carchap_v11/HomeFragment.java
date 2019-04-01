package org.techtown.carchap_v11;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private HomeFragmentListener listener;


    public EditText editText;
    public EditText editText2;

    private Button button;
    private ImageButton imagebutton;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_home, container, false);
        final ConstraintLayout constraintLayout=(ConstraintLayout) view.findViewById(R.id.home_findbar);


        //출발지, 도착지 설정, 앱 상단바
        editText = view.findViewById(R.id.editText);
        editText2 = view.findViewById(R.id.editText2);

        //X버튼, 출발지<>도착지 교환 버튼
        Button button =(Button)view.findViewById(R.id.button2);
        Button button2 =(Button)view.findViewById(R.id.button3);
        ImageButton imagebutton=(ImageButton)view.findViewById(R.id.imageButton4);


        constraintLayout.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                constraintLayout.setVisibility(View.INVISIBLE);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CharSequence input = editText.getText();
                CharSequence input2 =editText2.getText();
                editText.setText(input2);
                editText2.setText(input);

            }
        });

        imagebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                CharSequence find_path;
                MainActivity.kakaogetRESTApi kakao= new MainActivity.kakaogetRESTApi();
                kakao.execute(editText.getText().toString(), "37.0789561558879","7a1980c4a68692e396509a54b3c3223c");
                MainActivity.kakaogetRESTApi kakao2= new MainActivity.kakaogetRESTApi();
                kakao2.execute(editText2.getText().toString(), "37.0789561558879","7a1980c4a68692e396509a54b3c3223c");

                if(editText.getText().toString().length()>0){
                    CharSequence input=editText.getText().toString();
                    listener.onInputHome_start_Sent(input);
                }
                if(editText2.getText().toString().length()>0){
                    CharSequence input=editText2.getText().toString();
                    listener.onInputHome_finish_Sent(input);
                }

                if(editText.getText().toString().length()>0&&editText2.getText().toString().length()>0){
                    CharSequence input="start";
                    listener.onInputHome_polyline_Sent(input);
                }



            }
        });

        return view;

    }

    public interface HomeFragmentListener{
        void onInputHome_start_Sent(CharSequence startinput);
        void onInputHome_finish_Sent(CharSequence finishinput);
        void onInputHome_polyline_Sent(CharSequence polylineinput);



    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof HomeFragmentListener) {
            listener = (HomeFragmentListener) context;
        }else{
            // throw new RuntimeException(context.toString()+"must implement ReserFragmentListener");

        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
