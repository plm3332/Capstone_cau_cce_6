package org.techtown.carchap_v11;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import static org.techtown.carchap_v11.R.*;
import static org.techtown.carchap_v11.R.id.*;


public class MainActivity extends FragmentActivity implements MapView.MapViewEventListener,ReserFra_carchap.Reser_carchapFragmentListener {



    private MapView mapView;


    private static final MapPoint default_point = MapPoint.mapPointWithGeoCoord(37.510759, 126.977943);
    private static final MapPoint ichon_point = MapPoint.mapPointWithGeoCoord(37.517090, 126.968893);
    private static final MapPoint dongjag_point = MapPoint.mapPointWithGeoCoord(37.506060, 126.977329);
    private static final MapPoint banpo_point = MapPoint.mapPointWithGeoCoord(37.512829, 126.998870);
    private static final MapPoint ttugseom_point = MapPoint.mapPointWithGeoCoord(37.529430, 127.065510);
    private static final MapPoint jamsil_point = MapPoint.mapPointWithGeoCoord(37.518266, 127.081630);
    private static final MapPoint mangwon_point = MapPoint.mapPointWithGeoCoord(37.553422, 126.896874);


    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private BikeFragment bikeFragment;
    private InfoFragment infoFragment;
    private ReserFragment reserFragment;
    private CarchapFragment carchapFragment;
    //private Reser_carchapFragment reser_carchapFragment;


