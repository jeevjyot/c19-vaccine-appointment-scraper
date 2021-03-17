# Appointment-watcher

This console app just run scrapes the HTML from the appointment website
and tries to find the available slot.

## Features
1. Tries to find the available slot
2. Sends text (using twilio api) to your specified number 

---
Note: Accepting PR


## How to RUN
 Clone the console app, then run following app from root directory
 ```groovy
$./gradlew build 
```
```groovy
$./gradlew run
```

You can also directly run from intelliJ IDE (or any other preferred)  

Note: You would need access to twilio api: Please create account using 
www.twilio.com/referral/QMkbwm

Also, accepting PR