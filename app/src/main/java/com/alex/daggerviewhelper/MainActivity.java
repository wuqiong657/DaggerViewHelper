package com.alex.daggerviewhelper;

import android.os.Bundle;
import android.view.View;

import org.alex.baseui.BaseActivity;

public class MainActivity extends BaseActivity {

    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 执行在 onCreateView 中
     * 通过 findView 初始主视图化控件
     * 初始化所有基础数据，
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreateData(Bundle savedInstanceState) {


    }

    /**
     * 处理点击事件，过滤掉  300 毫秒内连续 点击
     * 不可以注释掉  super.onClick(v);
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (R.id.bt_6 == v.getId()) {
            startActivity(FreeLayoutActivity.class);
        } else if (R.id.bt_7 == v.getId()) {
            startActivity(DraggingPanelActivity.class);
        }
    }
}
