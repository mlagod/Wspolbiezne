package all;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Elevator extends Thread {
	
	int rodzaj; // 1, 2 lub 3
	int minFloor;
	int maxFloor; // pietro ograniczajace gorne dla windy
	int aktualnePietro = 0;
	final int FULL = 5; // tyle osob pomiesci winda
	int ileOsob = 0;
	
	int kierunek; // 1 = winda jedzie w gore, -1 = winda jedzie w dol
	
	Lock blokada = new ReentrantLock();
	
	
	
	public Elevator(int id){
		rodzaj = id;

		
		if(id == 2){
			
			maxFloor = Start.liczbaPieter;
			minFloor = (Start.liczbaPieter/2)+1;
			
			
		}else if(id == 1){
			
			maxFloor = Start.liczbaPieter/2;
			minFloor = 0;
			
		}else if(id == 3){
			
			minFloor = -2;
			maxFloor = 0;
		}
	}

	
	public void run(){
		
		ruchWindy();
	}
	
	public synchronized void ruchWindy(){
		
		while(true){
			
			if(rodzaj == 1){
				
				aktualnePietro = minFloor;
				
				akcjaWindy();
				
				for(int i = minFloor; i < maxFloor; i++){
					
					kierunek = 1;
					aktualnePietro = i;  // winda jest na pietrze
					
					
					
					if(aktualnePietro == maxFloor){
						kierunek = -1;
					}
					
					// aktualizacja pietra napis
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 0){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						
						spij();
					}
					
					akcjaWindy();
				}
				
				for(int i = maxFloor; i > minFloor; i--){
					
					kierunek = -1;
					aktualnePietro = i;
					
					if(i == minFloor){
						kierunek = 1;
					}
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 0){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						spij();
					}
					
					akcjaWindy();
				}
			
			}else if(rodzaj == 2){
				
				aktualnePietro = 0;
				kierunek = 1;
				
				akcjaWindy();
				
				for(int j = 0; j < Start.licznikTekstow; j++){
					if(j % 3 == 1){
					Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
					}
					spij();
				}
				for(int i = minFloor; i < maxFloor; i++){
					
					kierunek = 1;
					aktualnePietro = i;
					
					if(aktualnePietro == maxFloor){
						
						kierunek = -1;
					}
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 1){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						spij();
					}
					
					akcjaWindy();
				}
				
				for(int i = maxFloor; i >= minFloor; i--){
					
					kierunek = -1;
					aktualnePietro = i;
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 1){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						spij();
					}
					
					akcjaWindy();
				}
				
				if(aktualnePietro == minFloor){   
					aktualnePietro = 0;
					kierunek = 1;
					
					
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 1){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						spij();
					}
					
					akcjaWindy();
				}
			
			}else if(rodzaj == 3){
				
				aktualnePietro = 0;
				kierunek = -1;
				
				akcjaWindy();
				
				for(int i = 0; i > minFloor; i--){
					
					kierunek = -1;
					aktualnePietro = i;
					
					if(aktualnePietro == minFloor){
						
						kierunek = 1;
					}
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 2){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
					}
					spij();
					
					akcjaWindy();
					
				}
				
				for(int i = minFloor; i < maxFloor; i++){
					
					kierunek = 1;
					aktualnePietro = i;
					
					if(i == maxFloor){
						kierunek = -1;
					}
					
					for(int j = 0; j < Start.licznikTekstow; j++){
						if(j % 3 == 2){
						Start.pietra.get(j).setText("      Winda jest na pietrze: " + aktualnePietro);
						}
						spij();
					}
					
					akcjaWindy();
				}
			}
		}
	}
	
	
	private void spij(){
		
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void validate(){
		
		if(ileOsob > 0){
			ileOsob--;
		}
	}
	
	private void wysiadajZWindy(){
		
		
	
		try{
			
		for(int i = 0; i < Start.kolejkaOut[aktualnePietro].size();i++){
			
			if(aktualnePietro == Start.kolejkaOut[aktualnePietro].get(i).cel){
				
				blokada.lock();
				Start.kolejkaOut[aktualnePietro].get(i).reached = true;
				Start.kolejkaOut[aktualnePietro].remove(i);
				validate();
				blokada.unlock();
				
				// todo: dodawanie do kolejki na pietrze
				
			}
		}
		
		}catch(ArrayIndexOutOfBoundsException e){
			
			
			
			for(int i = 0; i < Start.kolejkaOut[Start.liczbaPieter - aktualnePietro].size();i++){
				
				if(aktualnePietro == Start.kolejkaOut[Start.liczbaPieter - aktualnePietro].get(i).cel){
					
					blokada.lock();
					Start.kolejkaOut[Start.liczbaPieter - aktualnePietro].get(i).reached = true;
					Start.kolejkaOut[Start.liczbaPieter - aktualnePietro].remove(i);
					validate();
					blokada.unlock();
					
				}
			}
			
		}
	
	}
	
	private void wsiadajDoWindy(){
		
		if(ileOsob < FULL){
			
			try{
			
			for(int k = 0; k < Start.listaPasazerow.size(); k++){
				
				if(aktualnePietro == Start.listaPasazerow.get(k).pietro && kierunek == Start.listaPasazerow.get(k).kierunekPasazera){
					
					blokada.lock();
					Start.kolejkaOut[Start.listaPasazerow.get(k).cel].add(Start.listaPasazerow.get(k));
					ileOsob++;
					
					if(Start.listaPasazerow.get(k).kierunekPasazera == 1){
						Start.kolejkaUp[aktualnePietro].poll();
					
					}else if(Start.listaPasazerow.get(k).kierunekPasazera == -1){
						
						Start.kolejkaDown[aktualnePietro].poll();
					}
					blokada.unlock();
				}
			}
			
			}catch(ArrayIndexOutOfBoundsException e){
				
				for(int k = 0; k < Start.listaPasazerow.size(); k++){
					
					if(aktualnePietro == Start.listaPasazerow.get(k).pietro && kierunek == Start.listaPasazerow.get(k).kierunekPasazera){
						
						blokada.lock();
						Start.kolejkaOut[Start.liczbaPieter - Start.listaPasazerow.get(k).cel].add(Start.listaPasazerow.get(k));
						ileOsob++;
						
						if(Start.listaPasazerow.get(k).kierunekPasazera == 1){
							Start.kolejkaUp[Start.liczbaPieter - aktualnePietro].poll();
						
						}else if(Start.listaPasazerow.get(k).kierunekPasazera == -1){
							
							Start.kolejkaDown[Start.liczbaPieter - aktualnePietro].poll();
						}
						blokada.unlock();
					}
				}
			}
			}
	}
	
	private void akcjaWindy(){
		
		wysiadajZWindy();
		wsiadajDoWindy();
		
	}
}
