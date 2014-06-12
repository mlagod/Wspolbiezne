package all;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Window {
	


	public void setup(){

		
		
	JFrame okno = new JFrame("Programowanie wspó³bie¿ne - Mateusz £agód");	
	
	Dimension d = new Dimension(200,200);
	Dimension pas = new Dimension(600,100);
	
	JPanel pasazerPanel = new JPanel();
	JLabel pasazerLabel = new JLabel("Pasazerowie");
	
	
	
	okno.setLayout(new FlowLayout());
	pasazerPanel.setLayout(new FlowLayout());
	
	pasazerPanel.setPreferredSize(pas);
	pasazerPanel.setBackground(Color.gray);
	
	pasazerPanel.add(pasazerLabel);
	
	
	for(int i = 0; i < Start.osoby.size(); i++){
		
		pasazerPanel.add(Start.osoby.get(i));
	}
	
	for(int i = 0; i < Start.panel.size(); i++){
		
		Start.panel.get(i).setPreferredSize(d);
		okno.add(Start.panel.get(i));
		
	}
	
	okno.add(pasazerPanel);

	okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	okno.setSize(640,480);

	okno.setVisible(true);
	
	
	
	}

}
