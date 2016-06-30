package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Methods {
	
	CANTalon motor;
	
	
	int end;
	int top;
	double totalHeight;
	int i = 0;
	
	int currentPos;
	int nextStop;
	
	int[] stops = {2,5,1,0}; //user inputs
	int index;
	
	public Methods(){
		motor = new CANTalon(5);
		
		index = 0; //index of "stops"
		
		top = 3124; //predetermined: encoder value for top of the elevator
		totalHeight = 0.75; //total height in feet of the elevator
		
		currentPos = 0; // current encoder value
		
		motor.changeControlMode(CANTalon.TalonControlMode.Position);
    	motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	motor.setPosition(0);
    	motor.enableControl();
    	
    	motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	
	//convert feet (input) to encoder values
	public void conversion(){
		//Given: stop123 in FEET
		//Goal: stop123 in ENCODER --> make this the new value of "stop123"
		
		stops[0] = (int)(stops[0] * top / totalHeight);
		stops[1] = (int)(stops[1] * top / totalHeight);
		stops[2] = (int)(stops[2] * top / totalHeight);
	}
	
	//determine if raising or lowering
	public void determine(){
		//Given: Which stop we are currently at --> currentStop
		//Goal: determine if we're going up or down according to our current stop and the next stop

		int diff = stops[i] - currentPos;
		if (diff>0){
			raising();	
		}
		else if (diff < 0){
			lowering();
		}
		else {
			stop();
		}
	}
	
	public void raising(){
		
		SmartDashboard.putString("DB/String 5", "Val: " + motor.getEncPosition());
		
//		stops[i] = 1500;
		
		if (stops[i] - motor.getEncPosition() <= 50){
			SmartDashboard.putString("DB/String 0", "Stopping");
			stopping();
		}
		else if (stops[i] - motor.getEncPosition() <= 100){
			motor.set(0.1);
			SmartDashboard.putString("DB/String 0", "Slowing down");
		}
		else{
			motor.set(0.25);
			SmartDashboard.putString("DB/String 0", "Normal");
		}
	}
	
	public void lowering(){
		
		if (motor.getEncPosition() - stops[i] <= 50){
			stopping();
		}
		else if (motor.getEncPosition() - stops[i] <= 100){
			motor.set(-0.05);
		}
		else{
			motor.set(-0.2);
		}

	}
	
	//adjustment
	public void stopping(){
		
		SmartDashboard.putString("DB/String 5", "Val: " + motor.getEncPosition());
		
		if (stops[i] - motor.getEncPosition() > 50){
			//raise at 0.1 speed
			motor.set(0.1);
			SmartDashboard.putString("DB/String 1", "Going up");
		}
		else if(motor.getEncPosition() - stops[i] > 50 ){
			//set speed to -.05 (lower)
			motor.set(-0.05);
			SmartDashboard.putString("DB/String 1", "Going down");
		}
		else{
			//set motor speed to 0.0 (stop)
			stop(); 
		}		
	}
	public void stop(){
		
		SmartDashboard.putString("DB/String 5", "Val: " + motor.getEncPosition());
		
		//set motor speed to 0.0
		motor.set(0.0);
		i++;
		SmartDashboard.putString("DB/String 1", "Stopped");
	}
}
