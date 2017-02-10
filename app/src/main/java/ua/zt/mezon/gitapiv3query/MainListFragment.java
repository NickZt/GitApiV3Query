package ua.zt.mezon.gitapiv3query;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ua.zt.mezon.gitapiv3query.datacontroller.RestManager;
import ua.zt.mezon.gitapiv3query.presenter.GetPresenter;

/**
 * Created by MezM on 08.02.2017.
 */

public class MainListFragment extends Fragment {
    private static final String EXTRAS_LIST = "repo_list";
    SwipeRefreshLayout mSwipeRefreshLayout;
    //RepositoryItemsAdapter mAdapter;
    ProgressBar mLoading;
    private RecyclerView mRecyclerView;
    private RestManager mManager;
    private GetPresenter mGetPresenter;


    public MainListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v=  inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.main_recycle_view);
        mGetPresenter=GetPresenter.GetPresenterInstanceMainFragment(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setAdapter(mGetPresenter.getmRepositoryItemsAdapter());





        return v;
    }
}
