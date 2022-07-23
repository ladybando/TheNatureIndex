package com.example.android.thenatureindex.mlkit

import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions
import com.google.mlkit.vision.objects.ObjectDetection

class ObjectDetection {

    /*
    * This class sets up object detection for images either captured or currently being captured or recorded
    * */

    //use stream mode for images
    //single use mode when an image is uploaded or captured
    //detect multiple images start off as true
    //classify objects set to true
    //classifications should add to database - maybe. classifications are limited

    // Live detection and tracking
    private val liveOptions = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.STREAM_MODE)
        .enableClassification()//optional
        .build()

    // Multiple object detection in static images
    private val staticOptions = ObjectDetectorOptions.Builder()
        .setDetectorMode(ObjectDetectorOptions.SINGLE_IMAGE_MODE)
        .enableMultipleObjects()
        .enableClassification()
        .build()

    val objectDetectorLive = ObjectDetection.getClient(liveOptions)
    val objectDetectorStatic = ObjectDetection.getClient(staticOptions)

}