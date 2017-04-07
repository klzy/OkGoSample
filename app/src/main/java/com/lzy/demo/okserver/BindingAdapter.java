package com.lzy.demo.okserver;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindingAdapter {

    @android.databinding.BindingAdapter({"imageUrl", "android:src"})
    public static void loadImageFromUrl(ImageView view,
                                        String url,
                                        Drawable drawable) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(drawable)
                .into(view);
    }

}
