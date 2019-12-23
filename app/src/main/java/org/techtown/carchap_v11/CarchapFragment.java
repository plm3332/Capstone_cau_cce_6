package org.techtown.carchap_v11;


import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import static org.techtown.carchap_v11.MainActivity.Heukseok_point;
import static org.techtown.carchap_v11.MainActivity.current_point;
import static org.techtown.carchap_v11.MainActivity.dda_dongjak11_point;
import static org.techtown.carchap_v11.MainActivity.dda_dongjak15_point;
import static org.techtown.carchap_v11.MainActivity.finish_x;
import static org.techtown.carchap_v11.MainActivity.finish_y;
import static org.techtown.carchap_v11.MainActivity.mapView;
import static org.techtown.carchap_v11.MainActivity.seven_line;
import static org.techtown.carchap_v11.MainActivity.start_x;
import static org.techtown.carchap_v11.MainActivity.start_y;
import static org.techtown.carchap_v11.MainActivity.subway_jangseung_point;
import static org.techtown.carchap_v11.MainActivity.subway_naebang_point;


/**
 * A simple {@link Fragment} subclass.
 */
public class CarchapFragment extends Fragment {

    ImageButton imageButton;
    private BottomSheetBehavior mBottomSheetBehavior;
    View mbottomSheet;

    public CarchapFragment() {
        // Required empty public constructor
    }

