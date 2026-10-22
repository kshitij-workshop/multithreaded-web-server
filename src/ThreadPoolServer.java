import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
     public static final int THREAD_POOL_SIZE = 100;

     public static void main(String[] args) throws IOException {
          ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
          try(ServerSocket serverSocket = new ServerSocket(8080, 1024)) {
               System.out.println("Thread pool server started on port: 8080");
               System.out.println("Pool Size: " + THREAD_POOL_SIZE);

               while(true) {
                    Socket clientSocket = serverSocket.accept();
                    threadPool.submit(() -> handleClient(clientSocket));
               }
          } finally {
              threadPool.shutdown();
          }
     }

     public static void handleClient(Socket clientSocket) {
          String threadName = Thread.currentThread().getName();

          try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
          ) {
               String request = in.readLine();
               System.out.println("[" + threadName + "] Received: " + request);
               Thread.sleep(2000);

               out.println("Echo: " + request);
          } catch (IOException | InterruptedException e) {
              e.printStackTrace();
          } finally {
              try {
                  clientSocket.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
     }

}
