package org.projeto;

import com.mongodb.MongoException;
import org.bson.Document;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private final static String URI = "mongodb+srv://cc25155_db_user:senha@cluster0.op0nkgz.mongodb.net/?appName=Cluster0";
    private static MongoClient  mongo;
    public static void main(String[] args){
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        FrameDados form = null;
                        mongo =  MongoClients.create(URI);
                        MongoDatabase daRoca = mongo.getDatabase("daRoca");
                        try{
                           form = new FrameDados(daRoca);
                        }catch(MongoException mongoException){
                            mongo.close();
                            System.out.println(mongoException.getMessage());
                        }
                        if(form != null)
                            form.setVisible(true);

                    }
                }
        );
    }


}