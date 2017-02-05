package com.tangpj.recyclerutils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SecondaryAdapter
 * @author create by Tang
 * @date date 17/2/4 下午4:34
 * @Description: 可展开的RecyclerView Adapter
 * @param <Group> 一级列表数据实体类
 * @param <Child> 二级列表数据实体类
 */

public abstract class SecondaryAdapter<Group,Child> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ISecondaryAdapter<Group,Child> {

    private static final String TAG = "SecondaryAdapter";

    private List<Group> groupData = new ArrayList<>();
    private List<List<Child>> childData = new ArrayList();

    //用于记录一级列表的在RecyclerView中的坐标
    private List<Integer> groupPositions = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SECONDARY_TYPE_GROUP){
            return onCreateGroupHolder(parent);
        }
        return onCreateChildHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == SECONDARY_TYPE_GROUP){
            onBindGroupHolder(holder,groupData.get(getGroupIndex(position)));
            return;
        }
        int groupIndex = getGroupIndex(position);
        onBindChildHolder(holder,childData.get(groupIndex).get(getChildIndex(groupIndex,position)));
    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateGroupHolder(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateChildHolder(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public void onBindGroupHolder(RecyclerView.ViewHolder holder, Group group) {
//
//    }
//
//    @Override
//    public void onBindChildHolder(RecyclerView.ViewHolder holder, Child child){
//
//    }

    @Override
    public int getItemCount() {
        if (groupData == null){
            return 0;
        }
        int childCount = 0;
        for (int i = 0; i < childData.size(); i++){
            if (groupPositions.size() < groupData.size()){
                groupPositions.add(childCount + i);
            }
            Log.d(TAG, "getItemCount: group index = " + groupPositions.get(i));
            List<Child> child = childData.get(i);
            childCount += child == null ? 0 : child.size();
        }
        return groupData.size() + childCount;
    }

    @Override
    public int getItemViewType(int position) {
        for (int index : groupPositions){
            if (index == position){
                return SECONDARY_TYPE_GROUP;
            }
        }
        return SECONDARY_TYPE_CHILD;
    }

    /**
     * @Method: setGroupData
     * @author create by Tang
     * @date date 17/2/4 下午5:44
     * @Description: 重新设置所有一级item
     * @param groupData 新的一级列表
     *                  当groupData的size小于childData size时
     *                  多于的childData会被删除
     */
    @Override
    public void setGroupData(List<Group> groupData) {
        this.groupData.clear();
        this.groupData.addAll(groupData);
        notifyGroupItem(0,this.groupData.size() - 1);
        if (groupData.size() > childData.size()){
            childData.subList(groupData.size() - 1,childData.size() - 1);
        }
    }

    /**
     * @Method: setGroup
     * @author create by Tang
     * @date date 17/2/5 上午11:11
     * @Description: 设置指定的一级列表数据
     * @param groupPosition 需要设置的一级列表坐标
     * @param group 一级列表的数据
     */
    @Override
    public void setGroup(int groupPosition, Group group) {
        groupData.set(groupPosition,group);
        notifyItemChanged(getGroupPosition(groupPosition));
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
        if (groupPosition > groupData.size()){
            throw new IllegalArgumentException("setChildData: "
                    + outOfBoundsMsg(groupPosition,groupData.size()));
        }
        this.childData.set(groupPosition,childData);
        int groupRealPosition = getGroupPosition(groupPosition);
        notifyItemMoved(groupRealPosition, groupRealPosition + childData.size());
    }

    /**
     * @Method: setChild
     * @author create by Tang
     * @date date 17/2/5 上午11:13
     * @Description: 设置指定的一级列表中二级列表的指定数据
     * @param groupPosition 一级列表坐标
     * @param childPosition 二级列表坐标
     * @param child 需要设置的二级列表数据
     */
    @Override
    public void setChild(int groupPosition, int childPosition, Child child) {
        childData.get(groupPosition).set(childPosition,child);
        notifyItemChanged(getGroupPosition(groupPosition) + childPosition + 1);
    }

    /**
     * @Method: setData
     * @author create by Tang
     * @date date 17/2/4 下午5:45
     * @Description: 同时设置所有数据这个方法限制只能根据数组的下标来分配
     *               一级列表与二级列表的关系，无法实现精确的设置
     * @param groupData 一级列表数组
     * @param childData 二级列表数组
     *                  childData size不能大于groupData size
     */
    @Override
    public void setData(List<Group> groupData,List<List<Child>> childData) {
        this.groupData.clear();
        this.childData.clear();
        this.groupData.addAll(groupData);
        this.childData.addAll(childData);
        notifyDataSetChanged();
    }

    /**
     * @Method: addItem
     * @author create by Tang
     * @date date 17/2/5 上午10:14
     * @Description: 新增数据项
     * @param group 一级列表数据
     * @param childData 对应的二级列表集合
     */
    @Override
    public void addItem(Group group, List<Child>  childData) {
        groupData.add(group);
        this.childData.add(groupData.size() - 1,childData);
        int realGroupPosition = getGroupPosition(groupData.size() - 1);
        notifyItemMoved(realGroupPosition,realGroupPosition + childData.size());
    }

    /**
     * @Method: addGroup
     * @author create by Tang
     * @date date 17/2/5 上午10:16
     * @Description: 增加
     */
    @Override
    public void addGroup(Group group) {
        groupData.add(group);
        notifyItemChanged(getGroupPosition(groupData.size() - 1));
    }

    /**
     * @Method: notifyGroupItem
     * @author create by Tang
     * @date date 17/2/5 上午10:26
     * @Description: 更新指定范围的一级列表
     */
    public void notifyGroupItem(int fromGroupIndex,int toGroupIndex){
        for (int i = fromGroupIndex; i <= toGroupIndex;i++){
            notifyItemChanged(getGroupPosition(i));
        }
    }

    /**
     * @Method: notifyGroupItemMove
     * @author create by Tang
     * @date date 17/2/5 上午10:24
     * @Description: 更新新指定一级列表间的所有数据(包括对应一级列表下的二级列表)
     * @param fromGroupIndex 起始一级列表
     * @param toGroupIndex 最终一级列表
     */
    public void notifyGroupItemMove(int fromGroupIndex,int toGroupIndex){
        for (int i = fromGroupIndex; i <= toGroupIndex; i++){
            notifyItemMoved(getGroupPosition(fromGroupIndex)
                    ,getGroupPosition(toGroupIndex) + childData.get(toGroupIndex).size());
        }
    }

    /**
     * @Method: getGroupPosition
     * @author create by Tang
     * @date date 17/2/4 下午5:38
     * @Description: 获取group在RecyclerView中的真实位置
     * @param groupIndex Group在groupData中的具体索引
     * @return RecyclerView真实位置

     */
    private int getGroupPosition(int groupIndex){
        int position = 0;
        for (int i = 0; i < groupIndex ;i++){
            position += childData.get(i).size();
            position ++;
        }
        return position;
    }

    /**
     * @Method: getGroupIndex
     * @author create by Tang
     * @date date 17/2/5 上午11:51
     * @Description: 根据RecyclerView中的位置计算item所在的一级列表
     * @param position item在RecyclerView中的位置
     * @return position对应的一级列表(如position恰好为一级列表,则该position对应的一级列表就是它自己)
     */
    private int getGroupIndex(int position){
        if (position > getItemCount() - 1){
            throw new IllegalArgumentException("getGroupIndex : "
                    + "position: " + position + ", Item count: " + getItemCount());
        }
        for (int i = 0; i < groupPositions.size(); i++){
            if (groupPositions.get(i) >= position){
                return i;

            }
        }
        return -1;
    }

    private int getChildIndex(int groupIndex,int position){
        return position - this.groupPositions.get(groupIndex) - 1;
    }

    private String outOfBoundsMsg(int index,int size) {
        return "Index: " + index + ", Size: "+  size;
    }


}
