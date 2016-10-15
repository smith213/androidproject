package com.freephone.justfofun.freephone.inject;

import android.content.Context;
import android.support.v4.app.DialogFragment;

import com.freephone.justfofun.freephone.inject.component.DaggerFragmentComponent;
import com.freephone.justfofun.freephone.inject.component.FragmentComponent;
import com.freephone.justfofun.freephone.inject.module.FragmentModule;


/**
 * Created by imorn on 16/5/19.
 */
public abstract class InjectDialogFragment extends DialogFragment {

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