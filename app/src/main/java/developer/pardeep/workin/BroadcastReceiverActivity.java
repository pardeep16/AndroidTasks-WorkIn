package developer.pardeep.workin;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by pardeep on 17-06-2016.
 */
public class BroadcastReceiverActivity extends Activity {

    public BroadcastReceiverActivity(){

    }

    private final static int notification_id=1;
    private final static String notification_msg="Receive new sms";
    private static int countNotification=0;

    BroadcastReceiver broadcastReceiver=new SmsReceiver();
    LocalBroadcastManager localBroadcastManager;
    Button registerButton;
    EditText editTextViewBroadcast;
    IntentFilter intentFilter;
    /*private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            System.out.println("Action perform");
            Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show();
            createNotification();
            editTextViewBroadcast=(EditText)findViewById(R.id.editTextViewBroadcastSms);

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
                        editTextViewBroadcast.setText(messageAddress + "\n" + messageDetails);
                        Toast.makeText(BroadcastReceiverActivity.this, "msg :"+messageAddress+"\n"+messageDetails, Toast.LENGTH_SHORT).show();
                        localBroadcastManager.unregisterReceiver(broadcastReceiver);
                    }
                }
            }

    };*/

    private void createNotification() {
       Intent intent=new Intent(this,HomeActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification.Builder notification=new Notification.Builder(getApplicationContext());
        notification.setAutoCancel(true);
        notification.setContentText(notification_msg);
        notification.setContentTitle("Notification");
        notification.setContentIntent(pendingIntent);
        notification.setSmallIcon(R.drawable.ic_cloud);
        notification.setSound(sound);

        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notification_id, notification.build());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        // receiver=new Receive();
        intentFilter=new IntentFilter();
        //intentFilter.setPriority(10);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        editTextViewBroadcast=(EditText)findViewById(R.id.editTextViewBroadcastSms);
        //localBroadcastManager= LocalBroadcastManager.getInstance(getApplicationContext());

        registerButton=(Button)findViewById(R.id.dynamicButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              registerReceiver(broadcastReceiver, intentFilter);
                Intent intent=new Intent();
              // localBroadcastManager.sendBroadcast(intent);
               //sendBroadcast(new Intent(BroadcastReceiverActivity.this,CustomBroadcastReceiver.class));
            }
        });
    }

    @Override
    protected void onPause() {
        //localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onPause();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(BroadcastReceiverActivity.this,HomeActivity.class);
       // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        //registerReceiver(broadcastReceiver, intentFilter);
     //   createNotification();
        super.onResume();
    }


    public class SmsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            createNotification();
            editTextViewBroadcast=(EditText)findViewById(R.id.editTextViewBroadcastSms);

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
                    editTextViewBroadcast.setText(messageAddress + "\n" + messageDetails);
                    Toast.makeText(BroadcastReceiverActivity.this, "msg :"+messageAddress+"\n"+messageDetails, Toast.LENGTH_SHORT).show();
                    unregisterReceiver(broadcastReceiver);
                }
            }

        }
    }

}
