package jt.com.JTHuaBan.Module.Main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import jt.com.JTHuaBan.Base.BaseActivity;
import jt.com.JTHuaBan.R;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String[] types;
    private String[] titles;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private NavigationView navigationView;
    private final int mDrawableList[] = {R.drawable.ic_loyalty_black_36dp, R.drawable.ic_camera_black_36dp,
            R.drawable.ic_message_black_36dp, R.drawable.ic_people_black_36dp};
    @Override
    protected String getTAG() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();//获取数据
        initView();//加载组件
        initDrawer(toolbar);
        initMenu();
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "我是Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getData() {
        types=getResources().getStringArray(R.array.type_array);
        titles=getResources().getStringArray(R.array.title_array);
        Log.d("text",titles.length+"");
    }

    private void initDrawer(Toolbar toolbar){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    //填充Menu
    private void initMenu() {
        //Log.d("text",titles.length+"");
        Menu menu=navigationView.getMenu();
//        menu.add(R.id.menu_group_type,1,Menu.NONE,titles[0])
//                .setIcon(mDrawableList[0])
//                .setCheckable(true);
        Log.d("text",titles.length+"");
        for(int i=0;i<titles.length;i++){
            Log.d("text",i+"");
            menu.add(R.id.menu_group_type,i+1,Menu.NONE,titles[i])
                    .setIcon(mDrawableList[0])
                    .setCheckable(true);
        }
        menu.getItem(0).setChecked(true);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
