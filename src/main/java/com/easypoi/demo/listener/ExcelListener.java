package com.easypoi.demo.listener;

import cn.afterturn.easypoi.cache.ImageCache;
import cn.afterturn.easypoi.cache.manager.POICacheManager;
import com.easypoi.demo.utils.EasypoiFileLoader;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.poi.util.IOUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * 在初始化spring容器后设置自己的loadingCache和fileLoder
 */
@Component
public class ExcelListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LoadingCache<String, byte[]> loadingCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                .maximumSize(2000).build(new CacheLoader<String, byte[]>() {
                    @Override
                    public byte[] load(String imagePath) throws Exception {
                        InputStream is = POICacheManager.getFile(imagePath);
                        BufferedImage bufferImg = ImageIO.read(is);
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        try {
                            ImageIO.write(bufferImg,
                                    //重写获取后缀面的方法
                                    imagePath.substring(imagePath.lastIndexOf(".") + 1),
                                    byteArrayOut);
                            return byteArrayOut.toByteArray();
                        } finally {
                            IOUtils.closeQuietly(is);
                            IOUtils.closeQuietly(byteArrayOut);
                        }
                    }
                });
        ImageCache.setLoadingCache(loadingCache);

        POICacheManager.setFileLoder(new EasypoiFileLoader());
    }
}
