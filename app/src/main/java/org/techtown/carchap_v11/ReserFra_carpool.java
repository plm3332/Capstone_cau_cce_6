package org.techtown.carchap_v11;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReserFra_carpool extends Fragment {


    public ReserFra_carpool() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_reser_fra_carpool, container, false);

        ImageButton imagebutton_tada = (ImageButton) view.findViewById(R.id.imageButton_tada);
        ImageButton imagebutton_buxi = (ImageButton) view.findViewById(R.id.imageButton_buxi);
        ImageButton imagebutton_poolus = (ImageButton) view.findViewById(R.id.imageButton_poolus);
        ImageButton imagebutton_uber = (ImageButton) view.findViewById(R.id.imageButton_uber);
        ImageButton imagebutton_adigo = (ImageButton) view.findViewById(R.id.imageButton_adigo);



        imagebutton_tada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("kr.co.vcnc.tada");
            }

        });

        imagebutton_buxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.buxikorea.buxi.user");
            }

        });
        imagebutton_poolus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("kr.poolus.rider.android");
            }

        });
        imagebutton_uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.ubercab");
            }

        });
        imagebutton_adigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.witzmobility.rider");
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
