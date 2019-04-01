package org.techtown.carchap_v11;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReserFra_bikesharing extends Fragment {


    public ReserFra_bikesharing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_reser_fra_bikesharing, container, false);

        ImageButton imagebutton_ddarong = (ImageButton) view.findViewById(R.id.imageButton_ddarong);
        ImageButton imagebutton_gbility = (ImageButton) view.findViewById(R.id.imageButton_gbility);
        ImageButton imagebutton_sbike = (ImageButton) view.findViewById(R.id.imageButton_sbike);
        ImageButton imagebutton_cookie = (ImageButton) view.findViewById(R.id.imageButton_cookie);
        ImageButton imagebutton_obike = (ImageButton) view.findViewById(R.id.imageButton_obike);
        ImageButton imagebutton_mobike = (ImageButton) view.findViewById(R.id.imageButton_mobike);
        ImageButton imagebutton_fifteen = (ImageButton) view.findViewById(R.id.imageButton_fifteen);
        ImageButton imagebutton_pedalro = (ImageButton) view.findViewById(R.id.imageButton_pedalro);
        ImageButton imagebutton_lycle = (ImageButton) view.findViewById(R.id.imageButton_lycle);
        ImageButton imagebutton_kakaobike = (ImageButton) view.findViewById(R.id.imageButton_kakaobike);

        imagebutton_ddarong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.dki.spb_android");
            }

        });

        imagebutton_gbility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.gbike.gbike");
            }

        });

        imagebutton_sbike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.trianglewide.sbike");
            }

        });
        imagebutton_cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("kr.co.cookiebike");
            }

        });
        imagebutton_obike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.obike");
            }

        });
        imagebutton_mobike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.mobike.mobikeapp");
            }

        });
        imagebutton_fifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.goyang.ecobike");
            }

        });
        imagebutton_pedalro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.my.ansanbike");
            }

        });
        imagebutton_lycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.roka.lycle");
            }

        });
        imagebutton_kakaobike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.kakao.taxi");
            }

        });
        return view;
    }

    public void Appinstallcheck(String x){
        String url=x;

        boolean isAppInstalled = appInstalledOrNot(url);

        if(isAppInstalled) {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = getActivity().getPackageManager()
                    .getLaunchIntentForPackage(url);
            startActivity(LaunchIntent);


        } else {
            String url2="market://details?id="+url;
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
            startActivity(i);

        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;

    }


}
