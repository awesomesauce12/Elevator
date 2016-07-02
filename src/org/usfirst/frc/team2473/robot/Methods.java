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
	
	double[] stops = {1457,2499,625,0}; //user inputs  //{0.35, 0.55, 0.20,0}
	int index;
	
	public Methods(){
		motor = new CANTalon(5);
		
		index = 0; //index of "stops"
		
		top = 3124; //predetermined: encoder value for top of the elevator
		totalHeight = 0.75; //total height in feet of the elevator*100
		
		currentPos = 0; // current encoder value
		
		motor.changeControlMode(CANTalon.TalonControlMode.Position);
    	motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	motor.setPosition(0);
    	motor.enableControl();
    	
    	motor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	
//    	stops[0] = stops[0] * (top / totalHeight);
//		stops[1] = stops[1] * (top / totalHeight);
//		stops[2] = stops[2] * (top / totalHeight);
	}
	
	
	//convert feet (input) to encoder values
	public void conversion(){
		//Given: stops in FEET
		//Goal: stops in ENCODER --> make these values the new values in "stops"
		
		stops[0] = stops[0] * (top / totalHeight);
		stops[1] = stops[1] * (top / totalHeight);
		stops[2] = stops[2] * (top / totalHeight);
		
		SmartDashboard.putString("DB/String 7", "" + stops[0]);
		SmartDashboard.putString("DB/String 8", "" + stops[1]);
		SmartDashboard.putString("DB/String 9", "" + stops[2]);

	}
	
	//determine if raising or lowering
	public void determine(){
		//Given: Which stop we are currently at --> currentStop
		//Goal: determine if we're going up or down according to our current stop and the next stop

		double diff = stops[i] - currentPos;
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
			SmartDashboard.putString("DB/String 2", "Stopping");
//			stopping();
			motor.set(0);
			SmartDashboard.putString("DB/String 9", "Stopped");
		}
		else if (stops[i] - motor.getEncPosition() <= 200){
			SmartDashboard.putString("DB/String 1", "Slowing down");
			motor.set(0.1);
		}
		else{
			SmartDashboard.putString("DB/String 0", "Normal");
			motor.set(0.25);	
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
		
		SmartDashboard.putString("DB/String 3", "Val: " + motor.getEncPosition());
		
		if (stops[i] - motor.getEncPosition() > 50){
			//raise at 0.1 speed
			motor.set(0.1);
			SmartDashboard.putString("DB/String 4", "Going up");
		}
		else if(motor.getEncPosition() - stops[i] > 50 ){
			//set speed to -.05 (lower)
			motor.set(-0.05);
			SmartDashboard.putString("DB/String 4", "Going down");
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
		if (i < 4){
			i++;		
		}
		SmartDashboard.putString("DB/String 6", "Stopped");
//		determine();
	}
}
