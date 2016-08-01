package developer.pardeep.workin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by pardeep on 17-06-2016.
 */
public class CustomBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


            Bundle bundle=intent.getExtras();
            if(bundle!=null){
                Object[] pdus= (Object[]) bundle.get("pdus");
                SmsMessage[] smsMessages=new SmsMessage[pdus.length];
                for(int i=0;i<smsMessages.length;i++ ){
                    smsMessages[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for(SmsMessage message:smsMessages){
                    String messageAddress=message.getOriginatingAddress();
                    String messageDetails=message.getDisplayMessageBody();

                    System.out.println(messageAddress);
                    System.out.println(messageDetails);
                   // editTextViewBroadcast.setText(messageAddress+"\n"+messageDetails);

                    Toast.makeText(context, " "+messageAddress+"\n"+messageDetails, Toast.LENGTH_LONG).show();
                }
            }
        }

}
