package org.techtown.carchap_v11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.techtown.carchap_v11.HomeFragment_third.finishname;
import static org.techtown.carchap_v11.HomeFragment_third.startname;
import static org.techtown.carchap_v11.MainActivity.current_point;
import static org.techtown.carchap_v11.MainActivity.dda_dongjak11_point;
//import static org.techtown.carchap_v11.MainActivity.finish_point;
import static org.techtown.carchap_v11.MainActivity.finish_x;
import static org.techtown.carchap_v11.MainActivity.finish_y;
import static org.techtown.carchap_v11.MainActivity.mapView;
import static org.techtown.carchap_v11.MainActivity.seven_line;
//import static org.techtown.carchap_v11.MainActivity.start_point;
import static org.techtown.carchap_v11.MainActivity.start_x;
import static org.techtown.carchap_v11.MainActivity.start_y;
import static org.techtown.carchap_v11.MainActivity.subway_naebang_point;


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

    public static final MapPoint start_point = MapPoint.mapPointWithGeoCoord(start_x, start_y);
    public static final MapPoint finish_point = MapPoint.mapPointWithGeoCoord(finish_x, finish_y);

    public static EditText editText;
    public static EditText editText2;

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

        String current_location="";
        //Log.d("HomeFragment_current_location_test3:", String.valueOf(bundle));
        Log.v("HomeFragment_current_location_test3","Param1 : "+mParam1 + " Param2 :"+mParam2);
        editText2.setText(mParam2);

        try{
            if(startname.length()>0)
                editText.setText(startname);
            if(finishname.length()>0)
                editText2.setText(finishname);
        }catch (NullPointerException e)
        {

        }



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

                CarchapFragment.location_temp = 1;
                mapView.removeAllPOIItems();
                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                MainActivity.createMarker(mapView,"현위치", current_point,1,"current_location");
                MainActivity.createMarker(mapView,"출발지", start_point,1,"current_location");
                MainActivity.createMarker(mapView,"도착지", finish_point,1,"current_location");
                MainActivity.createMarker(mapView,"상도역",MainActivity.subway_sangdo_point,1,"subway");
                MainActivity.createMarker(mapView,"숭실대입구역",MainActivity.subway_soongsil_point,1,"subway");
                MainActivity.createMarker(mapView,"남성역",MainActivity.subway_namseong_point,1,"subway");
                MainActivity.createMarker(mapView,"이수역",MainActivity.subway_isu_point,1,"subway");
                MainActivity.createMarker(mapView,"내방역", subway_naebang_point,1,"subway");
                MainActivity.createMarker(mapView,"고속터미널역",MainActivity.subway_gosok_point,1,"subway");
                MainActivity.createMarker(mapView,"반포역",MainActivity.subway_banpo_point,1,"subway");
                MainActivity.createMarker(mapView,"논현역",MainActivity.subway_nonhyeon_point,1,"subway");
                MainActivity.createMarker(mapView,"학동역",MainActivity.subway_hakdong_point,1,"subway");
                MainActivity.createMarker(mapView,"강남구청역",MainActivity.subway_gangnamgucheong_point,1,"subway");
                MainActivity.createMarker(mapView,"청담역",MainActivity.subway_cheongdam_point,1,"subway");
                MainActivity.createMarker(mapView,"뚝섬유원지역",MainActivity.subway_ddukseom_point,1,"subway");
                MainActivity.createMarker(mapView,"건대입구역",MainActivity.subway_gundae_point,1,"subway");
                MainActivity.createMarker(mapView,"어린이대공원역",MainActivity.subway_childrenpark_point,1,"subway");
                MainActivity.createMarker(mapView,"군자역",MainActivity.subway_gunja_point,1,"subway");
                MainActivity.createMarker(mapView,"중곡역",MainActivity.subway_junggok_point,1,"subway");
                MainActivity.createMarker(mapView,"용마산역",MainActivity.subway_yongmasan_point,1,"subway");
                MainActivity.createMarker(mapView,"사가정역",MainActivity.subway_sagajeong_point,1,"subway");
                MainActivity.createMarker(mapView,"면목역",MainActivity.subway_meonmok_point,1,"subway");
                MainActivity.createMarker(mapView,"상봉역",MainActivity.subway_sangbong_point,1,"subway");
                MainActivity.createMarker(mapView,"중화역",MainActivity.subway_junghwa_point,1,"subway");
                MainActivity.createMarker(mapView,"먹골역",MainActivity.subway_meokgol_point,1,"subway");

                MapPoint.GeoCoordinate current_coordinate =MainActivity.current_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate start_coordinate = start_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate finish_coordinate = finish_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_jangseung_coordinate = MainActivity.subway_jangseung_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_sangdo_coordinate = MainActivity.subway_sangdo_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_soongsil_coordinate = MainActivity.subway_soongsil_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_namseong_coordinate = MainActivity.subway_namseong_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_isu_coordinate = MainActivity.subway_isu_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_naebang_coordinate = MainActivity.subway_naebang_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_gosok_coordinate = MainActivity.subway_gosok_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_banpo_coordinate = MainActivity.subway_banpo_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_nonhyeon_coordinate = MainActivity.subway_nonhyeon_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_hakdong_coordinate = MainActivity.subway_hakdong_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_gangnamgucheong_coordinate = MainActivity.subway_gangnamgucheong_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_cheongdam_coordinate = MainActivity.subway_cheongdam_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_ddukseom_coordinate = MainActivity.subway_ddukseom_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_gundae_coordinate = MainActivity.subway_gundae_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_childrenpark_coordinate = MainActivity.subway_childrenpark_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_gunja_coordinate = MainActivity.subway_gunja_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_junggok_coordinate = MainActivity.subway_junggok_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_yongmasan_coordinate = MainActivity.subway_yongmasan_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_sagajeong_coordinate = MainActivity.subway_sagajeong_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_meonmok_coordinate = MainActivity.subway_meonmok_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_sangbong_coordinate = MainActivity.subway_sangbong_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_junghwa_coordinate = MainActivity.subway_junghwa_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate subway_meokgol_coordinate = MainActivity.subway_meokgol_point.getMapPointGeoCoord();
                ArrayList<MapPoint.GeoCoordinate> subwaylist=new ArrayList<MapPoint.GeoCoordinate>();
                subwaylist.add(subway_jangseung_coordinate);
                subwaylist.add(subway_sangdo_coordinate);
                subwaylist.add(subway_soongsil_coordinate);
                subwaylist.add(subway_namseong_coordinate);
                subwaylist.add(subway_isu_coordinate);
                subwaylist.add(subway_naebang_coordinate);
                subwaylist.add(subway_gosok_coordinate);
                subwaylist.add(subway_banpo_coordinate);
                subwaylist.add(subway_nonhyeon_coordinate);
                subwaylist.add(subway_hakdong_coordinate);
                subwaylist.add(subway_gangnamgucheong_coordinate);
                subwaylist.add(subway_cheongdam_coordinate);
                subwaylist.add(subway_ddukseom_coordinate);
                subwaylist.add(subway_gundae_coordinate);
                subwaylist.add(subway_childrenpark_coordinate);
                subwaylist.add(subway_gunja_coordinate);
                subwaylist.add(subway_junggok_coordinate);
                subwaylist.add(subway_yongmasan_coordinate);
                subwaylist.add(subway_sagajeong_coordinate);
                subwaylist.add(subway_meonmok_coordinate);
                subwaylist.add(subway_sangbong_coordinate);
                subwaylist.add(subway_junghwa_coordinate);
                subwaylist.add(subway_meokgol_coordinate);

                double min=100000.0;double minx=0.0;double miny=0.0;
                for(int i=0;i<subwaylist.size();i++){
                    if(MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude)<min) {
                        min=MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude);
                        minx=subwaylist.get(i).latitude;miny=subwaylist.get(i).longitude;
                    }
                }
                MainActivity.showpolyline(start_point,MapPoint.mapPointWithGeoCoord(minx,miny));


                int start_i = 0;
                int finish_i = 0;

                for(int i = 0; i < seven_line.length - 1; i++) {
                    MapPoint.GeoCoordinate temp = seven_line[i].getMapPointGeoCoord();
                    if (minx == temp.latitude && miny == temp.longitude) {
                        start_i = i;
                    }
                }


                min=100000.0; minx=0.0; miny=0.0;
                for(int i=0;i<subwaylist.size();i++){
                    if(MainActivity.distance(finish_coordinate.latitude,finish_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude)<min) {
                        min=MainActivity.distance(finish_coordinate.latitude,finish_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude);
                        minx=subwaylist.get(i).latitude;miny=subwaylist.get(i).longitude;
                    }
                }
                MainActivity.showpolyline(finish_point,MapPoint.mapPointWithGeoCoord(minx,miny));


                for(int i = 0; i < seven_line.length - 1; i++) {
                    MapPoint.GeoCoordinate temp = seven_line[i].getMapPointGeoCoord();
                    if (minx == temp.latitude && miny == temp.longitude) {
                        finish_i = i;
                    }
                }

                if(finish_i > start_i) {
                    for (int i = start_i; i < finish_i; i++) {
                        MapPolyline seven_route = new MapPolyline();
                        seven_route.setLineColor(Color.argb(255, 83, 144, 245));
                        seven_route.addPoint(seven_line[i]);
                        seven_route.addPoint(seven_line[i + 1]);
                        mapView.addPolyline(seven_route);
                    }
                }
                else {
                    for (int i = start_i; i > finish_i; i--) {
                        MapPolyline seven_route = new MapPolyline();
                        seven_route.setLineColor(Color.argb(255, 83, 144, 245));
                        seven_route.addPoint(seven_line[i]);
                        seven_route.addPoint(seven_line[i - 1]);
                        mapView.addPolyline(seven_route);
                    }
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

    public static void setStart(String text){
        CharSequence temp=text;
        editText.setText(temp);
    }

    public static void setfinish(String text){
        CharSequence temp=text;
        editText2.setText(temp);
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
