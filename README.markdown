一个富有动感的 Sheet,
[github](https://github.com/zzz40500/AndroidSweetSheet)
先发两张图:
![原型.GIF](http://upload-images.jianshu.io/upload_images/166866-deed43338a7c14d8.GIF?imageView2/2/w/1240)

![效果图.gif](http://upload-images.jianshu.io/upload_images/166866-535117a26cd1e2dd.gif?imageView2/2/w/1240)
真机效果更赞哦.

###Usage:

MainActivity.class
~~~
    //添加假数据

    MenuEntity menuEntity=new MenuEntity();
    menuEntity.resId=R.drawable.ic_account_child;
    menuEntity.name="QQ";
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);
    list.add(menuEntity);

    //根据Type生成对应的样式 SweetSheet 控件,根据 rl 确认位置
    mSweetSheet = new SweetSheet(rl, SweetSheet.Type.RecyclerView);
    //设置数据源 FragmentManager 在样式为Viewpager 是必须的, RecyclerView样式可以为 null, 不影响运行
    mSweetSheet.setMenuList(getSupportFragmentManager(), list);
    //设置背景灰度
    mSweetSheet.setBackgroundDim(0.8f);

    //设置点击事件
    mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
    @Override
    public boolean onItemClick(int position) {
    //根据返回值, true 会关闭 SweetSheet ,false 则不会.
            Toast.makeText(MainActivity.this,list.get(position).name+"             "+position,Toast.LENGTH_SHORT).show();
        return true;
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
###Note:
1. 目前SweetSheet支持的样式2种:SweetSheet.Type.RecyclerView 和 SweetSheet.Type.Viewpager
* 关于:SweetSheet.setMenuList(FragmentManager fm, LIst<MenuEntity> list);
第一个参数是用了做ViewPager的适配器使用的,类型SweetSheet.Type.RecyclerView 可以传null;
* 暂不支持使用LinearLayout 作为mSweetSheet 的父控件.

###Future (1.1):
1. 支持从menu中创建.
* 优化SweetSheet.Type.RecyclerView 的体验.
* gradle的支持
* 支持简单的扩展







