package Client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    Scanner scanner=new Scanner(System.in);
    public static void main(String[] args) {
            new Client().start();
    }

    @Override
    public void run() {
        try{
            Socket s =new Socket("localhost",1234);
            new Conversation(s).start();


        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    class Conversation extends Thread{
        private Socket s;
        Conversation(Socket s){
            this.s=s;
        }

        @Override
        public void run() {
            try{
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);


                    System.out.println("Connexion établie avec le serveur , authentification :");
                    System.out.println("Entrez Votre Login :");
                    String login = scanner.nextLine();
                    System.out.println("Entrez Votre mdp :");
                    String mdp = scanner.nextLine();

                    pw.println(login);
                    pw.println(mdp);
                    String msg_er= br.readLine();
                    if(msg_er.equals("Bad credential")){
                    s.close();
                    }
                while(true){
                    System.out.println("envoyer un message au serveur ");
                    String msg=scanner.nextLine();
                    pw.println("Message envoyé par le serveur  "+msg);

                    String msg_rec= br.readLine();

                    System.out.println(msg_rec);

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
