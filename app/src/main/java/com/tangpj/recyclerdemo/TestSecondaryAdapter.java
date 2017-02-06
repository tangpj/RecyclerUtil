package com.tangpj.recyclerdemo;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tangpj.recyclerdemo.bean.FriendGroupBean;
import com.tangpj.recyclerdemo.bean.UserBean;
import com.tangpj.recyclerutils.SecondaryAdapter;

/**
 * @ClassName: TestSecondaryAdapter
 * @author create by Tang
 * @date date 17/2/5 下午4:27
 * @Description: TODO
 */

public class TestSecondaryAdapter extends SecondaryAdapter<FriendGroupBean,UserBean>{
    @Override
    public RecyclerView.ViewHolder onCreateGroupHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_group_item,parent,false);
        return new TitleHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateChildHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_sub_item,parent,false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindGroupHolder(RecyclerView.ViewHolder holder, FriendGroupBean friendGroupBean) {
        ((TitleHolder)holder).groupName.setText(friendGroupBean.title);
    }

    @Override
    public void onBindChildHolder(RecyclerView.ViewHolder holder, UserBean userBean) {
        ((FriendHolder) holder).name.setText(userBean.name);
        ((FriendHolder) holder).age.setText(userBean.age + "");
        ((FriendHolder) holder).sex.setText(userBean.sex);
    }

    private class TitleHolder extends RecyclerView.ViewHolder{

        TextView groupName;

        public TitleHolder(View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.title);
        }
    }

    private class FriendHolder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView name;
        private TextView age;
        private TextView sex;

        public FriendHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.friend_sub_avatar);
            name = (TextView) itemView.findViewById(R.id.friend_sub_name);
            age = (TextView) itemView.findViewById(R.id.friend_sub_age);
            sex = (TextView) itemView.findViewById(R.id.friend_sub_sex);
        }
    }
}
