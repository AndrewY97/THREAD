import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


public class PruebaHilos extends JFrame implements ActionListener {
	JButton btn1,btn2;
	JTextArea txt1,txt2;
	int numero,n1,n2,n3=10000000;
	String p1="",p2="";
	String [] array=new String [10000000];
	JProgressBar barra;
	public PruebaHilos() {
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Encuestas Twitter");
		setSize(300,460);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JLabel lblTexto1 = new JLabel();
		lblTexto1.setText("Datos de encuestas:");
		lblTexto1.setBounds(2,0,120,30);
		add(lblTexto1);
		
		btn1 = new JButton();
		btn1.setText("Generar E:");
		btn1.addActionListener(this);
		btn1.setBounds(120,0,120,30);
		add(btn1);
		
		JLabel lblTexto2 = new JLabel();
		lblTexto2.setText("Datos:");
		lblTexto2.setBounds(88,30,250,60);
		add(lblTexto2);
		
		txt1 = new JTextArea();
		txt1.setBounds(70,70,40,90);
		add(txt1);
		
		JScrollPane scroll = new JScrollPane(txt1);
		scroll.setBounds(78,70,100,90);
		add(scroll);
		
		btn2 = new JButton();
		btn2.setText("Generar H:");
		btn2.addActionListener(this);
		btn2.setBounds(120,170,120,30);
		add(btn2);
		
		JLabel lblTexto3 = new JLabel();
		lblTexto3.setText("Generando:");
		lblTexto3.setBounds(2,210,120,30);
		add(lblTexto3);
		
		barra =new JProgressBar(0,100);
		barra.setBounds(70,214,160,20);
		barra.setStringPainted(true);
		add(barra);
		
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PruebaHilos();
			}
		});

}
	
class Hilo1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 1; i <= 10000000 ; i = i + 1)
		{
			if(array[i-1]=="SI") {
				p1=p2+p1+"SI \n";
				txt1.setText(p1);
			}
			if(array[i-1]=="NO"){
				p2=p1+p2+"NO \n";
				txt1.setText(p2);
			}
		}
		
	}
	
}

class Hilo2 implements Runnable{
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 1; i <= 100 ; i = i+1){
			barra.setValue(i);
			barra.repaint();
			try {
				Thread.sleep(50);
			}catch(Exception e) {	
			}
		}
		DefaultCategoryDataset dt = new DefaultCategoryDataset();
		dt.addValue(n1, "Twitter", "SI");
		dt.addValue(n2, "Twitter", "NO");
		JFreeChart barra =  ChartFactory.createBarChart3D("Encuesta Twitter", "Respuestas","Interacciones",dt,PlotOrientation.VERTICAL,true,true,true);
        ChartPanel cp = new ChartPanel(barra);
        cp.setBounds(2,240,260,170);
        add(cp);
	}
}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn1) {
			for(int i = 1; i <= 10000000 ; i = i + 1)
			{
				numero= (int)(Math.random()*100 + 1);
				//System.out.println(numero);
				if(numero%2==0) {
					array[i-1]="SI";
					n1=n1+1;
				}else {
					array[i-1]="NO";
					n2=n2+1;
				}
			}
			System.out.println(n1);
			System.out.println(n2);
			//System.out.println("------");
			Thread h1= new Thread(new Hilo1());
			
			//Paso 4: Invocar al metodo START del objeto Thread
			
			h1.start();
		}if(e.getSource()==btn2) {
			Thread h2= new Thread(new Hilo2());
			h2.start();
		}
	}   
}
