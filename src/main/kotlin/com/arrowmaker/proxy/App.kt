import com.arrowmaker.proxy.NanoToCot
import java.net.InetSocketAddress

/**
 * Entry Point into the Proxy Server
 * Parses command line arguments and starts the proxy server
 */
fun main(args: Array<String>) {
    //parse commandline args
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

    //start proxy server
    NanoToCot(localPort, remoteAddress).start()
}

//prints usage to the console when invalid arguments are supplied to the command line
private fun printUse() =
        System.err.println("Usage java -jar ${NanoToCot::class.java.simpleName}.jar <local port> <remote host> <remote port>")