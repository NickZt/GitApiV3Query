package ua.zt.mezon.gitapiv3query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ua.zt.mezon.gitapiv3query.presenter.GetPresenter;

/**
 * Created by MezM on 08.02.2017.
 */

public class RepoDetailFragment extends Fragment {

    View v;
    ProgressBar mLoading;
    private GetPresenter mPresenter = GetPresenter.getInstance();
    private RecyclerView mRecyclerView;
    private ImageView ivDetail_ava;
    private TextView twDetail_name, twDetail_subscribers_count;
    private GetPresenter mGetPresenter;


    public RepoDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments().containsKey(Constants.ARG_REPO_ID)) {
//
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_detail, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.detail_recycle_view);
        mGetPresenter = GetPresenter.GetPresenterInstanceRepoDetailFragment(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setAdapter(mGetPresenter.getmRepoDetailsAdapter());
        ivDetail_ava = (ImageView) v.findViewById(R.id.detail_ava);
        twDetail_name = (TextView) v.findViewById(R.id.detail_name);
        twDetail_subscribers_count = (TextView) v.findViewById(R.id.detail_subscribers_count);

        setIternalfields(mGetPresenter.getmRepoDetailsAdapter().getmRepoDetailFragment_urlto(),
                mGetPresenter.getmRepoDetailsAdapter().getmRepoDetailFragment_detail_name(),
                mGetPresenter.getmRepoDetailsAdapter().getmRepoDetailFragment_subscribers_count());
        return v;
    }

    private void setIternalfields(String url, String detail_name, int subscribers_count) {


        Picasso.with(v.getContext()).load(url).into(ivDetail_ava);

        twDetail_name.setText(detail_name);

        twDetail_subscribers_count.setText(Integer.toString(subscribers_count));
    }
}
