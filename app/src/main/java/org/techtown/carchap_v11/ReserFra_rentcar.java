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
public class ReserFra_rentcar extends Fragment {


    public ReserFra_rentcar() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_reser_fra_rentcar, container, false);

        ImageButton imagebutton_rentnow = (ImageButton) view.findViewById(R.id.imageButton_rentnow);
        ImageButton imagebutton_carplat = (ImageButton) view.findViewById(R.id.imageButton_carplat);
        ImageButton imagebutton_carmoa = (ImageButton) view.findViewById(R.id.imageButton_carmoa);
        ImageButton imagebutton_ppongcar = (ImageButton) view.findViewById(R.id.imageButton_ppongcar);


        imagebutton_rentnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("kr.co.rentnow");
            }

        });

        imagebutton_carplat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("kr.co.plat.carplat");
            }

        });
        imagebutton_carmoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.gogocarhome.gogocar");
            }

        });
        imagebutton_ppongcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appinstallcheck("com.breeze.ppongcar");
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
