//package com.smilcool.server.base.config.netty;
//
//import com.smilcool.server.base.config.netty.handler.ChatHandler;
//import com.smilcool.server.base.config.netty.handler.HeartbeatHandler;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * 请求 Channel 初始化器
// */
//@Component
//public class ChildChannelInitializer extends ChannelInitializer<SocketChannel> {
//
//    @Autowired
//    private ChatHandler chatHandler;
//
//    @Autowired
//    private HeartbeatHandler heartbeatHandler;
//
//    @Override
//    protected void initChannel(SocketChannel ch) throws Exception {
////        ChannelPipeline pipeline = ch.pipeline();
////        // WebSocket 基于 http 协议，需要 http 编解码器
////        pipeline.addLast(new HttpServerCodec())
////                // ChunkedWriteHandler 提供对写大数据流的支持
////                .addLast(new ChunkedWriteHandler())
////                // 对 HttpMessage 进行聚合，聚合成 FullHttpRequest 或 FullHttpResponse
////                .addLast(new HttpObjectAggregator(64 * 1024))
////                // 检测空闲状态 Handler
////                .addLast(new IdleStateHandler(30, 45, 60))
////                // 自定义心跳处理 Handler（空闲状态检测及处理）
////                .addLast(heartbeatHandler)
////                // WebSocket 服务器处理协议 Handler，用于指定给客户端连接访问的路由，以及处理一些繁杂的事务
////                .addLast(new WebSocketServerProtocolHandler("/ws"))
////                // 自定义 Handler，用于消息处理
////                .addLast(chatHandler);
//    }
//}
