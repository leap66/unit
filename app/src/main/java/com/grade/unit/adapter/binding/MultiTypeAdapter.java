package com.grade.unit.adapter.binding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.view.ViewGroup;

import com.grade.unit.adapter.binding.widget.BaseViewAdapter;
import com.grade.unit.adapter.binding.widget.BindingViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Super simple multi-type adapter using data-binding.
 *
 * @author markzhai on 16/8/23
 */
public class MultiTypeAdapter extends BaseViewAdapter<Object> {
  private ArrayList<Integer> mCollectionViewType;
  private ArrayMap<Integer, Integer> mItemTypeToLayoutMap;

  public MultiTypeAdapter(Context context) {
    super(context);
    mCollection = new ArrayList<>();
    mCollectionViewType = new ArrayList<>();
    mItemTypeToLayoutMap = new ArrayMap<>();
  }

  @NonNull
  @SuppressWarnings("unchecked")
  @Override
  public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new BindingViewHolder(
        DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
  }

  public void addViewTypeToLayoutMap(Integer viewType, Integer layoutRes) {
    mItemTypeToLayoutMap.put(viewType, layoutRes);
  }

  @Override
  public int getItemViewType(int position) {
    return mCollectionViewType.get(position);
  }

  public void set(List viewModels, int viewType) {
    mCollection.clear();
    mCollectionViewType.clear();
    addAll(viewModels, viewType);
  }

  public void set(List viewModels, MultiViewType viewType) {
    mCollection.clear();
    mCollectionViewType.clear();
    addAll(viewModels, viewType);
  }

  public void add(Object viewModel, int viewType) {
    mCollection.add(viewModel);
    mCollectionViewType.add(viewType);
    notifyItemInserted(0);
  }

  public void add(int position, Object viewModel, int viewType) {
    mCollection.add(position, viewModel);
    mCollectionViewType.add(position, viewType);
    notifyItemInserted(position);
  }

  public void addAll(List viewModels, int viewType) {
    mCollection.addAll(viewModels);
    for (int i = 0; i < viewModels.size(); ++i)
      mCollectionViewType.add(viewType);
    notifyDataSetChanged();
  }

  public void addAll(List viewModels, MultiViewType multiViewType) {
    mCollection.addAll(viewModels);
    for (int i = 0; i < viewModels.size(); ++i) {
      mCollectionViewType.add(multiViewType.getViewType(viewModels.get(i)));
    }
    notifyDataSetChanged();
  }

  public void remove(int position) {
    mCollectionViewType.remove(position);
    super.remove(position);
  }

  public void clear() {
    mCollectionViewType.clear();
    super.clear();
  }

  @LayoutRes
  private int getLayoutRes(int viewType) {
    return mItemTypeToLayoutMap.get(viewType);
  }

  public interface MultiViewType {
    int getViewType(Object item);
  }
}
