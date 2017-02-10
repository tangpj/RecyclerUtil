package com.tangpj.recyclerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tangpj.recyclerutils.divider.SimpleDecoration;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RadioGroup adapterGroup;
    private RadioGroup layoutManagerGroup;
    private RadioGroup dividerGroup;
    private RadioButton linearLayoutRadio;
    private RadioButton dividerLinesRadio;
    private RadioButton orientationVerticalRadio;

    private EditText intervalInput;
    private EditText spanInput;

    private int interval;
    private int span;
    private int orientation = OrientationHelper.VERTICAL;

    private TestSimpleFragment testSimpleFragment;
    private TestSecondaryFragment secondaryFragment;

    private RecyclerView.LayoutManager lm ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.main_nav);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        testSimpleFragment = new TestSimpleFragment();
        secondaryFragment = new TestSecondaryFragment();

        getSupportFragmentManager()
                .beginTransaction().replace(R.id.main_fragment_layout,testSimpleFragment)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.main_nav_img);


        initNavigationView(navigationView.getHeaderView(0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initNavigationView(View v){
        adapterGroup = (RadioGroup) v.findViewById(R.id.group_adapter);
        layoutManagerGroup = (RadioGroup) v.findViewById(R.id.group_layout_manager);
        dividerGroup = (RadioGroup) v.findViewById(R.id.group_divider);
        linearLayoutRadio = (RadioButton) v.findViewById(R.id.radio_layout_linear);
        dividerLinesRadio = (RadioButton) v.findViewById(R.id.radio_divider_lines);
        orientationVerticalRadio = (RadioButton) v.findViewById(R.id.radio_orientation_vertical);

        intervalInput = (EditText) v.findViewById(R.id.input_interval);
        spanInput = (EditText) v.findViewById(R.id.input_span);

        try{
            interval = Integer.valueOf(intervalInput.getText().toString());
            span = Integer.valueOf(spanInput.getText().toString());
        }catch (NumberFormatException e){
            interval = 1;
            span = 2;
            intervalInput.setText(interval +"");
            spanInput.setText(span + "");
            intervalInput.setFocusable(true);
            spanInput.setFocusable(true);
        }


        adapterGroup.setOnCheckedChangeListener(this);
        layoutManagerGroup.setOnCheckedChangeListener(this);
        dividerGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.simple_adapter:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_layout,testSimpleFragment)
                        .commit();
                linearLayoutRadio.setChecked(true);
                dividerLinesRadio.setChecked(true);
                orientationVerticalRadio.setChecked(true);
                span = 2;
                interval = 1;
                spanInput.setText(2 + "");
                intervalInput.setText(1 + "");


                break;
            case R.id.secondary_adapter:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_fragment_layout,secondaryFragment)
                        .commit();
                linearLayoutRadio.setChecked(true);
                dividerLinesRadio.setChecked(true);
                orientationVerticalRadio.setChecked(true);
                break;

            case R.id.radio_layout_linear:
                testSimpleFragment.setLayoutManager(new LinearLayoutManager(this,orientation,false));
                secondaryFragment.setLayoutManager(new LinearLayoutManager(this,orientation,false));

                break;

            case R.id.radio_layout_grid:
                testSimpleFragment.setLayoutManager(new GridLayoutManager(this,span,orientation,false));

                secondaryFragment.setLayoutManager(new GridLayoutManager(this,span,orientation,false));
                break;

            case R.id.radio_layout_staggered:
                testSimpleFragment.setLayoutManager(new StaggeredGridLayoutManager(span,orientation));
                secondaryFragment.setLayoutManager(new StaggeredGridLayoutManager(span,orientation));
                break;

            case R.id.radio_divider_lines:
                testSimpleFragment.setDivider(SimpleDecoration.newLinesDivider(this,interval));
                secondaryFragment.setDivider(SimpleDecoration.newLinesDivider(this,interval));
                break;
            case R.id.radio_divider_transparent:
                testSimpleFragment.setDivider(SimpleDecoration.newTransparentDivider(this,interval));
                secondaryFragment.setDivider(SimpleDecoration.newTransparentDivider(this,interval));
                break;
            case R.id.radio_divider_my:
                testSimpleFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.drawable.my_divider));
                secondaryFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.drawable.my_divider));
                break;

            case R.id.radio_divider_ic:
                testSimpleFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.mipmap.ic_launcher));
                secondaryFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.mipmap.ic_launcher));
                break;
        }
        lm = testSimpleFragment.getLayoutManager();
        drawerLayout.closeDrawers();
    }


    public void resetValue(View v){
        interval = Integer.valueOf(intervalInput.getText().toString());
        span = Integer.valueOf(spanInput.getText().toString());
        resetLayoutManager();
        resetInterval();


    }

    private void resetLayoutManager(){
        if (lm instanceof GridLayoutManager) {
            testSimpleFragment.setLayoutManager( new GridLayoutManager(this,span,orientation,false));
            secondaryFragment.setLayoutManager( new GridLayoutManager(this,span,orientation,false));
        }else if (lm instanceof LinearLayoutManager){
            testSimpleFragment.setLayoutManager(new LinearLayoutManager(this,orientation,false));
            secondaryFragment.setLayoutManager(new LinearLayoutManager(this,orientation,false));
        }else  if (lm instanceof StaggeredGridLayoutManager){
            testSimpleFragment.setLayoutManager(new StaggeredGridLayoutManager(span,orientation));
            secondaryFragment.setLayoutManager(new StaggeredGridLayoutManager(span,orientation));
        }
        lm = testSimpleFragment.getLayoutManager();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive()&&getCurrentFocus()!=null){
            if (getCurrentFocus().getWindowToken()!=null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private void resetInterval(){
        switch (dividerGroup.getCheckedRadioButtonId()){

            case R.id.radio_divider_lines:
                testSimpleFragment.setDivider(SimpleDecoration.newLinesDivider(this,interval));
                secondaryFragment.setDivider(SimpleDecoration.newLinesDivider(this,interval));
                break;
            case R.id.radio_divider_transparent:
                testSimpleFragment.setDivider(SimpleDecoration.newTransparentDivider(this,interval));
                secondaryFragment.setDivider(SimpleDecoration.newTransparentDivider(this,interval));
                break;
            case R.id.radio_divider_my:
                testSimpleFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.drawable.my_divider));
                secondaryFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.drawable.my_divider));
                break;

            case R.id.radio_divider_ic:
                testSimpleFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.mipmap.ic_launcher));
                secondaryFragment.setDivider(SimpleDecoration.newDrawableDivider(this,interval,R.mipmap.ic_launcher));
                break;
        }
    }
}
