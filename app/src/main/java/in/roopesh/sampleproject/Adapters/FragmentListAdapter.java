package in.roopesh.sampleproject.Adapters;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import in.roopesh.sampleproject.GetterSetter;
import in.roopesh.sampleproject.R;

public class FragmentListAdapter extends BaseAdapter {

    private Activity activity;
    private Context context;
    private ArrayList<GetterSetter> getterSettersList;
    private ArrayList<GetterSetter> searchGetterSetters;
    private LayoutInflater inflater;

    public FragmentListAdapter(Activity activity, Context context, ArrayList<GetterSetter> getterSetterArrayList) {
        this.activity = activity;
        this.context = context;
        getterSettersList = new ArrayList<>();
        searchGetterSetters = new ArrayList<>();
        getterSettersList.addAll(getterSetterArrayList);   //arraylist used for list display.
        searchGetterSetters.addAll(getterSetterArrayList);  //arraylist used for search.
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // Inflater instance to use layout inflater service
    }

    @Override
    public int getCount() {
        return getterSettersList.size();
    }

    @Override
    public Object getItem(int position) {
        return getterSettersList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class Holder{
        // class to declare object variables to be inflated
        TextView nameOfCar, costOfCar, widthOfCar, weightOfCar;
        CardView holderLayout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder;

        if (convertView == null){
            holder = new Holder();
            //if method invokes for first time all objects will gets initialised which are decleared in Holder class and set to a tag
            convertView = inflater.inflate(R.layout.adapter_fragments_list,null);
            holder.nameOfCar =convertView.findViewById(R.id.name_of_car);
            holder.costOfCar =convertView.findViewById(R.id.cost_of_car);
            holder.widthOfCar =convertView.findViewById(R.id.width_of_car);
            holder.weightOfCar =convertView.findViewById(R.id.weight_of_car);
            holder.holderLayout =convertView.findViewById(R.id.holder_layout);
            convertView.setTag(holder);
        }
        else {
            //if this method invokes after initailisation then gets the tag assigned previously and set to holder
            holder = (Holder) convertView.getTag();
        }

        //setting values from gettersetters
        holder.nameOfCar.setText(getterSettersList.get(position).getCarName());
        holder.costOfCar.setText(getterSettersList.get(position).getCarCost());
        holder.weightOfCar.setText(getterSettersList.get(position).getCarWeight());
        holder.widthOfCar.setText(getterSettersList.get(position).getCarWidth());

        // this will invoke when cardview is touched and started performing an peration
        holder.holderLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                //this will invoke if user clicked for an item for a long time
                holder.holderLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        View.DragShadowBuilder mShadow = new View.DragShadowBuilder(view); //this will create a shadow of the selected view
                        Map<String,String> map = new HashMap<>(); //initialises the map and set necessary information to be passed from this item
                        map.put("name",holder.nameOfCar.getText().toString().trim().replace(","," "));
                        map.put("cost",holder.costOfCar.getText().toString().trim().replace(","," "));
                        map.put("weight",holder.weightOfCar.getText().toString().trim().replace(","," "));
                        map.put("width",holder.widthOfCar.getText().toString().trim().replace(","," "));
                        // copies the map data converted to string to the clipboard
                        ClipData.Item item = new ClipData.Item(map.toString()); //copies to clipboard item
                        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN}; //set the mimetype of the copied content
                        ClipData data = new ClipData(map.toString(), mimeTypes, item); //sets the label and data to be copied to clipboard
                        //this will create the bounding box across the selected item for drag and drop
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            view.startDragAndDrop(data, mShadow, null, 0);
                        } else {
                            view.startDrag(data, mShadow, null, 0);
                        }
                        return false;
                    }
                });
                return false;
            }
        });

        return convertView;
    }

    // method invoked to filter the data in listview and sets back the filtered content to it
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        getterSettersList.clear();
        if (charText.length() == 0) {
            getterSettersList.addAll(searchGetterSetters);
        } else {
            for (GetterSetter wp : searchGetterSetters) {
                if (wp.getCarName().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getCarCost().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getCarWeight().toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.getCarWidth().toLowerCase(Locale.getDefault()).contains(charText)
                ) {
                    getterSettersList.add(wp);
                }
            }
        }
        //to inform the list adapter that data has changed and update listview with current updated data.
        notifyDataSetChanged();
    }

}
