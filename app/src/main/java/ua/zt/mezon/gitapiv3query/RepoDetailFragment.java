package ua.zt.mezon.gitapiv3query;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import ua.zt.mezon.gitapiv3query.model.helper.Constants;
import ua.zt.mezon.gitapiv3query.presenter.GetPresenter;

/**
 * Created by MezM on 08.02.2017.
 */

public class RepoDetailFragment extends Fragment {

    private GetPresenter mPresenter=GetPresenter.getInstance();

    ProgressBar mLoading;


    public RepoDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(Constants.ARG_REPO_ID)) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v=  inflater.inflate(R.layout.fragment_detail, container, false);







        return v;
    }
}
