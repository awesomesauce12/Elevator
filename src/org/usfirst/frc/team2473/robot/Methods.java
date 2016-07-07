package org.usfirst.frc.team2473.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Methods {


	CANTalon motor;
	
	
	int end;
	int top;
	double totalHeight;
	int i = 0;
	private Joystick joy;
	
	int currentPos;
	int nextStop;
	
	double[] stops = {1500, 2500, 500, 0};
	int index;
	
	public Methods(){
		joy = new Joystick(0);
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
	
	public void runLoop(){
		switch(currentState){
			case DETERMINE: determine(); break;
			case RAISING: raising(); break;
			case LOWERING: lowering(); break;
			case STOPPING: stopping(); break;
			case STOP: stop(); break;			
		}
	}
	
	
	public void joyControl(){
		if(joy.getRawButton(3)){
    		motor.set(0.3);
    		
    	}
    	else if(joy.getRawButton(2)) {
    		motor.set(-0.3);
    	}
    	else{
    		motor.set(0);
    	}
		SmartDashboard.putString("DB/String 3", "Val: " + motor.getEncPosition());
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
		
		SmartDashboard.putString("DB/String 2", "determining");

		double diff = stops[i] - currentPos;
		if (diff>0){
			currentState = State.RAISING; 
			runLoop();
//			SmartDashboard.putString("DB/String 3", "raising");
		}
		else if (diff < 0){
			currentState = State.LOWERING; 
			runLoop();
//			SmartDashboard.putString("DB/String 3", "lowering");
		}
		else {
			currentState = State.STOP; 
			runLoop();
//			SmartDashboard.putString("DB/String 3", "stop");
		}
	}
	
	public void raising(){
		
		SmartDashboard.putString("DB/String 2", "raising for real");
//		SmartDashboard.putString("DB/String 5", "Val: " + motor.getEncPosition());
		
		double x = stops[i];
		
		if (x - motor.getEncPosition() <= 50){
			SmartDashboard.putString("DB/String 2", "Stopping");
//			stopping();
			motor.set(0.0);
			currentState = State.STOPPING; 
			runLoop();
		}
		else if (x - motor.getEncPosition() <= 100){
			SmartDashboard.putString("DB/String 1", "Slowing down");
			motor.set(0.2);
		}
		else{
			SmartDashboard.putString("DB/String 0", "Normal");
			motor.set(0.3);	
		}
	}
	
	public void lowering(){
		
		if (motor.getEncPosition() - stops[i] <= 50){
			SmartDashboard.putString("DB/String 2", "Stopping");
//			stopping();
			currentState = State.STOPPING; 
			runLoop();
		}
		else if (motor.getEncPosition() - stops[i] <= 100){
			motor.set(-0.2);
			SmartDashboard.putString("DB/String 1", "Slowing down");
		}
		else{
			SmartDashboard.putString("DB/String 0", "Normal");
			motor.set(-0.3);
		}

	}
	
	//adjustment
	public void stopping(){
		
		SmartDashboard.putString("DB/String 3", "Val: " + motor.getEncPosition());
		SmartDashboard.putString("DB/String 10", "STOPPING");

		
		if (stops[i] - motor.getEncPosition() > 50){
			//raise at 0.1 speed
//			motor.set(0.1);
			motor.set(0.15);
			SmartDashboard.putString("DB/String 4", "Going up");
		}
		else if(motor.getEncPosition() - stops[i] > 50 ){
			//set speed to -.05 (lower)
//			motor.set(-0.05);
			motor.set(-0.15);
			SmartDashboard.putString("DB/String 4", "Going down");
		}
		else{
			//set motor speed to 0.0 (stop)
//			stop(); 
			currentState = State.STOP; 
			runLoop();
		}		
	}
	public void stop(){
		
		SmartDashboard.putString("DB/String 5", "Val: " + motor.getEncPosition());
		
		//set motor speed to 0.0
		motor.set(0.0);
		if (i < 4){
			i++;	
			currentState = State.DETERMINE; 
			runLoop();
		}
		if (i == 4){
			motor.set(0.0);
		}
		SmartDashboard.putString("DB/String 6", "Stopped");
	}
	
	private enum State{
		DETERMINE,
		RAISING,
		LOWERING,
		STOPPING,
		STOP;
	}
	
	private State currentState;
	
}