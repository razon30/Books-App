package razon.nctbteachersguide.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import razon.nctbteachersguide.R;
import razon.nctbteachersguide.model.ClassModel;
import razon.nctbteachersguide.utils.MyTextView;

/**
 * Created by razon30 on 28-07-17.
 */

public class AdapterHomeRecycler extends RecyclerView.Adapter<AdapterHomeRecycler.HomeViewholder> {

    ArrayList<ClassModel> classList;
    Context context;
    LayoutInflater inflater;

    public AdapterHomeRecycler(ArrayList<ClassModel> classList, Context context) {
        this.classList = classList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public HomeViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeViewholder(inflater.inflate(R.layout.item_home_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(HomeViewholder holder, int position) {

        if (position == classList.size()-1){
            holder.tvClass.setTextSize(19f);
        }
        holder.tvClass.setText(classList.get(position).getCls());

    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    class HomeViewholder extends RecyclerView.ViewHolder {

        MyTextView tvClass;

        public HomeViewholder(View itemView) {
            super(itemView);

            tvClass = (MyTextView) itemView.findViewById(R.id.tvClass);

        }
    }

}
