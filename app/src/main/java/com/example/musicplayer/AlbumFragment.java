package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;

import albums.AlbumAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlbumFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AlbumFragment() {
        // Required empty public constructor
    }

    public static AlbumFragment newInstance(String param1, String param2) {
        AlbumFragment fragment = new AlbumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView recyclerViewalbum;
    AlbumAdapter albumAdapter;
    ArrayList<AudioModel> songsList = new ArrayList<>();
    String artistName;
    AlbumHelper albumHelper;
    Button addBtn;
    EditText inputSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        addBtn = view.findViewById(R.id.addBtn);
        inputSearch = view.findViewById(R.id.search);

        addBtn.setOnClickListener(v -> handle());

        albumHelper = new AlbumHelper(getContext(), "Album", null, 1);
        albumHelper.queryData("CREATE TABLE IF NOT EXISTS album (id INTEGER PRIMARY KEY AUTOINCREMENT, titleAlbum  VARCHAR(100))");
//
//        for (AudioModel song : songsList) {
//            String artist = song.getArtist();
//            String query = "INSERT INTO album VALUES (null, '" + artist.replace("'", "''") + "')";
//            albumHelper.queryData(query);
//        }
//
//        Cursor albums = albumHelper.getData("SELECT * FROM album");
//        while (albums.moveToNext()) {
//            Log.d("title", albums.getString(1));
//        }

        recyclerViewalbum = view.findViewById(R.id.recycler_view_album);
        LinearLayoutManager lng = new LinearLayoutManager(getActivity());
        lng.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewalbum.setLayoutManager(new LinearLayoutManager(getContext()));
        albumAdapter = new AlbumAdapter(getContext(), songsList);
        recyclerViewalbum.setAdapter(albumAdapter);
//        // Lấy tên ca sĩ từ Media Store
//        artistName = getArtistName();
//        // Lấy danh sách bài hát từ Media Store
//        getSongList();

        return view;
    }

    void handle(){
        String valueInput = inputSearch.getText().toString();
        String query = "INSERT INTO album VALUES(null, '" + valueInput + "')";
        Cursor albums = albumHelper.getData("SELECT * FROM album");
        while (albums.moveToNext()) {
            Log.d("title", albums.getString(1));
        }
    }

//    private void getSongList() {
//
//        String[] projection = { MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST };
//        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
//                @SuppressLint("Range") String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                AudioModel songData = new AudioModel(artist);
//                //if (new File(songData.getPath()).exists())
//                if(songList.contains(artist)){
//                    continue;
//                }
//                songList.add(songData);
//            }
//            cursor.close();
//        }
//
//
//            //recyclerview
//            recyclerViewalbum.setLayoutManager(new LinearLayoutManager(getActivity()));
//            recyclerViewalbum.setAdapter(new AlbumAdapter(getActivity().getApplicationContext(), songList));
//
//    }

//    @SuppressLint("Range")
//    private String getArtistName() {
//        String artistName = "";
//        String[] projection = { MediaStore.Audio.Media.ARTIST };
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=0";
//        String sortOrder = MediaStore.Audio.Media.ARTIST + " ASC";
//
//        Cursor cursor = getActivity().getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                projection,
//                selection,
//                null,
//                sortOrder
//        );
//
//        if (cursor != null && cursor.moveToFirst()) {
//            artistName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
//        }
//
//        if (cursor != null) {
//            cursor.close();
//        }
//
//        return artistName;
//    }

    public ArrayList getAllSongs() {
        ArrayList<AudioModel> songList = new ArrayList<>();

        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            String title = cursor.getString(1);
            String duration = cursor.getString(2);
            String artist = cursor.getString(3);
            AudioModel songData = new AudioModel(path, title, duration, artist);
            if (new File(songData.getPath()).exists())
                songList.add(songData);
        }
        return songList;
    }
}