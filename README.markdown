一个富有动感的 Sheet,
先发两张图:
![原型.GIF](https://github.com/zzz40500/AndroidSweetSheet/raw/master/screenshot/Design.gif)

![效果图.gif](https://github.com/zzz40500/AndroidSweetSheet/raw/master/screenshot/SweetSheet.gif)


[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-AndroidSweetSheet-green.svg?style=flat)](https://android-arsenal.com/details/1/2271)

[![Android Gems](http://www.android-gems.com/badge/zzz40500/AndroidSweetSheet.svg?branch=master)](http://www.android-gems.com/lib/zzz40500/AndroidSweetSheet)

[![我的微博](http://www.easyicon.net/api/resizeApi.php?id=1164436&size=24)](http://weibo.com/u/5579192921/home?wvr=5)

###gradle      
/build.gradle
~~~
repositories {
    maven {
        url "https://jitpack.io"
    }
}
~~~
/app/build.gradle
~~~
compile 'com.github.zzz40500:AndroidSweetSheet:1.1.0'
~~~
###Usage:

~~~

// SweetSheet 控件,根据 rl 确认位置
mSweetSheet = new SweetSheet(rl);

//设置数据源 (数据源支持设置 list 数组,也支持从menu 资源中获取)
mSweetSheet.setMenuList(list);
//根据设置不同的 Delegate 来显示不同的风格.
mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
//根据设置不同Effect来设置背景效果:BlurEffect 模糊效果.DimEffect 变暗效果,NoneEffect 没有效果.
mSweetSheet.setBackgroundEffect(new BlurEffect(8));
//设置菜单点击事件
mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
    @Override
    public boolean onItemClick(int position, MenuEntity menuEntity1) {

        //根据返回值, true 会关闭 SweetSheet ,false 则不会.
        Toast.makeText(MainActivity.this, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
        return true;
    }
});
~~~
自定义扩展:
~~~

// SweetSheet 控件,根据 rl 确认位置
mSweetSheet3 = new SweetSheet(rl);
//定义一个 CustomDelegate 的 Delegate ,并且设置它的出现动画.
CustomDelegate customDelegate = new CustomDelegate(true,
        CustomDelegate.AnimationType.DuangLayoutAnimation);
View view = LayoutInflater.from(this).inflate(R.layout.layout_custom_view, null, false);
//设置自定义视图.
customDelegate.setCustomView(view);
//设置代理类
mSweetSheet3.setDelegate(customDelegate);
//因为使用了 CustomDelegate 所以mSweetSheet3中的 setMenuList和setOnMenuItemClickListener就没有效果了
view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        mSweetSheet3.dismiss();
    }
});
~~~


监听返回
~~~
    @Override
    public void onBackPressed() {
 
        if(mSweetSheet.isShow()){

            mSweetSheet.dismiss();
        }
        else{
            super.onBackPressed();
        }
    }

~~~
 

###v1.1(版本说明)
1. 去掉之前ViewPager 使用FragmentStatePagerAdapter 做为它的适配器.

* SweetSheet 不支持 LinearLayout 作为它的父控件.

* setMenuList 设置数据源支持从 List<MenuEntity>数组和Menu资源的填充.(Menu仅支持一级Menu,因为2级Menu我还没想好怎么展示).
* setBackgroundEffect(Effect  effect) 提供对背景效果的支持,目前提供3种风格:
BlurEffect:模糊效果,DimEffect 变暗效果,NoneEffect 没有效果.
你也可以继承Effect扩展背景的效果

* setDelegate 目前提供了三种风格
RecyclerViewDelegate,ViewPagerDelegate,CustomDelegate.

* CustomDelegate用于扩展类,你可以使用通过构造方法指定出现动画,通过setCustomView(View v)来设置你的自定义的布局.

* CustomDelegate中提供了4种类型:
DuangLayoutAnimation,DuangAnimation,
AlphaAnimation,Custom:
看名字大概就能知道它是什么效果.如果你对前3个效果不满意你就使用 Custom ,然后通过setCustomViewAnimation设置出现效果.

* CRImageView 内部实现了 Android 5.0上面的CircleReveal效果.使用方法:`CRImageView. circularReveal(int centerX, int centerY, float startRadius, float endRadius, long duration, Interpolator interpolator)`你可以在你的项目中用上它.


###FAQ
怎么使包括 actionBar 在内的背景也虚化? 
使用 一个 FrameLayout或者RelativeLayout 里面使用 ToolBar 来替代 ActionBar 将这个 FrameLayout 或者RelativeLayout作为SweetSheet 的父控件传递进去,





