package com.bignerdranch.android.haya.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.GetRetrofit;
import com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking.SubscribedRoomsAPI;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.Chat;
import com.bignerdranch.android.haya.view.adapters.ChatRecyclerViewAdapter;
import com.bignerdranch.android.haya.viewModel.Chats1to1ViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Chats1to1TabFragment extends Fragment {

    private static final String tag_chats1to1Tab = "Chats1to1Tab";
    private static final String USER = "USER";

    @BindView(R.id.recyclerview_chats_1to1)
    RecyclerView recyclerView;
    private User mUser;

    private ChatRecyclerViewAdapter adapter;
    private List<Room> chats = new ArrayList<>();
    private List<String> chatLastMessageList = new ArrayList<>();
    private Chats1to1ViewModel viewModel;

    public static Chats1to1TabFragment newInstance(User user){
        Chats1to1TabFragment fragment = new Chats1to1TabFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        fragment.setArguments(args);
        return  fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats_1to1, container, false);
        ButterKnife.bind(this,v);
        mUser = getArguments().getParcelable(USER);
        createChatsRecyclerView();
        viewModelFunction();
        return v;
    }

    private void viewModelFunction(){
        viewModel = ViewModelProviders.of(this).get(Chats1to1ViewModel.class);
        viewModel.setmRoomList();
        viewModel.getRoomList().observe(this, new Observer<List<Room>>() {
            @Override
            public void onChanged(List<Room> rooms) {
                chats.clear();
                chats.addAll(rooms);
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void createChatsRecyclerView() {
        if(chats!=null)
        {
            adapter = new ChatRecyclerViewAdapter(getActivity(),chats, chatLastMessageList, mUser);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
        }
    }
}
