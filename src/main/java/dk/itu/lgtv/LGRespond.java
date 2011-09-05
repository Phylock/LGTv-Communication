/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dk.itu.lgtv;

/**
 *
 * @author phylock
 */
public class LGRespond {
  public enum Acknowledge
  {
    Acknowledge, Failed
  }

  private final Acknowledge ack;
  private final char type;
  private final int id;
  private final long data;

  public LGRespond(Acknowledge ack, char type, int id, long data) {
    this.ack = ack;
    this.type = type;
    this.id = id;
    this.data = data;
  }

  public Acknowledge getAck() {
    return ack;
  }

  public int getId() {
    return id;
  }

  
  public long getData() {
    return data;
  }

  @Override
  public String toString() {
    return "LGRespond{" + "ack=" + ack + ", type=" + type + ", id=" + id + ", data=" + data + '}';
  }
  
}
