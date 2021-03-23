package com.umeeds.app.fragment.searchfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.umeeds.app.R;

import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSelectFragment extends Fragment implements View.OnClickListener {

    Unbinder unbinder;
    TextView tv_smart_search, tv_advanced_search, tv_occup_search, tv_edu_search, tv_matrimonal_search, tv_special_search, tv_location_search;
    ImageView iv_back, iv_home;


    public SearchSelectFragment() {
        // Required empty public constructor
    }

    public static SearchSelectFragment newInstance() {
        return new SearchSelectFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_search_select, container, false);

        tv_smart_search = view.findViewById(R.id.tv_smart_search);
        tv_advanced_search = view.findViewById(R.id.tv_advanced_search);
        tv_occup_search = view.findViewById(R.id.tv_occup_search);
        tv_edu_search = view.findViewById(R.id.tv_edu_search);
        tv_matrimonal_search = view.findViewById(R.id.tv_matrimonal_search);
        tv_special_search = view.findViewById(R.id.tv_special_search);
        tv_location_search = view.findViewById(R.id.tv_location_search);

        iv_back = view.findViewById(R.id.iv_back);
        iv_home = view.findViewById(R.id.iv_home);

        tv_smart_search.setOnClickListener(this);
        tv_occup_search.setOnClickListener(this);
        tv_advanced_search.setOnClickListener(this);
        tv_edu_search.setOnClickListener(this);
        tv_matrimonal_search.setOnClickListener(this);
        tv_special_search.setOnClickListener(this);
        tv_location_search.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_home.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_smart_search:
                replaceFragmentWithoutBack(new SmartSearchFragment(), "Smart Search");
                break;

            case R.id.tv_advanced_search:
                replaceFragmentWithoutBack(new AdvancedSearchFragment(), "Advanced Search");
                break;

            case R.id.tv_occup_search:
                replaceFragmentWithoutBack(new OccupationSearchFragment(), "Search by Occupational");
                break;
            case R.id.tv_edu_search:
                replaceFragmentWithoutBack(new EducationSearchFragment(), "Search by Educational");
                break;
            case R.id.tv_matrimonal_search:
                replaceFragmentWithoutBack(new MatrimonyIDSearchFragment(), "Search by Matrimonial ID");
                break;
            case R.id.tv_special_search:
                replaceFragmentWithoutBack(new SpecialSearchFragment(), "Search by Special case");
                break;
            case R.id.tv_location_search:
                replaceFragmentWithoutBack(new LocationSearchFragment(), "Search by Location");
                break;
        }
    }

    public void replaceFragmentWithoutBack(Fragment newFragment, String tag) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_containers, newFragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(tag);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // unbinder.unbind();
    }

}
