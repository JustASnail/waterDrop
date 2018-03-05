package com.drops.waterdrop.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

import static com.drops.waterdrop.ui.mine.fragment.CollectionFragment.ARG_TITLE;

/**
 * 懒加载读取网络， 节省流量。
 * Created by ended on 2017/4/7.
 */

public abstract class LazyLoadFragment<V, T extends BasePresenter<V>> extends LazyFragment {

    protected T mPresenter;

    /**
     * 记录当前Fragment的视图是否创建好
     */
    private boolean isPrepared = false;
    /**
     * 记录当前Fragment是否已经显示(加载)过一次了
     */
    private boolean hasLoadedOnce = false;

    //根布局
    private View uiView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //判断是否使用MVP模式
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//因为之后所有的子类都要实现对应的View接口
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (uiView == null) {
            uiView = LayoutInflater.from(getContext()).inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, uiView);

            initViews();

            isPrepared = true; //当视图创建完成时给它赋值。
            lazyLoad(); //不调用此方法 第一个显示的Fragment第一次显示时是不会主动刷新数据的（也就是lazyLoad() 方法不会走）
        }

        return uiView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }


    @Override
    protected void lazyLoad() {
        /**
         * 视图没有准备好， 已经加载过一次， Fragment没有显示出来时 都不做操作（读取数据）
         */
        if (!isPrepared || hasLoadedOnce || !isVisible) {
            return;
        }

        //to do something...
        initData();
        hasLoadedOnce = true;
    }


    protected abstract void initViews();

    /**
     * 仅当fragment第一次显示时 才会被调用
     * 适用场景： 里面做一些网络数据的读取， 仅当当前页面第一次显示时才会读取网络， 其它的情况想要读取网络(下拉加载) 要主动调用此方法
     * 好处： 节省流量
     */
    protected abstract void initData();

    public void initListener() {

    }


    protected <T extends View> T findView(int resId) {
        return (T) (uiView.findViewById(resId));
    }


    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public String getTitle() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getString(ARG_TITLE);
        }
        return "";
    }

}
