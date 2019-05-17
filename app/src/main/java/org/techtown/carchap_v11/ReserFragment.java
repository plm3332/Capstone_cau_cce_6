package org.techtown.carchap_v11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import org.techtown.carchap_v11.dummy.DummyContent;

import static org.techtown.carchap_v11.R.drawable.imagebutton_carchap_rentcar_grey;


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
    private FrameLayout reser_carpool;
    private FrameLayout reser_bikesharing;
    private FrameLayout reser_carsharing;
    private FrameLayout reser_deliveryservice;
    private FrameLayout reser_kickboard;
    private FrameLayout reser_rentcar;

    private BottomSheetBehavior mBottomSheetBehavior;
    View mbottomSheet;





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

        mbottomSheet=(View)view.findViewById(R.id.bottom_sheet);
        mBottomSheetBehavior=BottomSheetBehavior.from(mbottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        /*중간 스크롤뷰 이미지버튼 선언*/
        final ImageButton imageButton_reser_bikesharing=(ImageButton) view.findViewById(R.id.imagebutton_reser_bikesharing);
        final ImageButton imageButton_reser_carchapaqua=(ImageButton) view.findViewById(R.id.imageButton_reserp_aqua1);
        final ImageButton imageButton_reser_carpool=(ImageButton) view.findViewById(R.id.imageButton_reser_carpool);
        final ImageButton imageButton_reser_carsharing=(ImageButton) view.findViewById(R.id.imageButton_reser_carsharing);
        final ImageButton imageButton_reser_deliveryservice=(ImageButton) view.findViewById(R.id.imageButton_reser_deliveryservice);
        final ImageButton imageButton_reser_kickboard=(ImageButton) view.findViewById(R.id.imageButton_reser_kickboard);
        final ImageButton imageButton_reser_rentcar=(ImageButton) view.findViewById(R.id.imageButton_reser_rentcar);

        /*올인원 카찹 프레그먼트 로드시 초기 설정*/
        imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing);
        Fragment fragment = new ReserFra_carsharing();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
        fragmentTransaction.commit();

        TabLayout tabLayout =(TabLayout) view.findViewById(R.id.reser_tab);

        /*하위 프레그멘트 선언*/
        reser_bottom_frame = (FrameLayout) view.findViewById(R.id.reser_scrollview_frag);
        reser_carchap=(FrameLayout) view.findViewById(R.id.fragment_reser_carchap);
        reser_bikesharing=(FrameLayout) view.findViewById(R.id.fragment_reser_bikesharing);
        reser_carpool=(FrameLayout) view.findViewById(R.id.fragment_reser_carpool);
        reser_carsharing=(FrameLayout) view.findViewById(R.id.fragment_reser_carsharing);
        reser_deliveryservice=(FrameLayout) view.findViewById(R.id.fragment_reser_deliveryservice);
        reser_kickboard=(FrameLayout) view.findViewById(R.id.fragment_reser_kickboard);
        reser_rentcar=(FrameLayout) view.findViewById(R.id.fragment_reser_rentcar);




        /*예약 프레그먼트 중간 스크롤뷰 아이콘 클릭 이벤트*/
        imageButton_reser_bikesharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_bikesharing();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_carchapaqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_aqua();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_carpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_carpool();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_carsharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_carsharing();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_deliveryservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_deliveryservice();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_kickboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_kickboard();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard);

                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            private FragmentManager getSupportFragmentManager() {
                return null;
            }
        });
        imageButton_reser_rentcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ReserFra_rentcar();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.reser_scrollview_frag,fragment);
                imageButton_reser_rentcar.setImageResource(R.drawable.imagebutton_carchap_rentcar);


                imageButton_reser_kickboard.setImageResource(R.drawable.imagebutton_carchar_kickboard_grey);
                imageButton_reser_deliveryservice.setImageResource(R.drawable.imagebutton_reser_deliveryservice_grey);
                imageButton_reser_carsharing.setImageResource(R.drawable.imagebutton_carchap_carsharing_grey);
                imageButton_reser_carpool.setImageResource(R.drawable.imagebutton_carchap_carpool_grey);
                imageButton_reser_carchapaqua.setImageResource(R.drawable.imagebutton_carchap_aqua_grey);
                imageButton_reser_bikesharing.setImageResource(R.drawable.imagebutton_carchap_bikesharing_grey);

                fragmentTransaction.addToBackStack(null);
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




