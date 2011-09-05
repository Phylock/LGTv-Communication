/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.itu.lgtv;

/**
 * Encapsulate and standardize the way of building LG commands
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
 */
public class LGCommand {

  private static final char CR = 0x0D;
  private static final String TRANSMISSION_FORMAT = "%c%c !id %s" + CR;
  protected final String format;
  protected final int respond_size = 10;
  protected final char second;

  /**
   *  Define the first two parameters and the data.
   * @param first category, this can be 'j', 'k', 'm' or 'x'
   * @param second sub category
   * @param data the command
   */
  public LGCommand(char first, char second, long data) {
    format = String.format(TRANSMISSION_FORMAT, first, second, toDataString(data));
    this.second = second;
  }

  /**
   * Custom format, when the other two constructs just isn't enough.
   * @param format expects id "!id" and a data argument %s
   * @param data the command
   */
  public LGCommand(String format, char second, long data) {
    this.format = String.format(format, toDataString(data)) + CR;
    this.second = second;
  }

  /**
   *  get command to send, send to all connected devices
   * @return return bytes to send
   */
  public byte[] getCommand() {
    return getCommand((byte) 0);
  }

  /**
   * get command to send, define the id of the device to retrieve
   * @param id device 1-99, defined on the device
   * @return return bytes to send
   */
  public byte[] getCommand(byte id) {
    return format.replaceAll("!id", String.format("%02x", id)).getBytes();
  }

  public int respondSize() {
    return respond_size;
  }

  public LGRespond checkResponds(String responds) {
    String[] split = responds.trim().split(" ");
    char type = split[0].charAt(0);
    //TODO: is type the same as current command?
    int id = Integer.parseInt(split[1], 16);
    LGRespond.Acknowledge ack = split[2].substring(0, 2).equals("OK") ? LGRespond.Acknowledge.Acknowledge : LGRespond.Acknowledge.Failed;

    String str_data = split[2].substring(2);
    long data = 0;
    if (!str_data.isEmpty()) {
      data = Long.parseLong(str_data, 16);
    }

    return new LGRespond(ack,type, id, data);
  }

  /**
   * separate each byte with a space if data is 1000 it will return "03 E8"
   * @param data
   * @return
   */
  private static String toDataString(long data) {
    if (data == 0) {
      return "00";
    } else {
      String ret = "";
      long loc = data;
      while (loc > 0) {
        byte b = (byte) (loc & 0xFF);
        ret = String.format("%02x ", b) + ret;
        loc = loc >>> 8;
      }
      return ret.trim();
    }
  }
}
