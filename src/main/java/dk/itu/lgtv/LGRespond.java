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

/**
 *
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
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
