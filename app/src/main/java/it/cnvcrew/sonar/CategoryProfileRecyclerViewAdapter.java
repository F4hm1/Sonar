package it.cnvcrew.sonar;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by electrocamel on 20/06/16.
 */
public class CategoryProfileRecyclerViewAdapter extends RecyclerView.Adapter<CategoryProfileRecyclerViewAdapter.MyViewHolder> {
    private Category[] categories;
    private Context context;
    private TextView tv_nome;
    private LinearLayout ll_interests;
    private RelativeLayout rl_card;
    private View view;

    public CategoryProfileRecyclerViewAdapter(Category[] categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card_element, parent, false);
        this.view = itemView;
        tv_nome = (TextView) itemView.findViewById(R.id.tv_interest);
        ll_interests = (LinearLayout) itemView.findViewById(R.id.ll_interest);
        rl_card = (RelativeLayout) itemView.findViewById(R.id.rl_card_element);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = categories[position];
        Log.i("tv_nome",Integer.toString(tv_nome.getId()));
        Log.i("title",Integer.toString(holder.title.getId()));
        tv_nome.setText(String.valueOf(category.getName()));
        ProfileInterestListViewAdapter interestAdapter = new ProfileInterestListViewAdapter(context, category.getInterestsArray());
        for (int entry = 0; entry < interestAdapter.getCount(); entry++) {
            ll_interests.addView(interestAdapter.getView(entry, null, ll_interests));
            Log.i("adapter interest", interestAdapter.getInterest(entry).toString());
        }

    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ListView lvInterests;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tv_interest);
        }
    }
}
