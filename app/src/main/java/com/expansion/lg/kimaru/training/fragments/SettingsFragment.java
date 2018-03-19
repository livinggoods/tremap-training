package com.expansion.lg.kimaru.training.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

/**
 * Created by kimaru on 3/14/18.
 */

public class SettingsFragment extends Fragment {
    SessionManagement sessionManagement;
    TextView cloudUrl, apiPrefix, apiVersion, apiSuffix, trainingEndpoint, trainingDetailsEndpoint,
            trainingDetailsJSONRoot, trainingJsonRoot, usersEndpoint, sessionTopicsEndpoint, sessionTopicsJsonRoot;
    RelativeLayout appCloudUrlView, appApiPrefixView,appApiRelative, appiSuffixRelative, appTrainingRelative,
            trainingDetailsEndpointRelative, trainingDetailsJSONRootView, trainingJsonRootView,
            usersEndpointView, sessionTopicsJsonView,sessionTopicsView;

    public SettingsFragment(){}

    public static SettingsFragment newInstance(){
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        sessionManagement = new SessionManagement(getContext());
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        cloudUrl = (TextView) view.findViewById(R.id.cloudUrl);
        apiPrefix = (TextView) view.findViewById(R.id.apiPrefix);
        apiVersion = (TextView) view.findViewById(R.id.apiVersion);
        apiSuffix = (TextView) view.findViewById(R.id.apiSuffix);
        trainingEndpoint = (TextView) view.findViewById(R.id.trainingEndpoint);
        trainingDetailsEndpoint = (TextView) view.findViewById(R.id.trainingDetailsEndpoint);
        trainingDetailsJSONRoot = (TextView) view.findViewById(R.id.trainingDetailsJSONRoot);
        trainingJsonRoot = (TextView) view.findViewById(R.id.trainingJsonRoot);
        usersEndpoint = (TextView) view.findViewById(R.id.usersEndpoint);
        sessionTopicsEndpoint = (TextView) view.findViewById(R.id.sessionTopicsEndpoint);
        sessionTopicsJsonRoot = (TextView) view.findViewById(R.id.sessionTopicsJsonEndpoint);

        // Relative Views to Allow Clicking
        appCloudUrlView = (RelativeLayout) view.findViewById(R.id.appCloudUrlView);
        appApiPrefixView = (RelativeLayout) view.findViewById(R.id.appApiPrefixView);
        appApiRelative = (RelativeLayout) view.findViewById(R.id.appApiRelative);
        appiSuffixRelative = (RelativeLayout) view.findViewById(R.id.appiSuffixRelative);
        appTrainingRelative = (RelativeLayout) view.findViewById(R.id.appTrainingRelative);
        trainingDetailsEndpointRelative = (RelativeLayout) view.findViewById(R.id.trainingDetailsEndpointRelative);
        trainingDetailsJSONRootView = (RelativeLayout) view.findViewById(R.id.trainingDetailsJSONRootView);
        trainingJsonRootView = (RelativeLayout) view.findViewById(R.id.trainingJsonRootView);
        usersEndpointView = (RelativeLayout) view.findViewById(R.id.usersEndpointView);
        sessionTopicsJsonView = (RelativeLayout) view.findViewById(R.id.usersSessionTopicsJsonView);
        sessionTopicsView = (RelativeLayout) view.findViewById(R.id.usersSessionTopicsView);



        //Set Default values
        cloudUrl.setText(sessionManagement.getCloudUrl());
        apiPrefix.setText(sessionManagement.getApiPrefix());
        apiVersion.setText(sessionManagement.getApiVersion());
        apiSuffix.setText(sessionManagement.getApiSuffix());
        trainingEndpoint.setText(sessionManagement.getTrainingEndpoint());
        trainingDetailsEndpoint.setText(sessionManagement.getTrainingDetailsEndpoint());
        trainingDetailsJSONRoot.setText(sessionManagement.getTrainingDetailsJsonRoot());
        trainingJsonRoot.setText(sessionManagement.getTrainingJSONRoot());
        usersEndpoint.setText(sessionManagement.getUsersEndpoint());


        //Set the Popups
        appApiRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("API Version");
                final EditText userText = new EditText(getContext());
                userText.setHint("API Version");
                userText.setText(sessionManagement.getApiVersion());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trainingDetailsJsonRootText = userText.getText().toString();
                        if (!trainingDetailsJsonRootText.trim().equals("")){
                            sessionManagement.saveApiVersion(trainingDetailsJsonRootText);
                            apiSuffix.setText(sessionManagement.getApiVersion());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });




        appiSuffixRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("API Suffix");
                final EditText userText = new EditText(getContext());
                userText.setHint("API Suffix");
                userText.setText(sessionManagement.getApiSuffix());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trainingDetailsJsonRootText = userText.getText().toString();
                        if (!trainingDetailsJsonRootText.trim().equals("")){
                            sessionManagement.saveApiSuffix(trainingDetailsJsonRootText);
                            apiSuffix.setText(sessionManagement.getApiSuffix());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        appTrainingRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Training Endpoint");
                final EditText userText = new EditText(getContext());
                userText.setHint("Training Endpoint");
                userText.setText(sessionManagement.getTrainingEndpoint());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trainingDetailsJsonRootText = userText.getText().toString();
                        if (!trainingDetailsJsonRootText.trim().equals("")){
                            sessionManagement.saveTrainingEndpoint(trainingDetailsJsonRootText);
                            trainingEndpoint.setText(sessionManagement.getTrainingEndpoint());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        trainingDetailsEndpointRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Training Details Endpoint");
                final EditText userText = new EditText(getContext());
                userText.setHint("Training Details Endpoint");
                userText.setText(sessionManagement.getTrainingDetailsEndpoint());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trainingDetailsJsonRootText = userText.getText().toString();
                        if (!trainingDetailsJsonRootText.trim().equals("")){
                            sessionManagement.saveTrainingDetailsEndpoint(trainingDetailsJsonRootText);
                            trainingDetailsEndpoint.setText(sessionManagement.getTrainingDetailsEndpoint());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        trainingDetailsJSONRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Training Details JSON Root");
                final EditText userText = new EditText(getContext());
                userText.setHint("Training Details JSON Root");
                userText.setText(sessionManagement.getTrainingDetailsJsonRoot());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String trainingDetailsJsonRootText = userText.getText().toString();
                        if (!trainingDetailsJsonRootText.trim().equals("")){
                            sessionManagement.saveTrainingDetailsJsonRoot(trainingDetailsJsonRootText);
                            trainingDetailsJSONRoot.setText(sessionManagement.getTrainingDetailsJsonRoot());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        trainingJsonRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Training JSON Root");
                final EditText userText = new EditText(getContext());
                userText.setHint("Training JSON Root");
                userText.setText(sessionManagement.getTrainingJSONRoot());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String traiingJsonRootText = userText.getText().toString();
                        if (!traiingJsonRootText.trim().equals("")){
                            sessionManagement.saveTrainingJSONRoot(traiingJsonRootText);
                            trainingJsonRoot.setText(sessionManagement.getTrainingJSONRoot());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        usersEndpointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Users Endpoint");
                final EditText userText = new EditText(getContext());
                userText.setHint("Users endpoint");
                userText.setText(sessionManagement.getUsersEndpoint());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String userTextText = userText.getText().toString();
                        if (!userTextText.trim().equals("")){
                            sessionManagement.saveUsersEndpoint(userTextText);
                            usersEndpoint.setText(sessionManagement.getUsersEndpoint());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        appCloudUrlView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cloud URL");
                final EditText urlText = new EditText(getContext());
                urlText.setHint("Url to the Cloud");
                urlText.setText(sessionManagement.getCloudUrl());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(urlText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String cloudUrlText = urlText.getText().toString();
                        //holder.iconText.setText(registration.getName().substring(0,1));

                        if (!cloudUrlText.trim().equals("")){
                            sessionManagement.saveCloudUrl(cloudUrlText);
                            cloudUrl.setText(sessionManagement.getCloudUrl());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        appApiPrefixView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("API Prefix");
                final EditText prefixText = new EditText(getContext());
                prefixText.setHint("API Prefix");
                prefixText.setText(sessionManagement.getApiPrefix());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(prefixText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String apiPrefixText = prefixText.getText().toString();
                        if (!apiPrefixText.trim().equals("")){
                            sessionManagement.saveApiPrefix(apiPrefixText);
                            apiPrefix.setText(sessionManagement.getApiPrefix());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        sessionTopicsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Session Topic Endpoint");
                final EditText userText = new EditText(getContext());
                userText.setHint("endpoint");
                userText.setText(sessionManagement.getApiVersion());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String enteredText = userText.getText().toString();
                        if (!enteredText.trim().equals("")){
                            sessionManagement.saveSessionTopicsEndpoint(enteredText);
                            sessionTopicsEndpoint.setText(sessionManagement.getSessionTopicsEndpoint());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        sessionTopicsJsonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("API Version");
                final EditText userText = new EditText(getContext());
                userText.setHint("API Version");
                userText.setText(sessionManagement.getApiVersion());
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(userText);
                builder.setView(layout);
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String enteredText = userText.getText().toString();
                        if (!enteredText.trim().equals("")){
                            sessionManagement.saveSessionTopicsJsonRoot(enteredText);
                            sessionTopicsJsonRoot.setText(sessionManagement.getSessionTopicsJsonRoot());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        return view;
    }
}
