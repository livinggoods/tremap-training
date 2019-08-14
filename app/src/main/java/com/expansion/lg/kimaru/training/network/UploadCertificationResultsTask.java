package com.expansion.lg.kimaru.training.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.objs.TrainingExamResult;
import com.expansion.lg.kimaru.training.utils.Constants;
import com.expansion.lg.kimaru.training.utils.SessionManagement;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.body.JSONArrayBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class UploadCertificationResultsTask extends AsyncTask<String, Void, String> {

    SessionManagement session;
    Context context;

    public UploadCertificationResultsTask(Context context) {
        this.context = context;
        session = new SessionManagement(context);
    }

    @Override
    protected String doInBackground(String... strings) {

        String trainingId = strings[0];
        DatabaseHelper db = new DatabaseHelper(context);
        String endpoint = session.getApiUrl() + "training/exam/result/save";
        try {
            JSONArray params = new JSONArray();
            List<TrainingExamResult> results = db.getOfflineTrainingExamResults();
            for (TrainingExamResult result: results) {
                JSONObject param = new JSONObject();
                param.put("training_exam_id", result.getTrainingExamId());
                param.put("trainee_id", result.getTraineeId());
                param.put("question_id", result.getQuestionId());
                param.put("question_score", result.getQuestionScore());
                param.put("country", result.getCountry());
                param.put("answer", result.getAnswer());
                param.put("choice_id", result.getChoiceId());
                param.put("id", result.getId());

                params.put(param);
            }



            AsyncHttpPost p = new AsyncHttpPost(endpoint);
            Log.e("Tremap", params.toString());
            p.setBody(new JSONArrayBody(params));
            JSONObject ret = AsyncHttpClient.getDefaultInstance().executeJSONObject(p, null).get();
            Log.e("RESULTS : Sync", ret.toString());
            boolean status = ret.getBoolean("status");
            if (status) {
                for (TrainingExamResult result: results) {
                    db.updateSyncStatus(result.getId(), Constants.STATUS_SYNCED);
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return null;
    }
}