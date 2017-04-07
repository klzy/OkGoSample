package com.lzy.demo.okserver;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lzy.demo.R;
import com.lzy.demo.databinding.ItemDownloadManagerBinding;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.listener.DownloadListener;

import java.util.ArrayList;
import java.util.List;

import static com.lzy.demo.R.id.pbProgress;
import static com.lzy.demo.R.id.tvProgress;

/**
 * Created by KLZY on .
 */

public class DownloadManagerAdapter extends RecyclerView.Adapter<BindingViewHolder> {
    private Context mContext;
    private ViewDataBinding sBinding;
    private List<ApkEntity> mDatas;

    private LayoutInflater mInflater;
    public class Presenter {

    }

    public DownloadManagerAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        sBinding = DataBindingUtil.inflate(mInflater, R.layout.item_download_manager, parent, false);

        return new BindingViewHolder(sBinding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {

        final ApkEntity fileEntity = mDatas.get(position);

        holder.getBinding().setVariable(com.lzy.demo.BR.downInfo, fileEntity);
        holder.getBinding().setVariable(com.lzy.demo.BR.presenter, new Presenter());
        holder.getBinding().executePendingBindings();

        DownloadListener downloadListener = new MyDownloadListener();
        downloadListener.setUserTag(holder);
        fileEntity.getDownInfo().setListener(downloadListener);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addAll(List<ApkEntity> list) {
        mDatas = new ArrayList<>();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    private class MyDownloadListener extends DownloadListener {

        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            if (getUserTag() == null) return;
            BindingViewHolder holder = (BindingViewHolder) getUserTag();
            //holder.refresh();  //这里不能使用传递进来的 DownloadInfo，否者会出现条目错乱的问题
            String downloadLength = Formatter.formatFileSize(mContext, downloadInfo.getDownloadLength());
            String totalLength = Formatter.formatFileSize(mContext, downloadInfo.getTotalLength());
            ((ItemDownloadManagerBinding)holder.getBinding()).downloadSize.setText(downloadLength + "/" + totalLength);

            ((ItemDownloadManagerBinding)holder.getBinding()).tvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
            ((ItemDownloadManagerBinding)holder.getBinding()).pbProgress.setMax((int) downloadInfo.getTotalLength());
            ((ItemDownloadManagerBinding)holder.getBinding()).pbProgress.setProgress((int) downloadInfo.getDownloadLength());
        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            Toast.makeText(mContext, "下载完成:" + downloadInfo.getTargetPath(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            if (errorMsg != null) Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}

        /*@Override
        public int getCount() {
            return allTask.size();
        }

        @Override
        public DownloadInfo getItem(int position) {
            return allTask.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            DownloadInfo downloadInfo = getItem(position);
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(DownloadManagerActivity.this, R.layout.item_download_manager, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.refresh(downloadInfo);

            //对于非进度更新的ui放在这里，对于实时更新的进度ui，放在holder中
            ApkModel apk = (ApkModel) AppCacheUtils.getInstance(DownloadManagerActivity.this).getObject(downloadInfo.getUrl());
            if (apk != null) {
                Glide.with(DownloadManagerActivity.this).load(apk.getIconUrl()).error(R.mipmap.ic_launcher).into(holder.icon);
                holder.name.setText(apk.getName());
            } else {
                holder.name.setText(downloadInfo.getFileName());
            }
            holder.download.setOnClickListener(holder);
            holder.remove.setOnClickListener(holder);
            holder.restart.setOnClickListener(holder);

            DownloadListener downloadListener = new MyDownloadListener();
            downloadListener.setUserTag(holder);
            downloadInfo.setListener(downloadListener);
            return convertView;
        }*/
