package in.roopesh.sampleproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Locale;

import in.roopesh.sampleproject.Adapters.FragmentListAdapter;

public class SedanFragment extends Fragment {

    private View rootView;
    private ListView sedan;
    private FragmentListAdapter adapter;
    private ArrayList<GetterSetter> arrayList;
    //static string array decleration
    private String[] nameArray = new String[]{"Maruti Dzire","Honda City","Hyundai Verna","Honda Amaze"};
    private String[] costArray = new String[]{"9.52 Lakh","14.31 Lakh"," 14.07 Lakh","9.79 Lakh"};
    private String[] weightArray = new String[]{"5,009 Pounds","5,209 Pounds","5,809 Pounds","5,209 Pounds"};
    private String[] widthArray = new String[]{"6.9 feet","6.8 feet","6.5 feet","4 feet"};
    EditText searchSedanList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //to inflate the layout to the fragment
        rootView = inflater.inflate(R.layout.fragment_sedan,container,false);
        //initialises necessary objects in the layout
        sedan = (ListView) rootView.findViewById(R.id.sedan);
        searchSedanList = (EditText) rootView.findViewById(R.id.search_sedan_list);
        //initialises the list with dummy data
        arrayList = new ArrayList<>();
        for (int i=0;i<nameArray.length;i++) {
            GetterSetter getterSetter = new GetterSetter();
            getterSetter.setCarCost(costArray[i]);
            getterSetter.setCarName(nameArray[i]);
            getterSetter.setCarWeight(weightArray[i]);
            getterSetter.setCarWidth(widthArray[i]);
            arrayList.add(getterSetter);
        }

        //initialises the adapter with activity, context, arraylist of the values to be displayed in the listview
        adapter = new FragmentListAdapter(getActivity(),getActivity().getApplicationContext(),arrayList);
        sedan.setAdapter(adapter);

        //filters the content in the listview
        searchSedanList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = searchSedanList.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        return rootView;
    }
}
