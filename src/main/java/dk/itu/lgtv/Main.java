/**
 * Copyright 2011 Mikkel Wendt-Larsen(miwe@itu.dk), Tommy Andersen(toan@itu.dk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package dk.itu.lgtv;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import dk.itu.lgtv.command.Channel;
import dk.itu.lgtv.command.Channel.Mode;
import dk.itu.lgtv.command.IR;
import dk.itu.lgtv.command.IR.Command;
import dk.itu.lgtv.command.Power;
import dk.itu.lgtv.command.Power.State;
import dk.itu.lgtv.command.Volume;

/**
 *
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
 */
public class Main {

  final LGTvConnection connection = new LGTvConnection(1);

  public void connectionTest() {
    String defaultPort = "/dev/ttyUSB0";

    try {
      connection.setIgnoreResponse(true);
      if (connection.connect(defaultPort)) {

        printHex("On", Power.getCommand(State.On).getCommand());
        connection.sendCommand(Power.getCommand(State.On));
        Thread.sleep(10000);

        System.out.println(connection.sendCommand(Channel.getCommand(Mode.Analog, 10)));
        System.out.println("Read Channel State: " + connection.sendCommand(Channel.readState(Mode.Analog)));

        Thread.sleep(2000);
        
        System.out.println("Read Power State: " + connection.sendCommand(Power.readState()));
        Thread.sleep(2000);
        System.out.println(connection.sendCommand(Volume.setVolume(20)));Thread.sleep(1000);

        printHex("Menu", IR.getCommand(Command.Menu).getCommand());
        System.out.println(connection.sendCommand(IR.getCommand(Command.Menu)));Thread.sleep(1000);
        System.out.println(connection.sendCommand(IR.getCommand(Command.Left)));Thread.sleep(1000);
        System.out.println(connection.sendCommand(IR.getCommand(Command.Ok)));Thread.sleep(5000);
        System.out.println(connection.sendCommand(IR.getCommand(Command.Ok)));Thread.sleep(1000);        

        Thread.sleep(10000);

        printHex("Off", Power.getCommand(State.Off).getCommand());
        connection.sendCommand(Power.getCommand(State.Off));
        Thread.sleep(2000);

        connection.disconnect();
      } else {
        System.out.println("Could not connect to : " + defaultPort);
      }
    } catch (Exception ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public void parserTest() {
    try {
      byte[] fin = Power.getCommand(State.On).getCommand();
      System.out.println("Final");
      System.out.write(fin);
      byte[] dynone = IR.getCommand(Command.EnergiSaving).getCommand();
      System.out.println("\nDynamic, 1 byte");
      System.out.write(dynone);
      byte[] dyntwo = Channel.getCommand(Mode.Digital, 999).getCommand();
      System.out.println("\nDynamic, 2 byte");
      System.out.write(dyntwo);

    } catch (IOException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void printHex(String message, byte[] arr) {
    System.out.print(message + ": ");
    for (int i = 0; i < arr.length; i++) {
      System.out.print(String.format(" %02x", arr[i]));
    }
    System.out.println();
  }

  public static void main(String[] args) {
    Main m = new Main();
    
    m.parserTest();
    m.connectionTest();
  }
}
