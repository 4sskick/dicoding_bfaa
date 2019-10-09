package com.niteroomcreation.beginermade.fragment.detail;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.niteroomcreation.beginermade.R;
import com.niteroomcreation.beginermade.base.BaseFragmentView;
import com.niteroomcreation.beginermade.model.TokohModel;
import com.niteroomcreation.beginermade.view.ImageFitView;

import butterknife.BindView;

public class DetailFragment extends BaseFragmentView implements DetailFragmentContract.View {

    private static final String ARGS_KEY = "obj_tokoh_selected";

    @BindView(R.id.img_detail_tokoh)
    ImageFitView imgDetailTokoh;
    @BindView(R.id.txt_detail_name)
    TextView txtDetailName;
    @BindView(R.id.txt_detail_desc)
    TextView txtDetailDesc;

    private TokohModel model;

    public static DetailFragment newInstance(TokohModel model) {
        DetailFragment fragment = new DetailFragment();

        Bundle b = new Bundle();
        b.putSerializable(ARGS_KEY, model);
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    protected int contentLayout() {
        return R.layout.f_detail;
    }

    @Override
    protected void initComponents() {
        if (getArguments() != null)
            model = (TokohModel) getArguments().getSerializable(ARGS_KEY);
        else
            throw new RuntimeException("Model isn't carried by bundle arguments!");

        Glide.with(this)
                .load(model.getImage())
                .into(imgDetailTokoh);

        txtDetailName.setText(model.getName());
        txtDetailDesc.setText(model.getDesc());
    }


}
