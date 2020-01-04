package in.roopesh.sampleproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL;

import in.roopesh.sampleproject.Adapters.FragmentListAdapter;
import in.roopesh.sampleproject.Adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    TabLayout mTabs;
    ViewPager mviewPager;
    ArrayList<Fragment> mViewPagerList;
    ViewPagerAdapter adapter;
    ListView listSelected;
    ArrayList<GetterSetter> setterArrayList = new ArrayList<>();
    FragmentListAdapter fragmentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialises all the objects declared in the layout file
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mviewPager = (ViewPager) findViewById(R.id.view_pager);
        listSelected = (ListView) findViewById(R.id.list_selected);

        //initialise and set the fragments with constructor and adds to the arraylist as arraylist of fragments.
        mViewPagerList = new ArrayList<>();
        mViewPagerList.add(new MicroFragment());
        mViewPagerList.add(new SedanFragment());
        mViewPagerList.add(new SUVFragment());

        // initialises the class with required parameters
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),getApplicationContext(),mViewPagerList);
        mviewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mviewPager);

        // registers the listview to listen the drag and drop events
        listSelected.setOnDragListener(new View.OnDragListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    //invokes when user starts the drag and drop operation
                    case DragEvent.ACTION_DRAG_STARTED:
                        ((ListView) v).setOutlineAmbientShadowColor(Color.YELLOW);
                        v.invalidate();
                        return true;

                    //invokes when user enters into the boundaries of the registered view for drag and drop events
                    case DragEvent.ACTION_DRAG_ENTERED:

                        String clipData = event.getClipDescription().getLabel().toString();
                        v.invalidate();
                        return true;

                    //invokes when user exits from the boundaries of the registered view for drag and drop events
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.invalidate();
                        return true;

                    //invokes when user drops the dragged object into the boundaries of the registered view for drag and drop events
                    case DragEvent.ACTION_DROP:

                        //getting the clipboard data with label and trims the curly braces {} for parsing the content
                        clipData = event.getClipDescription().getLabel().toString().replace("{","").replace("}","");
                        try {
                            //convering the string back to map
                            clipData = clipData.substring(1, clipData.length()-1);
                            String[] keyValuePairs = clipData.split(",");
                            Map<String,String> map = new HashMap<>();

                            for(String pair : keyValuePairs)
                            {
                                String[] entry = pair.split("=");
                                map.put(entry[0].trim(), entry[1].trim());
                            }
                            //initalises the getter setter and adds those values to the arraylist of gettersetters
                            GetterSetter getterSetter = new GetterSetter();
                            getterSetter.setCarName(map.get("name"));
                            getterSetter.setCarCost(map.get("cost"));
                            getterSetter.setCarWeight(map.get("weight"));
                            getterSetter.setCarWidth(map.get("width"));
                            setterArrayList.add(getterSetter);
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }
                        v.invalidate();
                        return true;

                    //invokes when user completed the drag and drop event
                    case DragEvent.ACTION_DRAG_ENDED:

                        //checking if the event is sucessful or not and prompting for necessary user action
                        if (event.getResult()) {
                            Toast.makeText(MainActivity.this, "Added to list!", Toast.LENGTH_SHORT).show();
                            Log.d("SAMPLE", "onDrag: "+setterArrayList.size());
                            //initialises the adapter and set the data to listview
                            fragmentListAdapter = new FragmentListAdapter(MainActivity.this,getApplicationContext(),setterArrayList);
                            listSelected.setAdapter(fragmentListAdapter);
                            listSelected.setClickable(false);
                        } else {
                            Toast.makeText(MainActivity.this, "Aw Snap! Try dropping it again", Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    default:
                        return false;
                }
            }
        });

    }
}
