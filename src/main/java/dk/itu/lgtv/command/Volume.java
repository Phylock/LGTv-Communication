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
package dk.itu.lgtv.command;

import dk.itu.lgtv.LGCommand;

/**
 *
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
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
