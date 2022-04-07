package com.cooljson.transport;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ChenJie
 * @date 2022/4/4
 * Http的实现
 */

@Slf4j
public class HTTPTransportServer implements TransportServer{

    private static final Logger logger = LoggerFactory.getLogger(HTTPTransportServer.class);

    private RequestHandler handler;

    private Server server;

    @Override
    public void init(int port, RequestHandler handler) {
        this.handler = handler;
        this.server = new Server(port);

        // 接收请求
        ServletContextHandler sch = new ServletContextHandler();
        server.setHandler(sch);

        ServletHolder holder = new ServletHolder(new RequestServlet());
        sch.addServlet(holder, "/*");

    }

    @Override
    public void start() {
        try {
            server.start();
            // 等待结果,避免立刻返回
            server.join();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    class RequestServlet extends HttpServlet{
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("client connect");
            // 获取发送的数据
            InputStream inputStream = req.getInputStream();
            OutputStream out = resp.getOutputStream();

            // 调用服务
            if (handler != null) {
                handler.onRequest(inputStream, out);
            }

            out.flush();
        }
    }
}
