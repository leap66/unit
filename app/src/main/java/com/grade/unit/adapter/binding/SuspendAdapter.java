package com.grade.unit.adapter.binding;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.grade.unit.adapter.binding.widget.BindingViewHolder;
import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;

/**
 * SuspendAdapter :
 * <p>
 * </> Created by ylwei on 2018/4/27.
 */
public class SuspendAdapter extends MultiTypeAdapter {

  public SuspendAdapter(Context context) {
    super(context);
  }

  @Override
  public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    View view = holder.itemView;
    LayoutManager.LayoutParams lp = (LayoutManager.LayoutParams) view.getLayoutParams();
    lp.setSlm(LinearSLM.ID);
    lp.setFirstPosition(position);
    for (int i = position; i >= 0; i--) {
      if (getItemViewType(i) == 101) {
        lp.setFirstPosition(i);
        break;
      }
    }
    view.setLayoutParams(lp);
  }
}
