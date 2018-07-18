package br.com.alura.agenda.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebClient {

    public String post(String json) {
        try {

            /* Endereço do Servidor */
            URL url = new URL("https://www.caelum.com.br/mobile");

            /* Abrindo a conexão com o servidor*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            /* Indicando para o servidor que será enviado em formato JSON */
            /* E esperado em formato JSON */
            connection.setRequestProperty("Content-type","application/json");
            connection.setRequestProperty("Accept","application/json");

            /* Configurando que será POST */
            connection.setDoOutput(true);

            /* Facilitando a forma de escrever a resposta */
            PrintStream printStream = new PrintStream(connection.getOutputStream());

            /* Escrevendo a resposta */
            printStream.println(json);

            /* Estabelecendo a conexão com o servidor */
            connection.connect();

            /* Lendo a resposta do servidor depois de conectar */
            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            /* Caso dê tudo certo */
            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* Caso dê errado */
        return null;

    }

}
