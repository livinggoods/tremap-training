package com.expansion.lg.kimaru.training.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.expansion.lg.kimaru.training.R
import com.expansion.lg.kimaru.training.activity.MainActivity
import com.expansion.lg.kimaru.training.utils.SessionManagement

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private val mParam1: String? = null
    private val mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater!!.inflate(R.layout.fragment_home, container, false)
        //Naviagtion
        MainActivity.backFragment = null

        val dashBoardTrainingsButton: Button
        val dashBoardExamsButton: Button
        val dashBoardGraduationsButton: Button
        val dashBoardCertificationsButton: Button
        val dashBoardSettingsButton: Button
        val dashBoardCloudButton: Button
        dashBoardTrainingsButton = v.findViewById(R.id.dashboard_training)
        dashBoardExamsButton = v.findViewById(R.id.dashboard_exams)
        dashBoardGraduationsButton = v.findViewById(R.id.dashboard_graduations)
        dashBoardCertificationsButton = v.findViewById(R.id.dashboard_certifications)
        dashBoardSettingsButton = v.findViewById(R.id.dashboard_settings)
        dashBoardCloudButton = v.findViewById(R.id.dashboard_cloud)
        dashBoardTrainingsButton.setOnClickListener {
            val fragment = TrainingsFragment()
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()// getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }
        dashBoardCloudButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(SessionManagement(context).cloudUrl))
            startActivity(browserIntent)
        }
        dashBoardExamsButton.setOnClickListener { }
        dashBoardGraduationsButton.setOnClickListener { }
        dashBoardCertificationsButton.setOnClickListener { }
        dashBoardSettingsButton.setOnClickListener {
            val fragment = SettingsFragment()
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()// getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            fragmentTransaction.replace(R.id.frame, fragment)
            fragmentTransaction.commitAllowingStateLoss()
        }


        return v
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
