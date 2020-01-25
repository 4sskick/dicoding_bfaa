package com.niteroomcreation.basemade.ui.fragment.favourite;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.data.local.entity.MovieEntity;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 25/01/2020.
 * please be sure to add credential if you use people's code
 */
public class FavContract {

    interface View extends IBaseView {
        void setData(List<MovieEntity> data);
    }

    interface Presenter extends IBasePresenter<View> {
        void getFavs(String lang);
    }
}
