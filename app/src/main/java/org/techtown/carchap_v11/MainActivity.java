package org.techtown.carchap_v11;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.techtown.carchap_v11.R.id;
import static org.techtown.carchap_v11.R.id.daumMapView;
import static org.techtown.carchap_v11.R.id.main_frame;
import static org.techtown.carchap_v11.R.id.main_nav;
import static org.techtown.carchap_v11.R.id.nav_bike;
import static org.techtown.carchap_v11.R.id.nav_home;
import static org.techtown.carchap_v11.R.id.nav_info;
import static org.techtown.carchap_v11.R.id.nav_pinmove;
import static org.techtown.carchap_v11.R.id.nav_reser;
import static org.techtown.carchap_v11.R.layout;


public class MainActivity extends FragmentActivity implements MapView.MapViewEventListener,ReserFra_aqua.Reser_carchapFragmentListener,HomeFragment.HomeFragmentListener,MapReverseGeoCoder.ReverseGeoCodingResultListener, MapView.CurrentLocationEventListener {


    public static double cur_lati;
    public static double cur_longi;
    public static MapView mapView;

    private MapReverseGeoCoder mReverseGeoCoder = null;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    static double start_x;
    static double start_y;
    static double finish_x;
    static double finish_y;
    static boolean homefrg = false;
    static boolean mainfrg = false;
    public EditText editText;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    static String polytemp[]={};

    public static MapPoint current_point;
    private static final MapPoint default_point = MapPoint.mapPointWithGeoCoord(37.510759, 126.977943);
    private static final MapPoint ichon_point = MapPoint.mapPointWithGeoCoord(37.517090, 126.968893);
    private static final MapPoint dongjag_point = MapPoint.mapPointWithGeoCoord(37.506060, 126.977329);
    private static final MapPoint banpo_point = MapPoint.mapPointWithGeoCoord(37.512829, 126.998870);
    private static final MapPoint ttugseom_point = MapPoint.mapPointWithGeoCoord(37.529430, 127.065510);
    private static final MapPoint jamsil_point = MapPoint.mapPointWithGeoCoord(37.518266, 127.081630);
    private static final MapPoint mangwon_point = MapPoint.mapPointWithGeoCoord(37.553422, 126.896874);

    public static final MapPoint Heukseok_point = MapPoint.mapPointWithGeoCoord(37.508817, 126.963763);

    //public static final MapPoint start_point = MapPoint.mapPointWithGeoCoord(start_x, start_y);
    //public static final MapPoint finish_point = MapPoint.mapPointWithGeoCoord(finish_x, finish_y);
    //카셰어링 쏘카
    public static final MapPoint socar_seocho_point = MapPoint.mapPointWithGeoCoord(37.491448, 126.989250);
    public static final MapPoint socar_dobong_point = MapPoint.mapPointWithGeoCoord(37.660034, 127.048132);
    public static final MapPoint socar_seodaemoon_point = MapPoint.mapPointWithGeoCoord(37.580468, 126.952700);
    public static final MapPoint socar_gangnam_point = MapPoint.mapPointWithGeoCoord(37.513024, 127.024456);
    public static final MapPoint socar_gangnam2_point = MapPoint.mapPointWithGeoCoord(37.513086, 127.064813);
    public static final MapPoint socar_seongbook_point = MapPoint.mapPointWithGeoCoord(37.599415, 127.004563);
    public static final MapPoint socar_gangseo_point = MapPoint.mapPointWithGeoCoord(37.577174, 126.818345);
    public static final MapPoint socar_dongjak_point = MapPoint.mapPointWithGeoCoord(37.510217, 126.961446);
    public static final MapPoint socar_dongjak2_point = MapPoint.mapPointWithGeoCoord(37.506373, 126.964428);
    public static final MapPoint socar_dongjak3_point = MapPoint.mapPointWithGeoCoord(37.507256, 126.960073);
    public static final MapPoint socar_dongjak4_point = MapPoint.mapPointWithGeoCoord(37.506003, 126.956946);
    public static final MapPoint socar_dongjak5_point = MapPoint.mapPointWithGeoCoord(37.508652, 126.962812);
    public static final MapPoint socar_dongjak6_point = MapPoint.mapPointWithGeoCoord(37.506434, 126.962319);
    public static final MapPoint socar_dongjak7_point = MapPoint.mapPointWithGeoCoord(37.496503, 126.937377);
    public static final MapPoint socar_dongjak8_point = MapPoint.mapPointWithGeoCoord(37.493656, 126.959286);
    public static final MapPoint socar_dongjak9_point = MapPoint.mapPointWithGeoCoord(37.500958, 126.950566);
    public static final MapPoint socar_dongjak10_point = MapPoint.mapPointWithGeoCoord(37.506517, 126.942316);
    public static final MapPoint socar_dongjak11_point = MapPoint.mapPointWithGeoCoord(37.502200, 126.943039);
    public static final MapPoint socar_dongjak12_point = MapPoint.mapPointWithGeoCoord(37.501819, 126.950008);
    public static final MapPoint socar_yongsan_point = MapPoint.mapPointWithGeoCoord(37.524564, 126.966313);
    public static final MapPoint socar_yongsan2_point = MapPoint.mapPointWithGeoCoord(37.533708, 126.952890);
    public static final MapPoint socar_yongsan3_point = MapPoint.mapPointWithGeoCoord(37.528929, 126.968565);
    public static final MapPoint socar_yongsan4_point = MapPoint.mapPointWithGeoCoord(37.527802, 126.965365);
    public static final MapPoint socar_ydp_point = MapPoint.mapPointWithGeoCoord(37.534579, 126.900320);
    public static final MapPoint socar_ydp2_point = MapPoint.mapPointWithGeoCoord(37.535025, 126.902501);
    public static final MapPoint socar_ydp3_point = MapPoint.mapPointWithGeoCoord(37.520043, 126.891412);
    public static final MapPoint socar_ydp4_point = MapPoint.mapPointWithGeoCoord(37.519337, 126.886226);
    public static final MapPoint socar_ydp5_point = MapPoint.mapPointWithGeoCoord(37.523144, 126.923056);

