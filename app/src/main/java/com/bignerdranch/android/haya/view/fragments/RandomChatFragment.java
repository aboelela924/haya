package com.bignerdranch.android.haya.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bignerdranch.android.haya.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RandomChatFragment extends Fragment {
    @BindView(R.id.button_join_queue)
    Button buttonJoinQueue;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random_chat, container,false);

        ButterKnife.bind(this,v);
        return v;
    }

    @OnClick(R.id.button_join_queue)
    public void joinQueue(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new RandomChatWaitingInQueueFragment()).commit();

    }
}
