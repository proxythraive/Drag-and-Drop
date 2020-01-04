package in.roopesh.sampleproject.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import in.roopesh.sampleproject.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    //tab names decleared in strings file.
    private Integer[] tabNamesIndex = new Integer[]{R.string.tab_one_name,R.string.tab_two_name,R.string.tab_three_name};
    private ArrayList<Fragment> pagerFragments;

    //constructor to get the context and fragments to be set for the pageadapter
    public ViewPagerAdapter(FragmentManager fm, Context context,ArrayList<Fragment> viewPagerFragments) {
        super(fm);
        mContext = context;
        pagerFragments = new ArrayList<>();
        pagerFragments.addAll(viewPagerFragments);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //returns the title from the strings file with the integer array reference
        return mContext.getResources().getString(tabNamesIndex[position]);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //returns the fragment constructor to be loaded to the viewpager
        return pagerFragments.get(position);
    }

    @Override
    public int getCount() {
        //returns the count of the integer array
        return tabNamesIndex.length;
    }
}
