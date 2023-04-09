package cards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModel> cards;

    public void setData(List<CardModel> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardModel card = cards.get(position);
        if (card == null) return;
        holder.imgCard.setImageResource(card.getResourceID());
        holder.titleCard.setText(card.getTitle());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgCard;
        private TextView titleCard;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCard = itemView.findViewById(R.id.img_card);
            titleCard = titleCard.findViewById(R.id.title_card);
        }
    }
}
