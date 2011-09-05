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
public class Aspect {

  public enum AspectState {

    Normal, Wide16_9, Zoom, Original, Wide14_9, Scan, WideFull
  }

  private static final LGCommand READ = new LGCommand('k', 'c', 0xFF);
  
  private static final LGCommand ASPECT_NORMAL = new LGCommand('k', 'c', 0x01);
  private static final LGCommand ASPECT_WIDE_16_9 = new LGCommand('k', 'c', 0x02);
  private static final LGCommand ASPECT_ZOOM = new LGCommand('k', 'c', 0x04);
  private static final LGCommand ASPECT_ORIGINAL = new LGCommand('k', 'c', 0x06);
  private static final LGCommand ASPECT_WIDE_14_9 = new LGCommand('k', 'c', 0x07);
  private static final LGCommand ASPECT_SCAN = new LGCommand('k', 'c', 0x09);
  private static final LGCommand ASPECT_WIDE_FULL = new LGCommand('k', 'c', 0x0B);

  public static LGCommand readState()
  {
    return READ;
  }

  public static LGCommand getCommand(AspectState state) {
    switch (state) {
      case Normal:
        return ASPECT_NORMAL;
      case Wide16_9:
        return ASPECT_WIDE_16_9;
      case Zoom:
        return ASPECT_ZOOM;
      case Original:
        return ASPECT_ORIGINAL;
      case Wide14_9:
        return ASPECT_WIDE_14_9;
      case Scan:
        return ASPECT_SCAN;
      case WideFull:
        return ASPECT_WIDE_FULL;
      default:
        return null;
    }
  }

  public static LGCommand getCinemaCommand(int mode) {
    if (mode < 0 || mode > 16) {
      throw new IllegalArgumentException("mode is between 0 and 16");
    }

    return new LGCommand('k', 'c', (0x10 | (mode & 0x0F)));
  }
}
