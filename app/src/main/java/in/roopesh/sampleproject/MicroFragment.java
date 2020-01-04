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

public class MicroFragment extends Fragment {

    private View rootView;
    private ListView micro;
    private FragmentListAdapter adapter;
    private ArrayList<GetterSetter> arrayList;
    //static string array decleration
    private String[] nameArray = new String[]{"Maruti Suzuki Alto K10","Maruti Suzuki Celerio","Mahindra e2o","Maruti Suzuki Ritz"};
    private String[] costArray = new String[]{"4.47 Lakhs","5.5 Lakhs","5.7 Lakhs","5.8 Lakhs"};
    private String[] weightArray = new String[]{"4,009 Pounds","4,209 Pounds","4,809 Pounds","4,209 Pounds"};
    private String[] widthArray = new String[]{"6.5 feet","6.7 feet","6.8 feet","5 feet"};
    EditText searchMicroList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //to inflate the layout to the fragment
        rootView = inflater.inflate(R.layout.fragment_micro,container,false);

        //initialises necessary objects in the layout
        micro = (ListView) rootView.findViewById(R.id.micro);
        searchMicroList = (EditText) rootView.findViewById(R.id.search_micro_list);

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
        micro.setAdapter(adapter);

        //filters the content in the listview
        searchMicroList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text = searchMicroList.getText().toString().toLowerCase(Locale.getDefault());
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
