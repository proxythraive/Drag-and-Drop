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

public class SUVFragment extends Fragment {

    private View rootView;
    private ListView suv;
    private FragmentListAdapter adapter;
    private ArrayList<GetterSetter> arrayList;
    //static string array decleration
    private String[] nameArray = new String[]{"Ford Endeavour","Mahindra Scorpio","Hyundai Creta","Toyota Fortuner"};
    private String[] costArray = new String[]{"29.2 Lakhs","16.63 Lakh","18.73 Lakh","33.79 Lakh"};
    private String[] weightArray = new String[]{"6,009 Pounds","6,209 Pounds","6,809 Pounds","6,209 Pounds"};
    private String[] widthArray = new String[]{"5.0 feet","5.8 feet","5.5 feet","5.2 feet"};
    EditText searchSuvList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //to inflate the layout to the fragment
        rootView = inflater.inflate(R.layout.fragment_suv,container,false);
        //initialises necessary objects in the layout
        suv = (ListView) rootView.findViewById(R.id.suv);
        searchSuvList = (EditText) rootView.findViewById(R.id.search_suv_list);
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
        suv.setAdapter(adapter);

        //filters the content in the listview
        searchSuvList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = searchSuvList.getText().toString().toLowerCase(Locale.getDefault());
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
