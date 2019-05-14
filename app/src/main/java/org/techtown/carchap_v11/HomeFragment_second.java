package org.techtown.carchap_v11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.techtown.carchap_v11.R.id.main_frame;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment_second.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment_second#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment_second extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private EditText fragment_home_second_editext;
    private HomeFragment_third homeFragment_third;


    static ArrayList<String> data=new ArrayList<>();
    static ArrayAdapter<Listview_location_Item> adapter;
    static ListView mlistview;

    public HomeFragment_second() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment_second.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment_second newInstance(String param1, String param2) {
        HomeFragment_second fragment = new HomeFragment_second();
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

        Log.d("HomeFragment_second traking", "1");

        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_home_second, container, false);
        fragment_home_second_editext = view.findViewById(R.id.fragment_home_second_editext);
        adapter= new ArrayAdapter(getContext() ,android.R.layout.simple_list_item_1,data);
        mlistview=(ListView)view.findViewById(R.id.fragment_home_second_listview);


        /*길찾기 edittext에 글이 입력되었을 때 이벤트리스너*/
        fragment_home_second_editext.addTextChangedListener(new TextWatcher() {
            int textlength=fragment_home_second_editext.getText().toString().length();


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("HomeFragment_second", "textchanged3");

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {


                    Log.d("HomeFragment_second traking", "2");
                    textlength = fragment_home_second_editext.getText().toString().length();
                    Log.d("HomeFragment_second", "textchanged when :" + textlength);
                    kakaogetRESTApi kakao = new kakaogetRESTApi();
                    data.clear();
                    kakao.execute(fragment_home_second_editext.getText().toString(), "37.0789561558879", "7a1980c4a68692e396509a54b3c3223c");
                    Log.d("HomeFragment_second", "textchanged when2 :" + textlength);
                    Log.d("HomeFragment_second traking", "3");
                    Log.d("data test", String.valueOf(data.isEmpty()));

                    Log.d("HomeFragment_second traking", "7");
                    mlistview.setAdapter(adapter);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    }, 500);

                }
                catch (IllegalStateException e){
                    e.printStackTrace();
                    Log.e("list view update", "Error: " + e.getMessage());
                }


            }
        });
        Log.d("HomeFragment_second traking", "4");
        homeFragment_third = new HomeFragment_third();
        try {


            mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    FragmentTransaction replace = fragmentTransaction.replace(main_frame, homeFragment_third);

                    //data.get(i)
                    //fragment 키보드 조정
                    InputMethodManager mInputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(fragment_home_second_editext.getWindowToken(), 0);

                    replace.commit();
                }
            });
        }
        catch (IllegalStateException e){
            e.printStackTrace();
            Log.e("list view item click", "Error: " + e.getMessage());
        }

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


    private static final String KAKAO_REST_API= "https://dapi.kakao.com/v2/local/search/keyword.json?query=%s";
    static String polyline_signal="";
    static class kakaogetRESTApi extends AsyncTask<String, String, String> {

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
            return new MainActivity.kakaogetRESTApi().execute(x,y,key).get().replace("\"","");
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

                Log.d("HomeFragment_second traking", "8");

                for(int i = 0;i<10;i++)
                {
                    returnValue = json.getAsJsonArray("documents").get(i).getAsJsonObject().getAsJsonPrimitive("place_name").toString();
                    returnValue_x=json.getAsJsonArray("documents").get(i).getAsJsonObject().getAsJsonPrimitive("x").toString();
                    returnValue_y=json.getAsJsonArray("documents").get(i).getAsJsonObject().getAsJsonPrimitive("y").toString();
                    Log.d("HomeFragment_second ", "The response["+i+"] is  :" + returnValue);
                    returnValue=returnValue.replace("\"","");
                    returnValue_x=returnValue_x.replace("\"","");
                    returnValue_y=returnValue_y.replace("\"","");
                    Listview_location_Item item=new Listview_location_Item(returnValue,returnValue_x,returnValue_y);
                    data.add(item.getPlace_name());
                }



                for (int i=0;i<10;i++){
                   // Log.d("data test2", data.get(i));
                }
                Log.d("HomeFragment_second traking", "6");





                returnValue_x=returnValue_x.replace("\"","");
                returnValue_y=returnValue_y.replace("\"","");
                //Log.d("REST GET", "The response is :" + returnValue + returnValue_x + returnValue_y);


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
            return String.valueOf(0);
        }/*
            public MapPoint getmappoint(MapPoint mapPoint){
                MapPoint temp;
                //temp = MapPoint.mapPointWithGeoCoord(MapPoint.mapPoint);
                Log.d("TESTtemp", String.valueOf(temp));
                return temp;}*/

    }

}
