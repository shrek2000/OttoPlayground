package org.tikal;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import org.tikal.bus.BusProvider;
import org.tikal.bus.LabelMessage;
import org.tikal.bus.StartCommand;
import org.tikal.bus.StopCommand;


public class LabelFragment extends Fragment implements View.OnClickListener {
    private Bus uiBus, serviceBus;
    private Button startButton;
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

        startButton.setOnClickListener(this);
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

    boolean startMode = true;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.start_service) {
            if (startMode) {
                serviceBus.post(new StartCommand());
                startMode = false;
                startButton.setText("Stop");
            } else {
                serviceBus.post(new StopCommand());
                startMode =  true;
                startButton.setText("Start");
            }
        }
    }

    @Subscribe
    public void handleEvent(LabelMessage labelMessage){
        int target = labelMessage.getTargetNumber();
        if(target == 0 ){
            this.label1.setText(labelMessage.getMessage());
        } else if (target == 1){
            this.label2.setText(labelMessage.getMessage());
        }else if (target == 2){
            this.label3.setText(labelMessage.getMessage());
        }
    }
}
