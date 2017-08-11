package razon.nctballbooksfree.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import razon.nctballbooksfree.R;
import razon.nctballbooksfree.model.Book;
import razon.nctballbooksfree.utils.MyTextView;

/**
 * Created by razon30 on 28-07-17.
 */

public class AdapterBookListRecycler extends RecyclerView.Adapter<AdapterBookListRecycler.HomeViewholder> {

    ArrayList<Book> classList;
    Context context;
    LayoutInflater inflater;

    public AdapterBookListRecycler(ArrayList<Book> classList, Context context) {
        this.classList = classList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HomeViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewholder(inflater.inflate(R.layout.item_book_list_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewholder holder, int position) {

        holder.tvClass.setText("Class: "+classList.get(position).getCls());
        holder.tvSub.setText(classList.get(position).getName());
        holder.tvSize.setText("Size: "+classList.get(position).getSize()+"MB");

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class HomeViewholder extends RecyclerView.ViewHolder {

        MyTextView tvClass;
        MyTextView tvSub;
        MyTextView tvSize;

        public HomeViewholder(View itemView) {
            super(itemView);

            tvClass = (MyTextView) itemView.findViewById(R.id.tvClass);
            tvSub = (MyTextView) itemView.findViewById(R.id.tvSub);
            tvSize = (MyTextView) itemView.findViewById(R.id.tvSize);

        }
    }

}
