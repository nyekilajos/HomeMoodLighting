package com.nyekilajos.homemoodligtingclient.ui.bedroom;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContextScopedProvider;
import roboguice.inject.InjectView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.inject.Inject;
import com.nyekilajos.homemoodligtingclient.R;
import com.nyekilajos.homemoodligtingclient.communication.SendMessageTask;

/**
 * Created by Lajos_Nyeki on 11/30/2015.
 */
public class SendSingleDataFragment extends RoboFragment {

    @InjectView(R.id.ip_address)
    private EditText ipAddress;
    @InjectView(R.id.port)
    private EditText port;
    @InjectView(R.id.messege_to_be_sent)
    private EditText message;
    @InjectView(R.id.send_button)
    private Button send;

    @Inject
    private ContextScopedProvider<SendMessageTask> sendMessageTaskProvider;

    private final View.OnClickListener sendClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendMessageTaskProvider.get(getActivity()).send(ipAddress.getText().toString(), Integer.parseInt(port.getText().toString()),
                    message.getText().toString());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_single_data, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        send.setOnClickListener(sendClicked);

    }
}
