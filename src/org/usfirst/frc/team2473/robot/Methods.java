package org.usfirst.frc.team2473.robot;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.CANTalon;

public class Methods {
	
	CANTalon motor;
	
	
	int end;
	int top;
	int totalHeight;
	int i = 0;
	
	int currentPos;
	int nextStop;
	
	int[] stops = {2,5,1,0}; //user inputs
	int index;
	
	public Methods(){
		motor = new CANTalon(5);
		
		index = 0; //index of "stops"
		
		top = 3000; //predetermined: encoder value for top of the elevator
		totalHeight = 7; //total height in feet of the elevator
		
		currentPos = 0; // current encoder value
	}
	
	
	//convert feet (input) to encoder values
	public void conversion(){
		//Given: stop123 in FEET
		//Goal: stop123 in ENCODER --> make this the new value of "stop123"
		
		stops[0] = stops[0] * top / totalHeight;
		stops[1] = stops[1] * top / totalHeight;
		stops[2] = stops[2] * top / totalHeight;
	}
	
	//determine if raising or lowering
	public void determine(){
		//Given: Which stop we are currently at --> currentStop
		//Goal: determine if we're going up or down according to our current stop and the next stop

		int diff = stops[i] - currentPos;
		if (diff>0){
			//raise	
			}
		else if (diff < 0){
				//lower
		}
		else {
				//stop
		}
	}
	
	public void raising(){
		if (stops[i] - motor.getEncPosition() <= 5){
			//stop
		}
		else if (stops[i] - motor.getEncPosition() <= 10){
			//slow the speed to 0.05
		}
		else{
			//speed set at 0.2
		}
	}
	
	public void lowering(){
		if (motor.getEncPosition() - stops[i] <= 5){
			//stop
		}
		else if (motor.getEncPosition() - stops[i] <= 10){
			//slow the speed to -.02
		}
		else{
			//speed set at -.15
		}
	}
	
	//adjustment
	public void stopping(){
		if (stops[i] - motor.getEncPosition() > 5){
			//raise at 0.05 speed
			motor.set(0.05);
		}
		else if(motor.getEncPosition() - stops[i] > 5 ){
			//set speed to -.02 (lower)
			motor.set(-0.02);
		}
		else{
			//set motor speed to 0.0 (stop)
			stop(); 
		}		
	}
	public void stop(){
		//set motor speed to 0.0
		motor.set(0.0);
	}
}