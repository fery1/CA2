 package com.packageHesab;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by omid on 9/30/15.
 */

public class Hello {
     public static void main(String args[]){
            List<Hesab> users = new ArrayList<>();
            String customerNumber,depositType,depositBalance,durationInDays;
            try {

                File fXmlFile = new File("staff.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("deposit");


                for (int temp = 0; temp < nList.getLength(); temp++) {

                    Node nNode = nList.item(temp);

                    System.out.println("\nCurrent Element :" + nNode.getNodeName());

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element eElement = (Element) nNode;

                        //System.out.println("Staff id : " + eElement.getAttribute("id"));
                        customerNumber=eElement.getElementsByTagName("customerNumber").item(0).getTextContent();
                        depositType=eElement.getElementsByTagName("depositType").item(0).getTextContent();
                        depositBalance=eElement.getElementsByTagName("depositBalance").item(0).getTextContent();
                        durationInDays=eElement.getElementsByTagName("durationInDays").item(0).getTextContent();

                        System.out.println("customerNumber : " + customerNumber);
                        System.out.println("depositType : " + depositType);
                        System.out.println("depositBalance : " + depositBalance);
                       // System.out.println("durationInDays : " + durationInDays);
                        users.add(ReflectApp.reflectApp(customerNumber,depositType,depositBalance,durationInDays));
                    }
                }
            } catch (ParserConfigurationException | SAXException | IOException | DOMException e   ) {
                e.printStackTrace();
            }
            Collections.sort(users, new Sud());
            try {
                writeLogFile(users);
            } catch (IOException e) {
                e.printStackTrace();
            }



    }
    public static void writeLogFile(List<Hesab> users) throws IOException    {
        File textFile=new File("./log.txt");
        if(!textFile.exists())
            textFile.createNewFile();
        FileWriter fileWriter = new FileWriter(textFile, false);
        BufferedWriter bw=new  BufferedWriter(fileWriter);
        for (Hesab m : users) {
            Integer customerNum= m.getCN();
            Double payedInterest=m.getPi();
            String content=customerNum.toString()+"#"+payedInterest.toString();
            bw.write(content);
            bw.newLine();
            bw.flush();
        }
        fileWriter.close();

    }
}
class ReflectApp{
    private static Hesab user;

    public static Hesab reflectApp(String customerNumber,String depositType,String depositBalance,String durationInDays) {
        Class noparams[] = {};
        try{
            Class<? extends Seporde> cls =  Class.forName(depositType).asSubclass(Seporde.class);
            Constructor<?> construct = cls.getConstructor(Double.class, Double.class);
            Seporde seporde = (Seporde) construct.newInstance(Double.parseDouble(depositBalance), Double.parseDouble(durationInDays));
            Method method = cls.getMethod("returnPi",noparams);
            Object pi=method.invoke(seporde,null);
            out.println("meghdare Pi  "+(Double) pi);
            return new Hesab(Integer.parseInt(customerNumber),(Double) pi);
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex){
            ex.printStackTrace();
        }
        return user;
    }
}

class Sud implements Comparator<Hesab> {
    @Override
    public int compare(Hesab a, Hesab b) {
        return a.getPi() > b.getPi() ? -1 : a.getPi() == b.getPi() ? 0 : 1;
    }
}