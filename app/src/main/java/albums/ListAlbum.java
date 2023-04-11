package albums;


import static android.app.PendingIntent.getActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.AudioModel;
import com.example.musicplayer.MusicListAdapter;
import com.example.musicplayer.R;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListAlbum extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView noMusicTextView, artistTextView;
    ArrayList<AudioModel> songsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.artist_album);

        recyclerView = findViewById(R.id.recycler_view);
        noMusicTextView = findViewById(R.id.no_songs_text);
        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
        };


        String selection = MediaStore.Audio.Media.ARTIST + "=?";
        String casi = getIntent().getStringExtra("tencasi");
        artistTextView = findViewById(R.id.tencasialbum);
        artistTextView.setText(casi);
        String[] selectionArgs = new String[]{casi};
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);

        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            String title = cursor.getString(1);
            String duration = cursor.getString(2);
            String artist = cursor.getString(3);
            AudioModel songData = new AudioModel(path, title, duration, artist);
//            if (new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        if (songsList.size() == 0) {
            noMusicTextView.setVisibility(View.VISIBLE);
        } else {
            //recyclerview
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MusicListAdapter(songsList, getApplicationContext()));
        }

    }

}
