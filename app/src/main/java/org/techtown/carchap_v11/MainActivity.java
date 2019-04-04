package org.techtown.carchap_v11;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.techtown.carchap_v11.R.color;
import static org.techtown.carchap_v11.R.id;
import static org.techtown.carchap_v11.R.id.daumMapView;
import static org.techtown.carchap_v11.R.id.list;
import static org.techtown.carchap_v11.R.id.main_frame;
import static org.techtown.carchap_v11.R.id.main_nav;
import static org.techtown.carchap_v11.R.id.nav_bike;
import static org.techtown.carchap_v11.R.id.nav_home;
import static org.techtown.carchap_v11.R.id.nav_info;
import static org.techtown.carchap_v11.R.id.nav_pinmove;
import static org.techtown.carchap_v11.R.id.nav_reser;
import static org.techtown.carchap_v11.R.layout;


public class MainActivity extends FragmentActivity implements MapView.MapViewEventListener,ReserFra_carchap.Reser_carchapFragmentListener,HomeFragment.HomeFragmentListener,MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.CurrentLocationEventListener {



    static private MapView mapView;

    private MapReverseGeoCoder mReverseGeoCoder = null;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    public EditText editText;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    static String polytemp[]={};




    private static final MapPoint default_point = MapPoint.mapPointWithGeoCoord(37.510759, 126.977943);
    private static final MapPoint ichon_point = MapPoint.mapPointWithGeoCoord(37.517090, 126.968893);
    private static final MapPoint dongjag_point = MapPoint.mapPointWithGeoCoord(37.506060, 126.977329);
    private static final MapPoint banpo_point = MapPoint.mapPointWithGeoCoord(37.512829, 126.998870);
    private static final MapPoint ttugseom_point = MapPoint.mapPointWithGeoCoord(37.529430, 127.065510);
    private static final MapPoint jamsil_point = MapPoint.mapPointWithGeoCoord(37.518266, 127.081630);
    private static final MapPoint mangwon_point = MapPoint.mapPointWithGeoCoord(37.553422, 126.896874);




    public BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private AllinoneFragment bikeFragment;
    private InfoFragment infoFragment;
    private ReserFragment reserFragment;
    private CarchapFragment carchapFragment;




