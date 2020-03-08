package com.niteroomcreation.basemade.ui.act.search;

import com.niteroomcreation.basemade.base.IBasePresenter;
import com.niteroomcreation.basemade.base.IBaseView;
import com.niteroomcreation.basemade.models.FavsObjectItem;

import java.util.List;

/**
 * Created by Septian Adi Wijaya on 09/02/2020.
 * please be sure to add credential if you use people's code
 */
public interface SearchContract {

    interface View extends IBaseView {
        void setData(List<FavsObjectItem> data);
    }

    interface Presenter extends IBasePresenter<View> {

    }
}
