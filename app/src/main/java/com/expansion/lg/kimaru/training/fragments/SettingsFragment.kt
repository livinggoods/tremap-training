package com.expansion.lg.kimaru.training.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.utils.SessionManagement

/**
 * Created by kimaru on 3/14/18.
 */

class SettingsFragment : Fragment() {
    internal var sessionManagement: SessionManagement
    internal var cloudUrl: TextView
    internal var apiPrefix: TextView
    internal var apiVersion: TextView
    internal var apiSuffix: TextView
    internal var trainingEndpoint: TextView
    internal var trainingDetailsEndpoint: TextView
    internal var trainingDetailsJSONRoot: TextView
    internal var trainingJsonRoot: TextView
    internal var usersEndpoint: TextView
    internal var sessionTopicsEndpoint: TextView
    internal var sessionTopicsJsonRoot: TextView
    internal var traineeStatusJsonRoot: TextView
    internal var traineeStatusApi: TextView
    internal var examResultApi: TextView
    internal var examResultJson: TextView? = null
    internal var attendanceViewJson: TextView


    internal var appCloudUrlView: RelativeLayout
    internal var appApiPrefixView: RelativeLayout
    internal var appApiRelative: RelativeLayout
    internal var appiSuffixRelative: RelativeLayout
    internal var appTrainingRelative: RelativeLayout
    internal var trainingDetailsEndpointRelative: RelativeLayout
    internal var trainingDetailsJSONRootView: RelativeLayout
    internal var trainingJsonRootView: RelativeLayout
    internal var usersEndpointView: RelativeLayout
    internal var sessionTopicsJsonView: RelativeLayout
    internal var sessionTopicsView: RelativeLayout
    internal var traineeStatusApiView: RelativeLayout
    internal var traineeStatusJsonView: RelativeLayout
    internal var examResultApiView: RelativeLayout
    internal var examResultJSONView: RelativeLayout
    internal var attendanceView: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sessionManagement = SessionManagement(context)
        val view = inflater!!.inflate(R.layout.fragment_settings, container, false)

        cloudUrl = view.findViewById<View>(R.id.cloudUrl) as TextView
        apiPrefix = view.findViewById<View>(R.id.apiPrefix) as TextView
        apiVersion = view.findViewById<View>(R.id.apiVersion) as TextView
        apiSuffix = view.findViewById<View>(R.id.apiSuffix) as TextView
        trainingEndpoint = view.findViewById<View>(R.id.trainingEndpoint) as TextView
        trainingDetailsEndpoint = view.findViewById<View>(R.id.trainingDetailsEndpoint) as TextView
        trainingDetailsJSONRoot = view.findViewById<View>(R.id.trainingDetailsJSONRoot) as TextView
        trainingJsonRoot = view.findViewById<View>(R.id.trainingJsonRoot) as TextView
        usersEndpoint = view.findViewById<View>(R.id.usersEndpoint) as TextView
        sessionTopicsEndpoint = view.findViewById<View>(R.id.sessionTopicsEndpoint) as TextView
        sessionTopicsJsonRoot = view.findViewById<View>(R.id.sessionTopicsJsonEndpoint) as TextView
        attendanceViewJson = view.findViewById<View>(R.id.attendanceViewJson) as TextView
        traineeStatusJsonRoot = view.findViewById<View>(R.id.traineeStatusJsonRoot) as TextView
        traineeStatusApi = view.findViewById<View>(R.id.traineeStatusApi) as TextView
        examResultApi = view.findViewById<View>(R.id.examResultApi) as TextView

