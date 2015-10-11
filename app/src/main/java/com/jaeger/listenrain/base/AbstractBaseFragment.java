package com.jaeger.listenrain.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Jaeger on 15/9/15.
 * ListenRain
 */
public abstract class AbstractBaseFragment extends Fragment implements View.OnClickListener {
    protected Context context;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(setRootViewResId(), container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        beforeInitView();
        initView();
        setViewStatus();
    }

    protected abstract int setRootViewResId();

    protected abstract void beforeInitView();

    protected abstract void initView();

    protected abstract void setViewStatus();

    protected void showToastS(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    protected void showToastL(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
