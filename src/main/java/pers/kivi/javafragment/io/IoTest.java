package pers.kivi.javafragment.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author wangqiwei
 * @date 2021/01/22 9:52 PM
 */
public class IoTest {
    @Test
    public void testBlocking() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            System.out.println("开始监听");
            Socket socket = serverSocket.accept();
            System.out.println("Connection socket: " + socket);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String str = in.readLine();
                if ("END".equals(str))
                    break;
                System.out.println("Accept: " + str);
            }
        }
    }

    @Test
    public void testNoBlocking() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);
        while (true) {
            System.out.println("开始监听");
            SocketChannel socketChannel = serverSocketChannel.accept();

            if (socketChannel == null) {
                Thread.sleep(1000);
                continue;
            }

            System.out.println("Connection socket: " + socketChannel);
            socketChannel.configureBlocking(false);
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(buffer);
                if (read != 0) {
                    String content = new String(buffer.array(), 0, read);
                    System.out.print(content);
                }
                Thread.sleep(1000);
            }
        }
    }

    @Test
    public void testMultiplexing() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            System.out.println("开始监听");
            selector.select();
            Iterator<SelectionKey> iterator = selector.keys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel accept = channel.accept();
                    if (accept != null) {
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                        System.out.println("Connection: " + accept.socket());
                    }
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int read = channel.read(buffer);
                    if (read != 0) {
                        String content = new String(buffer.array(), 0, read);
                        System.out.print("accept: " + content);
                    }
                }
            }
        }
    }
}
