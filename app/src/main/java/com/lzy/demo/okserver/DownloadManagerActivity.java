package com.lzy.demo.okserver;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.lzy.demo.R;
import com.lzy.demo.base.BaseActivity;
import com.lzy.demo.databinding.ActivityDownloadManagerBinding;
import com.lzy.demo.model.ApkModel;
import com.lzy.demo.utils.AppCacheUtils;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;
import com.lzy.okserver.task.ExecutorWithListener;

import java.util.ArrayList;
import java.util.List;

public class DownloadManagerActivity extends BaseActivity implements View.OnClickListener, ExecutorWithListener.OnAllTaskEndListener {

    private List<DownloadInfo> allTask;
    private List<ApkEntity> mListFile;
    private DownloadManagerAdapter adapter;
    private Context mContext;
    private DownloadManager downloadManager;
    ActivityDownloadManagerBinding mBinding;

    public class Presenter {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_download_manager);
        mBinding = DataBindingUtil.setContentView(DownloadManagerActivity.this, R.layout.activity_download_manager);
        mContext = this;
        initToolBar(mBinding.toolbar, true, "下载管理");

        downloadManager = DownloadService.getDownloadManager();
        allTask = downloadManager.getAllTask();
        getListData();
        adapter = new DownloadManagerAdapter(mContext);
        adapter.addAll(mListFile);
        mBinding.rvData.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvData.setAdapter(adapter);

        downloadManager.getThreadPool().getExecutor().addOnAllTaskEndListener(this);
    }

    private void getListData() {
        mListFile = new ArrayList<>();
        for (DownloadInfo info : allTask) {
            ApkEntity apkEntity = new ApkEntity();

            ApkModel apk = (ApkModel) AppCacheUtils.getInstance(DownloadManagerActivity.this).getObject(info.getUrl());
            apkEntity.setName(apk.getName());
            apkEntity.setIcon(apk.getIconUrl());
            apkEntity.setUrl(apk.getUrl());
            apkEntity.setDownInfo(info);

            mListFile.add(apkEntity);
        }
    }

    @Override
    public void onAllTaskEnd() {
        for (DownloadInfo downloadInfo : allTask) {
            if (downloadInfo.getState() != DownloadManager.FINISH) {
                Toast.makeText(DownloadManagerActivity.this, "所有下载线程结束，部分下载未完成", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(DownloadManagerActivity.this, "所有下载任务完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记得移除，否者会回调多次
        downloadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeAll:
                downloadManager.removeAllTask();
                adapter.clearAll();
                //adapter.notifyDataSetChanged();  //移除的时候需要调用
                break;
            case R.id.pauseAll:
                downloadManager.pauseAllTask();
                break;
            case R.id.stopAll:
                downloadManager.stopAllTask();
                break;
            case R.id.startAll:
                downloadManager.startAllTask();
                break;
        }
    }

//    private class ViewHolder implements View.OnClickListener {
//        private DownloadInfo downloadInfo;
//        private ImageView icon;
//        private TextView name;
//        private TextView downloadSize;
//        private TextView tvProgress;
//        private TextView netSpeed;
//        private NumberProgressBar pbProgress;
//        private Button download;
//        private Button remove;
//        private Button restart;
//
//        public ViewHolder(View convertView) {
//            icon = (ImageView) convertView.findViewById(R.id.icon);
//            name = (TextView) convertView.findViewById(R.id.name);
//            downloadSize = (TextView) convertView.findViewById(R.id.downloadSize);
//            tvProgress = (TextView) convertView.findViewById(R.id.tvProgress);
//            netSpeed = (TextView) convertView.findViewById(R.id.netSpeed);
//            pbProgress = (NumberProgressBar) convertView.findViewById(R.id.pbProgress);
//            download = (Button) convertView.findViewById(R.id.start);
//            remove = (Button) convertView.findViewById(R.id.remove);
//            restart = (Button) convertView.findViewById(R.id.restart);
//        }
//
//        public void refresh(DownloadInfo downloadInfo) {
//            this.downloadInfo = downloadInfo;
//            refresh();
//        }
//
//        //对于实时更新的进度ui，放在这里，例如进度的显示，而图片加载等，不要放在这，会不停的重复回调
//        //也会导致内存泄漏
//        private void refresh() {
//            String downloadLength = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getDownloadLength());
//            String totalLength = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getTotalLength());
//            downloadSize.setText(downloadLength + "/" + totalLength);
//            if (downloadInfo.getState() == DownloadManager.NONE) {
//                netSpeed.setText("停止");
//                download.setText("下载");
//            } else if (downloadInfo.getState() == DownloadManager.PAUSE) {
//                netSpeed.setText("暂停中");
//                download.setText("继续");
//            } else if (downloadInfo.getState() == DownloadManager.ERROR) {
//                netSpeed.setText("下载出错");
//                download.setText("出错");
//            } else if (downloadInfo.getState() == DownloadManager.WAITING) {
//                netSpeed.setText("等待中");
//                download.setText("等待");
//            } else if (downloadInfo.getState() == DownloadManager.FINISH) {
//                if (ApkUtils.isAvailable(DownloadManagerActivity.this, new File(downloadInfo.getTargetPath()))) {
//                    download.setText("卸载");
//                } else {
//                    download.setText("安装");
//                }
//                netSpeed.setText("下载完成");
//            } else if (downloadInfo.getState() == DownloadManager.DOWNLOADING) {
//                String networkSpeed = Formatter.formatFileSize(DownloadManagerActivity.this, downloadInfo.getNetworkSpeed());
//                netSpeed.setText(networkSpeed + "/s");
//                download.setText("暂停");
//            }
//            tvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");
//            pbProgress.setMax((int) downloadInfo.getTotalLength());
//            pbProgress.setProgress((int) downloadInfo.getDownloadLength());
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (v.getId() == download.getId()) {
//                switch (downloadInfo.getState()) {
//                    case DownloadManager.PAUSE:
//                    case DownloadManager.NONE:
//                    case DownloadManager.ERROR:
//                        downloadManager.addTask(downloadInfo.getUrl(), downloadInfo.getRequest(), downloadInfo.getListener());
//                        break;
//                    case DownloadManager.DOWNLOADING:
//                        downloadManager.pauseTask(downloadInfo.getUrl());
//                        break;
//                    case DownloadManager.FINISH:
//                        if (ApkUtils.isAvailable(DownloadManagerActivity.this, new File(downloadInfo.getTargetPath()))) {
//                            ApkUtils.uninstall(DownloadManagerActivity.this, ApkUtils.getPackageName(DownloadManagerActivity.this, downloadInfo.getTargetPath()));
//                        } else {
//                            ApkUtils.install(DownloadManagerActivity.this, new File(downloadInfo.getTargetPath()));
//                        }
//                        break;
//                }
//                refresh();
//            } else if (v.getId() == remove.getId()) {
//                downloadManager.removeTask(downloadInfo.getUrl());
//                adapter.notifyDataSetChanged();
//            } else if (v.getId() == restart.getId()) {
//                downloadManager.restartTask(downloadInfo.getUrl());
//            }
//        }
//    }


}
