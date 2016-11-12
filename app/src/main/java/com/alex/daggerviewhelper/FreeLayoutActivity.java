package com.alex.daggerviewhelper;

import android.os.Bundle;

import org.alex.baseui.BaseActivity;

/**
 * 作者：Alex
 * 时间：2016/11/11 16:09
 * 简述：
 */
public class FreeLayoutActivity extends BaseActivity {
    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_freelayout;
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
}
