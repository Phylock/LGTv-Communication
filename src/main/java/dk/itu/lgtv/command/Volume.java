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
public class Volume {

  public enum State {
    On, Off
  }
  private static final LGCommand READ_MUTE = new LGCommand('k', 'e', 0xFF);
  private static final LGCommand READ_VOLUME = new LGCommand('k', 'f', 0xFF);

  private static final int ON = 0x01;
  private static final int OFF = 0x00;
  private static final LGCommand VOLUME_MUTE_ON = new LGCommand('k', 'e', ON);
  private static final LGCommand VOLUME_MUTE_OFF = new LGCommand('k', 'e', OFF);


  public static LGCommand readMuteState() {
    return READ_MUTE;
  }

  public static LGCommand readVolumeState() {
    return READ_VOLUME;
  }


  public static LGCommand mute(State state) {
    switch (state) {
      case On:
        return VOLUME_MUTE_ON;
      case Off:
        return VOLUME_MUTE_OFF;
      default:
        return null;
    }
  }

  public static LGCommand setVolume(int volume) {
    if (volume < 0 || volume > 100) {
      throw new IllegalArgumentException("volume should be between 0 and 100");
    }
    return new LGCommand('k', 'f', volume);
  }
}
