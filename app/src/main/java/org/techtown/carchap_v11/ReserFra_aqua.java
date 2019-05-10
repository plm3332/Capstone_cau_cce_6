package org.techtown.carchap_v11;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.techtown.carchap_v11.dummy.DummyContent;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReserFra_aqua extends Fragment implements AdapterView.OnItemSelectedListener,ItemFragment.OnListFragmentInteractionListener {

    private Reser_carchapFragmentListener listener;


    public ReserFra_aqua() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_reser_fra_aqua, container, false);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.carchap_aqua_start_point_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(), R.array.carchap_aqua_finish_point_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Log.d("spinner222 point", String.valueOf(spinner.getId()));

        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);
        Log.d("spinner2 point",String.valueOf(spinner2.getId()));



        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)


        Log.d("parent point",parent.getAdapter().toString());
        Log.d("spinner point", String.valueOf(id));
        String text= parent.getItemAtPosition(pos).toString();
        String temptext=parent.getItemAtPosition(0).toString();
        if(temptext.equals("출발지")){
            CharSequence input=parent.getItemAtPosition(pos).toString();
            listener.onInputReserF_start_Sent(input);
        }
        else{
            CharSequence input=parent.getItemAtPosition(pos).toString();
            listener.onInputReserF_finish_Sent(input);
        }




    }

    public interface Reser_carchapFragmentListener{
        void onInputReserF_start_Sent(CharSequence startinput);
        void onInputReserF_finish_Sent(CharSequence finishinput);


    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Reser_carchapFragmentListener) {
            listener = (Reser_carchapFragmentListener) context;
        }else{
           // throw new RuntimeException(context.toString()+"must implement ReserFragmentListener");

        }
    }
}
