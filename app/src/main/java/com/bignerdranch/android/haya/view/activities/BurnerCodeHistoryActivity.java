package com.bignerdranch.android.haya.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.android.haya.R;
import com.bignerdranch.android.haya.model.repo.User;
import com.bignerdranch.android.haya.model.repo.networking.burnerCodeHistoryNetworking.History;
import com.bignerdranch.android.haya.utils.dialouges.ToastUtils;
import com.bignerdranch.android.haya.view.adapters.BurnerHistoryAdapter;
import com.bignerdranch.android.haya.viewModel.BurnerCodeHistoryViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BurnerCodeHistoryActivity extends AppCompatActivity {
    public static final String USER = "USER";

    public static Intent newIntent(Context ctx, User user){
        Intent i = new Intent(ctx, BurnerCodeHistoryActivity.class);
        i.putExtra(USER, user);
        return i;
    }

    @BindView(R.id.burner_code_history_Recycler_view) RecyclerView mRecyclerView;

    private BurnerCodeHistoryViewModel mViewModel;
    private BurnerHistoryAdapter mAdapter;
    private List<History> mHistoryList = new ArrayList<>();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burner_code_history);
        ButterKnife.bind(this);

        mUser = getIntent().getParcelableExtra(USER);
        mViewModel = ViewModelProviders.of(this).get(BurnerCodeHistoryViewModel.class);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        mAdapter = new BurnerHistoryAdapter(this,mHistoryList,mViewModel,mUser);
        mRecyclerView.setAdapter(mAdapter);


        mViewModel.mHistoryData.observe(this, historyModel -> {
            mHistoryList.addAll(Arrays.asList(historyModel.getRooms()));
            mAdapter.notifyDataSetChanged();
        });
        mViewModel.mErrorData.observe(this, error -> {
            ToastUtils.showErrorToast(this,"Something goes wrong please try again");
        });
        mViewModel.mDeletedRoomId.observe(this, roomId ->{
            History history = new History();
            history.setId(roomId);
            int index = mHistoryList.indexOf(history);
            mHistoryList.remove(index);
            mAdapter.notifyDataSetChanged();
        });
        mViewModel.getHistroyBurnerCodes(mUser.getAccessToken());


    }


}
