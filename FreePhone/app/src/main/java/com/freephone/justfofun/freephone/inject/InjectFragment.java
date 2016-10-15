package com.freephone.justfofun.freephone.inject;

import android.content.Context;

import com.freephone.justfofun.freephone.inject.component.DaggerFragmentComponent;
import com.freephone.justfofun.freephone.inject.component.FragmentComponent;
import com.freephone.justfofun.freephone.inject.module.FragmentModule;


/**
 * Created by imorn on 15/11/24.
 */
public abstract class InjectFragment extends android.support.v4.app.Fragment {

    private FragmentComponent fragmentComponent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (fragmentComponent == null) {
            fragmentComponent = DaggerFragmentComponent.builder()
                    .appComponent(InjectApplication.getAppComponent(context))
                    .fragmentModule(new FragmentModule(this))
                    .build();
            initInject(fragmentComponent);
        }
    }


    protected abstract void initInject(FragmentComponent component);

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
