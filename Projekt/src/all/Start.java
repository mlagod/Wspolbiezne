package all;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Start {

	public static int liczbaPasazerow;
	public static int liczbaPieter;
	public static int liczbaWind;
	
	static List<Elevator> listaWind = new ArrayList<Elevator>();
	static List<Person> listaPasazerow = new ArrayList<Person>();
	
	static List<JPanel> panel = new ArrayList<JPanel>();
	static List<JLabel> nazwy = new ArrayList<JLabel>();
	static List<JLabel> pietra = new ArrayList<JLabel>();
	static List<JLabel> osoby = new ArrayList<JLabel>();
	static List<JLabel> pasazerowieWwindzie = new ArrayList<JLabel>();  // 
	
	
	static List<JLabel>[] osobyInElevator;
	
	static List<Person>[] kolejkaOut;  //kolejka osob w windzie wysiadajacych na i-tym pietrze
	static Queue<Person>[] kolejkaUp;	// kolejka osob jadacych do gory na i-tym pietrze
	static Queue<Person>[] kolejkaDown; // kolejka osob jadacych na dol na i-tym pietrze
	
	
	
	static int licznikTekstow;


public void dzialaj(){
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("Podaj liczbe pasazerow : ");
		liczbaPasazerow = s.nextInt();
		
		System.out.println("Podaj liczbe pieter : ");
		liczbaPieter = s.nextInt();
		
		System.out.println("Podaj liczbe wind (podzielna przez 3 stworzy tyle samo wind kazdego rodzaju) : ");
		liczbaWind = s.nextInt();

		
		
		kolejkaOut = new List[liczbaPieter+3];	// dwa ostatnie miejsca listy to pietra podziemne
		kolejkaUp = new Queue[liczbaPieter+3];
		kolejkaDown = new Queue[liczbaPieter+3];
		osobyInElevator = new List[liczbaWind];
		
		//tworzenie kolejek
		for(int i = 0; i < liczbaPieter+3; i++){
			
		
			kolejkaOut[i] = new ArrayList<Person>();
			kolejkaUp[i] = new LinkedBlockingQueue<Person>();
			kolejkaDown[i] = new LinkedBlockingQueue<Person>();
		}
		
		
		// dodanie nazw do wind
				for(int i = 0; i < liczbaWind; i++){
					
					// tworzenie wind
					listaWind.add(new Elevator((i%3)+1));
					listaWind.get(i).setName("Winda " + ((i%3)+1) + "." + ((i+1)/4));
					// reprezetacja graficzna
					panel.add(new JPanel());
					
					pietra.add(new JLabel()); 
					licznikTekstow++;
					
					pasazerowieWwindzie.add(new JLabel("Pasazerowie w windzie : "));
					
					
					
					if(listaWind.get(i).rodzaj == 1){
					
						nazwy.add(new JLabel("Rodzaj " + listaWind.get(i).rodzaj));
						
						
						panel.get(i).setBackground(Color.red);
						panel.get(i).add(nazwy.get(i));
						panel.get(i).add(pietra.get(i));
						
						pietra.get(i).setText("      Winda jest na pietrze: ");
						
					}else if(listaWind.get(i).rodzaj == 2){
						
						nazwy.add(new JLabel("Rodzaj " + listaWind.get(i).rodzaj));
						
						panel.get(i).setBackground(Color.yellow);
						panel.get(i).add(nazwy.get(i));
						panel.get(i).add(pietra.get(i));
						
						pietra.get(i).setText("      Winda jest na pietrze: ");
						
					}else if(listaWind.get(i).rodzaj == 3){
						
						nazwy.add(new JLabel("Rodzaj " + listaWind.get(i).rodzaj));
						
						panel.get(i).setBackground(Color.green);
						panel.get(i).add(nazwy.get(i));
						panel.get(i).add(pietra.get(i));
						
						pietra.get(i).setText("      Winda jest na pietrze: ");
					}
					
					//wyswietlanie osob w windzie
					osobyInElevator[i] = new ArrayList<JLabel>();
					osobyInElevator[i].add(new JLabel());
					
						for(int j = 0; j < osobyInElevator[i].size(); j++){

							panel.get(i).add(osobyInElevator[i].get(j));
						}
				}
				
				for(int i = 0; i < liczbaWind; i++){
					
					panel.get(i).add(pasazerowieWwindzie.get(i));
				}
				
				
				for(int i = 0; i < liczbaPasazerow; i++){
					
					
					listaPasazerow.add(new Person(""+ i));
					
				}
				
				
				
				for(int i = 0; i < liczbaWind; i++){
					listaWind.get(i).start();
				}
				
for(int i = 0; i < liczbaPasazerow; i++){
					
					listaPasazerow.get(i).start();
					
				}

}


}
