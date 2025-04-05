package com.example.tardinesstracker.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TravelTimeCalculator {
    
    private static final int DEFAULT_PREPARATION_TIME = 30; // minutes
    
    /**
     * Calculate wake-up time based on school start time and travel time
     * @param schoolStartTime Time when school starts (format: "HH:mm")
     * @param travelTimeMinutes Estimated travel time in minutes
     * @param preparationTimeMinutes Time needed to get ready in minutes
     * @return Calendar object set to the recommended wake-up time
     */
    public static Date calculateWakeUpTime(String schoolStartTime, int travelTimeMinutes, int preparationTimeMinutes) {
        try {
            // Parse school start time
            SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date startTime = format.parse(schoolStartTime);
            
            if (startTime == null) {
                return null;
            }
            
            // Calculate total minutes needed before school
            int totalMinutesNeeded = travelTimeMinutes + 
                    (preparationTimeMinutes > 0 ? preparationTimeMinutes : DEFAULT_PREPARATION_TIME);
            
            // Subtract required time from school start time
            long wakeUpTimeMillis = startTime.getTime() - (totalMinutesNeeded * 60 * 1000);
            
            return new Date(wakeUpTimeMillis);
            
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Estimate travel time based on distance and transport mode
     * @param distanceKm Distance in kilometers
     * @param transportMode Transport mode (walking, bicycle, car, public_transport)
     * @return Estimated travel time in minutes
     */
    public static int estimateTravelTime(double distanceKm, String transportMode) {
        if (transportMode == null) {
            transportMode = "walking"; // Default transport mode
        }
        
        // Average speeds in km/h for different transport modes
        final double WALKING_SPEED = 5.0;
        final double BICYCLE_SPEED = 15.0;
        final double CAR_SPEED = 30.0;
        final double PUBLIC_TRANSPORT_SPEED = 20.0;
        
        double speedKmh;
        
        switch (transportMode.toLowerCase()) {
            case "bicycle":
                speedKmh = BICYCLE_SPEED;
                break;
            case "car":
                speedKmh = CAR_SPEED;
                break;
            case "public_transport":
                speedKmh = PUBLIC_TRANSPORT_SPEED;
                break;
            case "walking":
            default:
                speedKmh = WALKING_SPEED;
                break;
        }
        
        // Calculate time in hours, then convert to minutes
        double timeHours = distanceKm / speedKmh;
        int timeMinutes = (int) Math.ceil(timeHours * 60);
        
        // Add a small buffer for safety
        return timeMinutes + 5;
    }
}
