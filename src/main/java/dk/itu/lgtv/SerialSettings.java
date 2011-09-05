package dk.itu.lgtv;

import gnu.io.SerialPort;

/**
 *
 * @author Mikkel Wendt-Larsen(miwe@itu.dk)
 */
public class SerialSettings {

    private int baud;
    private DataBits databit;
    private FlowControl flow;
    private Parity parity;
    private StopBits stopbit;

    public enum DataBits {

        DataBits_5, DataBits_6, DataBits_7, DataBits_8
    };

    public enum FlowControl {

        NONE, RTSCTS_IN, RTSCTS_OUT, XONXOFF_IN, XONXOFF_OUT
    };

    public enum Parity {

        NONE, EVEN, ODD, SPACE, MARK
    };

    public enum StopBits {

        One, OneHalf, Two
    }

    public SerialSettings() {
        baud = 9600;
        databit = DataBits.DataBits_8;
        stopbit = StopBits.One;
        parity = Parity.NONE;
        flow = FlowControl.NONE;
    }

    public SerialSettings(int baud) {
        this();
        this.baud = baud;
    }


    public SerialSettings(int baud, DataBits databit, Parity parity, StopBits stopbit) {
        this();
        this.baud = baud;
        this.databit = databit;
        this.parity = parity;
        this.stopbit = stopbit;
    }
   
    private int parseDataBits(DataBits db)
    {
        int ret = 0;
        switch(db)
        {
            case DataBits_5:
                ret = SerialPort.DATABITS_5;
                break;
            case DataBits_6:
                ret = SerialPort.DATABITS_6;
                break;
            case DataBits_7:
                ret = SerialPort.DATABITS_7;
                break;
            case DataBits_8:
                ret = SerialPort.DATABITS_8;
                break;
        }
        return ret;
    }

    private int parseFlowControl(FlowControl fc)
    {
        int ret = 0;
        switch(fc)
        {
            case NONE:
                ret = SerialPort.FLOWCONTROL_NONE;
                break;
            case RTSCTS_IN:
                ret = SerialPort.FLOWCONTROL_RTSCTS_IN;
                break;
            case RTSCTS_OUT:
                ret = SerialPort.FLOWCONTROL_RTSCTS_OUT;
                break;
            case XONXOFF_IN:
                ret = SerialPort.FLOWCONTROL_XONXOFF_IN;
                break;
            case XONXOFF_OUT:
                ret = SerialPort.FLOWCONTROL_XONXOFF_OUT;
                break;

        }
        return ret;
    }

    private int parseParity(Parity p) {
        int ret = 0;
        switch (p) {
            case NONE:
                ret = SerialPort.PARITY_NONE;
                break;
            case EVEN:
                ret = SerialPort.PARITY_EVEN;
                break;
            case MARK:
                ret = SerialPort.PARITY_MARK;
                break;
            case ODD:
                ret = SerialPort.PARITY_ODD;
                break;
            case SPACE:
                ret = SerialPort.PARITY_SPACE;
                break;
        }
        return ret;
    }

    private int parseStopBit(StopBits sb) {
        int ret = 0;
        switch (sb) {
            case One:
                ret = SerialPort.STOPBITS_1;
                break;
            case OneHalf:
                ret = SerialPort.STOPBITS_1_5;
                break;
            case Two:
                ret = SerialPort.STOPBITS_2;
                break;


        }
        return ret;
    }

    public int getBaud() {
        return baud;
    }

    public void setBaud(int baud) {
        this.baud = baud;
    }

    public DataBits getDatabit() {
        return databit;
    }

    public void setDatabit(DataBits databit) {
        this.databit = databit;
    }

    public FlowControl getFlow() {
        return flow;
    }

    public void setFlow(FlowControl flow) {
        this.flow = flow;
    }

    public Parity getParity() {
        return parity;
    }

    public void setParity(Parity parity) {
        this.parity = parity;
    }

    public StopBits getStopbit() {
        return stopbit;
    }

    public void setStopbit(StopBits stopbit) {
        this.stopbit = stopbit;
    }

    public int getStopBitValue()
    {
        return parseStopBit(stopbit);
    }

    public int getParityValue()
    {
        return parseParity(parity);
    }

    public int getFlowControlValue()
    {
        return parseFlowControl(flow);
    }

    public int getDataBitsValue()
    {
        return parseDataBits(databit);
    }
}
