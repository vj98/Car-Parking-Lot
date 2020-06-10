package com.vijay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ParkingLotService {

    public static Integer id = 0;
    public static String lotSize = null;
    public static HashMap<String, String[]> slotsAllocated = new HashMap<>();
    public static HashMap<String, ArrayList<String>> colorToRegNo = new HashMap<>();
    public static HashMap<String, ArrayList<String>> colorToSlot = new HashMap<>();
    public static HashMap<String, String> regNoToSlot = new HashMap<>();
    public static PriorityQueue<Integer> availableSlot = new PriorityQueue<>();

    /**
     * Defining the parking slots
     * @param lotsz
     */
    public void createParkingLot(String lotsz) {
        lotSize = lotsz;
        System.out.println("Created a parking lot with " + lotsz +" slots");
    }

    /**
     * Parking the car to the available and the nearest to the entry
     * @param regNo
     * @param color
     */
    public static void park(String regNo, String color) {
        Integer curr = id;
        if (!availableSlot.isEmpty()) {
            id = availableSlot.poll();
            System.out.println("Allocated slot number: " + id.toString());
        }
        else if (id + 1 <= Integer.parseInt(lotSize)) {
            id++;
            curr = id;
            System.out.println("Allocated slot number: " + id.toString());
        }
        else {
            System.out.println("Sorry, parking lot is full");
            return;
        }

        String idd = id.toString();
        slotsAllocated.put(idd, new String[]{idd, regNo, color});

        if (!colorToRegNo.containsKey(color)) {
            ArrayList <String> temp = new ArrayList<>();
            temp.add(regNo);
            colorToRegNo.put(color, temp);
        }
        else {
            colorToRegNo.get(color).add(regNo);
        }

        if (!colorToSlot.containsKey(color)) {
            ArrayList <String> temp = new ArrayList<>();
            temp.add(id.toString());
            colorToSlot.put(color, temp);
        }
        else {
            colorToSlot.get(color).add(id.toString());
        }

        regNoToSlot.put(regNo, idd);
        id = curr;
    }

    /**
     * Deallocate the slot by lotId
     * @param lotId
     */
    public static void leave(String lotId) {
        if (!availableSlot.contains(Integer.parseInt(lotId))) {
            availableSlot.add(Integer.parseInt(lotId));
        }

        if (slotsAllocated.containsKey(lotId)) {
            String[] detail = slotsAllocated.get(lotId);
            String parkId = detail[0];
            String regNo = detail[1];
            String color = detail[2];
            slotsAllocated.remove(lotId);
            colorToRegNo.get(color).remove(regNo);
            colorToSlot.get(color).remove(parkId);
            regNoToSlot.remove(regNo);
            System.out.println("Slot number " + lotId + " is free");
        }
        else {
            System.out.println("Slot is not allocated");
        }
    }

    /**
     * Fetch the status of the parking slot
     */
    public static void status() {
        if (id > 0) {
            System.out.println("Slot No." + "\t" + "Registration No" + "\t\t" + "Color");
            for (Integer i = 1; i <= id; i++) {
                if (slotsAllocated.containsKey(i.toString())) {
                    String[] detail = slotsAllocated.get(i.toString());
                        System.out.println(detail[0] + "\t\t" + detail[1] + "\t\t" + detail[2]);
                }
            }
        }
        else {
            System.out.println("No slot is allocated");
        }
    }

    /**
     * Fetch the registration number from the color
     * @param color
     */
    public static void getRegistrationNumbersFromColor(String color) {
        if (colorToRegNo.containsKey(color)) {
            ArrayList <String> regNo = colorToRegNo.get(color);
            displayRegistrationNumber(regNo);
        }
        else {
            System.out.println("Not found");
        }
    }

    /**
     * Fetch the slot number from the color
     * @param color
     */
    public static void getSlotNumbersFromColor(String color) {
        if (colorToSlot.containsKey(color)) {
            ArrayList<String> regNo = colorToSlot.get(color);
            displayRegistrationNumber(regNo);
        }
        else {
            System.out.println("Not found");
        }
    }

    /**
     * Fetch the slot number from the registration number
     * @param regNo
     */
    public static void getSlotNumberFromRegNo(String regNo) {
        if (regNoToSlot.containsKey(regNo)) {
            String slot = regNoToSlot.get(regNo);
            System.out.println(slot);
        }
        else {
            System.out.println("Not found");
        }
    }

    /**
     * Display the registration number
     * @param regNo
     */
    public static  void displayRegistrationNumber(ArrayList<String> regNo) {
        int sz = regNo.size();

        for (int i = 0; i < sz; i++) {
            System.out.print(regNo.get(i));
            if (i != sz-1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}