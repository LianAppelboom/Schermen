/*
 * Dit programma leest data vanaf de USB seriële poort uit (vanaf bijv. Arduino of Microbit)
 * en insert dit in een MySQL database.
 * Je kunt hiermee ook tekst schrijven naar de microbit (zie regel 71-73).
 * Eerste versie: 20190613 Gert den Neijsel; Deze versie werkt wel met jkd8, maar niet met jdk11
 * versie: 20191212;   Werkt nu ook met jdk11;
 *                          De jssc lib crashte met jdk11, daarom jssc lib vervangen door jSerialCom.
 * Versie 20201209;  Libraries JSerialComm-2.6.2 en mysql-connector-java-8.0.22 in project opgenomen, werkt nu met jdk15
 * <p>
 * Instructies om dit aan de praat te krijgen:
 * Voer na installatie de volgende commando's uit in MySQL server/workbench:
 *      CREATE DATABASE vb1;
 *      CREATE TABLE vb1.tbl1(tijdstip TEXT, temperatuur FLOAT);
 *      CREATE USER microbit IDENTIFIED BY 'geheim';
 *      GRANT INSERT, UPDATE, SELECT, DELETE ON vb1.* TO 'microbit';
 * Nadat dit programma data ingevoerd heeft in de database, dan kun je dit opvragen maken met dit commando:
 *      SELECT * FROM vb1.tbl1;
 * <p>
 * Gebruik van de jSerialCom library: https://github.com/Fazecast/jSerialComm/wiki/Usage-Examples
 * <p>
 * Gebruik Arduino voorbeeld "07.Temperaturesensor-Bluetooth.ino" om data te genereren.
 */


package App;

import com.fazecast.jSerialComm.*;


import java.util.Scanner;

import static com.fazecast.jSerialComm.SerialPort.*;


public class ComPortSendReceive {

    public static SerialPort serialPort;

    public static void main(String args) {

        String portName;
        SerialPort portNames[] = SerialPort.getCommPorts();

        if (portNames.length == 0) {
            System.out.println("Er zijn geen seriële poorten. Sluit je Micro:bit aan!");
            return;
        }

        if (portNames.length == 1) {
            portName = portNames[0].getSystemPortName();
            System.out.println(portName + " wordt nu gebruikt.");
        } else {
            System.out.println("Meerdere seriële poorten gedetecteerd: ");
            for (int i = 0; i < portNames.length; i++) {
                System.out.println(portNames[i].getSystemPortName());
            }

            System.out.println("Type poortnaam die je wilt gebruiken en druk Enter...");
            Scanner in = new Scanner(System.in);
            portName = in.next();
        }

        serialPort = SerialPort.getCommPort(portName);

        try {
            // seriële poort openen en instellen
            serialPort.openPort();
            serialPort.setComPortParameters(9600, 8, ONE_STOP_BIT, NO_PARITY);
            serialPort.setFlowControl(FLOW_CONTROL_DISABLED);

            // Schrijven naar seriële poort: schrijf string naar poort
            String uitvoer = "Djelies"; // de tekst die je naar de Microbit wilt sturen
            byte[] buffer = uitvoer.getBytes();
            serialPort.writeBytes(buffer, uitvoer.length());

            System.out.println("String naar seriële poort geschreven...");

        } catch (Exception ex) {
            System.out.println("Fout bij schrijven naar seriële poort: " + ex);
        }

        try {
            Thread.sleep(5000); // 5 seconden pauzeren
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder bericht = new StringBuilder();
        InsertIntoSQL database = new InsertIntoSQL();   //Deze regel uitcommenten als SQL nog niet werkt.
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }

            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) { return; }
                String vorigTijdstip = null;
                byte buffer[] = new byte[serialPort.bytesAvailable()];
                int numRead = serialPort.readBytes(buffer, buffer.length);

                for (byte b : buffer) {
                    if ((b == '\r' || b == '\n') && bericht.length() > 0) { // regeleinde gedetecteerd ('\r' of '\n')

                        // StringBuilder naar String converteren
                        String berichtData = bericht.toString();



                        // regeleindes verwijderen uit data en tijdstip
                        berichtData = berichtData.replace("\n", "").replace("\r", "");


                        // String naar float omzetten
                        Float temperatuur = Float.parseFloat(berichtData);

                        // afronden op 1 cijfer achter de komma
                        temperatuur = (float) (Math.round(temperatuur * 10.0) / 10.0);
                        database.insert(temperatuur);  //Deze regel uitcommenten als SQL nog niet werkt.
                        System.out.print("  ");
                        System.out.println(temperatuur);
                        bericht.setLength(0);
                    } else {
                        bericht.append((char) b);
                    }
                }
            }
        });
    }
}
