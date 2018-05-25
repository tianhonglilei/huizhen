package com.hoolai.huizhen.util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;

import com.hoolai.huizhen.R;

import java.io.IOException;

/**
 * Author: lilei
 * Date: 2018/5/22
 * Comment: // 获取资源路径工具类
 */

public class AssetAndRawUtil {

    public static Uri getRawPath(Context context, int id) {
        Uri uri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + id);
        return uri;
    }

    public static AssetFileDescriptor getAssetsPath(AssetManager manager, String filename) {
        AssetFileDescriptor afd;
        try {
            afd = manager.openFd(filename);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return afd;
    }

}
