/*
package com.umeed.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeed.app.R;
import com.umeed.app.fragment.searchfragment.AdvancedSearchFragment;
import com.umeed.app.fragment.searchfragment.EducationSearchFragment;
import com.umeed.app.fragment.searchfragment.MatrimonyIDSearchFragment;
import com.umeed.app.fragment.searchfragment.OccupationSearchFragment;
import com.umeed.app.fragment.searchfragment.SmartSearchFragment;
import com.umeed.app.fragment.searchfragment.SpecialSearchFragment;
import com.umeed.app.network.networking.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchSelectActivity extends AppCompatActivity  implements  View.OnClickListener{

    TextView tv_smart_search,tv_advanced_search,tv_occup_search,tv_edu_search,tv_matrimonal_search,tv_special_search;

    ImageView iv_back,iv_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_select);
        ButterKnife.bind(this);

        tv_smart_search = findViewById(R.id.tv_smart_search);
        tv_advanced_search = findViewById(R.id.tv_advanced_search);
        tv_occup_search = findViewById(R.id.tv_occup_search);
        tv_edu_search = findViewById(R.id.tv_edu_search);
        tv_matrimonal_search = findViewById(R.id.tv_matrimonal_search);
        tv_special_search = findViewById(R.id.tv_special_search);

        iv_back = findViewById(R.id.iv_back);
        iv_home = findViewById(R.id.iv_home);


        tv_occup_search.setOnClickListener(this);
        tv_advanced_search.setOnClickListener(this);
        tv_occup_search.setOnClickListener(this);
        tv_edu_search.setOnClickListener(this);
        tv_matrimonal_search.setOnClickListener(this);
        tv_special_search.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_home.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_home:
                finish();
                break;

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
               // replaceFragmentWithoutBack(new LocationSearchFragment(), "Search by Location");
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

}
*/
