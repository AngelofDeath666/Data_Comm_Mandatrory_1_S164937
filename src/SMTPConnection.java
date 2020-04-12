import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Open an SMTP connection to a mailserver and send one mail.
 *
 */
public class SMTPConnection {
    /* The socket to the server */
    private Socket connection;

    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;

    private static final int SMTP_PORT = 25;
    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the
       associated streams. Initialize SMTP connection. */
    public SMTPConnection(Envelope envelope) throws IOException {
        System.out.println(isConnected);
        connection = new Socket(envelope.DestAddr, SMTP_PORT);
        System.out.println("Connetion socket");
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        System.out.println("Getting from server");
        toServer = new DataOutputStream(connection.getOutputStream());
        System.out.println("Sending to server");

        /* TODO: Fill in */
	            /* Read a line from server and check that the reply code is 220.
	                 If not, throw an IOException. */
        /* TODO: Fill in */
        //Read greeting from server.
        String response = fromServer.readLine();
        if (parseReply(response) != 220){
            throw new IOException("No connection achieved");
        }


	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
        String localhost = (InetAddress.getLocalHost().getHostName());
        //we use EHLO here, because we use the ESMTP (extended SMTP)
        sendCommand("EHLO " + localhost, 250);
        System.out.println("We reach ehlo");
        /*the reason we have 9 readline here, is because when we use EHLO it sends back
        a number of functions, that we bypass by readLine 9 times.
         */
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();
        fromServer.readLine();


        isConnected = true;
    }

    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {
        sendCommand("MAIL FROM: <" + envelope.Sender+ ">",250); //Here we input the sender
        sendCommand("RCPT TO: <" + envelope.Recipient+ ">",250); //Here we input the recipent
        sendCommand("DATA", 354); //After DATA we write the rest of the message.
        sendCommand(envelope.Message.toString(),250);
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
        isConnected = false;
        try {
            sendCommand("QUIT",221); //it expects the reply code 221 because that closes the connection
            connection.close();
        } catch (IOException e) {
            System.out.println("Unable to close connection: " + e);
            //this is an exception to say that it couldn't close the connection
            isConnected = true;
        }
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
        /* Write command to server and read reply from server. */
        toServer.writeBytes(command+CRLF);
        int i = parseReply(fromServer.readLine());

	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
        if (rc != i){
            throw new IOException();
        }
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
       StringTokenizer tokens = new StringTokenizer(reply);
       String nt = tokens.nextToken();
       nt = nt.split("-")[0]; //we split by '-' because ESMTP adds '-' to some responses.
       return (new Integer(nt)).intValue();

    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
        if(isConnected) {
            close();
        }
        super.finalize();
    }
}

