package com.mingle.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mingle.entity.MenuEntity;
import com.mingle.listener.SingleClickListener;
import com.mingle.sweetpick.SweetSheet;
import com.mingle.sweetsheet.R;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import java.util.List;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuRVAdapter extends RecyclerView.Adapter<MenuRVAdapter.MenuVH> {

    List<MenuEntity> mDataList;
    private boolean mIsAnimation;
    private int mItemLayoutId;

    public MenuRVAdapter(List<MenuEntity> dataLis, SweetSheet.Type type) {
        mDataList = dataLis;


        if (type == SweetSheet.Type.RecyclerView) {
            mItemLayoutId = R.layout.item_horizon_rv;
        } else {
            mItemLayoutId = R.layout.item_vertical_rv;
        }
    }

    @Override
    public MenuVH onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(mItemLayoutId, null, false);
        return new MenuVH(view);
    }

    @Override
    public void onBindViewHolder(MenuVH menuVH, int i) {

        menuVH.itemRl.setOnClickListener(mSingleClickListener);
        menuVH.itemRl.setTag(menuVH.getAdapterPosition());
        MenuEntity menuEntity = mDataList.get(i);
        if (menuEntity.iconId != 0) {

            menuVH.iv.setVisibility(View.VISIBLE);
            menuVH.iv.setImageResource(menuEntity.iconId);
        } else if (menuEntity.icon != null) {

            menuVH.iv.setVisibility(View.VISIBLE);
            menuVH.iv.setImageDrawable(menuEntity.icon);

        } else {
            menuVH.iv.setVisibility(View.GONE);
        }
        menuVH.nameTV.setText(menuEntity.title);
        menuVH.nameTV.setTextColor(menuEntity.titleColor);
        if (mIsAnimation) {
            animation(menuVH);
        }
    }

    private void animation(MenuVH menuVH) {

        ViewHelper.setAlpha(menuVH.itemView, 0);

        ViewHelper.setTranslationY(menuVH.itemView, 300);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menuVH.itemView, "translationY", 500, 0);
        translationY.setDuration(300);
        translationY.setInterpolator(new OvershootInterpolator(1.6f));
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(menuVH.itemView, "alpha", 0, 1);
        alphaIn.setDuration(100);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationY, alphaIn);
        animatorSet.setStartDelay(30 * menuVH.getAdapterPosition());
        animatorSet.start();
    }

    @Override
    public int getItemCount() {

        return mDataList.size();
    }

    public void notifyAnimation() {
        mIsAnimation = true;
        this.notifyDataSetChanged();
    }

    public void notifyNoAimation() {
        mIsAnimation = false;
        this.notifyDataSetChanged();
    }


    static class MenuVH extends RecyclerView.ViewHolder {

        public ImageView iv;
        public TextView nameTV;
        public RelativeLayout itemRl;

        public MenuVH(View itemView) {
            super(itemView);

            iv = (ImageView) itemView.findViewById(R.id.iv);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            itemRl = (RelativeLayout) itemView.findViewById(R.id.itemRl);


        }
    }

    public AdapterView.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    private SingleClickListener mSingleClickListener = new SingleClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(null, v, position, position);
            }

        }
    });
}
