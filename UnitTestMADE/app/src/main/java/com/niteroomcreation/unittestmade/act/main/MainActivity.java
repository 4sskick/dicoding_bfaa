package com.niteroomcreation.unittestmade.act.main;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.niteroomcreation.unittestmade.R;
import com.niteroomcreation.unittestmade.base.BaseView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseView implements MainContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.edt_width)
    EditText edtWidth;
    @BindView(R.id.edt_height)
    EditText edtHeight;
    @BindView(R.id.edt_length)
    EditText edtLength;

    @BindView(R.id.btn_calculate_volume)
    Button btnCalculateVolume;
    @BindView(R.id.btn_calculate_circumference)
    Button btnCalculateCircumference;
    @BindView(R.id.btn_calculate_surface_area)
    Button btnCalculateSurfaceArea;
    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.tv_result)
    TextView tvResult;

    private MainPresenter presenter;

    @Override
    protected int parentLayout() {
        return 0;
    }

    @Override
    protected int contentLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void initComponents() {
        presenter = new MainPresenter(this);
        gone();
    }

    private void visible() {
        btnCalculateVolume.setVisibility(View.VISIBLE);
        btnCalculateCircumference.setVisibility(View.VISIBLE);
        btnCalculateSurfaceArea.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.GONE);
    }

    private void gone() {
        btnCalculateVolume.setVisibility(View.GONE);
        btnCalculateCircumference.setVisibility(View.GONE);
        btnCalculateSurfaceArea.setVisibility(View.GONE);
        btnSave.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btn_save
            , R.id.btn_calculate_volume
            , R.id.btn_calculate_circumference
            , R.id.btn_calculate_surface_area})
    void onClickedView(View view) {

        String length = edtLength.getText().toString().trim();
        String width = edtWidth.getText().toString().trim();
        String height = edtHeight.getText().toString().trim();

        if (TextUtils.isEmpty(length)) {
            edtLength.setError("Field ini tidak boleh kosong");
            return;
        } else if (TextUtils.isEmpty(width)) {
            edtWidth.setError("Field ini tidak boleh kosong");
            return;
        } else if (TextUtils.isEmpty(height)) {
            edtHeight.setError("Field ini tidak boleh kosong");
            return;
        }

        double l = Double.parseDouble(length);
        double w = Double.parseDouble(width);
        double h = Double.parseDouble(height);

        switch (view.getId()) {
            case R.id.btn_save:
                presenter.save(w, l, h);
                visible();
                break;
            case R.id.btn_calculate_volume:
                tvResult.setText(String.valueOf(presenter.getVolume()));
                gone();
                break;

            case R.id.btn_calculate_circumference:
                tvResult.setText(String.valueOf(presenter.getCircumference()));
                gone();
                break;

            case R.id.btn_calculate_surface_area:
                tvResult.setText(String.valueOf(presenter.getSurfaceArea()));
                gone();
                break;
        }
    }
}
