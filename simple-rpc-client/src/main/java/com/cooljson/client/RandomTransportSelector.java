package com.cooljson.client;

import com.cooljson.Peer;
import com.cooljson.common.utils.ReflectionUtils;
import com.cooljson.transport.HTTPTransportServer;
import com.cooljson.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author ChenJie
 * @date 2022/4/4
 * 随机路由选择器
 */

@Slf4j
public class RandomTransportSelector implements TransportSelector{

    private static final Logger logger = LoggerFactory.getLogger(RandomTransportSelector.class);

    // 已连接的client
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        this.clients = new ArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        // 确保count>=1
        count = Math.max(count, 1);

        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                // 与server建立连接
                client.connect(peer);
                clients.add(client);
            }
            logger.info("connect server:{}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        // 从集合中随机获取一个连接
        int index = new Random().nextInt(clients.size());
        return clients.remove(index);
    }

    @Override
    public synchronized void release(TransportClient client) {
        // 将当前连接放回池中
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            // 关闭连接
            client.close();
        }
        // 清空连接池
        clients.clear();
    }
}
