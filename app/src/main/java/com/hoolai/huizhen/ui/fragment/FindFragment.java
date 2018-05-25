package com.hoolai.huizhen.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hoolai.huizhen.R;
import com.hoolai.huizhen.ui.activity.PlayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: //TODO
 */

public class FindFragment extends Fragment implements View.OnClickListener {

    Unbinder unbinder;
    @BindView(R.id.btn_start_train)
    Button btnStartTrain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_start_train})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start_train:
                Intent playIntent = new Intent(getActivity(), PlayActivity.class);
                startActivity(playIntent);
                break;
        }
    }
}
