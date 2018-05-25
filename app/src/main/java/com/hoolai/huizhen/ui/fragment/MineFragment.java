package com.hoolai.huizhen.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoolai.huizhen.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: //TODO
 */

public class MineFragment extends Fragment {

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_train, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
}
