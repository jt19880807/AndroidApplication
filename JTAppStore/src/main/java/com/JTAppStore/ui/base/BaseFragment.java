package com.JTAppStore.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.JTAppStore.R;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public class BaseFragment extends Fragment {
    private String TAG=BaseFragment.class.getSimpleName();

    protected void openActivity(Class<?> cls){
        openActivity(cls,null);
    }
    protected void openActivity(Class<?> cls, Bundle bundle){
        Intent intent=new Intent(getActivity(),cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivity(String action){
        openActivity(action,null);
    }
    protected void openActivity(String action, Bundle bundle){
        Intent intent=new Intent(action);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

}