    public static int location_temp=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_carchap, container, false);
        final ConstraintLayout constraintLayout=(ConstraintLayout) view.findViewById(R.id.Main_underbar);

        mbottomSheet=(View)view.findViewById(R.id.bottom_sheet2);
        mBottomSheetBehavior=BottomSheetBehavior.from(mbottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);


        ImageButton carchap_aqua =(ImageButton)view.findViewById(R.id.imageButton_carchap_aqua);
        ImageButton carchap_bikesharing =(ImageButton)view.findViewById(R.id.imagebutton_carchap_bikesharing);
        ImageButton carchap_kickboard =(ImageButton)view.findViewById(R.id.imageButton_carchar_kickboard);
        ImageButton carchap_rentcar =(ImageButton)view.findViewById(R.id.imageButton_carchap_rentcar);
        ImageButton carchap_carpool =(ImageButton)view.findViewById(R.id.imageButton_carchap_carpool);
        ImageButton carchap_carsharing =(ImageButton)view.findViewById(R.id.imageButton_carchap_carsharing);
        carchap_aqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();

                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";

                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));
                MainActivity.createMarker(mapView,"흑석역",Heukseok_point,1,"aqua");
            }

        });
        carchap_bikesharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();
                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));
                MainActivity.createMarker(mapView,"현위치", current_point,1,"current_location");
                MainActivity.createMarker(mapView,"흑석역 1번 출구", MainActivity.dda_dongjak_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"흑석역 4번 출구", MainActivity.dda_dongjak2_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"한강현대아파트 건너편", MainActivity.dda_dongjak3_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"비계 버스정류소", MainActivity.dda_dongjak4_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"흑석한강푸르지오", MainActivity.dda_dongjak5_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"중앙대학교 정문", MainActivity.dda_dongjak6_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"상도역 1번 출구", MainActivity.dda_dongjak7_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"노들역 1번 출구", MainActivity.dda_dongjak8_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"상도아이파크아파트", MainActivity.dda_dongjak9_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"장승배기역 2번 출구", MainActivity.dda_dongjak10_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"장승배기역 5번 출구", dda_dongjak11_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"삼익아파트", MainActivity.dda_dongjak12_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"숭실대입구역 3번 출구", MainActivity.dda_dongjak13_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"동작역 7번 출구", MainActivity.dda_dongjak14_point,1,"bikesharing_dda");
                MainActivity.createMarker(mapView,"동작역 5번 출구", MainActivity.dda_dongjak15_point,1,"bikesharing_dda");

                ArrayList<MapPoint.GeoCoordinate> pointlist=new ArrayList<MapPoint.GeoCoordinate>();
                MapPoint.GeoCoordinate current_coordinate =MainActivity.current_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak_coordinate = MainActivity.dda_dongjak_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak2_coordinate = MainActivity.dda_dongjak2_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak3_coordinate = MainActivity.dda_dongjak3_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak4_coordinate = MainActivity.dda_dongjak4_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak5_coordinate = MainActivity.dda_dongjak5_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak6_coordinate = MainActivity.dda_dongjak6_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak7_coordinate = MainActivity.dda_dongjak7_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak8_coordinate = MainActivity.dda_dongjak8_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak9_coordinate = MainActivity.dda_dongjak9_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak10_coordinate = MainActivity.dda_dongjak10_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak11_coordinate = MainActivity.dda_dongjak11_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak12_coordinate = MainActivity.dda_dongjak12_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak13_coordinate = MainActivity.dda_dongjak13_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak14_coordinate = MainActivity.dda_dongjak14_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate dda_dongjak15_coordinate = MainActivity.dda_dongjak15_point.getMapPointGeoCoord();
                pointlist.add(dda_dongjak_coordinate);
                pointlist.add(dda_dongjak2_coordinate);
                pointlist.add(dda_dongjak3_coordinate);
                pointlist.add(dda_dongjak4_coordinate);
                pointlist.add(dda_dongjak5_coordinate);
                pointlist.add(dda_dongjak6_coordinate);
                pointlist.add(dda_dongjak7_coordinate);
                pointlist.add(dda_dongjak8_coordinate);
                pointlist.add(dda_dongjak9_coordinate);
                pointlist.add(dda_dongjak10_coordinate);
                pointlist.add(dda_dongjak11_coordinate);
                pointlist.add(dda_dongjak12_coordinate);
                pointlist.add(dda_dongjak13_coordinate);
                pointlist.add(dda_dongjak14_coordinate);
                pointlist.add(dda_dongjak15_coordinate);

                double min=100000.0;double minx=0.0;double miny=0.0;
                for(int i=0;i<pointlist.size();i++){
                    if(MainActivity.distance(current_coordinate.latitude,current_coordinate.longitude,pointlist.get(i).latitude,pointlist.get(i).longitude)<min) {
                        min=MainActivity.distance(current_coordinate.latitude,current_coordinate.longitude,pointlist.get(i).latitude,pointlist.get(i).longitude);
                        minx=pointlist.get(i).latitude;miny=pointlist.get(i).longitude;
                    }
                }
                MainActivity.showpolyline(current_point,MapPoint.mapPointWithGeoCoord(minx,miny));

            }

        });
        carchap_kickboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();

                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                MainActivity.createMarker(mapView,"현위치", current_point,1,"current_location");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam2_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam3_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam4_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam5_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam6_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam7_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam8_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam9_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam10_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam11_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam12_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam13_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam14_point,1,"kickboard_kick");
                MainActivity.createMarker(mapView,"킥고잉", MainActivity.kick_gangnam15_point,1,"kickboard_kick");
                MapPoint.GeoCoordinate current_coordinate =MainActivity.current_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam_coordinate = MainActivity.kick_gangnam_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam2_coordinate = MainActivity.kick_gangnam2_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam3_coordinate = MainActivity.kick_gangnam3_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam4_coordinate = MainActivity.kick_gangnam4_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam5_coordinate = MainActivity.kick_gangnam5_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam6_coordinate = MainActivity.kick_gangnam6_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam7_coordinate = MainActivity.kick_gangnam7_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam8_coordinate = MainActivity.kick_gangnam8_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam9_coordinate = MainActivity.kick_gangnam9_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam10_coordinate = MainActivity.kick_gangnam10_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam11_coordinate = MainActivity.kick_gangnam11_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam12_coordinate = MainActivity.kick_gangnam12_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam13_coordinate = MainActivity.kick_gangnam13_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam14_coordinate = MainActivity.kick_gangnam14_point.getMapPointGeoCoord();
                MapPoint.GeoCoordinate kick_gangnam15_coordinate = MainActivity.kick_gangnam15_point.getMapPointGeoCoord();
                ArrayList<MapPoint.GeoCoordinate> pointlist=new ArrayList<MapPoint.GeoCoordinate>();
                pointlist.add(kick_gangnam_coordinate);
                pointlist.add(kick_gangnam2_coordinate);
                pointlist.add(kick_gangnam3_coordinate);
                pointlist.add(kick_gangnam4_coordinate);
                pointlist.add(kick_gangnam5_coordinate);
                pointlist.add(kick_gangnam6_coordinate);
                pointlist.add(kick_gangnam7_coordinate);
                pointlist.add(kick_gangnam8_coordinate);
                pointlist.add(kick_gangnam9_coordinate);
                pointlist.add(kick_gangnam10_coordinate);
                pointlist.add(kick_gangnam11_coordinate);
                pointlist.add(kick_gangnam12_coordinate);
                pointlist.add(kick_gangnam13_coordinate);
                pointlist.add(kick_gangnam14_coordinate);
                pointlist.add(kick_gangnam15_coordinate);

                double min=100000.0;double minx=0.0;double miny=0.0;
                for(int i=0;i<pointlist.size();i++){
                    if(MainActivity.distance(current_coordinate.latitude,current_coordinate.longitude,pointlist.get(i).latitude,pointlist.get(i).longitude)<min) {
                        min=MainActivity.distance(current_coordinate.latitude,current_coordinate.longitude,pointlist.get(i).latitude,pointlist.get(i).longitude);
                        minx=pointlist.get(i).latitude;miny=pointlist.get(i).longitude;
                    }
                }
                MainActivity.showpolyline(current_point,MapPoint.mapPointWithGeoCoord(minx,miny));
            }

        });
        carchap_rentcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();
                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                MainActivity.createMarker(mapView,"노원역 1번 출구",MainActivity.rentnow_nowon_point,0,"rentcar_rentnow");
                MainActivity.createMarker(mapView,"건대입구역 6번 출구",MainActivity.rentnow_gundae_point,0,"rentcar_rentnow");
                MainActivity.createMarker(mapView,"천호역 9번 출구",MainActivity.rentnow_cheonho_point,0,"rentcar_rentnow");
            }

        });

        //지하철
        carchap_carpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();
                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                MainActivity.createMarker(mapView,"현위치", current_point,1,"current_location");
                MainActivity.createMarker(mapView,"신대방삼거리역", MainActivity.subway_sindaebang_point,1,"subway");
                MainActivity.createMarker(mapView,"보라매역", MainActivity.subway_boramae_point,1,"subway");
                MainActivity.createMarker(mapView,"신풍역", MainActivity.subway_shinpoong_point,1,"subway");
                MainActivity.createMarker(mapView,"대림역", MainActivity.subway_daelim_point,1,"subway");
                MainActivity.createMarker(mapView,"남구로역", MainActivity.subway_namguro_point,1,"subway");
                MainActivity.createMarker(mapView,"가산디지털단지역", MainActivity.subway_gasandigital_point,1,"subway");
                MainActivity.createMarker(mapView,"철산역", MainActivity.subway_chulsan_point,1,"subway");
                MainActivity.createMarker(mapView,"광명사거리역", MainActivity.subway_gwangmyoung_point,1,"subway");
                MainActivity.createMarker(mapView,"천왕역", MainActivity.subway_chunwang_point,1,"subway");
                MainActivity.createMarker(mapView,"온수역", MainActivity.subway_onsu_point,1,"subway");
                MainActivity.createMarker(mapView,"까치울역", MainActivity.subway_kkachiwool_point,1,"subway");
                MainActivity.createMarker(mapView,"장승배기역",MainActivity.subway_jangseung_point,1,"subway");
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
            }

        });
        carchap_carsharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location_temp=1;
                mapView.removeAllPOIItems();
                mapView.removeAllPolylines();
                String trackingcheck="TrackingModeOff";
                mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                mapView.setShowCurrentLocationMarker(true);
                Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                //카셰어링-쏘카
                MainActivity.createMarker(mapView,"함지박사거리",MainActivity.socar_seocho_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"자운고등학교",MainActivity.socar_dobong_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"무악재역 2번 출구",MainActivity.socar_seodaemoon_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"논현역 8번 출구",MainActivity.socar_gangnam_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"강남서울의료원",MainActivity.socar_gangnam2_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"정릉동 스카이파크 빌라",MainActivity.socar_seongbook_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"서울정곡초등학교",MainActivity.socar_gangseo_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"흑석역 4번 출구",MainActivity.socar_dongjak_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"흑석한강센트레빌 2차",MainActivity.socar_dongjak2_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"중앙대 입구",MainActivity.socar_dongjak3_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"중앙대 후문",MainActivity.socar_dongjak4_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"흑석역",MainActivity.socar_dongjak5_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"중앙대병원",MainActivity.socar_dongjak6_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"상도그레이스빌",MainActivity.socar_dongjak7_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"숭실대입구역 4번 출구",MainActivity.socar_dongjak8_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"상도역 5번 출구 (파미에)",MainActivity.socar_dongjak9_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"상도2동 주민센터",MainActivity.socar_dongjak10_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"동작관악교육지원청",MainActivity.socar_dongjak11_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"상도역 5번 출구",MainActivity.socar_dongjak12_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"용산공업고등학교",MainActivity.socar_yongsan_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"원효로 2동 주민센터",MainActivity.socar_yongsan_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"아모레퍼시픽본사",MainActivity.socar_yongsan_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"신용산역 3번 출구",MainActivity.socar_yongsan_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"당산역 12번 출구",MainActivity.socar_ydp_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"당산역 3번 출구",MainActivity.socar_ydp2_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"문래역 2번 출구",MainActivity.socar_ydp3_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"문래중학교",MainActivity.socar_ydp4_point,1,"carsharing_socar");
                MainActivity.createMarker(mapView,"여의도역 3번 출구",MainActivity.socar_ydp5_point,1,"carsharing_socar");

                //카셰어링-그린카
                MainActivity.createMarker(mapView,"중앙대병원",MainActivity.gcar_dongjak_point,1,"carsharing_gcar");
                MainActivity.createMarker(mapView,"흑석동 한강현대아파트",MainActivity.gcar_dongjak2_point,1,"carsharing_gcar");
                MainActivity.createMarker(mapView,"상도동 건영아파트",MainActivity.gcar_dongjak3_point,1,"carsharing_gcar");
                MainActivity.createMarker(mapView,"상도 브라운스톤아파트",MainActivity.gcar_dongjak4_point,1,"carsharing_gcar");
                MainActivity.createMarker(mapView,"상도역",MainActivity.gcar_dongjak5_point,1,"carsharing_gcar");

            }

        });



        return view;
    }

}
