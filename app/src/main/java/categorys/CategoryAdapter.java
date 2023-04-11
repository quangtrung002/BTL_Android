package categorys;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;


import java.util.List;

import cards.CardAdapter;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private List<CategoryModel> categorys;
    public CategoryAdapter(Context context){
        this.context = context;
    }

    public void setData(List<CategoryModel> categorys){
        this.categorys = categorys;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);

        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryModel category = categorys.get(position);
        if (category == null) return;
        holder.titleCategory.setText(category.getTitle());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rvCategory.setLayoutManager(linearLayoutManager);

        CardAdapter cardAdapter = new CardAdapter();
        cardAdapter.setData(category.getCards());
        holder.rvCategory.setAdapter(cardAdapter);
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView titleCategory;
        private RecyclerView rvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCategory = itemView.findViewById(R.id.tv_category);
            rvCategory = itemView.findViewById(R.id.recycler_view_category);
        }
    }
}
