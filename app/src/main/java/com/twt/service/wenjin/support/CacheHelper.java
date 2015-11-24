package com.twt.service.wenjin.support;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.jakewharton.disklrucache.DiskLruCache;
import com.twt.service.wenjin.WenJinApp;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by M on 2015/11/23.
 */
public class CacheHelper {
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    private DiskLruCache mDiskLruCache;

    private static class CacheHolder {
        private static final CacheHelper sInstance = new CacheHelper();
    }

    private CacheHelper() {
        File diskCacheDir = getDiskCacheDir(WenJinApp.getContext(), "content");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static CacheHelper getInstance() {
        return CacheHolder.sInstance;
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    private long getUsableSpace(File path) {
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getAvailableBlocks() * (long) stats.getBlockSize();
    }

    public void cacheJSONObject(String url, JSONObject jsonObject){
        if (mDiskLruCache == null) {
            return;
        }
        String key = MD5Utils.hashKeyFromUrl(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                if (jsonObjectToStream(jsonObject, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject loadJSONObjectFromDiskCache(String url) {
        if (mDiskLruCache == null) {
            return null;
        }

        String key = MD5Utils.hashKeyFromUrl(url);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream in = snapshot.getInputStream(DISK_CACHE_INDEX);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                return JSONHelper.createJSONObject(out.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean jsonObjectToStream(JSONObject jsonObject, OutputStream outputStream) {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            String jsonString = jsonObject.toString();
            in = new BufferedInputStream(new ByteArrayInputStream(jsonString.getBytes()), IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(in);
            close(out);
        }
        return false;
    }

    private void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
