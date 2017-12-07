package com.myrobot.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.myrobot.utils.Constant.NESW_URL;

public class ShangWuActivity extends BaseActivity {
    Retrofit retrofit;
    ProgressDialog progressDialog;
    NewsAdapter newsAdapter;
    @BindView(R.id.list_vertical)
    RecyclerView listVertical;

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
        ServiceApi service = retrofit.create(ServiceApi.class);
        Call<News> call = service.getNesw("机器人");
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

}
