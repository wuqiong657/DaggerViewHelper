package com.alex.daggerviewhelper;

import android.os.Bundle;

import org.alex.baseui.BaseActivity;

/**
 * 作者：Alex
 * 时间：2016年11月12日
 * 简述：
 * 启动者：
 */

public class DraggingPanelActivity extends BaseActivity {
    /**
     * 配置 布局文件的 资源 id
     */
    @Override
    public int getLayoutResId() {
        return R.layout.activity_dragging_panel;
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
