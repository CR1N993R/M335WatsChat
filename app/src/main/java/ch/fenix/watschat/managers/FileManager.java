package ch.fenix.watschat.managers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileManager {
    public static void saveDataToFile(Context context, String file, String data) {
        try {
            OutputStreamWriter streamWriter = new OutputStreamWriter(context.openFileOutput(file, MODE_PRIVATE));
            streamWriter.write(data);
            streamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDataFromFile(Context context, String file) {
        try {
            InputStream inputStream = context.openFileInput(file);
            if (inputStream != null) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();
                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                return stringBuilder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }
}
