package com.tangpj.recyclerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tangpj.recyclerdemo.bean.FriendGroupBean;
import com.tangpj.recyclerdemo.bean.UserBean;
import com.tangpj.recyclerutils.divider.SimpleDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: TestSecondaryFragment
 * @author create by Tang
 * @date date 17/2/5 下午4:26
 * @Description: TODO
 */
public class TestSecondaryFragment extends Fragment{

    private RecyclerView list;
    private TestSecondaryAdapter adapter;
    private List<FriendGroupBean> data;

    private RecyclerView.LayoutManager lm;
    private RecyclerView.ItemDecoration decoration;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        data = initData();
        init(view);
        return view;
    }

    private void init(View view){

        list = (RecyclerView) view.findViewById(R.id.fragment_list);

        adapter = new TestSecondaryAdapter();
        lm = new LinearLayoutManager(getActivity());
        if (decoration == null){
            decoration = SimpleDecoration.newLinesDivider(getActivity());

        }

        if (list.getLayoutManager() == null){
            list.setLayoutManager(lm);
        }

        list.setAdapter(adapter);
        list.addItemDecoration(decoration);

        List<List<UserBean>> userList = new ArrayList<>();
        for (FriendGroupBean group : data){
            userList.add(group.data);
        }
        adapter.setData(data,userList);
    }

    private List initData(){
        List<UserBean> family = new ArrayList<>();
        List<UserBean> colleague = new ArrayList<>();
        List<UserBean> friend = new ArrayList<>();
        family.add(new UserBean("爸爸",48,0));
        family.add(new UserBean("妈妈",45,0));
        family.add(new UserBean("姐姐",25,0));
        family.add(new UserBean("爷爷",67,0));
        family.add(new UserBean("奶奶",65,0));
        family.add(new UserBean("弟弟",16,0));
        family.add(new UserBean("妹妹",15,0));
        colleague.add(new UserBean("小涨",25,0));
        colleague.add(new UserBean("小李",25,0));
        colleague.add(new UserBean("小名",25,0));
        colleague.add(new UserBean("张校长",25,0));
        colleague.add(new UserBean("章子怡",25,0));
        colleague.add(new UserBean("周杰伦",25,0));
        colleague.add(new UserBean("小丽",25,0));
        friend.add(new UserBean("大红",25,R.drawable.my_divider));
        friend.add(new UserBean("大明",24,0));
        friend.add(new UserBean("大头",25,0));
        List<FriendGroupBean> friendGroups = new ArrayList<>();
        friendGroups.add(new FriendGroupBean("家人",family));
        friendGroups.add(new FriendGroupBean("同事",colleague));
        friendGroups.add(new FriendGroupBean("朋友",friend));
        friendGroups.add(new FriendGroupBean("同学",null));
        return friendGroups;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        if (list != null){
            list.setLayoutManager(layoutManager);
            list.setAdapter(adapter);
        }
        this.lm = layoutManager;
    }

    public void setDivider(RecyclerView.ItemDecoration decoration){
        if (list != null){
            list.removeItemDecoration(this.decoration);
            list.addItemDecoration(decoration);
        }
        this.decoration = decoration;
    }
}
