package br.com.rsicarelli.supportlibraryexample.presentation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;

import br.com.rsicarelli.supportlibraryexample.R;

/**
 * Created by rodrigosicarelli on 2/28/16.
 */
public class WonderPlaceDetailDialog extends BottomSheetDialogFragment {

    public static final String ARG_MESSAGE = "message";

    private CharSequence message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        message = args.getCharSequence(ARG_MESSAGE);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.dialog_bottom_sheet);

        TextView tvMessage = (TextView) dialog.findViewById(R.id.message);
        tvMessage.setText(message);
        return dialog;
    }

    public static class Builder {
        private Context context;
        private CharSequence message;

        public Builder(Context context) {
            this.context = context.getApplicationContext();
        }

        public Builder setMessage(int messageId) {
            this.message = context.getText(messageId);
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public WonderPlaceDetailDialog build() {
            WonderPlaceDetailDialog fragment = new WonderPlaceDetailDialog();
            Bundle args = new Bundle();
            args.putCharSequence(ARG_MESSAGE, message);
            fragment.setArguments(args);
            return fragment;
        }
    }


}
