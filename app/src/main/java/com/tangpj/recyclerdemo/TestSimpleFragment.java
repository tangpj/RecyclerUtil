package com.tangpj.recyclerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangpj.recyclerdemo.model.UserModel;
import com.tangpj.recyclerviewutils.decoration.SimpleDecoration;

import java.util.Arrays;

/**
 * @ClassName: TestSimpleFragment
 * @author create by Tang
 * @date date 17/1/23 下午12:09
 * @Description: TODO
 */

public class TestSimpleFragment extends Fragment {

    private RecyclerView list;
    private TestSimpleAdapter adapter;

    private RecyclerView.LayoutManager lm;
    private RecyclerView.ItemDecoration decoration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        init(view);
        return view;
    }

    private void init(View view){

        list = (RecyclerView) view.findViewById(R.id.fragment_list);

        adapter = new TestSimpleAdapter();
        lm = new LinearLayoutManager(getActivity());
        if (decoration == null){
            decoration = SimpleDecoration.newLinesDivider(getActivity());
        }

        list.setLayoutManager(lm);
        list.setAdapter(adapter);
        list.addItemDecoration(decoration);

        adapter.setData(Arrays.asList(UserModel.values()));
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        if (list != null){
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
        }
        lm = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager(){
        return list.getLayoutManager();
    }

    public void setDivider(RecyclerView.ItemDecoration decoration){
        if (list != null){
            list.removeItemDecoration(this.decoration);
            list.addItemDecoration(decoration);
        }
        this.decoration = decoration;
    }

}
