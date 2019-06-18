package com.example.backbasemobiletest;

import java.util.List;

/**
 * Created by Backbase R&D B.V on 28/06/2018.
 * MVP contract for AboutActivity
 */

public interface About {

    interface Model {
        void getAboutInfo();
    }

    interface Presenter {
        void getAboutInfo();
        void onSuccess(List<AboutInfo> aboutInfo);
        void onFail();
    }

    interface View {
        void initUI();
        void getCities(List<AboutInfo> aboutInfos);
        void showError();
        void showProgress();
        void hideProgress();
    }
}
