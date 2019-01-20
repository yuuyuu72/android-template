package jp.example.yuuyuu.servicesample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;

import java.io.IOException;
import java.net.URI;

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
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        //音声ファイルのURI文字列を作成。
        String mediaFileUriStr = "android.resource://"+ getPackageName() + "/" + R.raw.sound;
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
            //自分自身を終了。
            stopSelf();
        }
    }
}