    //카셰어링 그린카
    public static final MapPoint gcar_dongjak_point = MapPoint.mapPointWithGeoCoord(37.507205, 126.960566);
    public static final MapPoint gcar_dongjak2_point = MapPoint.mapPointWithGeoCoord(37.506147, 126.970470);
    public static final MapPoint gcar_dongjak3_point = MapPoint.mapPointWithGeoCoord(37.507378, 126.949504);
    public static final MapPoint gcar_dongjak4_point = MapPoint.mapPointWithGeoCoord(37.507503, 126.943666);
    public static final MapPoint gcar_dongjak5_point = MapPoint.mapPointWithGeoCoord(37.505298, 126.944191);

    //카풀 타다


    //전동킥보드 킥고잉
    public static final MapPoint kick_gangnam_point = MapPoint.mapPointWithGeoCoord(37.510243, 127.043878);
    public static final MapPoint kick_gangnam2_point = MapPoint.mapPointWithGeoCoord(37.508713, 127.038535);
    public static final MapPoint kick_gangnam3_point = MapPoint.mapPointWithGeoCoord(37.507305, 127.033782);
    public static final MapPoint kick_gangnam4_point = MapPoint.mapPointWithGeoCoord(37.511529, 127.048042);
    public static final MapPoint kick_gangnam5_point = MapPoint.mapPointWithGeoCoord(37.512735, 127.052574);
    public static final MapPoint kick_gangnam6_point = MapPoint.mapPointWithGeoCoord(37.505629, 127.041091);
    public static final MapPoint kick_gangnam7_point = MapPoint.mapPointWithGeoCoord(37.507803, 127.045332);
    public static final MapPoint kick_gangnam8_point = MapPoint.mapPointWithGeoCoord(37.500671, 127.037505);
    public static final MapPoint kick_gangnam9_point = MapPoint.mapPointWithGeoCoord(37.497775, 127.028138);
    public static final MapPoint kick_gangnam10_point = MapPoint.mapPointWithGeoCoord(37.502356, 127.043223);
    public static final MapPoint kick_gangnam11_point = MapPoint.mapPointWithGeoCoord(37.503824, 127.048234);
    public static final MapPoint kick_gangnam12_point = MapPoint.mapPointWithGeoCoord(37.496177, 127.049411);
    public static final MapPoint kick_gangnam13_point = MapPoint.mapPointWithGeoCoord(37.498812, 127.044803);
    public static final MapPoint kick_gangnam14_point = MapPoint.mapPointWithGeoCoord(37.499547, 127.047199);
    public static final MapPoint kick_gangnam15_point = MapPoint.mapPointWithGeoCoord(37.500757, 127.050413);