    private MapPOIItem mDefaultMarker;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_main);


        mapView = (MapView)findViewById(daumMapView);
        //mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mapView.setMapViewEventListener(this);

        mMainFrame = (FrameLayout) findViewById(main_frame);
        mMainNav = (BottomNavigationView)findViewById(main_nav);

        homeFragment = new HomeFragment();
        bikeFragment = new BikeFragment();
        infoFragment = new InfoFragment();
        reserFragment = new ReserFragment();
        carchapFragment= new CarchapFragment();

        setFragment(homeFragment);


        //가이드뷰 생성 -> 터치 후 사라짐
        final ImageButton main_temp_intro=(ImageButton)findViewById(id.imageButton_main_temp);
        Drawable alpha_main_temp_intro=main_temp_intro.getBackground();
        alpha_main_temp_intro.setAlpha(20);
        main_temp_intro.bringToFront();

        final ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.home_findbar);

        createbanpoMarker(mapView);
        createichonMarker(mapView);
        createjamsilMarker(mapView);
        createmangwonMarker(mapView);
        createttugseomMarker(mapView);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case nav_home:
                        mMainNav.setItemBackgroundResource(color.colorPrimary);
                        setFragment(homeFragment);
                        createbanpoMarker(mapView);
                        createichonMarker(mapView);
                        createjamsilMarker(mapView);
                        createmangwonMarker(mapView);
                        createttugseomMarker(mapView);
                        return true;

                    case nav_bike:
                        mMainNav.setItemBackgroundResource(color.colorAccent);
                        setFragment(bikeFragment);
                        return true;

                    case nav_pintag:
                        mMainNav.setItemBackgroundResource(color.colorPrimary);
                        setFragment(carchapFragment);
                        return true;
                        //Toast.makeText(this, "pin tag 찍음",  Toast.LENGTH_SHORT).show();

                    case nav_reser:
                        mMainNav.setItemBackgroundResource(color.colorPrimary);
                        setFragment(reserFragment);
                        return true;

                    case nav_info:
                        mMainNav.setItemBackgroundResource(color.colorPrimary);
                        setFragment(infoFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });


        ImageButton center_pin=(ImageButton)findViewById(id.center_pin);
        main_temp_intro.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                main_temp_intro.setVisibility(View.INVISIBLE);

            }


        });

        //상태바 투명상태, 하단바 유지
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);// | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);




        // 중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.510759, 126.977943), true);

        // 줌 레벨 변경
        mapView.setZoomLevel(5, true);




    }

    private void showAll(MapPoint mapPoint, MapPoint mapPoint2) {
        int padding = 200;
        float minZoomLevel = 3;
        float maxZoomLevel = 8;
        MapPointBounds bounds = new MapPointBounds(mapPoint, mapPoint2);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        Log.d("aa2","aaa");


    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {
        Log.d("aa","aaa");
        ImageButton center_pin=(ImageButton)findViewById(id.center_pin);
        //센터핀 임시제거
        center_pin.setVisibility(View.INVISIBLE);

        ImageButton main_temp_intro=(ImageButton)findViewById(id.imageButton_main_temp);
        main_temp_intro.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {
        MapPoint.GeoCoordinate mapPointGeo = mapView.getMapCenterPoint().getMapPointGeoCoord();
        //Toast.makeText(this, "중심좌표" + "x:"+mapPointGeo.latitude + "y:"+mapPointGeo.longitude, Toast.LENGTH_SHORT).show();


        /* 메인 중앙 핀!!*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageButton center_pin=(ImageButton)findViewById(id.center_pin);
                /*이미 핀 있을때는 계속 보여지게 한다.*/
                /*Log.d("aa",center_pin.getVisibility());
                if(==center_pin.getVisibility()) {
                    center_pin.setVisibility(View.INVISIBLE);
                }*/
            }
        },1000);


    }


    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(main_frame,fragment);
        fragmentTransaction.commit();
    }

    /* 선착장 pin 찍기*/
    /*--------------------------------------------------------------*/
    private void createDefaultMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "기준점";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(default_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(default_point, false);
    }

    private void createichonMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "이촌 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(ichon_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(ichon_point, false);
    }

    private void createbanpoMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "반포 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(banpo_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);

        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(banpo_point, false);
    }

    private void createttugseomMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "뚝섬 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(ttugseom_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(ttugseom_point, false);
    }

    private void createjamsilMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "잠실 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(jamsil_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(jamsil_point, false);
    }

    private void createmangwonMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "망원 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(mangwon_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(mangwon_point, false);
    }
    private void createdongjagMarker(MapView mapView) {
        mDefaultMarker = new MapPOIItem();
        String name = "동작 나루터";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(dongjag_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(dongjag_point, false);
    }


    CharSequence resentstartiuput;
    CharSequence resentfinishiuput;
    MapPoint zoomstartpoint;
    MapPoint zoomfinishpoint;

    @Override
    public void onInputReserF_start_Sent(CharSequence input) {
        Log.d("startpoint",input.toString());

        if(input.equals("출발지")) {
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(default_point, true);
        }
        else if(input.equals("이촌")){
            mapView.removeAllPOIItems();
            createichonMarker(mapView);
            mapView.setMapCenterPoint(ichon_point, true);
            zoomstartpoint=ichon_point;
        }
        else if(input.equals("동작")){
            mapView.removeAllPOIItems();
            createdongjagMarker(mapView);
            mapView.setMapCenterPoint(dongjag_point, true);
            zoomstartpoint=dongjag_point;
        }
        else if(input.equals("반포")){
            mapView.removeAllPOIItems();
            createbanpoMarker(mapView);
            mapView.setMapCenterPoint(banpo_point, true);
            zoomstartpoint=banpo_point;
        }
        else if(input.equals("뚝섬")){
            mapView.removeAllPOIItems();
            createttugseomMarker(mapView);
            mapView.setMapCenterPoint(ttugseom_point, true);
            zoomstartpoint=ttugseom_point;
        }
        else if(input.equals("잠실")){
            mapView.removeAllPOIItems();
            createjamsilMarker(mapView);
            mapView.setMapCenterPoint(jamsil_point, true);
            zoomstartpoint=jamsil_point;
        }
        else if(input.equals("망원")){
            mapView.removeAllPOIItems();
            createmangwonMarker(mapView);
            mapView.setMapCenterPoint(mangwon_point, true);
            zoomstartpoint=mangwon_point;
        }
        else{
            Toast.makeText(this,"잘못된 포인트 설정입니다.",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onInputReserF_finish_Sent(CharSequence input) {
        if(input.equals(resentfinishiuput)){
            mapView.removeAllPOIItems();
        }

        if(input.equals("도착지")) {
            mapView.setMapCenterPoint(default_point, true);
            resentfinishiuput=input;
            Log.d("temp point", String.valueOf(resentfinishiuput));
        }
        else if(input.equals("이촌")){
            createichonMarker(mapView);
            zoomfinishpoint=ichon_point;

        }
        else if(input.equals("동작")){
            createdongjagMarker(mapView);
            zoomfinishpoint=dongjag_point;
        }
        else if(input.equals("반포")){
            createbanpoMarker(mapView);
            zoomfinishpoint=banpo_point;
        }
        else if(input.equals("뚝섬")){
            createttugseomMarker(mapView);
            zoomfinishpoint=ttugseom_point;
        }
        else if(input.equals("잠실")){
            createjamsilMarker(mapView);
            zoomfinishpoint=jamsil_point;

        }
        else if(input.equals("망원")){
            createmangwonMarker(mapView);
            zoomfinishpoint=mangwon_point;
        }
        else{
            Toast.makeText(this,"잘못된 포인트 설정입니다.",Toast.LENGTH_SHORT).show();
            return;
        }
        showAll(zoomstartpoint,zoomfinishpoint);

    }


}
