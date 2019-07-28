package com.bignerdranch.android.haya.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.haya.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.view.activities.ChatActivity;
import com.bignerdranch.android.haya.viewModel.RandomRoomViewModel;

import butterknife.ButterKnife;

public class RandomChatWaitingInQueueFragment extends Fragment {
    private RandomRoomViewModel randomRoomViewModel;
    private Room mRandomRoom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random_chat_waiting_in_queue, container,false);

        viewModelFunction();
        return v;
    }

    private void viewModelFunction() {
        randomRoomViewModel = ViewModelProviders.of(this).get(RandomRoomViewModel.class);
        randomRoomViewModel.mRoomData.observe(this, new Observer<Room>() {
            @Override
            public void onChanged(Room randomRoom) {
                Intent intent = ChatActivity.newIntent(getActivity(), CurrentUser.user, randomRoom);
                startActivity(intent);
            }
        });
        randomRoomViewModel.joinQueue();
    }
}
