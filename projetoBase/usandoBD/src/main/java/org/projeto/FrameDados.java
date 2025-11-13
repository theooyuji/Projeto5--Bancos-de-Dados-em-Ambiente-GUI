package org.projeto;

import com.mongodb.client.MongoDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameDados extends JFrame {
    //private final JPanel cnt;
    private final JTabbedPane abas;
    private final MongoDatabase mongo;
    public FrameDados(MongoDatabase mongo){
        this.mongo = mongo;
        setTitle("Banco de dados em ambiente gráfico");
        setSize(700,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PainelProduto p = new PainelProduto(this.mongo);
        PainelCategoria p1 = new PainelCategoria(this.mongo);

        abas = new JTabbedPane();
        abas.add("Produtos",p);
        abas.add("Categorias",p1);

        setLayout(new BorderLayout());

        add(abas,BorderLayout.CENTER);

        p.setVisible(false);
        p1.setVisible(false);
    }
}
