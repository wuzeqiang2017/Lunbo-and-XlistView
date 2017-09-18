package test.bwie.com.wuzeqiang1507d20170825;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * 移动1507D  武泽强
 * 2017/8/25.
 * 作用： 图片缓存imageloader
 */

public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String path = Environment.getExternalStorageDirectory() + "1507Dyuekao";
        File cache = new File(path);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800)
                .threadPriority(100)
                .threadPoolSize(3)
                .diskCache(new UnlimitedDiskCache(cache))
                //缓存内存的储存大小
                .diskCacheSize(50*1024*1024)
                //图片缓存的大小
                .memoryCacheSize(2*1024*1024)
                //图片的名算法
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();
        ImageLoader.getInstance().init(config);
    }
    //对图片对缓存一系列的操作
    public static DisplayImageOptions GetOptios(){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_error)
                .showImageOnLoading(R.mipmap.loading)
                .showImageForEmptyUri(R.mipmap.ic_empty)
                .build();
        return options;
    }
}
