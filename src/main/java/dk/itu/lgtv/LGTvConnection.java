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

import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
 */
public class LGTvConnection {

  private static int DELAY = 1000;
  //private CommandBlocker blocker = new CommandBlocker();
  private final Object lock = new Object();
  private SerialSettings setting = new SerialSettings(
          9600,
          SerialSettings.DataBits.DataBits_8,
          SerialSettings.Parity.NONE,
          SerialSettings.StopBits.One);
  private byte id;
  private SerialConnection connection;
  private OutputStream os;
  private InputStreamReader isr;
  private ResponseReader responsreader;
  private boolean ignore_timeouts = false;
  private boolean ignore_response = false;
  private boolean verbose = false;

  public LGTvConnection(int id) {
    this.id = (byte) id;
  }

  public LGTvConnection() {
    this((byte) 0);
  }

  public boolean connect(String port) throws PortInUseException {
    boolean success = false;
    try {
      connection = new SerialConnection();
      success = connection.connect(port, setting);
      if (success) {
        os = connection.getOutputStream();
        isr = new InputStreamReader(new BufferedInputStream(connection.getInputStream()), "UTF8");
        responsreader = new ResponseReader(isr);
      }
    } catch (IOException ex) {
      Logger.getLogger(LGTvConnection.class.getName()).log(Level.SEVERE, null, ex);
      success = false;
    } catch (UnsupportedCommOperationException ex) {
      //should never happend, we are using basic stuff here 
    }
    return success;
  }

  public LGRespond sendCommand(LGCommand command) throws IOException, ResponsTimeoutExceoption {

    byte[] data = command.getCommand(id);
    synchronized (lock) {
      os.write(data);
      if (!ignore_response) {
        Thread task = new Thread(responsreader, "Timeout Wrapper");
        //task.setDaemon(true);
        task.start();
        try {
          task.join(500);
        } catch (InterruptedException e) {
        }
        if (task.isAlive()) {
          task.interrupt();
          //TODO: hmmm the tread is not stoppped :((
          if (ignore_timeouts) {
            return new LGRespond(LGRespond.Acknowledge.Failed, (char) 0, 00, 00);
          } else {
            throw new ResponsTimeoutExceoption();
          }
        }
        if (responsreader.getException() != null) {
          throw responsreader.getException();
        }

        return command.checkResponds(responsreader.getResponds());
      }
      return null;
    }
  }

  public void disconnect() {
    connection.disconnect();
  }

  public boolean isIgnoreTimeouts() {
    return ignore_timeouts;
  }

  public void setIgnoreTimeouts(boolean ignore_timeouts) {
    this.ignore_timeouts = ignore_timeouts;
  }

  public boolean isIgnoreResponse() {
    return ignore_response;
  }

  public void setIgnoreResponse(boolean ignore_response) {
    this.ignore_response = ignore_response;
  }

  public boolean isVerbose() {
    return verbose;
  }

  public void setVerbose(boolean verbose) {
    this.verbose = verbose;
  }

  private class ResponseReader
          implements Runnable {

    private InputStreamReader isr;
    private String responds;
    private StringBuilder sb = new StringBuilder();
    private IOException exception;

    public ResponseReader(InputStreamReader isr) {
      this.isr = isr;
    }

    @Override
    public void run() {
      exception = null;
      responds = "";
      char[] respond_array = new char[20];
      int cut = 0;
      while ((cut = sb.indexOf("x")) < 0) {
        try {
          if (isr.ready()) {
            int total = isr.read(respond_array);
            sb.append(respond_array, 0, total);
          }else
          {
            Thread.yield();
          }
        } catch (IOException ex) {
          exception = ex;
          if (sb.length() > 0) {
            sb.delete(0, sb.length());
          }
          return;
        }
      }
      responds = sb.substring(0, cut);
      sb.delete(0, cut + 1);
    }

    public String getResponds() {
      return responds;
    }

    public IOException getException() {
      return exception;
    }
  }
}
