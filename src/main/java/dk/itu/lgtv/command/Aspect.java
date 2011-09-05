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
