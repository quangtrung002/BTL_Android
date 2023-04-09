package com.example.musicplayer;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cards.CardModel;
import categorys.CategoryAdapter;
import categorys.CategoryModel;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RecyclerView rvSongsTop, rvCategorys;
    CategoryAdapter categoryAdapter;
    ArrayList<AudioModel> songsList = new ArrayList<>();

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvSongsTop = view.findViewById(R.id.rv_songsTop);
        rvCategorys = view.findViewById(R.id.rv_category);
        categoryAdapter = new CategoryAdapter(getActivity().getApplicationContext());

        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.slide1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slide2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slide3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slide4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        String[] projection = {
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ARTIST
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

        while (cursor.moveToNext()) {
            String path = cursor.getString(0);
            String title = cursor.getString(1);
            String duration = cursor.getString(2);
            String artist = cursor.getString(3);

            AudioModel songData = new AudioModel(path, title, duration, artist);
            if (new File(songData.getPath()).exists())
                songsList.add(songData);
        }

        ArrayList<AudioModel> songsTop = new ArrayList<>(songsList.subList(3, 8));

        rvSongsTop.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSongsTop.setAdapter(new MusicListAdapter(songsTop, getActivity().getApplicationContext()));

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//        rvCategorys.setLayoutManager(linearLayoutManager);
//
//        categoryAdapter.setData(getListCategory());
//        rvCategorys.setAdapter(categoryAdapter);
//        List<CategoryModel> categorys = new ArrayList<>(getListCategory());
//        for(int i = 0; i < categorys.size(); i++)
//            Log.d("Category" + i, categorys[i].get)

        return view;
    }

    private List<CategoryModel> getListCategory() {
        List<CategoryModel> categorys = new ArrayList<>();
        List<CardModel> cards = new ArrayList<>();
        cards.add(new CardModel(R.drawable.slide1, "title 1"));
        cards.add(new CardModel(R.drawable.slide2, "title 2"));
        cards.add(new CardModel(R.drawable.slide3, "title 3"));
        cards.add(new CardModel(R.drawable.slide4, "title 4"));

        categorys.add(new CategoryModel("Category 1", cards));

        return categorys;
    }
}