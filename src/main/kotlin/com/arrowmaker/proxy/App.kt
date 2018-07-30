import com.arrowmaker.proxy.Proxy
import java.net.InetSocketAddress


fun main(args: Array<String>) {
    if (args.size != 3) {
        System.err.println("Usage ${Proxy::class.java.simpleName} <local port> <remote host> <remote port>")
        return
    }
    val localPort = Integer.parseInt(args[0])
    val remoteHost = args[1]
    val remotePort = Integer.parseInt(args[2])
    val remoteAddress =   InetSocketAddress(remoteHost, remotePort)

    Proxy(localPort, remoteAddress).start()
}