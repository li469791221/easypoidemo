package com.easypoi.demo.utils;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 自定义fileloader
 * 网络图片使用httpclient替换urlconnection
 */
public class EasypoiFileLoader implements IFileLoader {
    @Override
    public byte[] getFile(String url) {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            if (url.startsWith("http")) {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(new URI(url));
                HttpResponse response = httpClient.execute(httpGet);
                is = response.getEntity().getContent();
            } else {
                is = new FileInputStream(url);
            }
            bos = new ByteArrayOutputStream();
            byte[] bts = new byte[1024];
            int len;
            while((len = is.read(bts)) > -1) {
                bos.write(bts, 0, len);
            }
            bos.flush();
            return bos.toByteArray();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(bos);
        }
        return null;
    }
}
