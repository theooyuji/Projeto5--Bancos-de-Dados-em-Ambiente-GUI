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
import java.util.Objects;

public class PainelProduto extends PainelDados{
    private final MongoDatabase bd;
    private JToolBar toolBar;
    private JButton btnInicio,btnProximo,btnAnterior,btnFim,
                    btnIncluir,btnExcluir,btnSalvar,btnBuscar;
    private JTextField txtNomeProduto,txtPreco;
    private ArrayList<Document> dados;
    private DefaultTableModel model;
    private  int ind = 0;
    private MongoCollection<Document> categoria;
    private MongoCollection<Document> produto;
    private JComboBox<String> cbUN;
    public PainelProduto(MongoDatabase database){
        bd = database;

        categoria = bd.getCollection("categoria");
        produto   = bd.getCollection("produto");
        dados = produto.find().sort(new Document("_id",1)).into(new ArrayList<>());
        setLayout(new BorderLayout());

        toolBar = new JToolBar();
        toolBar.setLayout(new FlowLayout());

        btnInicio = new JButton("Inicio", new ImageIcon("projetoBase/usandoBD/test/first.png"));
        btnInicio.setPreferredSize(new Dimension(65,45));
        btnInicio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnInicio.setHorizontalTextPosition(SwingConstants.CENTER);
        btnInicio.setFocusPainted(false);

        btnAnterior = new JButton("Voltar", new ImageIcon("projetoBase/usandoBD/test/prior.png"));
        btnAnterior.setPreferredSize(new Dimension(65,45));
        btnAnterior.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAnterior.setHorizontalTextPosition(SwingConstants.CENTER);
        btnAnterior.setFocusPainted(false);

        btnProximo = new JButton("Proximo", new ImageIcon("projetoBase/usandoBD/test/next.png"));
        btnProximo.setPreferredSize(new Dimension(65,45));
        btnProximo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnProximo.setHorizontalTextPosition(SwingConstants.CENTER);
        btnProximo.setFocusPainted(false);

        btnFim = new JButton("Fim", new ImageIcon("projetoBase/usandoBD/test/last.png"));
        btnFim.setPreferredSize(new Dimension(65,45));
        btnFim.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnFim.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFim.setFocusPainted(false);

        btnIncluir = new JButton("Incluir", new ImageIcon("projetoBase/usandoBD/test/Add.png"));
        btnIncluir.setPreferredSize(new Dimension(65,45));
        btnIncluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnIncluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnIncluir.setFocusPainted(false);

        btnExcluir = new JButton("Excluir", new ImageIcon("projetoBase/usandoBD/test/Minus.png"));
        btnExcluir.setPreferredSize(new Dimension(65,45));
        btnExcluir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnExcluir.setHorizontalTextPosition(SwingConstants.CENTER);
        btnExcluir.setFocusPainted(false);

        btnSalvar = new JButton("Alterar", new ImageIcon("projetoBase/usandoBD/test/Save.png"));
        btnSalvar.setPreferredSize(new Dimension(65,45));
        btnSalvar.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalvar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSalvar.setFocusPainted(false);

        btnBuscar = new JButton("Buscar", new ImageIcon("projetoBase/usandoBD/test/FIND.png"));
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
                    exibeDados(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Não existe nenhum registro !");
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
                    exibeDados(dados.get(ind));
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
                    exibeDados(dados.get(ind));
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
                    exibeDados(dados.get(ind));
                }
                else{
                    JOptionPane.showMessageDialog(null,"Não existe registros nessa coleção ! ");
                }
                verificaBotoes();
            }
        });

        btnIncluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Document d = new Document("_id", Integer.parseInt(txt1.getText()));

                    d.append("imagem", "");
                    d.append("nomeProduto", txtNomeProduto.getText());
                    d.append("qtdeEstoque",Integer.parseInt(txt2.getText()));
                    d.append("idCategoria", Integer.parseInt(Objects.requireNonNull(a.getSelectedItem()).toString().split(" - ")[0]));
                    d.append("unidadeMedida", Objects.requireNonNull(cbUN.getSelectedItem()).toString().split(" - ")[0]);
                    d.append("preco", Double.parseDouble(txtPreco.getText()));

                    produto.insertOne(d);
                    dados.add(d);

                    dados.sort(( d1, d2) -> {
                        int id1 = Integer.parseInt(d1.get("_id").toString());
                        int id2 = d2.getInteger("_id");
                        return Integer.compare(id1,id2);
                    });

                    JOptionPane.showMessageDialog(null, "Produto inserido.");

                    preencharDados(dados);
                }catch (MongoException me){
                    System.out.println(me.getMessage());
                }
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = Integer.parseInt(txt1.getText());
                    if(JOptionPane.showConfirmDialog(null,"Deseja realmente excluir esse produto ? ") == JOptionPane.OK_OPTION){
                        if(produto.find(new Document("_id", id)).first() != null){
                            produto.deleteOne(Filters.eq("_id",id));
                            dados.removeIf(d -> d.getInteger("_id") == id);
                            preencharDados(dados);
                            JOptionPane.showMessageDialog(null, "Produto excluído !");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Não existe nenhum produto com esse ID !");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Operação cancelada");
                    }
                } catch (MongoException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int id = Integer.parseInt(txt1.getText());
                    Document d = new Document("_id", id);
                    if(JOptionPane.showConfirmDialog(null, "Deseja realmente mudar esse produto ?") == JOptionPane.OK_OPTION){

                       if(produto.find(Filters.eq("_id", id)).first() != null){
                           d.append("nomeProduto", txtNomeProduto.getText());
                           d.append("qtdeEstoque",Integer.parseInt(txt2.getText()));
                           d.append("idCategoria", Objects.requireNonNull(a.getSelectedItem()).toString().split(" - ")[0]);
                           d.append("unidadeMedida", Objects.requireNonNull(cbUN.getSelectedItem()).toString().split(" -")[0]);
                           d.append("preco", Double.parseDouble(txtPreco.getText()));

                           produto.updateOne(Filters.eq("_id", id), new Document("$set", d));
                           dados.set(ind, d);
                           preencharDados(dados);
                           JOptionPane.showMessageDialog(null, "Produto alterado");

                       }
                       else{
                           JOptionPane.showMessageDialog(null,"Não existe produto com esse ID ");
                       }
                    }
                    else{

                    }
                }catch (MongoException me){
                    System.out.println(me.getMessage());
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(txt1.getText());
                try{
                    Document d = produto.find(Filters.eq("_id", id)).first();
                    if( d != null){
                         exibeDados(d);
                         for(int i =0 ; i < dados.size(); i++){
                             Document atual = dados.get(i);
                             if(atual.getInteger("_id") == id){
                                 ind = i;
                                 break;
                             }
                         }
                         verificaBotoes();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Não existe produto com esse ID");
                    }
                }catch(MongoException ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        JPanel p = new JPanel();
        JPanel p1 = new JPanel();

        String[] unidades = {"un - Unidade","kg - Kilograma","g - Grama","dz - Duzia","ct - Cento","dz/2 - Meia-duzia"};
        txt1 = new JTextField(15);
        txt2 = new JTextField(15);
        txt3 = new JTextField(15);
         cbUN = new JComboBox<>(unidades);
        a = new JComboBox<>();
        a.setPreferredSize(new Dimension(50,50));
        preencherCB(categoria);
        txtPreco = new JTextField(15);
        txtNomeProduto = new JTextField(15);
        p.setLayout(new GridLayout(6,2));
        p.add(new JLabel("ID"));
        p.add(txt1);
        p.add(new JLabel("Nome"));
        p.add(txtNomeProduto);
        p.add(new JLabel("Quantidade de estoque"));
        p.add(txt2);
        p.add(new JLabel("ID da Categoria"));
        p.add(a);
        p.add(new JLabel("Unidade de medida"));
        p.add(cbUN);
        p.add(new JLabel("Preço"));
        p.add(txtPreco);

        String[] nomes = {  "ID do produto", "Nome do produto",
                            "Quantidade de Estoque", "ID Categoria",
                            "Unidade de medida", "Preço"};
        model = new DefaultTableModel(nomes,0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tblTeste = new JTable(model);

        p1.setLayout(new BorderLayout());
        JScrollPane js= new JScrollPane(tblTeste);
        p1.add(js, BorderLayout.CENTER);

        add(p,BorderLayout.EAST);
        add(p1,BorderLayout.CENTER);

        if(!dados.isEmpty()){
            preencharDados(dados);
            exibeDados(dados.get(ind));
        }
    }
    private  void preencherCB(MongoCollection<Document> colecao){
        try(MongoCursor<Document> cursor = colecao.find().sort(new Document("_id", 1)).iterator()){
            Document doc = null;
            a.removeAllItems();
            while(cursor.hasNext()){
                doc= cursor.next();
                a.addItem(doc.get("_id").toString() + " - " + doc.get("nomeCategoria"));
            }
        }
    }

    private void exibeDados(Document d){
       /* p.add(new JLabel("ID"));
        p.add(txt1);
        p.add(new JLabel("Nome"));
        p.add(txtNomeProduto);
        p.add(new JLabel("Quantidade de estoque"));
        p.add(txt2);
        p.add(new JLabel("ID da Categoria"));
        p.add(a);
        p.add(new JLabel("Unidade de medida"));
        p.add(cbUN);
        p.add(new JLabel("Preço"));
        p.add(txtPreco);*/
        txt1.setText(d.get("_id").toString());
        txtNomeProduto.setText(d.get("nomeProduto").toString());
        txt2.setText(d.get("qtdeEstoque").toString());
        int ind = Integer.parseInt(d.get("idCategoria").toString());
        for(int i = 0 ; i < a.getItemCount(); i++){
            String item = a.getItemAt(i);
            int indItem = Integer.parseInt(item.split(" - ")[0]);
            if(ind == indItem){
                a.setSelectedIndex(i);
                break;
            }
        }

        String un = d.get("unidadeMedida").toString();
        for(int i = 0 ; i < cbUN.getItemCount(); i++){
            String item = cbUN.getItemAt(i);
            String u = item.split(" - ")[0];
            if(un.equals(u)){
                cbUN.setSelectedIndex(i);
            }
        }

        txtPreco.setText(d.get("preco").toString());
    }

    private void preencharDados(ArrayList<Document> dados){
        model.setRowCount(0);
        for (Document dado : dados) {
            model.addRow(new Object[]{dado.get("_id"), dado.get("nomeProduto"), dado.get("qtdeEstoque"), dado.get("idCategoria"),dado.get("unidadeMedida"),dado.get("preco")});
        }
    }

    public void atualizaTela(){
        preencherCB(categoria);
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
