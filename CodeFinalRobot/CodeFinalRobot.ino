// define motor control pins
#include <Servo.h>
#include <AccelStepper.h>

// Define stepper motor connections and steps per revolution
#define DIR_PIN 2
#define STEP_PIN 10
#define STEPS_PER_REVOLUTION 200

// Define motor angle limits
#define ANGLE_MIN -90
#define ANGLE_MAX 90
//variables bluetooth
int a;
int m;
int x;
char c;
String inpute;


// Initialize stepper motor object
AccelStepper stepper(AccelStepper::DRIVER, STEP_PIN, DIR_PIN);

// Define initial position and target position
int currentPosition = 0;
int targetPosition = 0;

const int ENA = 11;
const int IN1 = 8;
const int IN2 = 7;
const int ENB = 3;
const int IN3 = 5;
const int IN4 = 4;

//const int servoPin = 9;
//const int myservoPin = 6;

// set initial motor speed
int motorSpeed1 = 0;
int motorSpeed2 = 0;
int servoAngle = 0;


Servo servo;
Servo myservo;


void setup() {
  // initialize serial communication
  Serial.begin(9600);
  //Pin Laser
  pinMode(LED_BUILTIN, OUTPUT);
  

  // set motor control pins as outputs
  pinMode(ENA, OUTPUT);
  pinMode(IN1, OUTPUT);
  pinMode(IN2, OUTPUT);
  pinMode(ENB, OUTPUT);
  pinMode(IN3, OUTPUT);
  pinMode(IN4, OUTPUT);
  digitalWrite(IN1, LOW);
	digitalWrite(IN2, LOW);
	digitalWrite(IN3, LOW);
	digitalWrite(IN4, LOW);

  servo.write(0);
  servo.attach(9); 

  
  myservo.write(110);
  myservo.attach(6);
 
  

  // Set maximum speed and acceleration
  stepper.setMaxSpeed(1000);
  stepper.setAcceleration(500);
  // Set motor direction
  stepper.setPinsInverted(false, true, false);
  // Set initial position as zero
  stepper.setCurrentPosition(0);

}

void loop() {
  // read input from serial monitor
  //if (Serial.available() > 0) {
  inpute = "";
  
  if(Serial.available() > 0 ){
    a = Serial.read();
    //Serial.println(a);

    if (a == 1){
      inpute = 'a';

    }
    else if (a==2){
      inpute = 'b';
    }
    else if(a == 3){
      inpute = 'c';
    }
    else if(a == 4){
      inpute = 'd';
    }
    else if(a == 5){
      inpute = 'e';
    }
    else if(a == 6){
      inpute = 'f';
    }
    else if(a == 7){
      inpute = 'g';
    }
    else if(a == 8){
      inpute = 'i';
    }
    else if(a == 9){
      inpute = 'j';
    }
    
    else if (a == 10){
      inpute = 'h';
    }
    else if ( a == 11){
      inpute = 'k';
    }
    
    
    // else{
    //     m = a % 10;
    //     x = a /10;
    //     switch (m){
    //       case 1:
    //         c = 'a';
    //         test = 10;
    //         break;
    //       case 2:
    //         c = 'b';
    //         test = 10;
    //         break;
    //       default:
    //         break; 
    //     }
    //}

  
  // switch (m){
  //   case 1:
  //     c = 'a';
  //     break;
  //   case 2:
  //     c = 'b';
  //     break;
  //   // case 3:
  //   //   c = 'c';
  //   //   break;
  //   // case 4:
  //   //   c = 'd';
  //   //   break;
  //   // case 5:
  //   //   c = 'e';
  //   //   break;
  //   // case 6:
  //   //   c = 'f';
  //   //   break;

  //   default:
  //      Serial.println("Value outside 1,2,3 received");
  //        break;
  // }


  
    

  Serial.println(inpute);
  
  
    //char inpute = Serial.read();
    for(int i = 0; i < sizeof(inpute); i++){
      Serial.println(inpute[i]);
      switch (inpute[0]) {
        case 'a': // increase motor speed
          
          if (motorSpeed1<100 && motorSpeed2<100){
            motorSpeed1 += 100;
            motorSpeed2 += 100;
          }
          else if (motorSpeed1 >= 100 && motorSpeed2 >= 100){
            motorSpeed1 += 25;
            motorSpeed2 += 25;
          }
          else if (motorSpeed1 > 255 && motorSpeed2 > 255) {
            motorSpeed1 = 250;
            motorSpeed2 = 250;
          }
          

          break;
          
        case 'b': // decrease motor speed
          if (motorSpeed1 < 0 && motorSpeed2 < 0) {
            motorSpeed1 = 0;
            motorSpeed2 = 0;
          }
          else if (motorSpeed1<=100 && motorSpeed2<=100){
            motorSpeed1 -= 100;
            motorSpeed2 -= 100;
          }
          else if (motorSpeed1 > 100 && motorSpeed2> 100){
            motorSpeed1 -= 25;
            motorSpeed2 -= 25;
          }
          
          

          break;
        case 'c':
          servoAngle += 20;
          servo.write(servoAngle);
          break;
        // Decrease the angle of the servo motor
        case 'd':
          servoAngle -= 20;
          servo.write(servoAngle);
          break;
        case 'f':
          // Increase target position by 10 degrees
          targetPosition = currentPosition + 5;
          break;
        case 'e':
          // Decrease target position by 10 degrees
          targetPosition = currentPosition - 5;
          break;
        //shoot
        case 'g':
         
          myservo.write(175);  
          
          break;
          
        case 'h':
          
          myservo.write(110);  
          break;    
        //Allumer LED 
        case 'i':
          digitalWrite(LED_BUILTIN, HIGH);      
          break;
        //eteindre LED
        case 'j':
          digitalWrite(LED_BUILTIN, LOW);
          break;
        
        case 'k': 
          digitalWrite(LED_BUILTIN,LOW);
          servo.write(0);
          motorSpeed1 = 0;
          motorSpeed2 = 0;
          break; 
          
        default:
          // Invalid command
          //Serial.println("Invalid command");
          break;

        
      }
      inpute = "";
    }
      // Check if the target position is within the angle limits
    if (targetPosition < ANGLE_MIN) {
      targetPosition = ANGLE_MIN;
    } else if (targetPosition > ANGLE_MAX) {
      targetPosition = ANGLE_MAX;
    }

    // Move the motor to the target position
    int targetSteps = map(targetPosition, ANGLE_MIN, ANGLE_MAX, -STEPS_PER_REVOLUTION / 4, STEPS_PER_REVOLUTION / 4);
    int currentSteps = stepper.currentPosition();
    int stepsToMove = targetSteps - currentSteps;
    stepper.move(stepsToMove);

    // Wait for motor to complete movement
    while (stepper.distanceToGo() != 0) {
      stepper.run();
    }
    analogWrite(ENB, motorSpeed1);
    analogWrite(ENA, motorSpeed2);
    digitalWrite(IN1, LOW);
    digitalWrite(IN2, HIGH);
    digitalWrite(IN3, LOW);
    digitalWrite(IN4, HIGH);
    Serial.println(servoAngle);       
          
    // Update the current position
    currentPosition = map(stepper.currentPosition(), -STEPS_PER_REVOLUTION / 4, STEPS_PER_REVOLUTION / 4, ANGLE_MIN, ANGLE_MAX);
  } 

}
