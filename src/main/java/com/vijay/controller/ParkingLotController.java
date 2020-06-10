package com.vijay.controller;

import com.vijay.commands.CommandsExecutor;
import com.vijay.service.ParkingLotService;

import java.io.*;
import java.lang.reflect.Method;

public class ParkingLotController {

    private static final CommandsExecutor commandExecutor = new CommandsExecutor();
    private static final ParkingLotService parkingLotService = new ParkingLotService();

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
                    Method method = commandExecutor.commandsHashMap.get(details[0].trim());
                    if (method != null) {
                        method.invoke(parkingLotService);
                    } else {
                        System.out.println("Invalid input");
                    }
                } else if (details.length == 2) {
                    Method method = commandExecutor.commandsHashMap.get(details[0].trim());
                    if (method != null) {
                        method.invoke(parkingLotService, details[1]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } else if (details.length == 3) {
                    Method method = commandExecutor.commandsHashMap.get(details[0].trim());
                    if (method != null) {
                        method.invoke(parkingLotService, details[1], details[2]);
                    } else {
                        System.out.println("Invalid input");
                    }
                } else {
                    System.out.println("Invalid input");
                }
            }
        }
    }
}