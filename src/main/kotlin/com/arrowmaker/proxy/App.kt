import com.arrowmaker.proxy.NanoToCotServer
import java.net.InetSocketAddress


fun main(args: Array<String>) {
    if (args.size != 3) {
        printUse()
        return
    }
    val localPort: Int
    val remoteHost: String
    val remotePort: Int

    try {
        localPort = Integer.parseInt(args[0])
        remoteHost = args[1]
        remotePort = Integer.parseInt(args[2])
    } catch (e: Exception) {
        printUse()
        return
    }

    val remoteAddress =   InetSocketAddress(remoteHost, remotePort)

    NanoToCotServer(localPort, remoteAddress).start()
}

fun printUse() = System.err.println("Usage ${NanoToCotServer::class.java.simpleName} <local port> <remote host> <remote port>")