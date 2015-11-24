#include <doxygen.h>
#include <ESP8266.h>
#include <SoftwareSerial.h>
#include <FastLED.h>
#define ESP8266_USE_SOFTWARE_SERIAL true
#define NUM_LEDS 10
#define DATA_PIN A2

CRGB leds[NUM_LEDS];
CRGB lightColors[3];

int loopCounter;
int fade;

SoftwareSerial altSerial(10, 11); // RX, TX
ESP8266 wifi(altSerial);

void setup() {

  altSerial.begin(9600);
  
  pinMode(13, OUTPUT);
  pinMode(DATA_PIN, OUTPUT);

  loopCounter = 0;
  fade = -1;
}

void initLeds() {
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
}

void loop() {

  if (wifi.kick()) {
    digitalWrite(13, HIGH);
  } else {
    digitalWrite(13, LOW);
  }

  delay(1000);
  digitalWrite(13, LOW);
  delay(1000);

  //showLeds();
  //blinkStatusLed();
  loopCounter++;
}


void showLeds() {
  for (int i = 1; i < NUM_LEDS; i++) {
    leds[i - 1].g = leds[i].g;
  }
  leds[NUM_LEDS - 1].g = leds[0].g;
  FastLED.show();
}

void blinkStatusLed() {
  digitalWrite(13, HIGH);
  delay(100);
  digitalWrite(13, LOW);
  delay(100);
}

