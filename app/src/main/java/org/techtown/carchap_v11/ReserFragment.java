package org.techtown.carchap_v11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.carchap_v11.dummy.DummyContent;

import java.util.ArrayList;

import static org.techtown.carchap_v11.R.id.main_frame;
import static org.techtown.carchap_v11.R.id.reser_scrollview;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReserFragment extends Fragment implements AdapterView.OnItemSelectedListener, ItemFragment.OnListFragmentInteractionListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ReserFragmentListener listener;
    private FrameLayout reser_bottom_frame;
    private FrameLayout reser_carchap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReserFragment newInstance(String param1, String param2) {

        ReserFragment fragment = new ReserFragment();
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


    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        //Toast.makeText(this,item.toString(),Toast.LENGTH_SHORT)

    }

    public interface ReserFragmentListener{
        void onInputReserF_start_Sent(CharSequence startinput);
        void onInputReserF_finish_Sent(CharSequence finishinput);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_reser, container, false);
        ViewPager viewPager =(ViewPager) view.findViewById(R.id.pager);
        final ScrollView scrollView=(ScrollView) view.findViewById(R.id.reser_scrollview);
        ImageButton imageButton_reser_carpool=(ImageButton) view.findViewById(R.id.imageButton_reser_carpool);
        TabLayout tabLayout =(TabLayout) view.findViewById(R.id.reser_tab);
        reser_bottom_frame = (FrameLayout) view.findViewById(R.id.reser_scrollview_frag);
        reser_carchap=(FrameLayout) view.findViewById(R.id.reser_11123);

        MyPagerAdapter adapter3 = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter3);

        viewPager.refreshDrawableState();
        tabLayout.setupWithViewPager(viewPager);
        imageButton_reser_carpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_carchap();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)


        Log.d("parent point",parent.getAdapter().toString());
        Log.d("spinner point", String.valueOf(id));
        String text= parent.getItemAtPosition(pos).toString();
        String temptext=parent.getItemAtPosition(0).toString();
        if(temptext.equals("출발지")){
            CharSequence input=parent.getItemAtPosition(pos).toString();
            listener.onInputReserF_start_Sent(input);
        }
        else{
            CharSequence input=parent.getItemAtPosition(pos).toString();
            listener.onInputReserF_finish_Sent(input);
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        if(context instanceof ReserFragmentListener) {
            listener = (ReserFragmentListener) context;
        }else{
            //throw new RuntimeException(context.toString()+"must implement ReserFragmentListener");

        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        listener = null;
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




