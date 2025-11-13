package org.projeto;

import com.mongodb.client.MongoDatabase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameDados extends JFrame {
    private final JPanel cnt;
    private final MongoDatabase mongo;
    public FrameDados(MongoDatabase mongo){
        this.mongo = mongo;
        setTitle("Banco de dados em ambiente gráfico");
        setSize(700,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PainelProduto p = new PainelProduto(this.mongo);
        PainelCategoria p1 = new PainelCategoria(this.mongo);
        cnt = new JPanel(new CardLayout());

        cnt.add(p,"produto");
        cnt.add(p1,"categoria");

        JMenuBar menu = new JMenuBar();
        JMenu jMenu = new JMenu("Coleções");

        JMenuItem item1 = new JMenuItem("Produtos");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudaTela("produto");
            }
        });
        JMenuItem item2 = new JMenuItem("Categorias");
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mudaTela("categoria");
            }
        });
        jMenu.add(item1);
        jMenu.add(item2);
        menu.add(jMenu);

        setLayout(new BorderLayout());

        add(cnt,BorderLayout.CENTER);

        p.setVisible(false);
        p1.setVisible(false);

        setJMenuBar(menu);
    }
    private void mudaTela(String atual){
        CardLayout cl = (CardLayout) cnt.getLayout();
        cl.show(cnt, atual);
        if(atual.equals("produto")){
            for(Component c : cnt.getComponents()){
                if(c instanceof PainelProduto){
                    ((PainelProduto) c).atualizaTela();
                }
            }
        }
    }
}
