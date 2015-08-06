package com.mingle.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.SweetSheet;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SweetSheet mSweetSheet;
    private SweetSheet mSweetSheet2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rl);

        final ArrayList<MenuEntity> list=new ArrayList<>();


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

        mSweetSheet2 = new SweetSheet(rl, SweetSheet.Type.Viewpager);
        mSweetSheet2.setMenuList(getSupportFragmentManager(),list);
        //设置背景灰度
        mSweetSheet.setBackgroundDim(0.8f);
        mSweetSheet2.setBackgroundDim(0.4f);

        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position) {

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Toast.makeText(MainActivity.this,list.get(position).name+"  "+position,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mSweetSheet2.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position) {

                Toast.makeText(MainActivity.this,list.get(position).name+"  "+position,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if(mSweetSheet.isShow() || mSweetSheet2.isShow()){
            if(mSweetSheet.isShow()){
                mSweetSheet.dismiss();
            }
            if(mSweetSheet2.isShow()){
                mSweetSheet2.dismiss();
            }
        }else{
            super.onBackPressed();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_recyclerView) {
            if(mSweetSheet2.isShow()){
                mSweetSheet2.dismiss();
            }
            mSweetSheet.toggle();
            return true;
        }
        if (id == R.id.action_viewpager) {
            if(mSweetSheet.isShow()){
                mSweetSheet.dismiss();
            }
            mSweetSheet2.toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