    static ArrayList<String> kakaoresult= new ArrayList<String>();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);


        mapView = (MapView)findViewById(daumMapView);
        //mapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mapView.setMapViewEventListener(this);
        mapView.setCurrentLocationEventListener(this);



        homeFragment = new HomeFragment();
        bikeFragment = new AllinoneFragment();
        infoFragment = new InfoFragment();
        reserFragment = new ReserFragment();
        carchapFragment= new CarchapFragment();


        setFragment(homeFragment);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }


        final ImageButton main_temp_intro=(ImageButton)findViewById(id.imageButton_main_temp);
        Drawable alpha_main_temp_intro=main_temp_intro.getBackground();
        alpha_main_temp_intro.setAlpha(20);
        main_temp_intro.bringToFront();

        final TextView main_current_location=(TextView) findViewById(R.id.current_location);



        final ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.home_findbar);

        //하단 액션바 아이콘 고정 및 사이즈 조정
        mMainFrame = (FrameLayout) findViewById(main_frame);
        BottomNavigationView mMainNav = (BottomNavigationView)findViewById(main_nav);
        mMainNav.setItemIconSize(120);
        Log.d("TEST22","@222");
        BottomNavigationHelper.disableShiftMode(mMainNav);


        //하단 액션바 기능
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case nav_home:
                        //mMainNav.setItemBackgroundResource(color.colorPrimary);
                        setFragment(homeFragment);
                        return true;

                    case nav_bike:
                        setFragment(bikeFragment);
                        return true;

                    case nav_pinmove:
                        setFragment(carchapFragment);
                        createbanpoMarker(mapView);
                        createichonMarker(mapView);
                        createjamsilMarker(mapView);
                        createmangwonMarker(mapView);
                        createttugseomMarker(mapView);
                        return true;


                    case nav_reser:
                        setFragment(reserFragment);
                        return true;

                    case nav_info:
                        setFragment(infoFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

        ImageButton center_pin=(ImageButton)findViewById(id.center_pin);

        //tracking mode 제어 3종류
        final ImageButton button3 = (ImageButton)findViewById(R.id.button);
        button3.bringToFront();
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //button3.setImageResource(R.drawable.center_pin);
                String trackingcheck="TrackingModeOff";
                String trackingcheck2="TrackingModeOnWithoutHeading";
                String trackingcheck3="TrackingModeOnWithHeading";
                if(trackingcheck.equals(String.valueOf(mapView.getCurrentLocationTrackingMode()))){
                    mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                    mapView.setShowCurrentLocationMarker(true);
                    button3.setImageResource(R.drawable.trackmode_currentlocation);
                    Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));
                }
                else if(trackingcheck2.equals(String.valueOf(mapView.getCurrentLocationTrackingMode()))){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
                    mapView.setShowCurrentLocationMarker(true);
                    button3.setImageResource(R.drawable.trackmode_currentlicationwithheading);
                }
                else if(trackingcheck3.equals(String.valueOf(mapView.getCurrentLocationTrackingMode()))){
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    mapView.setShowCurrentLocationMarker(false);
                    button3.setImageResource(R.drawable.trackmode_default);
                }


            }
        });
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
        mReverseGeoCoder = new MapReverseGeoCoder("80042944961bf7e2672a63faefa4ad36", mapView.getMapCenterPoint(), MainActivity.this, MainActivity.this);
        mReverseGeoCoder.startFindingAddress();
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

    final private void showpolyline(MapPoint mapPoint, MapPoint mapPoint2){
        int padding = 200;
        float minZoomLevel = 3;
        float maxZoomLevel = 8;
        MapPointBounds bounds = new MapPointBounds(mapPoint, mapPoint2);
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0));
        polyline.addPoint(mapPoint);
        polyline.addPoint(mapPoint2);
        mapView.addPolyline(polyline);
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
        //키보드 내리기 올리기
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mapView.getWindowToken(),0);



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

        //키보드 내리기 올리기
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mapView.getWindowToken(),0);

        //tracking mode 해체
        ImageButton button3 = (ImageButton)findViewById(R.id.button);
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
        button3.setImageResource(R.drawable.trackmode_default);
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
        //키보드 내리기 올리기
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mapView.getWindowToken(),0);

    }



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(main_frame,fragment);
        fragmentTransaction.commit();
    }

    /* 선착장 pin 찍기*/
    /*--------------------------------------------------------------*/
    private void createDefaultMarker(MapView mapView) {
        MapPOIItem mDefaultMarker;
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
        MapPOIItem mDefaultMarker;
        mDefaultMarker = new MapPOIItem();
        String name = "이촌 나루터22222222222";
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setTag(0);
        mDefaultMarker.setMapPoint(ichon_point);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(ichon_point, false);
    }
    static public void createMarker(MapView mapView, String name,MapPoint mapPoint,int setTag, String pintype){
        MapPOIItem mDefaultMarker;
        mDefaultMarker= new MapPOIItem();
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setMapPoint(mapPoint);
        mDefaultMarker.setTag(setTag);
        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(mapPoint, true);
    }

    private void createbanpoMarker(MapView mapView) {
        MapPOIItem mDefaultMarker;
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
        MapPOIItem mDefaultMarker;
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
        MapPOIItem mDefaultMarker;
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
        MapPOIItem mDefaultMarker;
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
        MapPOIItem mDefaultMarker;
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
    CharSequence Homestartinput;
    CharSequence Homefinishinput;
    MapPoint zoomstartpoint;
    MapPoint zoomfinishpoint;

    //carchap_aqua 경로 설정
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

    public void onInputHome_start_Sent(CharSequence input) {
        String start_string=input.toString();
        Log.d("TEST start sent", input.toString());

    }
    public void onInputHome_finish_Sent(CharSequence input) {
        String finish_string=input.toString();
        Log.d("TEST finish sent", input.toString());

    }

    public void onInputHome_polyline_Sent(CharSequence input) {
        polyline_signal=input.toString();
        Log.d("TEST poly sent", input.toString());
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
        Log.d("11111111","11111112");
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }
    private void onFinishReverseGeoCoding(String result) {
        Toast.makeText(MainActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
        Log.d("11111111","11111113");

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float v) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i("111", String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, v));

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음

            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }
    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



        private static final String KAKAO_REST_API= "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s";
    static String polyline_signal="";
        static class kakaogetRESTApi extends AsyncTask<String, String, String> {
            void showpolyline(MapPoint mapPoint, MapPoint mapPoint2){
                int padding = 200;
                float minZoomLevel = 3;
                float maxZoomLevel = 8;
                MapPointBounds bounds = new MapPointBounds(mapPoint, mapPoint2);
                MapPolyline polyline = new MapPolyline();
                polyline.setTag(1000);
                polyline.setLineColor(Color.argb(128, 255, 51, 0));
                polyline.addPoint(mapPoint);
                polyline.addPoint(mapPoint2);
                mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String...strings) {
                Log.d("Task3","POST");
                String temp="Not Gained";
                try{
                    temp= GET(strings[0],strings[1],strings[2]);
                    Log.d("REST", temp);
                    return temp;
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                return temp;
            }
            public String run(String x,String y,String key)throws ExecutionException, InterruptedException{
                Log.d("Task5","POST");
                return new kakaogetRESTApi().execute(x,y,key).get().replace("\"","");
            }

            private String GET(String x,String y, String key) throws IOException {
                String myUrl = String.format(KAKAO_REST_API, x);
                Log.d("REST GET", "RESQUEST URL = " + myUrl);
                InputStream inputStream = null;
                String returnString = "";
                String returnValue = "";
                String returnValue_x = "";
                String returnValue_y = "";
                String returnValue_x2 = "";
                String returnValue_y2 = "";
                int length = 1024;

                try {
                    URL url = new URL(myUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestProperty("Authorization", "KakaoAK " + key);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestProperty("charset", "utf-8");
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();
                    int response = conn.getResponseCode();
                    Log.d("REST GET", "The response is :" + response);
                    inputStream = conn.getInputStream();
                    JsonObject json = new JsonParser().parse(new InputStreamReader(inputStream, "UTF-8")).getAsJsonObject();
                    json.getAsJsonArray("documents").get(0).getAsJsonObject().getAsJsonPrimitive("place_name").toString();
                    returnValue = json.getAsJsonArray("documents").get(0).getAsJsonObject().getAsJsonPrimitive("place_name").toString();
                    returnValue_x=json.getAsJsonArray("documents").get(0).getAsJsonObject().getAsJsonPrimitive("x").toString();
                    returnValue_y=json.getAsJsonArray("documents").get(0).getAsJsonObject().getAsJsonPrimitive("y").toString();

                    //Log.d("REST GET x", "The response is :" +returnValue_y +returnValue_x+Double.parseDouble(returnValue_y));

                    kakaoresult.add(returnValue);
                    kakaoresult.add(returnValue_x);
                    kakaoresult.add(returnValue_y);
                    returnValue_x=returnValue_x.replace("\"","");
                    returnValue_y=returnValue_y.replace("\"","");
                    Log.d("REST GET", "The response is :" + returnValue + returnValue_x + returnValue_y);
                    mapView.removeAllPOIItems();
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(returnValue_y), Double.parseDouble(returnValue_x)), true);
                    createMarker(mapView,returnValue,MapPoint.mapPointWithGeoCoord(Double.parseDouble(returnValue_y), Double.parseDouble(returnValue_x)),1,"keyword");
                    //MapPoint temp=getmappoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(returnValue_y), Double.parseDouble(returnValue_x)));
                    Log.d("tttest", "The response is 0 :" +kakaoresult.get(0));
                    Log.d("tttest", "The response is 1 :" +kakaoresult.get(1));
                    Log.d("tttest", "The response is 2 :" +kakaoresult.get(2));
                    Log.d("tttest", "The response is 3 :" +kakaoresult.get(3));
                    Log.d("tttest", "The response is 4 :" +kakaoresult.get(4));
                    Log.d("tttest", "The response is 5 :" +kakaoresult.get(5));
                    Log.d("tttest", "The response is size :" +kakaoresult.size());
                    mapView.removeAllPolylines();
                    mapView.removeAllPOIItems();
                    if(kakaoresult.size()>4){
                        Log.d("tttest", "The response is size :" +kakaoresult.size());
                        createMarker(mapView,kakaoresult.get(0),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(2).replace("\"","")), Double.parseDouble(kakaoresult.get(1).replace("\"",""))),1,"keyword");
                        createMarker(mapView,kakaoresult.get(3),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5).replace("\"","")), Double.parseDouble(kakaoresult.get(4).replace("\"",""))),1,"keyword");
                        MapPolyline polyline = new MapPolyline();
                        polyline.setTag(1000);
                        polyline.setLineColor(Color.argb(128, 255, 51, 0));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(2).replace("\"","")), Double.parseDouble(kakaoresult.get(1).replace("\"",""))));
                        polyline.addPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5).replace("\"","")),Double.parseDouble(kakaoresult.get(4).replace("\"",""))));
                        mapView.addPolyline(polyline);
                        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                        int padding = 300; // px
                        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding, 3,8));
                        //mapView.removeAllPOIItems();
                        Log.d("Rtttest3", String.valueOf(kakaoresult.size()));
                        //showpolyline(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5)), Double.parseDouble(kakaoresult.get(4))),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5)), Double.parseDouble(kakaoresult.get(4))));
                        Log.d("Rtttest3", String.valueOf(kakaoresult.size()));
                        kakaoresult.clear();
                    }




                } catch (Exception e) {
                    Log.e("REST GET", "Error: " + e.getMessage());
                } finally {
                    if (inputStream != null)
                        inputStream.close();

                }
                /*
                EditText editText=(EditText) getClass().findViewById(id.editText);
                EditText editText2=findViewById(id.editText2);

                if(editText.getText().toString().length()>0 && editText2.getText().toString().length()>0){
                    Log.d("REST GET2", "2222222");
                }*/
                return String.valueOf(kakaoresult);
            }/*
            public MapPoint getmappoint(MapPoint mapPoint){
                MapPoint temp;
                //temp = MapPoint.mapPointWithGeoCoord(MapPoint.mapPoint);
                Log.d("TESTtemp", String.valueOf(temp));
                return temp;}*/

        }


}

