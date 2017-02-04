package com.tangpj.recyclerutils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ISecondaryAdapter
 * @author create by Tang
 * @date date 17/2/4 下午4:38
 * @Description: TODO
 */

public interface ISecondaryAdapter<Group,Child> {

    int TYPE_GROUP = 99;
    int TYPE_CHILD = 100;

    RecyclerView.ViewHolder onCreateGroupHolder(ViewGroup parent);

    RecyclerView.ViewHolder onCreateChildHolder(ViewGroup parent);

    void onBindGroupHolder(RecyclerView.ViewHolder holder, int groupPosition);

    void onBindChildHolder(RecyclerView.ViewHolder holder, int position);

    void setGroupData(List<Group> groupData);

    void setChildData(int groupPosition,List<Child> childData);

    void setData(List<Group> groupData,List<Child> childData);

    void addItem(Group group,List<Child> childData);

    void addGroup(Group group);
}
