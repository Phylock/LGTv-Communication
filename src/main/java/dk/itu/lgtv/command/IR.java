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
public class IR {

  public enum Command {

    EnergiSaving(0x95), Power(0x08), Input(0x0B), Ratio(0x79), Tv(0xF0),
    QMenu(0x45), Menu(0x43), Guide(0xAB), Up(0x40), Down(0x41),
    Left(0x07), Right(0x06), Ok(0x44), Back(0x28), Exit(0x5B),
    Info(0xAA), AvMode(0x30), VolumeUp(0x02), VolumeDown(0x03),
    Fav(0x1E), Mute(0x09), ProgramUp(0x00), ProgramDown(0x01),
    Number0(0x10), Number1(0x11), Number2(0x12), Number3(0x13),
    Number4(0x14), Number5(0x15), Number6(0x16), Number7(0x17),
    Number8(0x18), Number9(0x19), List(0x53), QView(0x1A),
    Red(0x72), Green(0x71), Yellow(0x63), Blue(0x61), Text(0x20),
    TOpt(0x21), Subtitle(0x39), Simplink(0x7E), AD(0x91),
    Stop(0xB1), Play(0xB0), Freeze(0xBA), Rewind(0x8F), Forward(0x8E),
    NetCast(0x59), At(0x58), Mode3D(0xDC), App(0x9F);
    int value;

    Command(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }
  }
  
  public static LGCommand getCommand(Command command) {
    return new LGCommand('m', 'c', command.value());
  }

  public static LGCommand getNumberCommand(int number) {
    if(number < 0 || number > 9)
    {
      throw new IllegalArgumentException("only number key from 0 to 9 is allowed");
    }
    return new LGCommand('m', 'c', 0x10 | (0x0F & number));
  }
}
