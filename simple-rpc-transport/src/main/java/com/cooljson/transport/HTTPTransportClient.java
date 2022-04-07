package com.cooljson.transport;

import com.cooljson.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ChenJie
 * @date 2022/4/3
 * Http连接的实现方式
 */

public class HTTPTransportClient implements TransportClient{

    private String url;

    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getHost();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            // 设置允许输出
            httpConn.setDoOutput(true);
            //设置允许输入
            httpConn.setDoInput(true);
            httpConn.setUseCaches(false);

            // 设置请求方法为post
            httpConn.setRequestMethod("POST");
            httpConn.connect();

            // 发送数据将输入的数据复制到http的输出流中
            IOUtils.copy(data, httpConn.getOutputStream());

            int resultCode = httpConn.getResponseCode();
            // 判断是否成功
            if (resultCode == HttpURLConnection.HTTP_OK) {
                // 返回HTTP的输出
                return httpConn.getInputStream();
            }else {
                return httpConn.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {

    }
}
