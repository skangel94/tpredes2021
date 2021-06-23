
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client {
    public static String clientAddress;
    public static Integer clientPort;
    private final String host = this.getClientAddress();
    private final Integer port = this.getClientPort();

    public Client() {
    }

    public static void process() throws IOException {
        Client client = new Client();
        client.getData();
    }


    public void getData() throws IOException {
        Socket s = new Socket();
        PrintWriter s_out = null;
        BufferedReader s_in = null;

        try {
            s.connect(new InetSocketAddress(host , port));
            System.out.println("Connected to server");

            //writer for socket
            s_out = new PrintWriter( s.getOutputStream(), true);
            //reader for socket
            s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        //Host not found
        catch (UnknownHostException e) {
            System.err.println("Don't know about host : " + host);
            System.exit(1);
        //Sever caído o puerto invalido
        } catch (IOException e) {
            System.out.println("Server down or invalid port");
        }

        //listener se mantiene constantemente escuchando mensajes del servido y los imprime por consola
        if (s_in !=null) {
            new Listener(s, s_in);

            Scanner scanner = new Scanner(System.in);

            //System.out.println("Pick a nickname: ");
            String message ="";


            while(!message.equals("x")) {
                //Send message to server
                System.out.printf(" * > ");
                message = scanner.nextLine();
                if (message.equals("x")) {
                    s.close();
                    s_in.close();
                    s_out.close();

                }
                s_out.println( message );
            }

        }
    }

    //listener se mantiene constantemente escuchando mensajes del servido y los imprime por consola
    public class Listener extends Thread  //----------- CLASE ANIDADA ------------
    {
        BufferedReader s_in;
        Socket s;

        public Listener(Socket s,BufferedReader s_in){
            this.s_in = s_in;
            this.s = s;
            start();
        }

        public void run(){
            String response;
            while(!s.isClosed()){
                try {

                    if ((response = s_in.readLine()) != null) {
                        if (response.equals("x")){
                            s.close();
                            break;
                        }else {
                            System.out.println( response );
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Closing Connection  . . . Goodbye");
                    try {
                        s.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    //---------------------------------------------------------------------------------------

    public String getClientAddress() {
        System.out.println("Insert IP address:");
        Scanner scanner = new Scanner( System. in);
        clientAddress = scanner.nextLine();
        if (!clientAddress.equals("127.0.0.1") && !clientAddress.equals("localhost")) {
            System.out.println("Address not valid");
        }
        while (!clientAddress.equals("127.0.0.1") && !clientAddress.equals("localhost")) {
            System.out.println("Insert IP address:");
            clientAddress = scanner.nextLine();
            if (!clientAddress.equals("127.0.0.1") && !clientAddress.equals("localhost")) {
                System.out.println("Address not valid");
            }
        }
        return clientAddress;
    }

    public Integer getClientPort() {
        Scanner scanner = new Scanner( System. in);
        System.out.println("Insert Port:")   ;
        if ( scanner.hasNextInt()) {
            clientPort = scanner.nextInt();
            if (clientPort.equals(null)) {
                System.out.println("Port not valid");
            }
        }else {
            System.out.println("Port not valid");
            getClientPort();
        }

        return clientPort;
    }
}