    //공유자전거 따릉이
    public static final MapPoint dda_dongjak_point = MapPoint.mapPointWithGeoCoord(37.509325, 126.963446);
    public static final MapPoint dda_dongjak2_point = MapPoint.mapPointWithGeoCoord(37.508991, 126.963313);
    public static final MapPoint dda_dongjak3_point = MapPoint.mapPointWithGeoCoord(37.505960, 126.969179);
    public static final MapPoint dda_dongjak4_point = MapPoint.mapPointWithGeoCoord(37.506316, 126.968925);
    public static final MapPoint dda_dongjak5_point = MapPoint.mapPointWithGeoCoord(37.505352, 126.966037);
    public static final MapPoint dda_dongjak6_point = MapPoint.mapPointWithGeoCoord(37.507074, 126.959039);
    public static final MapPoint dda_dongjak7_point = MapPoint.mapPointWithGeoCoord(37.502288, 126.948290);
    public static final MapPoint dda_dongjak8_point = MapPoint.mapPointWithGeoCoord(37.512831, 126.952401);
    public static final MapPoint dda_dongjak9_point = MapPoint.mapPointWithGeoCoord(37.500900, 126.941643);
    public static final MapPoint dda_dongjak10_point = MapPoint.mapPointWithGeoCoord(37.504605, 126.939041);
    public static final MapPoint dda_dongjak11_point = MapPoint.mapPointWithGeoCoord(37.505663, 126.939459);
    public static final MapPoint dda_dongjak12_point = MapPoint.mapPointWithGeoCoord(37.510511, 126.944821);
    public static final MapPoint dda_dongjak13_point = MapPoint.mapPointWithGeoCoord(37.496617, 126.953610);
    public static final MapPoint dda_dongjak14_point = MapPoint.mapPointWithGeoCoord(37.503170, 126.976070);
    public static final MapPoint dda_dongjak15_point = MapPoint.mapPointWithGeoCoord(37.503110, 126.977274);

    //렌트카 렌트나우
    public static final MapPoint rentnow_nowon_point = MapPoint.mapPointWithGeoCoord(37.656332, 127.064076);
    public static final MapPoint rentnow_gundae_point = MapPoint.mapPointWithGeoCoord(37.540558, 127.068295);
    public static final MapPoint rentnow_cheonho_point = MapPoint.mapPointWithGeoCoord(37.538319, 127.123108);

    //지하철 7호선

