package com.bignerdranch.android.haya.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.haya.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.view.activities.JoinConversationActivity;
import com.bignerdranch.android.haya.view.adapters.ConversationsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConversationsFragment extends Fragment {
    private static final String USER = "USER";
    private User mUser;
    @BindView(R.id.conversations_view_pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.imageview_add_conversation) ImageView add_conversation;
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
        ButterKnife.bind(this,v);
        mUser = getArguments().getParcelable(USER);
        createConversationsTabLayout();
        return v;
    }

    private void createConversationsTabLayout() {
        List<String> mTitles = new ArrayList<String>();
        List<Fragment> mFragments = new ArrayList<Fragment>();

        mFragments.add(Chats1to1TabFragment.newInstance(mUser));
        mFragments.add(new ChatsGroupFragment());

        mTitles.add("CHATS");
        mTitles.add("GROUPS");

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ConversationsPagerAdapter adapter = new ConversationsPagerAdapter(fragmentManager,mTitles,mFragments);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.imageview_add_conversation)
    public void joinConversation(){
        Intent i = JoinConversationActivity.newIntent(getActivity(), CurrentUser.user);
        startActivity(i);
    }
}
