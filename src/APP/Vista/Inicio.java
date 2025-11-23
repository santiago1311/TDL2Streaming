package APP.Vista;
import javax.swing.*;


public class Inicio extends JFrame{
    private JButton botonRegistrar;
    private JButton botonLogin;

    public Inicio(){
        botonRegistrar = new JButton("Registrar");
        botonLogin = new JButton("Login");
        setTitle("Bienvenido a TDL2 Streaming");
        setSize(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.FlowLayout());
        add(botonRegistrar);
        add(botonLogin);
    }
    public static void main (String[] args){
        Inicio inicio = new Inicio();
        inicio.setVisible(true);
    }
}
