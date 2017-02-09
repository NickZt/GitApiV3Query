package ua.zt.mezon.gitapiv3query;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.zt.mezon.gitapiv3query.model.adapter.RepositoryItemsAdapter;

import static android.R.id.list;

/**
 * Created by MezM on 08.02.2017.
 */

public class MainListFragment extends Fragment {
    private static final String EXTRAS_LIST = "repo_list";
    SwipeRefreshLayout mSwipeRefreshLayout;
    RepositoryItemsAdapter mAdapter;


    public MainListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v=  inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new RepositoryItemsAdapter(this.getActivity(), list, mIClickItem);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setAdapter(mAdapter);





        return v;
    }
}
