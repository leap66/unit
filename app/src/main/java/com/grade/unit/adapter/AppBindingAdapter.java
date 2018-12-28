package com.grade.unit.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.grade.unit.R;
import com.tonicartos.superslim.LayoutManager;

/**
 * AppBindingAdapter : 用于处理自定义的xml中的属性操作
 * <p>
 * </> Created by ylwei on 2018/2/26.
 */
public class AppBindingAdapter {

  @BindingAdapter("resUrl")
  public static void loadImageUrl(ImageView view, Object resourceUrl) {
    RequestOptions options = new RequestOptions();
    options.placeholder(R.mipmap.icon_placeholder);
    options.error(R.mipmap.icon_placeholder);
    Glide.with(view.getContext()).load(resourceUrl).apply(options).into(view);
  }

  @BindingAdapter("adapter")
  public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
    view.setAdapter(adapter);
  }

  @BindingAdapter("adapterAnd")
  public static void setAdapterAnd(RecyclerView view, RecyclerView.Adapter adapter) {
    view.setAdapter(adapter);
    view.setLayoutManager(new LinearLayoutManager(view.getContext()));
  }

  @BindingAdapter("adapterSub")
  public static void setAdapterSub(RecyclerView view, RecyclerView.Adapter adapter) {
    view.setAdapter(adapter);
    view.setLayoutManager(new LayoutManager(view.getContext()));
  }
}
