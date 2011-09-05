package dk.itu.lgtv;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 *
 * @author phylock
 */
public class SerialConnection {

  private boolean connected;
  private CommPortIdentifier portId;
  private SerialPort serialPort;

  /**
   * Open a serial connection
   * @param portid the id, this is defined by the system, eg COM1 or /dev/ttyUSB1
   * @param settings a container class with the connection specific settings encapsulated
   * @return success
   * @throws UnsupportedCommOperationException
   */
  public boolean connect(String portid, SerialSettings settings) throws UnsupportedCommOperationException, PortInUseException {
    boolean success = false;
    Enumeration portList = CommPortIdentifier.getPortIdentifiers();
    while (portList.hasMoreElements() && !connected) {
      portId = (CommPortIdentifier) portList.nextElement();

      if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {

        if (portId.getName().equals(portid)) {
          serialPort = (SerialPort) portId.open("Conn:" + portid, 2000);
          success = true;
          break;
        }
      }
    }
    if (success) {
      serialPort.setSerialPortParams(settings.getBaud(),
              settings.getDataBitsValue(),
              settings.getStopBitValue(),
              settings.getParityValue());
    }
    connected = success;
    return success;
  }

  public OutputStream getOutputStream() throws IOException {
    return serialPort.getOutputStream();
  }

  public InputStream getInputStream() throws IOException {
    return serialPort.getInputStream();
  }

  public void disconnect() {
    if (connected) {
      serialPort.close();
    }
  }

  public boolean isConnected() {
    return connected;
  }

  @Override
  public String toString() {
    return "SerialConnection{" + "connected=" + connected + ", portId=" + portId.getName() + '}';
  }

  @Override
  protected void finalize() throws Throwable {
    disconnect();
    super.finalize();
  }
}
