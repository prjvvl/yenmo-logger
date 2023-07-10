import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


class Runner {
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(8000);
        System.out.println("Waiting for client...");
        Socket client = server.accept();
        System.out.println(client.isConnected());
        System.out.println(client.isClosed());
        

        // ObjectOutputStream oout = new ObjectOutputStream(client.getOutputStream());
        // oout.writeObject("testing");
        // oout.close();

        Path path = Paths.get("C:/Users/prajw/OneDrive/Desktop/yenmo/logs/");
        WatchService watcher = FileSystems.getDefault().newWatchService();    
        path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);

        while (true) {
            WatchKey key = watcher.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.OVERFLOW) continue;
                Path fileName = (Path) event.context();
                System.out.println("file changed: " + fileName);

                // Send data to client
                DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                dout.writeUTF("file changed: " + fileName);
                dout.flush();
                dout.close();
            }
            if (!key.reset()) break;
        }

        System.out.println("Closing the server");
        server.close();
        System.out.println("Server Closed!");

    }
}
