package Serveur;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur extends Thread {

    Scanner scanner=new Scanner(System.in);
   private int nbr_client;

    public static void main(String[] args) {
        new Serveur().start();
        }

    @Override
    public void run() {
      try{
          ServerSocket serverSocket=new ServerSocket(1234);
          System.out.println("Demarrage du serveur");
          while (true){
              Socket socket =serverSocket.accept();
              ++nbr_client;
              new Conversation(socket,nbr_client).start();
          }
    }catch (IOException e) {
          e.printStackTrace();
      }
}

    class Conversation extends Thread{
        private Socket s;
        private String login;
        private  String mdp;

        private int nbr_client;

        Conversation(Socket socket,int nbr){
            this.s=socket;
            this.nbr_client=nbr;
        }
        @Override
        public void run() {
            try {
                InputStream is = s.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = s.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);

                while (true){
                    try {
                        login=br.readLine();
                        mdp=br.readLine();
                        File f=new File("C:\\Users\\Administrateur\\Desktop\\Programmation_Reseau\\TP3_Socket\\TP3\\src\\Serveur\\MDP");
                        FileReader fileReader = new FileReader(f);
                        BufferedReader reader=new BufferedReader(fileReader);
                        String sa;
                        while((sa=reader.readLine())!=null){
                            String [] t = sa.split(":");
                            if (login.equals(t[0])&& mdp.equals(t[1])){
                                System.out.println("correct login and password ");
                                System.out.println(t[0]+" vient de se connecter ");
                            }
                            else {
                                pw.println("Bad credential");
                               s.close();
                            }
                        }
                        while (true){
                            String msg= br.readLine();
                            System.out.println(msg);
                            System.out.println("Repondez le client ");
                            String msg_envoi=scanner.nextLine();
                            pw.println("Message Recu du client  "+msg_envoi);
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}


