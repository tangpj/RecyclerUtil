package com.tangpj.recyclerutils.internal;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @ClassName: ISecondaryAdapter
 * @author create by Tang
 * @date date 17/2/4 下午4:38
 * @Description: TODO
 */

public interface ISecondaryAdapter<Group,Child> {

    int SECONDARY_TYPE_GROUP = 1;
    int SECONDARY_TYPE_CHILD = 0;

    RecyclerView.ViewHolder onCreateGroupHolder(ViewGroup parent);

    RecyclerView.ViewHolder onCreateChildHolder(ViewGroup parent);

    void onBindGroupHolder(RecyclerView.ViewHolder holder, Group group);

    void onBindChildHolder(RecyclerView.ViewHolder holder, Child child);

    void setGroupData(List<Group> groupData);

    void setGroup(int groupPosition,Group group);

    void setChildData(int groupPosition,List<Child> childData);

    void setChild(int groupPosition,int childPosition,Child child);

    void setData(List<Group> groupData,List<List<Child>> childData);

    void addItem(Group group,List<Child> childData);

    void addGroup(Group group);

    List<Group> getGroupData();

    List<List<Child>> getChildData();

    void notifyGroupItem(int fromGroupIndex,int toGroupIndex);

    void notifyGroupItemMove(int fromGroupIndex,int toGroupIndex);
}
