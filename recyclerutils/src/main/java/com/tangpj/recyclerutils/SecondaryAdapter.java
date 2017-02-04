package com.tangpj.recyclerutils;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SecondaryAdapter
 * @author create by Tang
 * @date date 17/2/4 下午4:34
 * @Description: 可展开的RecyclerView Adapter
 * @param <Group> 一级列表数据实体类
 * @param <Child> 二级列表数据实体类
 */

public class SecondaryAdapter<Group,Child> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ISecondaryAdapter<Group,Child> {

    private List<Group> groupData = new ArrayList<>();
    private List<List<Child>> childData = new ArrayList();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (groupData == null || groupData.size() == 0){
            return 0;
        }
        int childCount = 0;
        for (List<Child> child: childData){
            childCount += child == null ? 0 : child.size();
        }
        return groupData.size() + childCount;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateGroupHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateChildHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindGroupHolder(RecyclerView.ViewHolder holder, int groupPosition) {

    }

    @Override
    public void onBindChildHolder(RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * @Method: setGroupData
     * @author create by Tang
     * @date date 17/2/4 下午5:44
     * @Description: 重新设置一级item
     * 不会影响二级子item
     */
    @Override
    public void setGroupData(List<Group> groupData) {
        this.groupData.clear();
        this.groupData.addAll(groupData);
        notifyGroupItem(groupData);
    }

    /**
     * @Method: setChildData
     * @author create by Tang
     * @date date 17/2/4 下午5:45
     * @Description: 重设指定子item列表的值
     * @param groupPosition 子item对应的一级item坐标
     *                      如不存在该坐标，则抛出异常
     */
    @Override
    public void setChildData(int groupPosition,List<Child> childData) {
        if (groupPosition >= groupData.size()){
            throw new IllegalArgumentException("can not find this group");
        }
        this.childData.set(groupPosition,childData);
        int groupRealPosition = getGroupRealPosition(groupPosition);
        notifyItemMoved(groupRealPosition, groupRealPosition + childData.size());
    }

    /**
     * @Method:
     * @author create by Tang
     * @date date 17/2/4 下午5:45
     * @Description: TODO
     */
    @Override
    public void setData(List<Group> groupData,List<Child> childData) {

    }

    @Override
    public void addItem(Group group, List<Child> childData) {

    }

    @Override
    public void addGroup(Group group) {

    }

    /**
     * @Method: getGroupRealPosition
     * @author create by Tang
     * @date date 17/2/4 下午5:38
     * @Description: 获取group在RecyclerView中的真实位置
     * @return RecyclerView真实位置
     */
    private int getGroupRealPosition(int groupPosition){
        int position = 0;
        for (int i = 0; i < groupPosition ;i++){
            position += childData.get(i).size();
            position ++;
        }
        return position;
    }

    private void notifyGroupItem(List<Group> groupData){
        for (int i = 0; i < groupData.size();i++){
            notifyItemChanged(getGroupRealPosition(i));
        }
    }


}