        // Relative Views to Allow Clicking
        appCloudUrlView = view.findViewById<View>(R.id.appCloudUrlView) as RelativeLayout
        appApiPrefixView = view.findViewById<View>(R.id.appApiPrefixView) as RelativeLayout
        appApiRelative = view.findViewById<View>(R.id.appApiRelative) as RelativeLayout
        appiSuffixRelative = view.findViewById<View>(R.id.appiSuffixRelative) as RelativeLayout
        appTrainingRelative = view.findViewById<View>(R.id.appTrainingRelative) as RelativeLayout
        trainingDetailsEndpointRelative = view.findViewById<View>(R.id.trainingDetailsEndpointRelative) as RelativeLayout
        trainingDetailsJSONRootView = view.findViewById<View>(R.id.trainingDetailsJSONRootView) as RelativeLayout
        trainingJsonRootView = view.findViewById<View>(R.id.trainingJsonRootView) as RelativeLayout
        usersEndpointView = view.findViewById<View>(R.id.usersEndpointView) as RelativeLayout
        sessionTopicsJsonView = view.findViewById<View>(R.id.usersSessionTopicsJsonView) as RelativeLayout
        sessionTopicsView = view.findViewById<View>(R.id.usersSessionTopicsView) as RelativeLayout
        traineeStatusApiView = view.findViewById<View>(R.id.traineeSessionApiView) as RelativeLayout
        traineeStatusJsonView = view.findViewById<View>(R.id.traineeSessionJsonView) as RelativeLayout
        attendanceView = view.findViewById<View>(R.id.attendanceView) as RelativeLayout

        examResultApiView = view.findViewById<View>(R.id.examResultApiView) as RelativeLayout
        examResultJSONView = view.findViewById<View>(R.id.examResultJSONView) as RelativeLayout


        //Set Default values
        cloudUrl.text = sessionManagement.cloudUrl
        apiPrefix.text = sessionManagement.apiPrefix
        apiVersion.text = sessionManagement.apiVersion
        apiSuffix.text = sessionManagement.apiSuffix
        trainingEndpoint.text = sessionManagement.trainingEndpoint
        trainingDetailsEndpoint.text = sessionManagement.trainingDetailsEndpoint
        trainingDetailsJSONRoot.text = sessionManagement.trainingDetailsJsonRoot
        trainingJsonRoot.text = sessionManagement.trainingJSONRoot
        usersEndpoint.text = sessionManagement.usersEndpoint


        //Set the Popups
        appApiRelative.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("API Version")
            val userText = EditText(context)
            userText.hint = "API Version"
            userText.setText(sessionManagement.apiVersion)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val trainingDetailsJsonRootText = userText.text.toString()
                if (trainingDetailsJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveApiVersion(trainingDetailsJsonRootText)
                    apiSuffix.text = sessionManagement.apiVersion
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }




