package com.mingle.sweetpick;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mingle.utils.Blur;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/9.
 * @github: https://github.com/zzz40500
 */
public class BlurEffect implements Effect {

    private float Value;


    private BlurAsyncTask mBlurAsyncTask;

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

        if(mBlurAsyncTask!= null){
            mBlurAsyncTask.cancel(true);
        }

        mBlurAsyncTask= new BlurAsyncTask(vp, view, Blur.convertFromView(vp));
        mBlurAsyncTask.execute((int) Value);
    }


    /**
     * Async task used to process blur out of ui thread
     */
    private class BlurAsyncTask extends AsyncTask<Integer, Void, Bitmap> {

        private View mViewGroup;
        private ImageView mImageView;

        private Bitmap mOriginalBitmap;

        private Context mContext;

//        private long starTime;


        public BlurAsyncTask(View viewGroup, ImageView imageView, Bitmap originalBitmap) {
            mOriginalBitmap = originalBitmap;
            mViewGroup = viewGroup;
            mImageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mContext = mViewGroup.getContext();
            mImageView.setImageBitmap(null);
//            starTime=System.currentTimeMillis();
//
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            //process to the blue
            if (!isCancelled() && mOriginalBitmap != null) {

//                Bitmap b
//                        =Bitmap.createScaledBitmap(mOriginalBitmap, mOriginalBitmap.getWidth() / 4,
//                        mOriginalBitmap.getHeight() / 4, false);
//
//                if (b == null) {
//                    b = mOriginalBitmap;
//                }
                Bitmap bm = Blur.fastblur(mContext, mOriginalBitmap, params[0]);
                mOriginalBitmap.recycle();
                mOriginalBitmap = null;
                return bm;
            } else {
                return null;
            }


        }

        @Override
        @SuppressLint("NewApi")
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }

//            Log.e("tag",System.currentTimeMillis()-starTime+"ms");
            mViewGroup.destroyDrawingCache();
            mViewGroup.setDrawingCacheEnabled(false);
            mViewGroup = null;
            mImageView = null;
            mContext = null;
        }


    }

}
