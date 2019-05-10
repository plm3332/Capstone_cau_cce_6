package org.techtown.carchap_v11;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


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


        return view;
    }

}
