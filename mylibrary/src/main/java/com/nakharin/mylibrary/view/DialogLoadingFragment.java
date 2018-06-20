package com.nakharin.mylibrary.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nakharin.mylibrary.R;

import java.util.Objects;

/**
 * Created by nakharin on 8/22/2017 AD.
 */

public class DialogLoadingFragment extends DialogFragment {

    private static final String KEY_CANCELABLE = "KEY_CANCELABLE";
    private static final String KEY_TITLE = "KEY_TITLE";

    private ProgressBar progressBar;
    private TextView tvTitle;

    private String title = "";
    private boolean cancelable = true;

    public static DialogLoadingFragment newInstance(boolean cancelable) {
        DialogLoadingFragment fragment = new DialogLoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_CANCELABLE, cancelable);
        bundle.putString(KEY_TITLE, "Please waiting...");
        fragment.setCancelable(cancelable);
        fragment.setArguments(bundle);
        return fragment;
    }


    public static DialogLoadingFragment newInstance(String title, boolean cancelable) {
        DialogLoadingFragment fragment = new DialogLoadingFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_CANCELABLE, cancelable);
        bundle.putString(KEY_TITLE, title);
        fragment.setCancelable(cancelable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            restoreArguments(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading_dialog, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Method from this class
        bindView(view);
        // Method from this class
        setupView();
    }

    private void bindView(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.loading_dialog_progress_bar);
        tvTitle = (TextView) v.findViewById(R.id.loading_dialog_text_view_title);

    }

    private void setupView() {
        tvTitle.setText(title);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CANCELABLE, cancelable);
        outState.putString(KEY_TITLE, title);
    }

    private void restoreInstanceState(Bundle bundle) {
        cancelable = bundle.getBoolean(KEY_CANCELABLE);
        title = bundle.getString(KEY_TITLE);
    }

    private void restoreArguments(Bundle bundle) {
        cancelable = bundle.getBoolean(KEY_CANCELABLE);
        title = bundle.getString(KEY_TITLE);
    }
}
