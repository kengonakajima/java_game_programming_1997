package ringo;

import java.awt.*;
import java.io.*;

public class Console
{
    static volatile boolean initflg = false;
    static ConsoleFrame f;

    public static ConsolePrintStream stream = new ConsolePrintStream();
    public static ConsolePrintStream out = stream;
    public static ConsolePrintStream err = stream;

}
