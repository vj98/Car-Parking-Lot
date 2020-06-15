package com.vijay.commands;

import com.vijay.service.ParkingLotService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * abstract classs command with abstract function process
 */
abstract class Command {
    public abstract void process(String args[]) throws Exception;
}


/**
 * Allocate the total lot available
 */
class CreateParkingLotCommand extends Command {
    public void process(String args[]) throws Exception {
        if (args.length != 2) {
            throw new Exception("Please provide the create slot number");
        }

        String createSlot = args[1];
        createSlot = ParkingLotService.getInstance().createParkingLot(createSlot);

        System.out.println("Created a parking lot with " + createSlot + " slots");
    }
}

/**
 * Park the car accordingly to the availability of the slot
 */
class ParkCommand extends Command {
    public void process(String args[]) throws Exception {
        if (args.length != 3) {
            throw new Exception("Please provide the registration number and the color");
        }
        String registrationNumber = args[1];
        String color = args[2];

        boolean flag = Pattern.compile("[A-Z]{2}-[0-9]{2}-[A-Z]{1,2}-[0-9]{1,4}").matcher(registrationNumber).matches();

        int slotNumber = 0;

        if (flag) {
            slotNumber = ParkingLotService.getInstance().park(registrationNumber, color);
        }

        if (slotNumber != 0 && slotNumber != -1) {
            System.out.println("Allocated slot number: " + slotNumber);
        }
        else {
            if (slotNumber == -1) {
                System.out.println("Sorry, parking lot is full");
            }
            else {
                System.out.println("Please enter the valid registration number");
            }
        }
    }
}

/**
 * leave the slot by providing slot number
 */
class LeaveCommand extends Command {
    public void process(String args[]) throws Exception {
        if (args.length != 2) {
            throw new Exception("Please provide the slot number");
        }

        String slotNumber = args[1];

        int slotNo = ParkingLotService.getInstance().leave(slotNumber);
        if (slotNo != -1) {
            System.out.println("Slot number " + slotNumber + " is free");
        }
        else {
            System.out.println("Slot is not allocated");
        }
    }
}

/**
 * See the status of the lot filled and available
 */
class StatusCommand extends  Command {
    public void process(String args[]) throws Exception {
        List<String[]> allocationDetail = ParkingLotService.getInstance().status();

        if (!allocationDetail.isEmpty()) {
            System.out.println("Slot No." + "\t" + "Registration No" + "\t\t" + "Color");

            for (int i = 0; i < allocationDetail.size(); i++) {
                String[] detail = allocationDetail.get(i);
                System.out.println(detail[0] + "\t\t" + detail[1] + "\t\t" + detail[2]);
            }
        }
        else {
            System.out.println("No slot is allocated");
        }
    }
}

/**
 * Get the registration number from the color
 */
class GetRegistrationNumbersFromColorCommand extends  Command {
    public void process(String args[]) throws Exception {
        if (args.length != 2) {
            throw new Exception("Please provide the color");
        }

        String color = args[1];

        List <String> regNo = ParkingLotService.getInstance().getRegistrationNumbersFromColor(color);

        if (regNo.isEmpty()) {
            System.out.println("Not found");
        }
        else {
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
}

/**
 * Get the slot number from the color
 */
class GetSlotNumbersFromColorCommand extends  Command {
    public void process(String args[]) throws Exception {
        if (args.length != 2) {
            throw new Exception("Please provide the color");
        }

        String color = args[1];

        List <String> slotNo = ParkingLotService.getInstance().getSlotNumbersFromColor(color);

        if (slotNo.isEmpty()) {
            System.out.println("Not found");
        }
        else {
            int sz = slotNo.size();

            for (int i = 0; i < sz; i++) {
                System.out.print(slotNo.get(i));
                if (i != sz-1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }
}

/**
 * Get the slot number form the registration number
 */
class GetSlotNumberFromRegNoCommand extends  Command {
    public void process(String args[]) throws Exception {
        if (args.length != 2) {
            throw new Exception("Please provide the reg");
        }

        String registrationNumber = args[1];

        String slotNumber = null;
        boolean flag = Pattern.compile("[A-Z]{2}-[0-9]{2}-[A-Z]{1,2}-[0-9]{1,4}").matcher(registrationNumber).matches();

        if (flag) {
            slotNumber = ParkingLotService.getInstance().getSlotNumberFromRegNo(registrationNumber);
        }

        if (slotNumber == null) {
            System.out.println("Not found");
        }
        else {
            System.out.println(slotNumber);
        }
    }
}


class ParkingLotController {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader;

        if (args.length == 0) {
            // Input Command Line Reader
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } else {
            // Input File Reader
            String filePath = args[0];
            File inputFile = new File(filePath);
            bufferedReader = new BufferedReader(new FileReader(inputFile));
        }

        while (true) {
            String commandText = bufferedReader.readLine();
            if (commandText == null || "exit".equalsIgnoreCase(commandText)) {
                break;
            } else {
                String[] details = commandText.split(" ");
                if (details.length == 1) {
                    if (details[0].equals("status")) {
                        Command obj = new StatusCommand();
                        obj.process(new String[]{"status"});
                    } else {
                        System.out.println("Invalid input");
                    }
                }

                else if (details.length == 2) {
                    if (details[0].equals("leave")) {
                        Command obj = new LeaveCommand();
                        obj.process(details);
                    }

                    else if (details[0].equals("create_parking_lot")) {
                        Command obj = new CreateParkingLotCommand();
                        obj.process(details);
                    }

                    else if (details[0].equals("registration_numbers_for_cars_with_colour")) {
                        Command obj = new GetRegistrationNumbersFromColorCommand();
                        obj.process(details);
                    }

                    else if (details[0].equals("slot_numbers_for_cars_with_colour")) {
                        Command obj = new GetSlotNumbersFromColorCommand();
                        obj.process(details);
                    }

                    else if (details[0].equals("slot_number_for_registration_number")) {
                        Command obj = new GetSlotNumberFromRegNoCommand();
                        obj.process(details);
                    }
                }

                else if (details.length == 3) {
                    if (details[0].equals("park")) {
                        Command obj = new ParkCommand();
                        obj.process(details);
                    }
                }

                else {
                    System.out.println("Invalid input");
                }
            }
        }
    }
}