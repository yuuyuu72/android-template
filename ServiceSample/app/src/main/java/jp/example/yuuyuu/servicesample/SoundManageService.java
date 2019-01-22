package jp.example.yuuyuu.servicesample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;

public class SoundManageService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private MediaPlayer _player;

    @Override
    public void onCreate(){
        //フィールドのメディアプレイヤーオブジェクトを生成。
        _player = new MediaPlayer();
        //通知チャンネルのID文字列を用意。
        String id = "soundmanagerservice_notification_channel";
        //通知チャンネル名をstrings.xmlから取得。
        String name = getString(R.string.notification_channel_name);
        //通知チャンネルの重要度を標準に設定。
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        //通知チャンネルを生成。
        NotificationChannel channel = new NotificationChannel(id,name,importance);
        //NotificationManagerオブジェクトを取得。
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //通知チャンネルを設定。
        manager.createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        //音声ファイルのURI文字列を作成。
        String mediaFileUriStr = "android.resource://"+ getPackageName() + "/" + R.raw.morning;
        //音声ファイルのURI文字列をもとにURIオブジェクトを生成。
        Uri mediaFileUri = Uri.parse(mediaFileUriStr);
        try{
            //メディアプレイヤーに音声ファイルを指定。
            _player.setDataSource(SoundManageService.this,mediaFileUri);
            //非同期でのメディア再生準備が完了した際のリスナを設定。
            _player.setOnPreparedListener(new PlayerPreparedListener());
            //メディア再生が終了した際のリスナを設定。
            _player.setOnCompletionListener(new PlayerCompletionListener());
            //非同期でメディア再生を準備。
            _player.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //定数を返す
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //プレイヤーが再生中なら…
        if(_player.isPlaying()){
            //プレイヤーを停止。
            _player.stop();
        }
        //プレイヤーを解放。
        _player.release();
        //プレイヤー用フィールドをnullに。
        _player = null;
    }

    private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {
        @Override
        public void onPrepared(MediaPlayer mp) {
            //メディアを再生。
            mp.start();
        }
    }

    private class PlayerCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(SoundManageService.this,"soundmanagerservice_notification_channel");
            //通知エリアに表示されるアイコンを設定。
            builder.setSmallIcon(android.R.drawable.ic_dialog_info);
            //通知ドロワーでの表示タイトルを設定。
            builder.setContentTitle(getString(R.string.msg_notification_title_finish));
            //通知ドロワーでの表示メッセージを設定。
            builder.setContentText(getString(R.string.msg_notification_text_finish));
            //BuilderからNotificationオブジェクトを生成。
            Notification notification = builder.build();
            //NotificationManagerオブジェクトを取得。
            NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            //通知
            manager.notify(0,notification);
            //自分自身を終了。
            stopSelf();
        }
    }
}