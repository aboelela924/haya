package com.bignerdranch.android.haya.view.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.bignerdranch.android.haya.App;
import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.chatNetworking.SyncBody;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.ChatDB;
import com.bignerdranch.android.haya.model.repo.roomDatabase.classes.MessageDB;
import com.bignerdranch.android.haya.view.activities.JoinConversationActivity;
import com.bignerdranch.android.haya.view.adapters.ConversationsPagerAdapter;
import com.bignerdranch.android.haya.viewModel.ChatRoomsViewModel;
import com.bignerdranch.android.haya.viewModel.ChatViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationsFragment extends Fragment {
    private static final String USER = "USER";
    private User mUser;
    private ChatRoomsViewModel mViewModel;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.imageview_add_conversation) ImageView add_conversation;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout mRefreshLayout;
    public ConversationsFragment() {
        // Required empty public constructor
    }

    public static ConversationsFragment newInstance(User user){
        ConversationsFragment fragment = new ConversationsFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_conversations, container, false);
        ButterKnife.bind(this, v);
        mUser = getArguments().getParcelable(USER);
        mViewModel = ViewModelProviders.of(this).get(ChatRoomsViewModel.class);
        mViewModel.sync(mUser.getAccessToken());

        mRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mViewModel.sync(mUser.getAccessToken());
                        mRefreshLayout.setRefreshing(false);
                    }
                }
        );
        createConversationsTabLayout();
        return v;
    }

    private void createConversationsTabLayout() {
        TabLayout.Tab tab1to1Chats = tabLayout.newTab();
        tab1to1Chats.setText("Chats");

        TabLayout.Tab tabGroupChats = tabLayout.newTab();
        tabGroupChats.setText("Groups");

        TabLayout.Tab tabPrivateChats;
        if(false){ //Check database.
            tabPrivateChats= tabLayout.newTab();
            tabPrivateChats.setText("Private");
            tabPrivateChats.setIcon(R.drawable.lock_icon);
        }


        tabLayout.addTab(tab1to1Chats,0);
        tabLayout.addTab(tabGroupChats, 1);
        if(false){//Check database.
            tabLayout.addTab(tabPrivateChats,2);
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_chats_container, new Chats1to1TabFragment()).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:{
                        fragmentManager.beginTransaction().replace(R.id.fragment_chats_container, new Chats1to1TabFragment()).commit();
                        break;
                    }
                    case 1:{
                        fragmentManager.beginTransaction().replace(R.id.fragment_chats_container, new ChatsGroupFragment()).commit();
                        break;
                    }
                    case 2:{
                        fragmentManager.beginTransaction().replace(R.id.fragment_chats_container, new ChatsPrivateFragment()).commit();
                        break;
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @OnClick(R.id.imageview_add_conversation)
    public void joinConversation(){
        Intent i = JoinConversationActivity.newIntent(getActivity(), CurrentUser.user);
        startActivity(i);
    }
}
