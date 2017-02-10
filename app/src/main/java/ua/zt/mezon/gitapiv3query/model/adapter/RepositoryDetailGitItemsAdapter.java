package ua.zt.mezon.gitapiv3query.model.adapter;

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
import ua.zt.mezon.gitapiv3query.model.pojo.GitSubscriber;

/**
 * Created by MezM on 10.02.2017.
 */


public class RepositoryDetailGitItemsAdapter extends RecyclerView.Adapter<RepositoryDetailGitItemsAdapter.Holder> {

    private static final String TAG = RepositoryDetailGitItemsAdapter.class.getSimpleName();

    private List<GitSubscriber> mGitSubscribers;

    public RepositoryDetailGitItemsAdapter() {
        mGitSubscribers = new ArrayList<>();

    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_f_item_layout, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        GitSubscriber currGitSubscriber = mGitSubscribers.get(position);

        holder.mName.setText(currGitSubscriber.getLogin());
        holder.mForks.setText(Integer.toString(currGitSubscriber.getId()));//Integer.toString(currGitSubscriber.getForksCount())
        holder.mDescription.setText("");

        Picasso.with(holder.itemView.getContext()).load(currGitSubscriber.getAvatarUrl()).into(holder.mAva);

    }

    @Override
    public int getItemCount() {
        return mGitSubscribers.size();
    }

    public void addGitSubscriberItem(GitSubscriber repoi) {
        mGitSubscribers.add(repoi);
        notifyDataSetChanged();
    }

    /**
     * @param position
     * @return
     */
    public GitSubscriber getSelectedRepositoryItem(int position) {
        return mGitSubscribers.get(position);
    }

    public void reset() {
        mGitSubscribers.clear();
        notifyDataSetChanged();
    }



    public class Holder extends RecyclerView.ViewHolder   {

        private ImageView mAva;
        private TextView mName,
                mForks,
                mDescription;

        public Holder(View itemView) {
            super(itemView);
            mAva = (ImageView) itemView.findViewById(R.id.ava);
            mName = (TextView) itemView.findViewById(R.id.repo_name);
            mForks = (TextView) itemView.findViewById(R.id.forks_in_list);
            mDescription = (TextView) itemView.findViewById(R.id.rdescription_in_list);

        }


    }
}
