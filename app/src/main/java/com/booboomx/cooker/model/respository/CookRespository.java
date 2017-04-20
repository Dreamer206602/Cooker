package com.booboomx.cooker.model.respository;

import com.booboomx.cooker.model.bean.CategorySubscriberResultInfo;
import com.booboomx.cooker.model.bean.SearchCookMenuSubscriberResultInfo;
import com.booboomx.cooker.model.interfaces.ICookRespository;
import com.booboomx.cooker.model.interfaces.ICookService;
import com.booboomx.cooker.model.net.RetrofitService;
import com.booboomx.cooker.utils.Constants;
import com.google.gson.Gson;

import rx.Observable;

/**
 * Created by booboomx on 17/4/20.
 */

public class CookRespository implements ICookRespository {

    private static  CookRespository Instance=null;

    public static CookRespository getInstance(){
        if (Instance == null) {

            Instance=new CookRespository();
        }

        return Instance;
    }

    private Gson gson;

    private CookRespository(){
        gson=new Gson();
    }


    @Override
    public Observable<CategorySubscriberResultInfo> getCategoryQuery() {

        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);

        return iCookService.getCategoryQuery(Constants.Key_MobAPI_Cook);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByID(String cid, int page, int size) {

        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);

        return iCookService.searchCookMenuByID(Constants.Key_MobAPI_Cook,cid,page,size);
    }

    @Override
    public Observable<SearchCookMenuSubscriberResultInfo> searchCookMenuByName(String name, int page, int size) {

        ICookService iCookService = RetrofitService.getInstance().createApi(ICookService.class);

        return iCookService.searchCookMenuByName(Constants.Key_MobAPI_Cook,name,page,size);
    }
}
