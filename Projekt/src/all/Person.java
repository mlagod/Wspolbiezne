package all;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JLabel;

public class Person extends Thread {

	int pietro;
	int cel;
	boolean reached = true;
	int kierunekPasazera; // 1 - gora, -1 = dol
	String name;
	
	public Person(String s){
		name = s;
		
		Start.osoby.add(new JLabel(name));
	}
	
	Lock lock = new ReentrantLock();
	
	
	public void run(){
		akcja();
	}
	
	
	private void losujPietroStart(){
		
		Random r = new Random();
		pietro = r.nextInt(Start.liczbaPieter+3);
		pietro -= 2;
		
	}
	
	private void losujPietroCel(){
		
		Random r = new Random();
		cel = r.nextInt(Start.liczbaPieter+3);
		cel -= 2;
		
		if(cel == pietro){
			cel = (cel+1) % Start.liczbaPieter;
			cel -= 2;
		}
		
		if(cel > pietro){	// chce jechac do gory
			kierunekPasazera = 1;
		}else if(cel < pietro){
			kierunekPasazera = -1;
		}
		
		reached = false;
	}
	
	public void akcja(){
		
		losujPietroStart();
		
		while(true){
			
			if(reached == true){
				losujPietroCel();
				
				if(kierunekPasazera == 1){	//jesli chce jechac do gory, dodaj do kolejki do gory
					
					try{
					
					lock.lock();
					Start.kolejkaUp[pietro].add(this);	// sekcja krytyczna
					lock.unlock();
					
					}catch(ArrayIndexOutOfBoundsException e){
						
						lock.lock();
						Start.kolejkaUp[Start.liczbaPieter - pietro].add(this);
						lock.unlock();
					}
					
				}else if(kierunekPasazera == -1){	// jesli chce jechac do dolu, dodaj do kolejki do dolu
					
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
