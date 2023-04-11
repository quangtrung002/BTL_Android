package albums;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicplayer.AudioModel;
import com.example.musicplayer.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>{

    Context context;
    ArrayList<AudioModel> mangalbum;

    public AlbumAdapter(Context context, ArrayList<AudioModel> mangalbum) {
        this.context = context;
        this.mangalbum = mangalbum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bang_album,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel album = mangalbum.get(position);
        holder.txtcasialbum.setText(album.getArtist());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String casi;
                casi = album.getArtist();
                Intent intent = new Intent(context, ListAlbum.class);
                intent.putExtra("tencasi",casi);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return mangalbum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imghinhalbum;
        TextView txtcasialbum;
        public ViewHolder(View itemView) {
            super(itemView);
            imghinhalbum = itemView.findViewById(R.id.imageviewalbum);
            txtcasialbum = itemView.findViewById(R.id.tencasialbum);
        }

    }

}
