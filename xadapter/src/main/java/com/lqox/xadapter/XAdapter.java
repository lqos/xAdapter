package com.lqox.xadapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class XAdapter<VH> extends RecyclerView.Adapter<XViewHolder> {
    protected Context context;
    protected List<VH> data;
    protected XItemOnclickListener xItemOnclickListener;
    private boolean isScrolling;//是否在滚动

    abstract int getLayoutId();


    public XAdapter(Context context, List<VH> data) {
        this.data = data;
        this.context = context;
        if (data == null) {
            data = new ArrayList<>(1);
        }
    }

    public abstract void convert(XViewHolder holder, VH item, int position, boolean isScrolling);

    @NonNull
    @Override
    public XViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new XViewHolder(context, View.inflate(context, getLayoutId(), parent));
    }

    @Override
    public final void onBindViewHolder(@NonNull XViewHolder holder, int position) {
        if (this.xItemOnclickListener != null) {
            this.xItemOnclickListener.onclick(holder.getmConvertView(), position, this.data.get(position));
        }
        convert(holder, this.data.get(position), position, isScrolling);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 插入一条
     *
     * @param vh
     * @param position
     */
    public void insert(VH vh, int position) {
        data.add(position, vh);
        notifyItemInserted(position);
    }

    /**
     * 删除一条
     *
     * @param position
     */
    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void appendAll(List<VH> list) {
        data.addAll(list);
        notifyItemRangeInserted(data.size() - 1, list.size());
    }

    public void setItemOnclickListener(XItemOnclickListener xItemOnclickListener) {
        this.xItemOnclickListener = xItemOnclickListener;
    }

    public void setData(List<VH> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
            }
        });
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.clearOnScrollListeners();
        setItemOnclickListener(null);
    }
}
