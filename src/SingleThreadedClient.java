import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SingleThreadedClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost",8080);
        System.out.println("Connected to server...");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter message: ");
        String message = userInput.readLine();

        out.println(message);

        String response = in.readLine();
        System.out.println("Server Response: " + response);

        socket.close();
    }

}
