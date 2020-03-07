package com.company;
import com.sun.xml.internal.messaging.saaj.soap.Envelope;

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
    private PrintStream toServer;

    private static final int SMTP_PORT = 25;
    private static final String CRLF = "\r\n";

    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the
       associated streams. Initialize SMTP connection. */
    public SMTPConnection(Envelope envelope) throws IOException {
        connection = new Socket("127.0.0.1", SMTP_PORT);
        fromServer = new BufferedReader(new InputStreamReader(System.in));
        toServer = new PrintStream(connection.getOutputStream())//new DataOutputStream(System.out);

        /* TODO: Fill in */
	            /* Read a line from server and check that the reply code is 220.
	                 If not, throw an IOException. */
        /* TODO: Fill in */
        //Read greeting from server.
        String response = fromServer.readLine();
        if (!response.startsWith("220")) {
            //connection.close();
            throw new IOException();
            //throw an IO exception if the respons from the server is not 220.
        }

	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */
        String localhost = (InetAddress.getLocalHost().getCanonicalHostName());
        sendCommand(250 + "CRLF", 250);
        sendCommand(250 + "HELO CRLF",250);
        sendCommand(localhost, 250);

        isConnected = true;
    }

    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {
        /* TODO: Fill in */
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
        /* TODO: Fill in */
        sendCommand("MAIL FROM: " + );
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
        isConnected = false;
        try { //TODO:  fill in sendCommand.
            sendCommand(221+"CRLF",221);
            // connection.close();
        } catch (IOException e) {
            System.out.println("Unable to close connection: " + e);
            isConnected = true;
        }
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what is is supposed to be according to RFC 821. */
    private void sendCommand(String command, int rc) throws IOException {
        /* Write command to server and read reply from server. */
        toServer.writeBytes(command);
        int i = parseReply(fromServer.readLine());

	/* Check that the server's reply code is the same as the parameter
	   rc. If not, throw an IOException. */
        if (rc != i){
            throw new IOException();
        }
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
        String[] argv = reply.split("\\s");
        int i = Integer.parseInt(argv[0]);
        return i;

    }

    /* Destructor. Closes the connection if something bad happens. */
    protected void finalize() throws Throwable {
        if(isConnected) {
            close();
        }
        super.finalize();
    }
}

