package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpURLConnectionExample {

    public static String date;{}
    public static String codeval;{}

    public static void main(String[] args) throws Exception {


        Scanner in = new Scanner(System.in);

        System.out.print("Введите дату в формате dd/mm/yyyy: ");
        date = in.nextLine();

        System.out.print("Введите код валюты: ");
        codeval = in.nextLine();

        HttpURLConnectionExample obj = new HttpURLConnectionExample();

        System.out.println(" ");
        obj.sendGet();





    }

    private void sendGet() throws Exception {

        String url = ("http://www.cbr.ru/scripts/XML_daily.asp?date_req=" + date);

        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();

        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");


        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }




            NodeList nl = null;
            try {
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(response.toString())));
                XPathFactory xPathfactory = XPathFactory.newInstance();
                XPath xpath = xPathfactory.newXPath();
                XPathExpression expr = xpath.compile("//ValCurs/Valute[CharCode='" + codeval +"']/Value/text()");
                nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                // Output NodeList
                for (int i = 0; i < nl.getLength(); i++) {
                    Node n = nl.item(i);
                    System.out.println("Значение: " + n.getTextContent());
                }
            } catch (XPathExpressionException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (ParserConfigurationException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SAXException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }
}