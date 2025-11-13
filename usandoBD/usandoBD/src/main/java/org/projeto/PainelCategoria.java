package org.projeto;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;

public class PainelCategoria extends PainelDados{
    private final MongoDatabase bd;
    private JToolBar toolBar;
    private JButton btnInicio,btnProximo,btnAnterior,btnFim,
            btnIncluir,btnExcluir,btnSalvar,btnBuscar;
    private final DefaultTableModel model;
    private MongoCursor<Document> cursor;
    private final ArrayList<Document> dados;
    private int ind = 0;
    public PainelCategoria(MongoDatabase mongoDatabase){
        this.bd = mongoDatabase;

        MongoCollection<Document> categoria = bd.getCollection("categoria");
        MongoCollection<Document> produto = bd.getCollection("produto");

        cursor = categoria.find().sort(new Document("_id",1)).iterator();
        dados = categoria.find().sort(new Document("_id",1)).into(new ArrayList<>());
        setLayout(new BorderLayout());
        toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout());

        btnInicio = new JButton("Inicio", new ImageIcon("test/first.png"));
        btnInicio.setPreferredSize(new Dimension(65,45));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar", new ImageIcon("test/prior.png"));
        btnAnterior.setPreferredSize(new Dimension(65,45));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Proximo", new ImageIcon("test/next.png"));
        btnProximo.setPreferredSize(new Dimension(65,45));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFim = new JButton("Fim", new ImageIcon("test/last.png"));
        btnFim.setPreferredSize(new Dimension(65,45));
        btnFim.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFim.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFim.setFocusPainted(false);

        btnIncluir = new JButton("Incluir", new ImageIcon("test/Add.png"));
        btnIncluir.setPreferredSize(new Dimension(65,45));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnExcluir = new JButton("Excluir", new ImageIcon("test/Minus.png"));
        btnExcluir.setPreferredSize(new Dimension(65,45));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnSalvar = new JButton("Alterar", new ImageIcon("test/Save.png"));
        btnSalvar.setPreferredSize(new Dimension(65,45));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnBuscar = new JButton("Buscar", new ImageIcon("test/FIND.png"));
        btnBuscar.setPreferredSize(new Dimension(65,45));
        btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnBuscar.setFocusPainted(false);

        toolBar.add(btnInicio);
        toolBar.add(btnAnterior);
        toolBar.add(btnProximo);
        toolBar.add(btnFim);

        toolBar.addSeparator();

        toolBar.add(btnBuscar);

        toolBar.addSeparator();

        toolBar.add(btnIncluir);
        toolBar.add(btnExcluir);
        toolBar.add(btnSalvar);

        toolBar.setRollover(false);

        add(toolBar,BorderLayout.NORTH);

        btnInicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = 0;
                if(dados.get(ind) != null){
                    exibeDado(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Não existe nenhum registro  !");
                }
                verificaBotoes();
            }
        });

        btnAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ind > 0){
                    ind--;
                }
                if(dados.get(ind) != null){
                    exibeDado(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Não existe nenhum registro anterior !");
                }
                verificaBotoes();
            }
        });

        btnProximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ind < dados.size() -1){
                    ind++;
                }
                if(dados.get(ind) != null){
                    exibeDado(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Não existe nenhum registro à frente !");
                }
                verificaBotoes();
            }
        });

        btnFim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ind = dados.size() - 1;
                if(dados.get(ind)  != null){
                    exibeDado(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Não existe registros nessa coleção ! ");
                }
            }
        });

        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Document doc = new Document();
                    doc.append("nomeCategoria", txt1.getText());
                    doc.append("_id",Integer.parseInt(txt2.getText()));
                    categoria.insertOne(doc);
                    dados.add(doc);
                    dados.sort((d1,d2) ->{
                        int id1 = Integer.parseInt(d1.get("_id").toString());
                        int id2 = Integer.parseInt(d2.get("_id").toString());
                        return Integer.compare(id1,id2);
                    });
                    model.setRowCount(0);
                    for (Document d : dados) {
                        model.addRow(new Object[]{d.get("nomeCategoria"), d.get("_id")});
                    }
                    JOptionPane.showMessageDialog(null,"Produto inserido com sucesso !");
                }catch (MongoException m){
                    System.out.println(m.getMessage());
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(JOptionPane.showConfirmDialog(null,"Deseja realemnte apagar esse registro ?") == JOptionPane.OK_OPTION){
                            int idCategoria = Integer.parseInt(txt2.getText());
                            if(produto.find(Filters.eq("idCategoria", idCategoria)).first() == null){
                                categoria.deleteOne(Filters.eq("_id", idCategoria));
                                dados.remove(idCategoria - 1);
                                JOptionPane.showMessageDialog(null,"Exclusão realizada com sucesso");
                                preencheDados(categoria);
                            }else{
                                JOptionPane.showMessageDialog(null,"Existe um produto com essa categoria ! Delete-o primeiro");
                            }
                    }else{
                        JOptionPane.showMessageDialog(null,"Exclusão cancelada.");
                    }
                }catch (MongoException m){
                    System.out.println(m.getMessage());
                }
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(JOptionPane.showConfirmDialog(null,"Deseja realmente  mudar esse registro? ") == JOptionPane.OK_OPTION){
                        categoria.updateOne(new Document("_id", Integer.parseInt(txt2.getText())),new Document("$set", new Document("nomeCategoria", txt1
                                .getText())));
                        dados.set(Integer.parseInt(txt2.getText()),new Document("nomeCategoria", txt1.getText()).append("_id",txt2.getText()));
                        JOptionPane.showMessageDialog(null,"Registro alterado com sucesso !");
                        preencheDados(categoria);

                    }else{
                        JOptionPane.showMessageDialog(null,"Registro mantido");
                    }
                }catch (MongoException ex){
                    ex.printStackTrace();
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Document d = categoria.find(new Document("_id", Integer.parseInt(txt2.getText()))).first();
               if(d != null){
                   exibeDado(d);
                   int indi = Integer.parseInt(txt2.getText());
                   for(int i = 0 ; i < indi; i++){
                       if(Integer.parseInt(dados.get(i).get("_id").toString()) == indi){
                           ind = i;
                           break;
                       }
                   }
                   verificaBotoes();
               }else{
                   JOptionPane.showMessageDialog(null,"Não foi encontrado registro com esse ID !");
               }
            }
        });


        JPanel p = new JPanel();
        JPanel p1 = new JPanel();

        txt1 = new JTextField(20);
        txt2 = new JTextField(20);

        p.setLayout(new GridLayout(2,2,10,10));
        p.add(new JLabel("Nome"));
        p.add(txt1);
        p.add(new JLabel("ID"));
        p.add(txt2);

        String[] txt = {"Nome da categoria", "ID da categoria"};
        model = new DefaultTableModel(txt,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tblTeste = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(tblTeste);
        p1.setLayout(new BorderLayout());
        p1.add(scrollPane, BorderLayout.CENTER);

        add(p1,BorderLayout.CENTER);
        add(p,BorderLayout.EAST);

        preencheDados(categoria);
        if(!dados.isEmpty()){
            exibeDado(dados.get(0));
            verificaBotoes();
        }
    }

    private void preencheDados(MongoCollection<Document> categoria){
        model.setRowCount(0);
        try(MongoCursor<Document>cursor = categoria.find().sort(new Document("_id",1)).iterator()){
            Document doc = null;
            while(cursor.hasNext()){
                doc = cursor.next();
                model.addRow(new Object[]{doc.get("nomeCategoria"), doc.get("_id")});
            }
        }
    }

    private void exibeDado(Document doc){
        txt1.setText((String) doc.get("nomeCategoria"));
        txt2.setText(Integer.toString((int)doc.get("_id")));
    }

    private void verificaBotoes(){
        btnInicio.setEnabled(true);
        btnProximo.setEnabled(true);
        btnAnterior.setEnabled(true);
        btnFim.setEnabled(true);
        if(ind == 0){
            btnInicio.setEnabled(false);
            btnAnterior.setEnabled(false);
        }
        if(ind == dados.size() - 1){
            btnProximo.setEnabled(false);
            btnFim.setEnabled(false);
        }
    }
}
