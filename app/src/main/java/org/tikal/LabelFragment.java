package org.tikal;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;

import org.tikal.bus.BusProvider;
import org.tikal.bus.StartCommand;
import org.tikal.bus.StopCommand;


public class LabelFragment extends Fragment implements View.OnClickListener {
    private Bus uiBus, serviceBus;
    private Button startButton, stopButton;
    private TextView label1, label2, label3;

    public LabelFragment() {
        uiBus = BusProvider.getUiBusInstance();
        serviceBus = BusProvider.getServiceBusInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_label, container, false);
        startButton = (Button) view.findViewById(R.id.start_service);
        stopButton = (Button) view.findViewById(R.id.stop_service);
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        label1 = (TextView) view.findViewById(R.id.label1);
        label2 = (TextView) view.findViewById(R.id.label2);
        label3 = (TextView) view.findViewById(R.id.label3);
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        uiBus.register(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        uiBus.unregister(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_service){
            serviceBus.post(new StartCommand());
        } else if(id == R.id.stop_service){
            serviceBus.post(new StopCommand());
        }
    }
}