        appiSuffixRelative.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("API Suffix")
            val userText = EditText(context)
            userText.hint = "API Suffix"
            userText.setText(sessionManagement.apiSuffix)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val trainingDetailsJsonRootText = userText.text.toString()
                if (trainingDetailsJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveApiSuffix(trainingDetailsJsonRootText)
                    apiSuffix.text = sessionManagement.apiSuffix
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        appTrainingRelative.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Training Endpoint")
            val userText = EditText(context)
            userText.hint = "Training Endpoint"
            userText.setText(sessionManagement.trainingEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val trainingDetailsJsonRootText = userText.text.toString()
                if (trainingDetailsJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingEndpoint(trainingDetailsJsonRootText)
                    trainingEndpoint.text = sessionManagement.trainingEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        trainingDetailsEndpointRelative.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Training Details Endpoint")
            val userText = EditText(context)
            userText.hint = "Training Details Endpoint"
            userText.setText(sessionManagement.trainingDetailsEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val trainingDetailsJsonRootText = userText.text.toString()
                if (trainingDetailsJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingDetailsEndpoint(trainingDetailsJsonRootText)
                    trainingDetailsEndpoint.text = sessionManagement.trainingDetailsEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        trainingDetailsJSONRootView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Training Details JSON Root")
            val userText = EditText(context)
            userText.hint = "Training Details JSON Root"
            userText.setText(sessionManagement.trainingDetailsJsonRoot)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val trainingDetailsJsonRootText = userText.text.toString()
                if (trainingDetailsJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingDetailsJsonRoot(trainingDetailsJsonRootText)
                    trainingDetailsJSONRoot.text = sessionManagement.trainingDetailsJsonRoot
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        trainingJsonRootView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Training JSON Root")
            val userText = EditText(context)
            userText.hint = "Training JSON Root"
            userText.setText(sessionManagement.trainingJSONRoot)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val traiingJsonRootText = userText.text.toString()
                if (traiingJsonRootText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingJSONRoot(traiingJsonRootText)
                    trainingJsonRoot.text = sessionManagement.trainingJSONRoot
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        usersEndpointView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Users Endpoint")
            val userText = EditText(context)
            userText.hint = "Users endpoint"
            userText.setText(sessionManagement.usersEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val userTextText = userText.text.toString()
                if (userTextText.trim { it <= ' ' } != "") {
                    sessionManagement.saveUsersEndpoint(userTextText)
                    usersEndpoint.text = sessionManagement.usersEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        appCloudUrlView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Cloud URL")
            val urlText = EditText(context)
            urlText.hint = "Url to the Cloud"
            urlText.setText(sessionManagement.cloudUrl)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(urlText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val cloudUrlText = urlText.text.toString()
                //holder.iconText.setText(registration.getName().substring(0,1));

                if (cloudUrlText.trim { it <= ' ' } != "") {
                    sessionManagement.saveCloudUrl(cloudUrlText)
                    cloudUrl.text = sessionManagement.cloudUrl
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        appApiPrefixView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("API Prefix")
            val prefixText = EditText(context)
            prefixText.hint = "API Prefix"
            prefixText.setText(sessionManagement.apiPrefix)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(prefixText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val apiPrefixText = prefixText.text.toString()
                if (apiPrefixText.trim { it <= ' ' } != "") {
                    sessionManagement.saveApiPrefix(apiPrefixText)
                    apiPrefix.text = sessionManagement.apiPrefix
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        sessionTopicsView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Session Topic Endpoint")
            val userText = EditText(context)
            userText.hint = "endpoint"
            userText.setText(sessionManagement.apiVersion)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveSessionTopicsEndpoint(enteredText)
                    sessionTopicsEndpoint.text = sessionManagement.sessionTopicsEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        sessionTopicsJsonView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("API Version")
            val userText = EditText(context)
            userText.hint = "API Version"
            userText.setText(sessionManagement.apiVersion)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveSessionTopicsJsonRoot(enteredText)
                    sessionTopicsJsonRoot.text = sessionManagement.sessionTopicsJsonRoot
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        //Added JSONroot for traineeStatus
        traineeStatusJsonView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Trainee Status")
            val userText = EditText(context)
            userText.hint = "JSON Root"
            userText.setText(sessionManagement.traineeStatusJSONRoot)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTraineeStatusJsonRoot(enteredText)
                    traineeStatusJsonRoot.text = sessionManagement.traineeStatusJSONRoot
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        //Added JSONroot for traineeStatus
        traineeStatusApiView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Trainee Status")
            val userText = EditText(context)
            userText.hint = "Api Endpoint"
            userText.setText(sessionManagement.traineeStatusEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTraineeStatusEndpoint(enteredText)
                    traineeStatusApi.text = sessionManagement.traineeStatusEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        attendanceView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Attendance API")
            val userText = EditText(context)
            userText.hint = "Api Endpoint"
            userText.setText(sessionManagement.uploadSessionAttendanceEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.uploadSessionAttendanceEndpoint(enteredText)
                    attendanceViewJson.text = sessionManagement.uploadSessionAttendanceEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }


        examResultApiView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Exam Result Endpoint")
            val userText = EditText(context)
            userText.hint = "Api Endpoint"
            userText.setText(sessionManagement.trainingExamResultsEndpoint)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingExamResultsEndpoint(enteredText)
                    examResultApi.text = sessionManagement.trainingExamResultsEndpoint
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }


        examResultJSONView.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("JSON Root")
            val userText = EditText(context)
            userText.hint = "Json Root"
            userText.setText(sessionManagement.trainingExamResultsJSONRoot)
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            layout.addView(userText)
            builder.setView(layout)
            builder.setPositiveButton("Save") { dialogInterface, i ->
                val enteredText = userText.text.toString()
                if (enteredText.trim { it <= ' ' } != "") {
                    sessionManagement.saveTrainingExamResultsJSONRoot(enteredText)
                    examResultJson!!.text = sessionManagement.trainingExamResultsJSONRoot
                }
            }
            builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
            builder.show()
        }

        return view
    }

    companion object {

        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
