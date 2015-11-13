#include <SoftwareSerial.h>

#include <FastLED.h>
#define NUM_LEDS 10
#define DATA_PIN A2

CRGB leds[NUM_LEDS];
CRGB lightColors[3];
int loopCounter;
int fade;

SoftwareSerial mySerial(10, 11); // RX, TX

void setup() {

  Serial.begin(115200);
  /*while (!Serial) {
    ; // wait for serial port to connect. Needed for native USB port only
  }
  */
  Serial.println("Hello");

  // set the data rate for the SoftwareSerial port
  mySerial.begin(115200);
  mySerial.println("Hello, world?");
  
  pinMode(13, OUTPUT);
  pinMode(A2, OUTPUT);
  FastLED.addLeds<WS2812B, DATA_PIN>(leds, NUM_LEDS);

  lightColors[0].r = 10;
  lightColors[1].g = 10;
  lightColors[2].b = 10;


  leds[0].g = 10;
  for (int i = 0; i < 4; i++) {
    leds[i + 1].g = (i + 1) * 40;
  }
  for (int i = 0; i < 4; i++) {
    leds[i + 5].g = 180 - i * 30;
  }
  leds[9].g = 10;

  loopCounter = 0;
  fade = -1;
}

void loop() {

  if (mySerial.available()) {
    Serial.write(mySerial.read());
  }
  if (Serial.available()) {
    mySerial.write(Serial.read());
  }
  
  for (int i = 1; i < NUM_LEDS; i++) {
    leds[i - 1].g = leds[i].g;
  }
  leds[NUM_LEDS - 1].g = leds[0].g;
  FastLED.show();
  delay(100);
  loopCounter++;
}
