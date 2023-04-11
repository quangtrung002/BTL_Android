package albums;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.AudioModel;
import com.example.musicplayer.MusicPlayerActivity;
import com.example.musicplayer.MyMediaPlayer;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ViewHolder> {
    ArrayList<AudioModel> songsList;
    Context context;

    public ListAlbumAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListAlbumAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel songData = songsList.get(position);
        holder.titleTextViewAlbum.setText(songData.getTitle());

        if(MyMediaPlayer.currentIndex==position){
            holder.titleTextViewAlbum.setTextColor(Color.parseColor("#FF0000"));
        }else{
            holder.titleTextViewAlbum.setTextColor(Color.parseColor("#000000"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex = position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextViewAlbum;
        ImageView iconImageViewAlbum;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextViewAlbum = itemView.findViewById(R.id.music_title_text_album);
            iconImageViewAlbum = itemView.findViewById(R.id.icon_view_album);
        }
    }
}
