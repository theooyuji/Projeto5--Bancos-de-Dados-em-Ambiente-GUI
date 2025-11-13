package org.projeto;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;

public class PainelDados extends JPanel {

    protected   JTextField txt1, txt2, txt3;
    protected   JComboBox<String> a;
    protected   JTable tblTeste;
    public PainelDados(){

        setLayout(new BorderLayout());

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        String[] t = {"a","b","c","d"};

        txt1 = new JTextField(10);
        txt2 = new JTextField(10);
        txt3 = new JTextField(10);
        a = new JComboBox(t);

        p1.setLayout(new GridLayout(4,2,5,5));
        p1.add(new JLabel("Texto 1:"));
        p1.add(txt1);
        p1.add(new JLabel("Texto 2:"));
        p1.add(txt2);
        p1.add(new JLabel("Texto 3:"));
        p1.add(txt3);
        p1.add(new JLabel("Opções a: "));
        p1.add(a);

        Object[][] v = {{"","","",""}};
        String[] s = {"Texto 1", "Texto 2","Texto 3","Texto4"};
        tblTeste = new JTable(v,s);
        p2.setLayout(new BorderLayout());
        p2.add(new JScrollPane(tblTeste), BorderLayout.CENTER);

        p1.setPreferredSize(new Dimension(750,0));

        add(p1,BorderLayout.EAST);
        add(p2,BorderLayout.CENTER);
    }

    protected JComboBox getA(){
        return a;
    }

    protected void setA(JComboBox a){
        this.a = a;
    }
}
