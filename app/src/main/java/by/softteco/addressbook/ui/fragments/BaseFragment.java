package by.softteco.addressbook.ui.fragments;

import android.support.v4.app.Fragment;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kirila on 17.4.17.
 */

public class BaseFragment extends Fragment {

    private CompositeSubscription mCompositeSubscription;

    public void bindObservable(Observable<?> observable, Observer observer) {
        if (mCompositeSubscription == null) mCompositeSubscription = new CompositeSubscription();
        addSubscription(observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer));
    }

    private void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCompositeSubscription == null) return;
        if (!mCompositeSubscription.isUnsubscribed() && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
        }
    }
}
