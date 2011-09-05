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
