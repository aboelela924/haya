package com.bignerdranch.android.haya.view.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.CurrentUser;
import com.bignerdranch.android.haya.model.repo.Room;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.SubscribedRoomsNetworking.RoomOptionsCallBacks;
import com.bignerdranch.android.haya.view.adapters.SlidingChatRecyclerViewAdapter;
import com.bignerdranch.android.haya.viewModel.ChatRoomsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Chats1to1TabFragment extends Fragment implements RoomOptionsCallBacks {

    private static final String tag_chats1to1Tab = "Chats1to1Tab";
    private static final String USER = "USER";

    @BindView(R.id.recyclerview_chats_1to1) RecyclerView recyclerView;
    private User mUser;

    private SlidingChatRecyclerViewAdapter adapter;
    private ChatRoomsViewModel viewModel;

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
        mUser = CurrentUser.user;

        adapter = new SlidingChatRecyclerViewAdapter(getActivity(), mUser, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModelFunction();
    }

    private void viewModelFunction(){
        viewModel = ViewModelProviders.of(this).get(ChatRoomsViewModel.class);
        viewModel.set1to1RoomList();
        viewModel.observeNewChatCreated();
        viewModel.observewNewMessage();
        viewModel.observeOnRoomDeleted();
        viewModel.getRoomList().observe(this, rooms -> {
            List<Room> temp = new ArrayList<Room>();
            adapter.setRoomChats(temp);
            adapter.notifyDataSetChanged();
            adapter.setRoomChats(rooms);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel.stopNewChatObserver();
        viewModel.stopNewMessageObserver();
        viewModel.stopOnRoomDeleted();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.stopNewChatObserver();
        viewModel.stopNewMessageObserver();
        viewModel.stopOnRoomDeleted();
    }


    @Override
    public void deleteRoom(String room_id) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.confirmation_dialog);

        TextView textViewTitle = dialog.findViewById(R.id.confirmation_dialog_title_text_view);
        TextView textviewMessage = dialog.findViewById(R.id.confirmation_dialog_message_text_view);
        Button buttonCancel = dialog.findViewById(R.id.confirmation_dialog_cancel_button);
        Button buttonDelete = dialog.findViewById(R.id.confirmation_dialog_confirm_button);

        textViewTitle.setText("Delete Conversation");
        textviewMessage.setText("Are you sure you want to delete this conversation?");
        buttonDelete.setText("Delete");
        buttonCancel.setOnClickListener(view->{
            dialog.dismiss();
        });
        buttonDelete.setOnClickListener(view->{
            viewModel.disconnectRoom(room_id);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void setToPrivate(String room_id) {

    }
}
