package ua.zt.mezon.gitapiv3query.model.adapter;

/**
 * Created by MezM on 08.02.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ua.zt.mezon.gitapiv3query.R;
import ua.zt.mezon.gitapiv3query.model.pojo.RepositoryItem;

public class RepositoryItemsAdapter extends RecyclerView.Adapter<RepositoryItemsAdapter.Holder> {

    private static final String TAG = RepositoryItemsAdapter.class.getSimpleName();
    private final RepositoryItemClickListener mListener;
    private List<RepositoryItem> mRepositoryItems;

    public RepositoryItemsAdapter(RepositoryItemClickListener listener) {
        mRepositoryItems = new ArrayList<>();
        mListener = listener;
    }



    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_f_item_layout, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        RepositoryItem currRepositoryItem = mRepositoryItems.get(position);

        holder.mName.setText(currRepositoryItem.getName());
        holder.mForks.setText( currRepositoryItem.getForksCount());
        holder.mDescription.setText( currRepositoryItem.getForksCount());

            Picasso.with(holder.itemView.getContext()).load(currRepositoryItem.getOwner().getAvatarUrl()).into(holder.mAva);

    }

    @Override
    public int getItemCount() {
        return mRepositoryItems.size();
    }

    public void addRepositoryItem(RepositoryItem repoi) {
        mRepositoryItems.add(repoi);
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @return
     */
    public RepositoryItem getSelectedRepositoryItem(int position) {
        return mRepositoryItems.get(position);
    }

    public void reset() {
        mRepositoryItems.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mAva;
        private TextView mName, mForks,mDescription;

        public Holder(View itemView) {
            super(itemView);
            mAva = (ImageView) itemView.findViewById(R.id.ava);
            mName = (TextView) itemView.findViewById(R.id.repo_name);
            mForks = (TextView) itemView.findViewById(R.id.forks);
             mDescription = (TextView) itemView.findViewById(R.id.rdescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface RepositoryItemClickListener {

        void onClick(int position);
    }
}
