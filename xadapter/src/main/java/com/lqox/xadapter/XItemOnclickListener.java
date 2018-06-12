package com.lqox.xadapter;

import android.view.View;

public interface XItemOnclickListener<T> {
    void onclick(View view, int positon, T t);
}
