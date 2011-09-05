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
