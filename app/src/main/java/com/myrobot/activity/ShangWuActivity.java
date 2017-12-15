package com.myrobot.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.DefaultChildSelectionListener;
import com.myrobot.R;
import com.myrobot.adapter.NewsAdapter;
import com.myrobot.api.ServiceApi;
import com.myrobot.base.BaseActivity;
import com.myrobot.bean.News;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

import static com.myrobot.utils.Constant.NESW_URL;

public class ShangWuActivity extends BaseActivity implements Callback<News> {
    Retrofit retrofit;
    ProgressDialog progressDialog;
    NewsAdapter newsAdapter;
    @BindView(R.id.list_vertical)
    RecyclerView listVertical;
    @BindView(R.id.msg_1)
    TextView msg1;
    @BindView(R.id.msg_2)
    TextView msg2;
    @BindView(R.id.msg_3)
    TextView msg3;
    @BindView(R.id.msg_4)
    TextView msg4;
    @BindView(R.id.msg_5)
    TextView msg5;
    @BindView(R.id.msg_6)
    TextView msg6;
    ServiceApi service;

    @Override
    protected int getContentView() {
        return R.layout.activity_shang_wu;
    }

    @Override
    protected void init() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("数据查询中...");
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(NESW_URL)
                .build();
        progressDialog.show();
        service = retrofit.create(ServiceApi.class);
        Call<News> call = service.getNesw("人工智能", "1");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //请求成功操作
                News news = response.body();
                if (news.getShowapi_res_code() == 0) {
                    showToastor("查询成功");
                    Log.d("news", news.toString());
                    newsAdapter = new NewsAdapter(news, ShangWuActivity.this);
                    initRecyclerView(listVertical, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), newsAdapter);

                } else {
                    showToastor("查询失败");

                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                //请求失败操作
                progressDialog.dismiss();
                showToastor("查询失败");
            }
        });
        Call<News> call2 = service.getNesw("机器人", "1");
        call2.enqueue(this);
    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final NewsAdapter adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.setMaxVisibleItems(2);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(false);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                final int position = recyclerView.getChildLayoutPosition(v);
                final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
                Toast.makeText(ShangWuActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }, recyclerView, layoutManager);

        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

            @Override
            public void onCenterItemChanged(final int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    // final int value = adapter.mPosition[adapterPosition];
/*
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                    adapter.notifyItemChanged(adapterPosition);
*/
                }
            }
        });
    }

    private void initView(News.ShowapiResBodyBean.PagebeanBean pagebeanBean) {
        msg1.setText(pagebeanBean.getContentlist().get(0).getTitle());
        msg2.setText(pagebeanBean.getContentlist().get(1).getTitle());
        msg3.setText(pagebeanBean.getContentlist().get(2).getTitle());
        msg4.setText(pagebeanBean.getContentlist().get(3).getTitle());
        msg5.setText(pagebeanBean.getContentlist().get(4).getTitle());
        msg6.setText(pagebeanBean.getContentlist().get(5).getTitle());

    }

    @OnClick({R.id.shuaxin, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.fanhui_bt, R.id.home_bt})
    public void onViewClicked(View view) {
        play();
        switch (view.getId()) {
            case R.id.shuaxin:

                break;
            case R.id.bt_1:
                break;
            case R.id.bt_2:
                break;
            case R.id.bt_3:
                break;
            case R.id.bt_4:
                break;
            case R.id.bt_5:
                break;
            case R.id.bt_6:
                break;
            case R.id.fanhui_bt:
                finish();
                break;
            case R.id.home_bt:
                finish();
                break;
            default:
                break;
        }
    }


    @Override
    public void onResponse(Call<News> call, Response<News> response) {
        //请求成功操作
        News news = response.body();
        if (news.getShowapi_res_code() == 0) {
            showToastor("查询成功");
            initView(news.getShowapi_res_body().getPagebean());

        } else {
            showToastor("查询失败");

        }
        progressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<News> call, Throwable t) {
        //请求失败操作
        progressDialog.dismiss();
        showToastor("查询失败");
    }

}