    public static final MapPoint subway_sindaebang_point = MapPoint.mapPointWithGeoCoord(37.499751, 126.928178);
    public static final MapPoint subway_boramae_point = MapPoint.mapPointWithGeoCoord(37.500210, 126.920303);
    public static final MapPoint subway_shinpoong_point = MapPoint.mapPointWithGeoCoord(37.500385, 126.908958);
    public static final MapPoint subway_daelim_point = MapPoint.mapPointWithGeoCoord(37.493313, 126.895776);
    public static final MapPoint subway_namguro_point = MapPoint.mapPointWithGeoCoord(37.486336, 126.887263);
    public static final MapPoint subway_gasandigital_point = MapPoint.mapPointWithGeoCoord(37.481094, 126.882498);
    public static final MapPoint subway_chulsan_point = MapPoint.mapPointWithGeoCoord(37.476202, 126.868240);
    public static final MapPoint subway_gwangmyoung_point = MapPoint.mapPointWithGeoCoord(37.479485, 126.854696);
    public static final MapPoint subway_chunwang_point = MapPoint.mapPointWithGeoCoord(37.486865, 126.838735);
    public static final MapPoint subway_onsu_point = MapPoint.mapPointWithGeoCoord(37.492355, 126.823559);
    public static final MapPoint subway_kkachiwool_point = MapPoint.mapPointWithGeoCoord(37.506488, 126.811445);
    public static final MapPoint subway_jangseung_point = MapPoint.mapPointWithGeoCoord(37.504948, 126.939180);
    public static final MapPoint subway_sangdo_point = MapPoint.mapPointWithGeoCoord(37.503110, 126.947753);
    public static final MapPoint subway_soongsil_point = MapPoint.mapPointWithGeoCoord(37.496561, 126.953492);
    public static final MapPoint subway_namseong_point = MapPoint.mapPointWithGeoCoord(37.484884, 126.970662);
    public static final MapPoint subway_isu_point = MapPoint.mapPointWithGeoCoord(37.485219, 126.981601);
    public static final MapPoint subway_naebang_point = MapPoint.mapPointWithGeoCoord(37.487660, 126.993624);
    public static final MapPoint subway_gosok_point = MapPoint.mapPointWithGeoCoord(37.506005, 127.004390);
    public static final MapPoint subway_banpo_point = MapPoint.mapPointWithGeoCoord(37.508153, 127.011635);
    public static final MapPoint subway_nonhyeon_point = MapPoint.mapPointWithGeoCoord(37.511073, 127.021316);
    public static final MapPoint subway_hakdong_point = MapPoint.mapPointWithGeoCoord(37.514240, 127.031592);
    public static final MapPoint subway_gangnamgucheong_point = MapPoint.mapPointWithGeoCoord(37.517162, 127.041282);
    public static final MapPoint subway_cheongdam_point = MapPoint.mapPointWithGeoCoord(37.519077, 127.051804);
    public static final MapPoint subway_ddukseom_point = MapPoint.mapPointWithGeoCoord(37.531590, 127.066687);
    public static final MapPoint subway_gundae_point = MapPoint.mapPointWithGeoCoord(37.540405, 127.069235);
    public static final MapPoint subway_childrenpark_point = MapPoint.mapPointWithGeoCoord(37.547920, 127.074605);
    public static final MapPoint subway_gunja_point = MapPoint.mapPointWithGeoCoord(37.557155, 127.079485);
    public static final MapPoint subway_junggok_point = MapPoint.mapPointWithGeoCoord(37.565813, 127.084252);
    public static final MapPoint subway_yongmasan_point = MapPoint.mapPointWithGeoCoord(37.573638, 127.086739);
    public static final MapPoint subway_sagajeong_point = MapPoint.mapPointWithGeoCoord(37.580957, 127.088492);
    public static final MapPoint subway_meonmok_point = MapPoint.mapPointWithGeoCoord(37.588765, 127.087489);
    public static final MapPoint subway_sangbong_point = MapPoint.mapPointWithGeoCoord(37.596863, 127.085293);
    public static final MapPoint subway_junghwa_point = MapPoint.mapPointWithGeoCoord(37.602535, 127.0779267);
    public static final MapPoint subway_meokgol_point = MapPoint.mapPointWithGeoCoord(37.610778, 127.077699);
    public static final MapPoint seven_line[] = {subway_kkachiwool_point, subway_onsu_point, subway_chunwang_point, subway_gwangmyoung_point, subway_chulsan_point,
            subway_gasandigital_point, subway_namguro_point, subway_daelim_point, subway_shinpoong_point, subway_boramae_point, subway_sindaebang_point, subway_jangseung_point,
            subway_sangdo_point, subway_soongsil_point, subway_namseong_point, subway_isu_point, subway_naebang_point,
            subway_gosok_point, subway_banpo_point, subway_nonhyeon_point, subway_hakdong_point, subway_gangnamgucheong_point, subway_cheongdam_point,
            subway_ddukseom_point, subway_gundae_point, subway_childrenpark_point, subway_gunja_point, subway_junggok_point, subway_yongmasan_point,
            subway_sagajeong_point, subway_meonmok_point, subway_sangbong_point, subway_junghwa_point, subway_meokgol_point};


    public BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private HomeFragment homeFragment;
    private HomeFragment_first homeFragment_first;
    private AllinoneFragment bikeFragment;
    private InfoFragment infoFragment;
    private ReserFragment reserFragment;
    private CarchapFragment carchapFragment;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATIOM=2;

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
        homeFragment_first = new HomeFragment_first();
        bikeFragment = new AllinoneFragment();
        infoFragment = new InfoFragment();
        reserFragment = new ReserFragment();
        carchapFragment= new CarchapFragment();


        setFragment(homeFragment_first);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        final ImageButton main_temp_intro=(ImageButton)findViewById(id.imageButton_main_temp);
        Drawable alpha_main_temp_intro=main_temp_intro.getBackground();
        alpha_main_temp_intro.setAlpha(20);
        main_temp_intro.bringToFront();

