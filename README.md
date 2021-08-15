# Intelligent Pi Camera 📸

An intelligent, people recognising camera module for the Raspberry Pi,
powered by Deep Learning Models. This JVM application wraps a Deep Learning model
and gets inferences from it in real time (every 1000 milliseconds). 
Since this application (with the DL model) is to be deployed onto the same hardware
(Raspberry Pi) as a presumably high-definition camera, it is highly recommended that you have at least 4 GB of memory.
The model can currently infer the following objects, however, **we advise strongly against using this 
application for mission critical applications.**


*person, bicycle, car, motorcycle, airplane, bus, train, truck, boat, traffic light, fire hydrant, 
stop sign, parking meter, bench, bird, cat, dog, horse, sheep, cow, elephant, bear, zebra, giraffe, 
backpack, umbrella, handbag, tie, suitcase, frisbee, skis, snowboard, sports ball, kite, baseball bat, 
baseball glove, skateboard, surfboard, tennis racket, bottle, wine glass, cup, fork, knife, spoon, bowl, 
banana, apple, sandwich, orange, broccoli, carrot, hot dog, pizza, donut, cake, chair, couch, potted plant, 
bed, dining table, toilet, tv, laptop, mouse, remote, keyboard, cell phone, microwave, oven, toaster, sink, 
refrigerator, book, clock, vase, scissors, teddy bear, hair drier, toothbrush*


### How it works:
This containerised JVM application stays afloat 24x7.
It shoots a picture every 1000<sup>th</sup> millisecond and feeds into the model for inference.
When the model recognises an object that it's supposed to be looking for (ex: a person), the application
triggers a notification to the user with the respective photograph. The detection, however, is limited 
only to the objects the model onboard can recognise.

---
If you wish to contribute to this project, feel free to check out the 
[contributors' guide](CONTRIBUTING.md) before you hop in 😁.