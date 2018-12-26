package ir.artapps.aroundme.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import ir.artapps.aroundme.R;

public class BaseDialogFragment extends DialogFragment implements FragmentManager.OnBackStackChangedListener {

    public static final String TABLET_MODE_KEY = "tabletModeKey";

    interface OnDismissListener {
        void onBaseDialogDismissed();
    }

    private boolean tabletMode = true;

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    private OnDismissListener onDismissListener;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TABLET_MODE_KEY, tabletMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            tabletMode = savedInstanceState.getBoolean(TABLET_MODE_KEY);
        }

        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.AppTheme);

        getFragmentManager().addOnBackStackChangedListener(this);
    }

    /*
     * handle backPress in dialogFragment
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {

                if (!handleDialogBack()) {
                    super.onBackPressed();
                }
            }
        };

        return dialog;
    }

    protected boolean handleDialogBack() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack();
            return true;
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDismissListener) {
            onDismissListener = (OnDismissListener) context;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {

        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onBaseDialogDismissed();
        }
    }

    @Override
    public void onBackStackChanged() {

    }
}
