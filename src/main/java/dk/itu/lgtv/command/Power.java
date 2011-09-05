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
public class Power {

    private static final LGCommand READ = new LGCommand('k', 'a', 0xFF);

    public enum State {

        On(0x01), Off(0x00);
        private int value;
        private LGCommand command;

        State(int i) {
            value = i;
            command = new LGCommand('k', 'a', i);
        }

        public int value() {
            return value;
        }

        public LGCommand command() {
            return command;
        }
    }

    public static LGCommand getCommand(State state) {
        return state.command();
    }

    public static LGCommand readState() {
        return READ;
    }
}
