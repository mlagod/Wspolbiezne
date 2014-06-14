
package all;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

public class Person extends Thread {

	int pietro;
	int cel;
	boolean reached;
	Direction kierunekPasazera; // 1 - gora, -1 = dol
	String name;
	
	 static Random r = new Random();
	
	public Person(String s){
		name = s;		
		
		Start.osoby.add(new JLabel(name));
	}
	
	Lock lock = new ReentrantLock();
	
	
	public void run(){
		akcja();
	}
	
	
	private synchronized void losujPietroStart(){
		
		pietro = r.nextInt(Start.liczbaPieter+3);
		pietro -= 2;
		
		
		
	}
	
	private synchronized void losujPietroCel(){
		
	//	Random r = new Random();
		cel = r.nextInt(Start.liczbaPieter+3);
		cel -= 2;
		
		if(cel == pietro){
			cel = (cel+1) % Start.liczbaPieter;
			cel -= 2;
		}
		
		if(cel > pietro){	// chce jechac do gory
			kierunekPasazera = Direction.Up;
		}else if(cel < pietro){
			
			kierunekPasazera = Direction.Down;
		}
		
		reached = false;
		
		
	}
	
	public void akcja(){

		losujPietroStart();
		
		while(true){
			
			if(reached == true){
				
				losujPietroCel();				
				
				// wyswietlanie stanu pasazerow
				for(int i = 0; i < Start.osoby.size(); i++){
					Start.osoby.get(i).setText("osoba " + i + " : " + pietro + " -> " + cel + ";           ");
				}
				
				if(kierunekPasazera == Direction.Up){	//jesli chce jechac do gory, dodaj do kolejki do gory
					
					try{
					
					lock.lock();
					Start.kolejkaUp[pietro].add(this);	// sekcja krytyczna
					lock.unlock();
					
					}catch(ArrayIndexOutOfBoundsException e){
						
						lock.lock();
						Start.kolejkaUp[Start.liczbaPieter - pietro].add(this);
						lock.unlock();
					}
					
				}else if(kierunekPasazera == Direction.Down){	// jesli chce jechac do dolu, dodaj do kolejki do dolu
					
					try{
					
					lock.lock();
					Start.kolejkaDown[pietro].add(this);	// sekcja krytyczna
					lock.unlock();
				
					}catch(ArrayIndexOutOfBoundsException e){
						
						lock.lock();
						Start.kolejkaDown[Start.liczbaPieter - pietro].add(this);
						lock.unlock();
					}
					}
			}
		}
	}
}
