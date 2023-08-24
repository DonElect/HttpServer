package com.donatus.sipleHttpServer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }

    InputStream inputStream = null;
    OutputStream outputStream = null;

    @Override
    public void run() {
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String response = getString();

            outputStream.write(response.getBytes());

            LOGGER.info("Connection Processing finished.");
            //serverSocket.close();
        } catch (IOException e) {
            LOGGER.info("Problem with communication", e);
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }

    private String getString() {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <title>JavaScript</title>\n" +
                "    <style>\n" +
                "      .text-button{\n" +
                "        font-family: Arial;\n" +
                "        font-size: 20px;\n" +
                "        color: white;\n" +
                "        background-color: yellow;\n" +
                "        border: none;\n" +
                "      }\n" +
                "      .red-button{\n" +
                "        font-family: Arial;\n" +
                "        font-size: 20px;\n" +
                "        color: white;\n" +
                "        background-color: red;\n" +
                "        border: none;\n" +
                "      }\n" +
                "    </style>\n" +
                "  </head>\n" +
                "\n" +
                "  <body>\n" +
                "    <button title=\"My guy!\" class=\"red-button\" \n" +
                "    onclick=\"\n" +
                "      alert('Good Job!');\n" +
                "    \n" +
                "    \">Helo</button>\n" +
                "    <p>\n" +
                "      Paragraph of \n" +
                "      <button class=\"text-button\"> Hello </button>\n" +
                "      text\n" +
                "    </p>\n" +
                "\n" +
                "    <script>\n" +
                "      //alert('Hi Donatus. Good job');\n" +
                "      let x = 4;\n" +
                "      let y = 6;\n" +
                "      console.log(x+y);\n" +
                "      console.log('Donatus My guy');\n" +
                "      console.log('How' +\" \"+\"My main G\");\n" +
                "    </script>\n" +
                "  </body>\n" +
                "</html>";
//        String html = "<html>" +
//                "<head>" +
//                    "<title> Simple Java HTTP Server</title>"+
//                "</head>"+
//                "<body>" +
//                    "<h1>This page was served using my Simple Java HTTP Server</h1>"+
//                    "<button>Click Here</button>"+
//                "</body>"+
//                "</html>";

        final String CRLF = "\n\r";  // 13, 10

        String response =
                "HTTP/1.1 200 OK" + // Status Line : HTTP/VERSION RESPONSE_CODE RESPONSE_MESSAGE
                        CRLF +
                        "Content-Length: "+html.getBytes().length + CRLF +
                        CRLF+
                        html+
                        CRLF + CRLF;
        return response;
    }
}
