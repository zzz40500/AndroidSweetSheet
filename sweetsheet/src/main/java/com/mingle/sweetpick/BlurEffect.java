package com.mingle.sweetpick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mingle.utils.Blur;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 *
 */
public class BlurEffect implements   Effect {

    private float Value;

    public BlurEffect(float value) {
        Value = value;
    }

    public void setValue(float value) {
        Value = value;
    }

    @Override
    public float getValue() {
        return Value;
    }

    @Override
    public void effect(ViewGroup vp, ImageView view) {
        new BlurAsyncTask(vp,view,Blur.convertFromView(vp)).execute((int)Value);
    }


    /**
     * Async task used to process blur out of ui thread
     */
    private class BlurAsyncTask extends AsyncTask<Integer, Void, Bitmap> {

        private View mViewGroup;
        private ImageView mImageView;
        
        private Bitmap mOriginalBitmap;

        private Context mContext;

//

        public BlurAsyncTask( View viewGroup, ImageView imageView,Bitmap originalBitmap) {
            mOriginalBitmap = originalBitmap;
            mViewGroup = viewGroup;
            mImageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContext=mViewGroup.getContext();
//
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            //process to the blue
            if (!isCancelled() && mOriginalBitmap != null) {
                Bitmap bm=Blur.fastblur(mContext, mOriginalBitmap, params[0]);
                mOriginalBitmap.recycle();
                mOriginalBitmap =null;
                return  bm;
            } else {
                return null;
            }


        }

        @Override
        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                mImageView.setImageBitmap(bitmap);
            }
            mViewGroup.destroyDrawingCache();
            mViewGroup.setDrawingCacheEnabled(false);
            mViewGroup = null;
            mImageView = null;
            mContext = null;
        }


    }

}
