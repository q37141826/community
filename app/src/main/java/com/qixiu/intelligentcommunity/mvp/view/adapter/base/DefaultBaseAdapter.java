package com.qixiu.intelligentcommunity.mvp.view.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qixiu.intelligentcommunity.mvp.view.adapter.base.interf.IAdapter;
import com.qixiu.intelligentcommunity.mvp.view.holder.base.DefaultBaseHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 * 该类可提供ListView,GridView等Adapter（不包含RecyclerView）
 */

public abstract class DefaultBaseAdapter<D> extends BaseAdapter implements IAdapter<DefaultBaseHolder> {


    List<D> datas = new ArrayList<>();

    @Override
    public Object getItem(int position) {
        return datas == null && datas.size() > 0 ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public final void addDatas(List<D> datas) {
        if (datas != null && datas.size() > 0) {
            this.datas.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();

    }

    public void refreshData(List<D> datas) {

        if (this.datas.size() > 0) {
            this.datas.clear();
        }
        this.datas.addAll(datas == null ? new ArrayList<D>() : datas);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DefaultBaseHolder baseDefaultHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    getLayoutId(), null);
            baseDefaultHolder = createViewHolder(convertView, parent.getContext(), getItemViewType(position));
            convertView.setTag(baseDefaultHolder);
        } else {
            baseDefaultHolder = (DefaultBaseHolder) convertView.getTag();
        }

        if (datas != null) {

            baseDefaultHolder.setData(datas.get(position));
        }

        return convertView;
    }


}
