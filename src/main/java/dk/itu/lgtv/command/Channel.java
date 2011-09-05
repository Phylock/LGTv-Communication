/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.lgtv.command;

import dk.itu.lgtv.LGCommand;

/**
 *
 * @author phylock
 */
public class Channel {

  public enum Mode {

    Analog, Digital, Radio
  }

  public static LGCommand readState(Mode mode) {
    return getCommand(mode, 0xFFFF);
  }

  public static LGCommand getCommand(Mode mode, int channel) {
    switch (mode) {
      case Analog:
        if ((channel >= 0 && channel <= 99) || channel == 0xFFFF) {
          return new LGCommand("ma !id %s 00", 'a', channel);
        } else {
          throw new IllegalArgumentException("Analog channels is between 0 and 99");
        }
      case Digital:
        if ((channel >= 0 && channel <= 9999) || channel == 0xFFFF) {
          return new LGCommand("ma !id %s 10", 'a', channel);
        } else {
          throw new IllegalArgumentException("Digital channels is between 0 and 9999");
        }
      case Radio:
        if ((channel >= 0 && channel <= 99) || channel == 0xFFFF) {
          return new LGCommand("ma !id %s 20", 'a', channel);
        } else {
          throw new IllegalArgumentException("Radio channels is between 0 and 99");
        }

      default:
        return null;
    }
  }
}
