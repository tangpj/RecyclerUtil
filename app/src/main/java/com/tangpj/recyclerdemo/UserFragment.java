package com.tangpj.recyclerdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangpj.recyclerutils.RecyclerViewDivider;

import java.util.Arrays;

/**
 * @ClassName: UserFragment
 * @author create by Tang
 * @date date 17/1/23 下午12:09
 * @Description: TODO
 */

public class UserFragment extends Fragment{

    private UserAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        init(view);
        return view;
    }

    private void init(View view){

        RecyclerView userList = (RecyclerView) view.findViewById(R.id.fragment_user_list);

        adapter = new UserAdapter();
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        GridLayoutManager gm = new GridLayoutManager(getActivity(),3);
        StaggeredGridLayoutManager sm = new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL);
        userList.setLayoutManager(sm);

        userList.setAdapter(adapter);
        adapter.setData(Arrays.asList(User.values()));
        userList.addItemDecoration(RecyclerViewDivider.newLinesDivider(getActivity(),16));
    }


}
