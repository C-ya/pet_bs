//package com.smilcool.server.base.config.netty.handler;
//
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandler.Sharable;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
///**
// * 自定义心跳机制 Handler
// *
// * @author Angus
// * @date 2018/12/24
// */
//@Slf4j
//@Sharable
//@Component
//public class HeartbeatHandler extends ChannelInboundHandlerAdapter {
//
//    /**
//     * 发送到远程节点的 PING 消息
//     */
//    private static final PingWebSocketFrame PING = new PingWebSocketFrame();
//
//
//    /**
//     * 实现 userEventTriggered() 方法以发送 PING 消息
//     */
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        // 判断 evt 是否是 IdleStateEvent（用于触发用户事件，包含读空闲、写空闲、读写空闲）
//        if (evt instanceof IdleStateEvent) {
//            Channel channel = ctx.channel();
//            IdleState state = ((IdleStateEvent) evt).state();
//            switch (state) {
//                case READER_IDLE:
//                    log.debug("channel-{} 进入读空闲...", channel.id().asShortText());
//                    break;
//                case WRITER_IDLE:
//                    log.debug("channel-{} 进入写空闲...", channel.id().asShortText());
//                    break;
//                case ALL_IDLE:
//                    log.debug("channel-{} 进入读写空闲...", channel.id().asShortText());
//                    // 发送 PING 消息（副本，同时增加引用次数防止被释放），并在发送失败时关闭该连接，以防资源浪费
//                    // ctx.writeAndFlush(PING.retainedDuplicate())
//                    //        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//                    // 关闭 channel，节省资源
//                    channel.close();
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unhandled: state = " + state);
//            }
//        } else {
//            // 不是 IdleStateEvent 事件，所以将它传递给下一个 ChannelInboundHandler
//            super.userEventTriggered(ctx, evt);
//        }
//    }
//}
