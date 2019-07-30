package com.bignerdranch.android.haya.view.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.view.activities.ChatActivity;
import com.bignerdranch.android.haya.viewModel.RandomRoomViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RandomChatWaitingInQueueFragment extends Fragment {
    private RandomRoomViewModel randomRoomViewModel;
    private Room mRandomRoom;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_random_chat_waiting_in_queue, container,false);
        randomRoomViewModel = ViewModelProviders.of(this).get(RandomRoomViewModel.class);
        randomRoomViewModel.mRoomData.observe(this, randomRoom -> {
            Intent intent = ChatActivity.newIntent(getActivity(), CurrentUser.user, randomRoom);
            startActivity(intent);
        });
        randomRoomViewModel.joinQueue();


        ButterKnife.bind(this, v);
        return v;
    }
    @OnClick(R.id.button_leave_queue)
    public void leaveQueue()
    {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.confirmation_dialog);
        Button buttonLeave = dialog.findViewById(R.id.confirmation_dialog_confirm_button);
        Button buttonCancel = dialog.findViewById(R.id.confirmation_dialog_cancel_button);
        TextView textViewTitle = dialog.findViewById(R.id.confirmation_dialog_title_text_view);
        TextView textViewMessage = dialog.findViewById(R.id.confirmation_dialog_message_text_view);

        buttonLeave.setText("LEAVE");
        textViewTitle.setText("Leave Queue");
        textViewMessage.setText("Are you sure you want to leave the queue?");

        buttonLeave.setOnClickListener(view -> {
            randomRoomViewModel.leaveQueue();
            dialog.dismiss();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new RandomChatFragment()).commit();
        });
        buttonCancel.setOnClickListener(view ->{
            dialog.dismiss();
        });

        dialog.show();
    }
}
