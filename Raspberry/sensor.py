import pyrebase
import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)
GPIO.setup(17, GPIO.IN)
GPIO.setup(5, GPIO.IN)
GPIO.setup(26, GPIO.IN)

config = {
  "apiKey": "z1KxoVWGu6bwciNM6hAIoVhtF3XtFLDvj7V27Cca",
  "authDomain": "test-robo-e8aa0.firebaseapp.com",
  "databaseURL": "https://test-robo-e8aa0-default-rtdb.firebaseio.com",
  "storageBucket": "test-robo-e8aa0.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()


while True:
    def getValue():
       score = db.child('score').get()
       for obj in score.each():
          if(obj.key() == 'cuurentScore'):
             current_score = obj.val()
       return current_score
    
    if GPIO.input(17) == 0 :
        if GPIO.input(17) == 0:
          print('la balle vient de passer ', 'droite')
          current_score= getValue()
          score = 20 + current_score
          db.child("score").child("cuurentScore").set(score)
          time.sleep(0.5)

    elif  GPIO.input(26) == 0 :
        time.sleep(0.05)
        if GPIO.input(26) == 0:
          print('la balle vient de passer ', 'milieu')
          current_score= getValue()
          score = 10 + current_score
          db.child("score").child("cuurentScore").set(score)
          time.sleep(0.5)

    elif  GPIO.input(26) == 0 :
      time.sleep(0.05)
      if GPIO.input(26) == 0:
        print('la balle vient de passer ', 'gauche')
        current_score= getValue()
        score = 30 + current_score
        db.child("score").child("cuurentScore").set(score)
        time.sleep(0.5)
    else:
        time.sleep(0.5)
    
