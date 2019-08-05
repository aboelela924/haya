package com.bignerdranch.android.haya.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.adapters.SlidingChatRecyclerViewAdapter;
import com.bignerdranch.android.haya.viewModel.ChatRoomsViewModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatsGroupFragment extends Fragment {
    private static final String tag_chats1to1Tab = "Chats1to1Tab";
    private static final String USER = "USER";

    @BindView(R.id.recyclerview_chats_group)
    RecyclerView recyclerView;
    private User mUser;

    private SlidingChatRecyclerViewAdapter adapter;
    private ChatRoomsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats_group, container, false);
        ButterKnife.bind(this,v);
        mUser = CurrentUser.user;

        adapter = new SlidingChatRecyclerViewAdapter(getActivity(), mUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        viewModelFunction();
        return v;
    }

    private void viewModelFunction(){
        viewModel = ViewModelProviders.of(this).get(ChatRoomsViewModel.class);
        viewModel.setGroupRoomList();
        viewModel.getRoomList().observe(getViewLifecycleOwner(), new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                adapter.setRoomChats(rooms);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.observeNewChatCreated();
        viewModel.observewNewMessage();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.stopNewChatObserver();
    }
}