        final ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.home_findbar);

        //하단 액션바 아이콘 고정 및 사이즈 조정
        mMainFrame = (FrameLayout) findViewById(main_frame);
        final BottomNavigationView mMainNav = (BottomNavigationView)findViewById(main_nav);
        mMainNav.setItemIconSize(240);

        Log.d("TEST22","@222");
        BottomNavigationHelper.disableShiftMode(mMainNav);

        //하단 액션바 기능
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case nav_home:
                        //menuItem.setIcon(R.drawable.home_findpath);
                        setFragment(homeFragment_first);
                        homefrg=true;
                        mainfrg=false;

                        return true;

                    case nav_bike:
                        //menuItem.setIcon(R.drawable.home_allinonecarchap);
                        setFragment(homeFragment);
                        homefrg=false;
                        mainfrg=true;

                        return true;

                    case nav_pinmove:
                        //menuItem.setIcon(R.drawable.home_carchap);
                        setFragment(carchapFragment);
                        return true;


                    case nav_reser:
                        //menuItem.setIcon(R.drawable.home_carchapforall);
                        setFragment(reserFragment);
                        return true;

                    case nav_info:
                        //menuItem.setIcon(R.drawable.home_mypage);
                        setFragment(infoFragment);
                        return true;

                        default:
                            return false;
                }
            }
        });

        ImageButton center_pin=(ImageButton)findViewById(id.center_pin);
        final Button map_zoomin=(Button)findViewById(id.map_zoomin);
        Button map_zoomout=(Button)findViewById(id.map_zoomout);
        map_zoomin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int current_zoomlevel=0;
                current_zoomlevel=mapView.getZoomLevel();
                current_zoomlevel-=1;
                mapView.setZoomLevel(current_zoomlevel, true);

            }
        });
        map_zoomout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int current_zoomlevel=0;
                current_zoomlevel=mapView.getZoomLevel();
                current_zoomlevel+=1;
                mapView.setZoomLevel(current_zoomlevel, true);

            }
        });

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

        // 줌 레벨 변경
        mapView.setZoomLevel(5, true);
        final TextView main_current_location=(TextView) findViewById(R.id.current_location);
        editText =findViewById(R.id.editText);

        main_current_location.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment=new HomeFragment();
                Bundle bundle=new Bundle();
                if(bundle!=null) {
                    Log.d("HomeFragment_current_location_test1 :", main_current_location.getText().toString());
                    bundle.putString("current_location", main_current_location.getText().toString());
                    Log.d("HomeFragment_current_location_test2 :", String.valueOf(bundle));
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(main_frame,  homeFragment.newInstance("current_location",main_current_location.getText().toString()));
                    fragmentTransaction.commit();
                }
            }


        });
    }

    private void showAll(MapPoint mapPoint, MapPoint mapPoint2) {
        int padding = 200;
        float minZoomLevel = 3;
        float maxZoomLevel = 8;
        MapPointBounds bounds = new MapPointBounds(mapPoint, mapPoint2);
        mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(bounds, padding, minZoomLevel, maxZoomLevel));
    }

    static final public void showpolyline(MapPoint mapPoint, MapPoint mapPoint2){

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

    final private void showpolyline_carchap(MapPoint mapPoint, MapPoint mapPoint2){
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

    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List <Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;

                }
            }

        } catch (IOException e) {
            Toast.makeText(MainActivity.this,"잘못된 포인트 설정입니다.",Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return nowAddress;
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };




    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        //키보드 내리기 올리기
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mapView.getWindowToken(),0);

        /*현재 위치 받아와서 textview에 띄우기*/
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        TextView main_current_location=(TextView) findViewById(R.id.current_location);

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( MainActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        else{
            try{
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
                MapPoint current_location_point = MapPoint.mapPointWithGeoCoord(latitude, longitude);
                MapPoint.GeoCoordinate mapPointGeo = mapView.getMapCenterPoint().getMapPointGeoCoord();
                String temp=getAddress(MainActivity.this.getBaseContext(),mapPointGeo.latitude,mapPointGeo.longitude);
                temp=temp.replace("대한민국","");
                main_current_location.setText(temp);


                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000,
                        1,
                        gpsLocationListener);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000,
                        1,
                        gpsLocationListener);
            }catch (NullPointerException e) {
                Log.d("current location ","current location error");
            }
        }


    }


    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(main_frame,fragment);
        fragmentTransaction.commit();
    }


    static public void createMarker(MapView mapView, String name,MapPoint mapPoint,int setTag, String pintype){
        MapPOIItem mDefaultMarker;
        mDefaultMarker= new MapPOIItem();
        mDefaultMarker.setItemName(name);
        mDefaultMarker.setMapPoint(mapPoint);
        mDefaultMarker.setTag(setTag);
        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        if(pintype.equals("carsharing_socar")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_socar);
        }
        else if(pintype.equals("carsharing_gcar")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_greencar);
        }
        else if(pintype.equals("bikesharing_dda")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_bike);
        }
        else if(pintype.equals("subway")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_subway);
        }
        else if(pintype.equals("current_location")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.current_location);
        }
        else if(pintype.equals("start_location")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_start);
        }
        else if(pintype.equals("finish_location")){
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_finish);
        }
        else {
            mDefaultMarker.setCustomImageResourceId(R.drawable.marker_default);
        }
        mapView.addPOIItem(mDefaultMarker);
        mapView.selectPOIItem(mDefaultMarker, true);
        mapView.setMapCenterPoint(mapPoint, true);
    }

    /*for capstone*/
    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;
        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
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
            mapView.setMapCenterPoint(ichon_point, true);
            createMarker(mapView,"이촌",ichon_point,3,"aqua");
            zoomstartpoint=ichon_point;
        }
        else if(input.equals("동작")){
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(dongjag_point, true);
            createMarker(mapView,"동작",dongjag_point,3,"aqua");
            zoomstartpoint=dongjag_point;
        }
        else if(input.equals("반포")){
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(banpo_point, true);
            createMarker(mapView,"반포",banpo_point,3,"aqua");
            zoomstartpoint=banpo_point;
        }
        else if(input.equals("뚝섬")){
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(ttugseom_point, true);
            createMarker(mapView,"뚝섬",ttugseom_point,3,"aqua");
            zoomstartpoint=ttugseom_point;
        }
        else if(input.equals("잠실")){
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(jamsil_point, true);
            createMarker(mapView,"잠실",jamsil_point,3,"aqua");
            zoomstartpoint=jamsil_point;
        }
        else if(input.equals("망원")){
            mapView.removeAllPOIItems();
            mapView.setMapCenterPoint(mangwon_point, true);
            createMarker(mapView,"망원",mangwon_point,3,"aqua");
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
            createMarker(mapView,"이촌",ichon_point,3,"aqua");
            zoomfinishpoint=ichon_point;

        }
        else if(input.equals("동작")){
            zoomfinishpoint=dongjag_point;
            createMarker(mapView,"동작",dongjag_point,3,"aqua");
        }
        else if(input.equals("반포")){
            zoomfinishpoint=banpo_point;
            createMarker(mapView,"반포",banpo_point,3,"aqua");
        }
        else if(input.equals("뚝섬")){
            zoomfinishpoint=ttugseom_point;
            createMarker(mapView,"뚝섬",ttugseom_point,3,"aqua");
        }
        else if(input.equals("잠실")){
            zoomfinishpoint=jamsil_point;
            createMarker(mapView,"잠실",jamsil_point,3,"aqua");

        }
        else if(input.equals("망원")){
            zoomfinishpoint=mangwon_point;
            createMarker(mapView,"망원",mangwon_point,3,"aqua");
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
        if(CarchapFragment.location_temp==1){
            cur_lati = mapPointGeo.latitude;
            cur_longi = mapPointGeo.longitude;
            current_point = MapPoint.mapPointWithGeoCoord(cur_lati, cur_longi);
        }
        CarchapFragment.location_temp=0;
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
                        Log.d("drag_button_test1","if문 돌입");
                        createMarker(mapView,kakaoresult.get(0),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(2).replace("\"","")), Double.parseDouble(kakaoresult.get(1).replace("\"",""))),1,"keyword");
                        createMarker(mapView,kakaoresult.get(3),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5).replace("\"","")), Double.parseDouble(kakaoresult.get(4).replace("\"",""))),1,"keyword");
                        //createMarker(mapView,kakaoresult.get(3),MapPoint.mapPointWithGeoCoord((Double.parseDouble(kakaoresult.get(2).replace("\"",""))+Double.parseDouble(kakaoresult.get(5).replace("\"","")))/2,(Double.parseDouble(kakaoresult.get(1).replace("\"",""))+Double.parseDouble(kakaoresult.get(4).replace("\"","")))/2),1,"keyword");
                        Log.d("drag_button_test2", String.valueOf((Double.parseDouble(kakaoresult.get(1).replace("\"",""))+Double.parseDouble(kakaoresult.get(4).replace("\"","")))/2));
                                //(Double.parseDouble(kakaoresult.get(2).replace("\"","")))

                        //mapView.removeAllPOIItems();
                        Log.d("polyline_test", String.valueOf(mapView.findPolylineByTag(1001)));
                        Log.d("Rtttest3", String.valueOf(kakaoresult.size()));
                        //showpolyline(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5)), Double.parseDouble(kakaoresult.get(4))),MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5)), Double.parseDouble(kakaoresult.get(4))));
                        Log.d("Rtttest3", String.valueOf(kakaoresult.size()));

                        start_x=Double.parseDouble(kakaoresult.get(2).replace("\"",""));
                        start_y=Double.parseDouble(kakaoresult.get(1).replace("\"",""));

                        finish_x=Double.parseDouble(kakaoresult.get(5).replace("\"",""));
                        finish_y=Double.parseDouble(kakaoresult.get(4).replace("\"",""));

                        MapPoint start_point = MapPoint.mapPointWithGeoCoord(start_x, start_y);
                        MapPoint finish_point = MapPoint.mapPointWithGeoCoord(finish_x, finish_y);

                        if(homefrg) {
                            MapPolyline polyline = new MapPolyline();
                            polyline.setTag(1001);
                            polyline.setLineColor(Color.argb(128, 255, 51, 0));
                            polyline.addPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(2).replace("\"", "")), Double.parseDouble(kakaoresult.get(1).replace("\"", ""))));
                            polyline.addPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(kakaoresult.get(5).replace("\"", "")), Double.parseDouble(kakaoresult.get(4).replace("\"", ""))));
                            mapView.addPolyline(polyline);
                            MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                            int padding = 300; // px
                            mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding, 3, 8));
                        }


                        if(mainfrg) {
                            CarchapFragment.location_temp = 1;
                            mapView.removeAllPOIItems();
                            mapView.removeAllPolylines();
                            String trackingcheck="TrackingModeOff";
                            mapView.setCurrentLocationTrackingMode((MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading));
                            mapView.setShowCurrentLocationMarker(true);
                            Log.d("TEST2", String.valueOf(mapView.getCurrentLocationTrackingMode()));

                            //출발지, 도착지
                            MainActivity.createMarker(mapView,"출발지", start_point,1,"start_location");
                            MainActivity.createMarker(mapView,"도착지", finish_point,1,"finish_location");

                            //현위치
                            MainActivity.createMarker(mapView,"현위치", current_point,1,"current_location");

                            //따릉이
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

                            //지하철역
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

                            MapPoint.GeoCoordinate current_coordinate =MainActivity.current_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate start_coordinate = start_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate finish_coordinate = finish_point.getMapPointGeoCoord();

                            //따릉이 좌표
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

                            //지하철역 좌표
                            MapPoint.GeoCoordinate subway_boramae_coordinate = MainActivity.subway_boramae_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_shinpoong_coordinate = MainActivity.subway_shinpoong_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_daelim_coordinate = MainActivity.subway_daelim_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_namguro_coordinate = MainActivity.subway_namguro_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_gasandigital_coordinate = MainActivity.subway_gasandigital_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_chulsan_coordinate = MainActivity.subway_chulsan_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_gwangmyoung_coordinate = MainActivity.subway_gwangmyoung_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_chunwang_coordinate = MainActivity.subway_chunwang_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_onsu_coordinate = MainActivity.subway_onsu_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_kkachiwool_coordinate = MainActivity.subway_kkachiwool_point.getMapPointGeoCoord();
                            MapPoint.GeoCoordinate subway_sindaebang_coordinate = MainActivity.subway_sindaebang_point.getMapPointGeoCoord();
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

                            ArrayList<MapPoint.GeoCoordinate> bicyclelist=new ArrayList<MapPoint.GeoCoordinate>();
                            bicyclelist.add(dda_dongjak_coordinate);
                            bicyclelist.add(dda_dongjak2_coordinate);
                            bicyclelist.add(dda_dongjak3_coordinate);
                            bicyclelist.add(dda_dongjak4_coordinate);
                            bicyclelist.add(dda_dongjak5_coordinate);
                            bicyclelist.add(dda_dongjak6_coordinate);
                            bicyclelist.add(dda_dongjak7_coordinate);
                            bicyclelist.add(dda_dongjak8_coordinate);
                            bicyclelist.add(dda_dongjak9_coordinate);
                            bicyclelist.add(dda_dongjak10_coordinate);
                            bicyclelist.add(dda_dongjak11_coordinate);
                            bicyclelist.add(dda_dongjak12_coordinate);
                            bicyclelist.add(dda_dongjak13_coordinate);
                            bicyclelist.add(dda_dongjak14_coordinate);
                            bicyclelist.add(dda_dongjak15_coordinate);
                            double bicycle_min=100000.0; double bicycle_minx=0.0; double bicycle_miny=0.0;
                            for(int i=0;i<bicyclelist.size();i++){
                                if(MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,bicyclelist.get(i).latitude,bicyclelist.get(i).longitude)<bicycle_min) {
                                    bicycle_min=MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,bicyclelist.get(i).latitude,bicyclelist.get(i).longitude);
                                    bicycle_minx=bicyclelist.get(i).latitude; bicycle_miny=bicyclelist.get(i).longitude;
                                }
                            }
                            double bicycle_distance = bicycle_min;

                            ArrayList<MapPoint.GeoCoordinate> subwaylist=new ArrayList<MapPoint.GeoCoordinate>();
                            subwaylist.add(subway_kkachiwool_coordinate);
                            subwaylist.add(subway_onsu_coordinate);
                            subwaylist.add(subway_chunwang_coordinate);
                            subwaylist.add(subway_gwangmyoung_coordinate);
                            subwaylist.add(subway_chulsan_coordinate);
                            subwaylist.add(subway_gasandigital_coordinate);
                            subwaylist.add(subway_namguro_coordinate);
                            subwaylist.add(subway_daelim_coordinate);
                            subwaylist.add(subway_shinpoong_coordinate);
                            subwaylist.add(subway_boramae_coordinate);
                            subwaylist.add(subway_sindaebang_coordinate);
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

                            double subway_min=100000.0; double subway_minx=0.0; double subway_miny=0.0;
                            for(int i=0;i<subwaylist.size();i++){
                                if(MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude)<subway_min) {
                                    subway_min=MainActivity.distance(start_coordinate.latitude,start_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude);
                                    subway_minx=subwaylist.get(i).latitude; subway_miny=subwaylist.get(i).longitude;
                                }
                            }
                            MainActivity.showpolyline(start_point,MapPoint.mapPointWithGeoCoord(subway_minx, subway_miny));


                            double subway_distance = subway_min;

                            int start_i = 0;
                            int finish_i = 0;

                            for(int i = 0; i < seven_line.length - 1; i++) {
                                MapPoint.GeoCoordinate temp = seven_line[i].getMapPointGeoCoord();
                                if (subway_minx == temp.latitude && subway_miny == temp.longitude) {
                                    start_i = i;
                                }
                            }

                            subway_min = 100000.0; subway_minx=0.0; subway_miny=0.0;
                            for(int i=0;i<subwaylist.size();i++){
                                if(MainActivity.distance(finish_coordinate.latitude,finish_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude)<subway_min) {
                                    subway_min=MainActivity.distance(finish_coordinate.latitude,finish_coordinate.longitude,subwaylist.get(i).latitude,subwaylist.get(i).longitude);
                                    subway_minx=subwaylist.get(i).latitude; subway_miny=subwaylist.get(i).longitude;
                                }
                            }
                            MainActivity.showpolyline(finish_point,MapPoint.mapPointWithGeoCoord(subway_minx, subway_miny));


                            for(int i = 0; i < seven_line.length - 1; i++) {
                                MapPoint.GeoCoordinate temp = seven_line[i].getMapPointGeoCoord();
                                if (subway_minx == temp.latitude && subway_miny == temp.longitude) {
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
                                for(int i = start_i; i > finish_i; i--) {
                                    MapPolyline seven_route = new MapPolyline();
                                    seven_route.setLineColor(Color.argb(255, 83, 144, 245));
                                    seven_route.addPoint(seven_line[i]);
                                    seven_route.addPoint(seven_line[i - 1]);
                                    mapView.addPolyline(seven_route);
                                }
                            }

                        }



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

