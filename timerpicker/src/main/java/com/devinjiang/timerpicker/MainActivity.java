package com.devinjiang.timerpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.devinjiang.view.PickerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    PickerView mMinute_pv;
    PickerView mSecond_Pv;
    List<String> mMinute_data;
    List<String> mSecond_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMinute_pv= (PickerView) findViewById(R.id.minute_pv);
        mSecond_Pv= (PickerView) findViewById(R.id.second_pv);
        mMinute_data=new ArrayList<String>();
        mSecond_data=new ArrayList<String>();

        for (int i=0;i<12;i++)
        {
            mMinute_data.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i=0;i<60;i++)
        {
            mSecond_data.add(i < 10 ? "0" + i : "" + i);
        }
        mMinute_pv.setData(mMinute_data);
        mMinute_pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(MainActivity.this, "选择了 " + text + " 分",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mSecond_Pv.setData(mSecond_data);
        mSecond_Pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Toast.makeText(MainActivity.this, "选择了 " + text + " 秒",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
