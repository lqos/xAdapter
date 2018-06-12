package com.lqox.xadapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class XViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public XViewHolder(Context context, View itemView) {
        super(itemView);
        mConvertView = itemView;
        context = mContext;
        mViews = new SparseArray<>();
    }

    /**
     * 通过id获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    public XViewHolder setText(@IdRes int viewId, String data) {
        TextView tv = getView(viewId);
        tv.setText(data);
        return this;
    }

    public XViewHolder setImageResource(@IdRes int viewId, @DrawableRes int data) {
        ImageView tv = getView(viewId);
        tv.setImageResource(data);
        return this;
    }

    public XViewHolder setImageResource(@IdRes int viewId, Drawable drawable) {
        View tv = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tv.setBackground(drawable);
        } else {
            tv.setBackgroundDrawable(drawable);
        }
        return this;
    }


    /**
     * @param viewId
     * @param visibility
     * @return
     */
    public XViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View tv = getView(viewId);
        tv.setVisibility(visibility);
        return this;
    }

    public XViewHolder setOnClickListener(View.OnClickListener l, int... ViewId) {
        for (int id : ViewId) {
            try {
                getView(id).setOnClickListener(l);
            } catch (Exception e) {
                throw new Error("not found the view'id --->" + id);
            }
        }
        return this;
    }


}
