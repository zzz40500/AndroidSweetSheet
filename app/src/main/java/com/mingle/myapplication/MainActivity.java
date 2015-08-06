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

        mSweetSheet = new SweetSheet(rl, SweetSheet.Type.RecyclerView);
        mSweetSheet.setMenuList(getSupportFragmentManager(), list);
        mSweetSheet2 = new SweetSheet(rl, SweetSheet.Type.Viewpager);
        mSweetSheet2.setMenuList(getSupportFragmentManager(),list);
        mSweetSheet.setBackgroundDim(0.8f);
        mSweetSheet2.setBackgroundDim(0.4f);
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position) {

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
            mSweetSheet.toggle();
            return true;
        }
        if (id == R.id.action_viewpager) {
            mSweetSheet2.toggle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
