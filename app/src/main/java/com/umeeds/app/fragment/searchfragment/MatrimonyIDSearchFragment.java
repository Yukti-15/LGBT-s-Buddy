package com.umeeds.app.fragment.searchfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.umeeds.app.MainActivity;
import com.umeeds.app.R;
import com.umeeds.app.network.networking.Constant;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatrimonyIDSearchFragment extends Fragment implements View.OnClickListener {

    EditText edt_matrimonial_id;
    RelativeLayout rl_search;

    public MatrimonyIDSearchFragment() {
        // Required empty public constructor
    }

    public static MatrimonyIDSearchFragment newInstance() {
        return new MatrimonyIDSearchFragment();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matrimony_idsearch, container, false);

        edt_matrimonial_id = view.findViewById(R.id.edt_matrimonial_id);
        rl_search = view.findViewById(R.id.rl_search);


        rl_search.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_search:

                if (validationSuccess()) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "Matrimonial");
                    bundle.putString("id", edt_matrimonial_id.getText().toString().trim());

                    MainActivity activity = (MainActivity) getActivity();
                    assert activity != null;
                    activity.loadFragment(Constant.SEARCH_RESULT_FRAGMENT, bundle);

                }

                break;
        }
    }

    private Boolean validationSuccess() {
        if (edt_matrimonial_id.getText().toString().length() == 0) {
            edt_matrimonial_id.setError("Please enter matri id !");
            edt_matrimonial_id.requestFocus();
            return false;
        }


        return true;
    }


}
