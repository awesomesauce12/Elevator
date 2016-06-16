# Elevator

Software Design Document


**3 USER INPUTS: stop1, stop2, stop3

Start at bottom
Know if we have a tote
**User input (human)
Sensor (US, IR)
Know if it’s at the very bottom (if not, move down)
**Limit switch
Hard stop
Encoder position at expected value
Reset encoder value to known value (0)
Print to dashboard/logger
Lifting (before it has reached the 1st encoder stop)
ACTION
Set speed
Consider weight
Consider encoder difference
Ground 0: 
Determine if we need to slow down as we approach stop (threshold?)
If it passes (temperature) threshold on motor → stop.
No matter where, always go back to expected stop
Display current encoder value

DETERMINE 1ST STOP
If feet → conversion
Before executing: algorithm with predetermined conversions
Print to dashboard/logger
Slow down before stop
Threshold/buffer
See above
Stopped
Account for overshooting
Threshold
Stop where it is?
Bring it to goal?
^^ Consensus: If over 1 in threshold, bring it in. Otherwise, leave it
PID (stretch)
Weight of the tote
PID (maybe)
Test to see how it reacts
Safety
Temperature → kill. Threshold around limit switches or has reached maxSpeed.
Going from Stop1 to Stop2, Stop2 to Stop3
ACTION
Speed
Account for weight
Consider difference between the 2 points
Determine if we need to slow down as we approach stop (threshold?)
Display current encoder value

DETERMINE 1ST STOP

Conversion from feet → encoder values

Before executing: algorithm with predetermined conversions

Slow down before stop

Threshold/buffer

Going down

Same idea, but account for gravity/look more at the speed and resistance 

Bottom limit switch

Going down to bottom
Same as “going down” but account for bottom limit switch
Threshold for limit switch
SAFETY
Temperature/current of motor
Limit switch buffer
Speed of motor
